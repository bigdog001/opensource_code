#include  "stdafx.h"
#include  "io.h"
#include "MySocket.h"
#include "MySocketAddress.h"
#define MaxBufferSize 1024
#define MaxCGIBufferSize 1024

volatile int nConnectionCount;
volatile bool bServerListenStatus=false;
volatile int nMaxConnectionCount;
volatile UINT nListenPort;


char cHostBasePath[255];
char cHostName[100];
char cDefaultPage[50];
MySocket msServerListenSocket;
struct HttpHeader
{
	char RequestMethod[20];
	char FileName[255];
	char FilePath[255];
	char HttpVersion[10];
	char Host[255];
	char Language[10];
	char QueryString[1000];
	int ContentLength;
	char Parameter[5000];//具体最大值，再看协议的说明了。
	char Referer[255];
	bool IsValid;
};


HttpHeader& ReadHttpHead(const char* pHttpRequest);
char* ExecCGI(char* pFilePath,char* pCurrentPath,char* pCmdLine,char* pOutMsg,const char* pPostParam);
char* GetFileExtName(const char *pFileName);
char* GetResponseHead(int nStatusCode,const char* pStatusDesStr,const char* pFileModifyTime,int nResponseContextSize,char *pRtnVal);
char* GetLocationHead(int nStatusCode,const char* pStatusDesStr,char *pLocationUrl,char* pRtnVal);
CFile* OpenFile(const char *pFileName);
int ReadFile(const char *pFileName,char* pFileContext,int nSize);
void ReadConfigProperty(const char *pCPropertyName,char* pConfigPropertyValue);
UINT ServerListenThread(LPVOID pParam);
UINT HttpCommunicate(LPVOID pParam);
//发送普通报头
void SendHead(MySocket	*msSocket,const int nStatusCode,const char *pStatusDesStr,const char* pFileModifyTime,int nHtmlSize,const char *pErrorDetailMsg);
//发送重定位报头
void SendLocationHead(MySocket	*msSocket,const int nStatusCode,const char *pStatusDesStr,const char *pLocationUrl);
void SendFile(MySocket  *msSocket,CFile* pFile);

//建立临界区变量以处理并发操作
//----连接数的临界区变量
CRITICAL_SECTION cs_nMaxConnectionCount;
//----因为环境变量是全局共享的，所以此处应该处理并发连接
CRITICAL_SECTION cs_EnvVar;

void SendFile(MySocket  *msSocket,CFile* pFile)
{
	int nFileSize=pFile->GetLength();
	//以最大5K的方式发送文件内容
	char* pFileContext=new char[MaxBufferSize];
	int nSizeToBeRead=nFileSize;//还要读的文件大小
	int nReadSize=0;//当前要读的内容大小
	while(nSizeToBeRead>0)
	{	
		nReadSize=min(MaxBufferSize,nSizeToBeRead);
		memset(pFileContext,0,MaxBufferSize);
		pFile->Read(pFileContext,nReadSize);
		nSizeToBeRead-=nReadSize;
		msSocket->ConfirmSend(pFileContext,nReadSize,10);
	}
	delete[] pFileContext;
}

//获得文件扩展名
char* GetFileExtName(const char *pFileName)
{
	//const char *pFileName="abc.asp";
	char *pExt;
	pExt=strrchr(pFileName,'.');
	if(pExt)
		return ++pExt;
	else
		return NULL;
}

 
CFile* OpenFile(const char *pFileName)
{
	CFile* pFile= new CFile();
	CFileException e;
	if(pFile->Open(pFileName,CFile::modeRead,&e))
		return pFile;
	if(e.m_cause==CFileException::accessDenied||e.m_cause==CFileException::badPath)
	{
		delete pFile;
	}
	return NULL;
}

int ReadFile(const char *pFileName,char* pFileContext,int nSize)
{
	int nFileSize=0;
	CFile* pFile=OpenFile(pFileName);
	nFileSize=pFile->GetLength();
	char* buffer=new char[nSize];
	memset(buffer,0,nSize);
	if(nSize<nFileSize)
		pFile->Read(buffer,nSize-1);
	else
		pFile->Read(buffer,nFileSize-1);
	strcpy(pFileContext,buffer);
	pFile->Close();
	delete pFile;
	delete[] buffer;
	return nFileSize;
}


void ReadConfigProperty(const char *pCPropertyName,char* pConfigPropertyValue)
{
	char *pPropertyName=new char[strlen(pCPropertyName)];
	strcpy(pPropertyName,pCPropertyName);
	strlwr(pPropertyName);
	int nContextSize=10;
	int nFileSize=0;
	char* configContext=new char[nContextSize];
	nFileSize=ReadFile("config.ini",configContext,nContextSize);
	strlwr(configContext);
	char *pBegin,*pEnd;
	pBegin=strstr(configContext,pPropertyName);
	if(pBegin)
	{
		pBegin+=strlen(pPropertyName)+1;
		pEnd=strchr(pBegin,'\n');
		if((pEnd-pBegin)>0)
		{
			strncpy(pConfigPropertyValue,pBegin,pEnd-pBegin);
			pConfigPropertyValue[pEnd-pBegin]='\0';
		}
	}
	else
	{
		delete[] configContext;
		configContext=new char[nFileSize];
		nFileSize=ReadFile("config.ini",configContext,nFileSize);
		strlwr(configContext);
		pBegin=strstr(configContext,pPropertyName);
		if(pBegin)
		{
			pBegin+=strlen(pPropertyName)+1;
			pEnd=strchr(pBegin,'\n');
			if((pEnd-pBegin)>0)
			{
				strncpy(pConfigPropertyValue,pBegin,pEnd-pBegin);
				pConfigPropertyValue[pEnd-pBegin]='\0';
			}
		}
	}
	delete[] configContext;
}

char* GetLocationHead(int nStatusCode,const char* pStatusDesStr,const char *pLocationUrl,char* pRtnVal)
{
	char cHeadFmt[]=
		"HTTP/1.1 %d %s\r\n"
		"Server: Braveboy HttpServer\r\n"
		"Date: %s\r\n"
		"Location:%s\r\n"
		"Content-Type: text/html\r\n"
		"Accept-Ranges: bytes\r\n";
	char cHead[500];
	CString strGmtNow=CTime::GetCurrentTime().FormatGmt("%a, %d %b %Y %H:%M:%S GMT");
	wsprintf(cHead,cHeadFmt,nStatusCode,pStatusDesStr,(const char*)strGmtNow,pLocationUrl);
	strcat(cHead,"\r\n");
	strcpy(pRtnVal,cHead);
	return pRtnVal;
}

char* GetResponseHead(int nStatusCode,const char* pStatusDesStr,const char* pFileModifyTime,int nResponseContextSize,char* pRtnVal)
{
	char cHeadFmt[]=
		"HTTP/1.1 %d %s\r\n"
		"Server: Braveboy HttpServer\r\n"
		"Date: %s\r\n"
		"Content-Type: text/html\r\n"
		"Accept-Ranges: bytes\r\n"
		"Content-Length: %d\r\n";
	char cHead[500];
	CString strGmtNow=CTime::GetCurrentTime().FormatGmt("%a, %d %b %Y %H:%M:%S GMT");
	wsprintf(cHead,cHeadFmt,nStatusCode,pStatusDesStr,(const char*)strGmtNow,nResponseContextSize);
	if(!pFileModifyTime)
	{
		char hdrModified[50];
		wsprintf(hdrModified, "Last-Modified: %s\r\n\r\n",pFileModifyTime);
		strcat(cHead,hdrModified);
	}
	strcat(cHead,"\r\n");
	strcpy(pRtnVal,cHead);
	return pRtnVal;
}

void SendLocationHead(MySocket	*msSocket,const int nStatusCode,const char *pStatusDesStr,const char *pLocationUrl)
{
	char *pHead=new char[500];
	GetLocationHead(nStatusCode,pStatusDesStr,pLocationUrl,pHead);
	msSocket->ConfirmSend(pHead,strlen(pHead),10);
	delete[] pHead;
}

void SendHead(MySocket	*msSocket,const int nStatusCode,const char *pStatusDesStr,const char* pFileModifyTime,int nHtmlSize,const char *pErrorDetailMsg)
{
	char *pHead=new char[500];
	if(pErrorDetailMsg!=NULL)
	{
		char cErrorHtml[]="<Html>\n"
						"<Head><Title>HTTP %d (%s)</Title>\n"
						"</Head>\n"
						"<Body>\n"
						"<h1>%s</h1>\n"
						"<h2>错误详细描述：%s</h2>\n"
						"</Body>\n"
						"</Html>";
		char cErrorBody[500];
		wsprintf(cErrorBody,cErrorHtml,nStatusCode,pStatusDesStr,pStatusDesStr,pErrorDetailMsg);
		GetResponseHead(nStatusCode,pStatusDesStr,pFileModifyTime,strlen(cErrorBody),pHead);
		msSocket->ConfirmSend(pHead,strlen(pHead),10);	
		msSocket->ConfirmSend(cErrorBody,strlen(cErrorBody),10);
	}
	else
	{
		
		GetResponseHead(nStatusCode,pStatusDesStr,pFileModifyTime,nHtmlSize,pHead);
		msSocket->ConfirmSend(pHead,strlen(pHead),10);
	}
	delete[] pHead;
}


UINT HttpCommunicate(LPVOID pParam)
{
	MySocket* msAcceptConn=(MySocket *)pParam;
	char *pRequestLine=new char[1000];
	//get the http request head from browner;
	msAcceptConn->Receive(pRequestLine,1000,1000);
	//Anayze the http request head;
	HttpHeader head=ReadHttpHead(pRequestLine);
	//当请求为无效格式的时候，不予理会！
	if(!head.IsValid)
		return 0;
	delete pRequestLine;
	//检查页面及路径是否存
	//如果请求里面没有带具体的页面信息
	if(!(*head.FileName))
	{
		//检查默认页面如果被设置,且默认页面都存在，则发送默认页面,否则发送403错误！
		if(cDefaultPage)
		{
			CString csFileFullPath;
			char* pBegin=strstr(head.Referer,head.Host);
			//如果引用页里面包含完整的文件名，则请求头里面的已经包括该请求的相对路径，
			//否则进行页面重定向
			if(!strchr(head.Referer,'.')&&pBegin)
			{
				//发送重定向请求
				csFileFullPath.Format("%s/%s",head.Referer,head.FileName);
				SendLocationHead(msAcceptConn,300,"Redirect Page",csFileFullPath.GetBuffer(0));
			}
			else if(pBegin)
			{
				csFileFullPath.Format("%s/%s",head.Referer,cDefaultPage);
				SendLocationHead(msAcceptConn,300,"Redirect Page",csFileFullPath.GetBuffer(0));
			}
			else
			{
				if(head.FilePath[1]!='\0')
					csFileFullPath.Format("http://%s%s/%s",head.Host,head.FilePath,cDefaultPage);
				else
					csFileFullPath.Format("http://%s/%s",head.Host,cDefaultPage);
				SendLocationHead(msAcceptConn,300,"Redirect Page",csFileFullPath.GetBuffer(0));
			}
		}
		else
		{
			SendHead(msAcceptConn,403,"无权访问",NULL,NULL,"您无权访问目录列表");//发送403错误！
		}
	}
	else
	{
		//检查要访问的文件是不是存在，不是则发送403错误，否则根据文件的类型不同处理后进行访问
		//考虑到相对目录的应用,所以应该先判断引用面是不是存在。然后文件的物理地址应该
		//是引用的地址再加上文件地址
		CString csFileFullPath;
		char* pBegin=strstr(head.Referer,head.Host);
		if(!strchr(head.Referer,'.')&&pBegin&&head.FilePath[1]=='\0')
		{
			if(strlen(pBegin)>(strlen(head.Host)+1))
			{
				pBegin+=strlen(head.Host)+1;
				if(pBegin)
					csFileFullPath.Format("%s\\%s\\%s",cHostBasePath,pBegin,head.FileName);
			}
			else
				csFileFullPath.Format("%s\\%s\\%s",cHostBasePath,head.FilePath,head.FileName);
		}
		else
		{
			csFileFullPath.Format("%s\\%s\\%s",cHostBasePath,head.FilePath,head.FileName);
		}
		csFileFullPath.Replace("/","\\");
		csFileFullPath.Replace("\\\\","\\");
		if(_access(csFileFullPath.GetBuffer(0),0)==-1)
		{
			SendHead(msAcceptConn,404,"未找到",NULL,NULL,"您要查看的网页可能已被删除、名称已被更改，或者暂时不可用。");//发送403错误
		}
		else
		{
			CFile* pFile=OpenFile(csFileFullPath.GetBuffer(0));
			if(pFile!=NULL)
			{
				//对后缀判断选择不同的方法进行处理
				char *pFileExtName=GetFileExtName(head.FileName);
				if(stricmp(pFileExtName,"cgi")==0||stricmp(pFileExtName,"exe")==0)
				{
					CFileStatus fileStatus;
					pFile->GetStatus(fileStatus);
					CString strGmtMod=fileStatus.m_mtime.FormatGmt("%a, %d %b %Y %H:%M:%S GMT");
					pFile->Close();
					CString csFilePath;
					csFilePath.Format("%s%s",cHostBasePath,head.FilePath);
					csFilePath.Replace("/","\\");
					csFilePath.Replace("\\\\","\\");
					//-----------------准备CGI环境变量,第一次都要对所有的变量进行初始化,读取得脏数据。
					//-----------------同时因为环境变量是全局共享的，所以此处应该处理并发连接
					EnterCriticalSection(&cs_EnvVar);
					CString csPrepareEnv;
					if(head.ContentLength>0)
					{
						csPrepareEnv.Format("%s%d","CONTENT_LENGTH=",head.ContentLength);
						putenv(csPrepareEnv.GetBuffer(0));
					}
					//csPrepareEnv.Format("%s%d","")
					//-----------------准备CGI环境变量结束。
					
					//调用CGI程序并得到运行结果
					//一定要给pOutMsg进行初始化，在break的地方出错。
					char *pOutMsg=new char[5000];
					memset(pOutMsg,0,5000);
					ExecCGI(csFileFullPath.GetBuffer(0),csFilePath.GetBuffer(0),NULL,pOutMsg,head.Parameter);
					LeaveCriticalSection(&cs_EnvVar);
					//分离CGI程序的返回文件类型与正文信息。
					//MINE信息与正文信息之前有一定有一行空白行。
					//所以此处的判断可因为暂时没有看全CGI标准而导致部分程序内容出错。
					char* pRtnValue=strchr(pOutMsg,'\n');
					if(pRtnValue)
					{
						while(*(++pRtnValue)!='\n'&&*pRtnValue);
						//最后其实还是指在\n上。
						if(*(pRtnValue+1))
							pRtnValue++;
					}
					else
						pRtnValue=pOutMsg;
					char *pResponseHead=new char[500];
					GetResponseHead(200,"OK",(const char*)strGmtMod,strlen(pRtnValue),pResponseHead);
					msAcceptConn->ConfirmSend(pResponseHead,strlen(pResponseHead),10);
					delete[] pResponseHead;
					//以最大5K的方式发送文件内容
					char* pFileContext=new char[MaxBufferSize];
					int nSizeToBeRead=strlen(pRtnValue);//还要读的内存大小
					int nReadSize=0;//当前要读的内容大小
					while(nSizeToBeRead>0)
					{	
						char* pTemp=pRtnValue;
						nReadSize=min(MaxBufferSize,nSizeToBeRead);
						memset(pFileContext,0,MaxBufferSize);
						strncpy(pFileContext,pTemp,nReadSize);
						pTemp+=nReadSize;
						nSizeToBeRead-=nReadSize;
						msAcceptConn->ConfirmSend(pFileContext,nReadSize,10);
					}
					delete[] pFileContext;
					delete[] pOutMsg;
				}
				else if(stricmp(pFileExtName,"blsp")==0)
				{
					;//自定义脚本的页面，记得处理完后完毕后关闭文件指针.
				}
				else//直接读文件处理方式html及图像等多媒体文件
				{
					int nFileSize=pFile->GetLength();
					CFileStatus fileStatus;
					pFile->GetStatus(fileStatus);
					CString strGmtMod=fileStatus.m_mtime.FormatGmt("%a, %d %b %Y %H:%M:%S GMT");
					SendHead(msAcceptConn,200,"OK",strGmtMod.GetBuffer(0),nFileSize,NULL);
					SendFile(msAcceptConn,pFile);
					pFile->Close();
				}
			}
			else
			{
				//内部程序处理错误
				pFile->Close();
				SendHead(msAcceptConn,505,"内部错误",NULL,NULL,"内部服务器错误！");
			}
			delete pFile;
}
}
	
	
	
	msAcceptConn->Close();
	//处理并发操作
	//进入临界区变量
	EnterCriticalSection(&cs_nMaxConnectionCount);
	if(nConnectionCount>0)nConnectionCount--;
	LeaveCriticalSection(&cs_nMaxConnectionCount);
	delete msAcceptConn;
	return 0;
}

UINT ServerListenThread(LPVOID pParam)
{
	nConnectionCount=0;
	//初始化临界区变量
	InitializeCriticalSection(&cs_nMaxConnectionCount);
	InitializeCriticalSection(&cs_EnvVar);
	while(true)
	{
		MySocket* msAcceptConn=new MySocket();
		MySocketAddress msaAcceptAddress;
		if(!bServerListenStatus)
			return 0;
		try
		{
			if(msServerListenSocket.Accept(*msAcceptConn,msaAcceptAddress))
			{
				//连接数自加，以控制连接数
				//此处对连接数应该处理并发事件
				EnterCriticalSection(&cs_nMaxConnectionCount);
				if(!((nConnectionCount++)>=nMaxConnectionCount))
					AfxBeginThread(HttpCommunicate,(LPVOID)msAcceptConn,THREAD_PRIORITY_NORMAL);
				LeaveCriticalSection(&cs_nMaxConnectionCount);
			}
		}
		catch(CException ex)
		{
			;
		}
	}
	DeleteCriticalSection(&cs_EnvVar);
	DeleteCriticalSection(&cs_nMaxConnectionCount);
	return 0;
}
HttpHeader& ReadHttpHead(const char* pHttpRequest)
{
	char *pRequest=new char[strlen(pHttpRequest)+1];
	strcpy(pRequest,pHttpRequest);
	strcat(pRequest,"\0");
	//该服务器不区别大小写，统一用小写。
	strlwr(pRequest);
	HttpHeader head;
	char *pBegin=pRequest,*pEnd;
	int nCount=0;
	//防止非法请求或者不合格请求，即前面有若干的空格。
	while(*pBegin==' ')pBegin++;
	//get method;
	pEnd=strchr(pBegin,' ');
	nCount=pEnd-pBegin;
	if(nCount>0)
	{
		strncpy(head.RequestMethod,pBegin,nCount);
		head.RequestMethod[nCount]='\0';
	}
	if(strcmp(head.RequestMethod,"post")!=0&&strcmp(head.RequestMethod,"get")!=0)
	{
		delete[] pRequest;
		head.IsValid=true;
		return head;
	}
	else
		head.IsValid=true;
	//get filepath;
	pBegin=pEnd;
	while(*pBegin==' ')pBegin++;
	pEnd=strchr(pBegin,' ');
	nCount=0;
	nCount=pEnd-pBegin;
	if(nCount>0)
	{
		strncpy(head.FilePath,pBegin,nCount);
		head.FilePath[nCount]='\0';
	}
	//get HTTP Version
	pBegin=pEnd;
	while(*pBegin==' ')pBegin++;
	pEnd=strchr(pBegin,'\n');
	nCount=0;
	nCount=pEnd-pBegin;
	if(nCount>0)
	{
		strncpy(head.HttpVersion,pBegin,nCount);
		head.HttpVersion[nCount]='\0';
	}
	//get Reference
	if(pBegin=strstr(pBegin,"referer:"))
	{
		pBegin+=8;
		while(*pBegin==' ')pBegin++;
		pEnd=strchr(pBegin,'\n');
		nCount=0;
		nCount=pEnd-pBegin;
		if(nCount>0)
		{
			strncpy(head.Referer,pBegin,nCount-1);
			head.Referer[nCount-1]='\0';
		}
	}


	//get host;
	pBegin=pRequest;
	pBegin=strstr(pBegin,"host:");
	pEnd=strchr(pBegin,'\n');
	pBegin+=5;
	while(*pBegin==' ')pBegin++;
	nCount=0;
	nCount=pEnd-pBegin;
	if(nCount)
	{
		strncpy(head.Host,pBegin,nCount-1);
		head.Host[nCount-1]='\0';
	}
	//继续分析文件名
	//如果采用get的方法传递参数,会以?的形式跟在文件路径后面的。
	pBegin=head.FilePath;
	pBegin=strchr(pBegin,'?');
	if(pBegin)
	{
		strcpy(head.QueryString,pBegin+1);
		*pBegin='\0';
	}
	//从完整路径里面分离出相对路径
	pBegin=head.FilePath;
	if(strrchr(pBegin,'.'))
	{
		if(pBegin=strrchr(pBegin,'/'))
		{
			strcpy(head.FileName,pBegin+1);
			*(pBegin+1)='\0';
		}
	}
	else
		head.FileName[0]='\0';
	//when is method is post,get the parameter;
	//在post方式里面，表单数据与请求头之间一定会有一空行。
	if(strcmp(head.RequestMethod,"post")==0)
	{
		pBegin=pRequest;
		pBegin=strstr(pBegin,"content-length:");
		pEnd=strchr(pBegin,'\n');
		pBegin+=15;
		while(*pBegin==' ')pBegin++;
		nCount=pEnd-pBegin;
		if(nCount)
		{
			char temp[10];
			strncpy(temp,pBegin,nCount);
			temp[nCount]='\0';
			head.ContentLength=atoi(temp);
		}
		pBegin=pRequest;
		if(pBegin=strrchr(pBegin,'\n'))
		{	
			strncpy(head.Parameter,pBegin+1,head.ContentLength);//直接复制到尾即可
			head.Parameter[head.ContentLength]='\0';
		}
	}
	else//Get时了最好欠赋一下初值。
	{
		head.ContentLength=0;
		head.Parameter[0]='\0';
	}
	delete[] pRequest;
	return head;
	
}

//CGI执行程序
char* ExecCGI(char* pFilePath,char* pCurrentPath,char* pCmdLine,char* pOutMsg,const char* pPostParam)
{
	HANDLE hReadPipe,hWritePipe,hStdIn,hStdOut,hProcess;
	SECURITY_ATTRIBUTES SecuAttr;
	SecuAttr.bInheritHandle=true;
	SecuAttr.nLength=sizeof(SECURITY_ATTRIBUTES);
	SecuAttr.lpSecurityDescriptor=NULL;
	if(!CreatePipe(&hReadPipe,&hWritePipe,&SecuAttr,0))
	{
		CString temp;
		temp.Format("管道创建失败，错误号：%d",GetLastError());
		//MessageBox(temp);
		return NULL;
	}
	if(!CreatePipe(&hStdOut,&hStdIn,&SecuAttr,0))
	{
		CString temp;
		temp.Format("管道创建失败，错误号：%d",GetLastError());
		//MessageBox(temp);
		return NULL;
	}

	STARTUPINFO InitInfo;
	PROCESS_INFORMATION ProInfo;
	memset(&InitInfo,0,sizeof(STARTUPINFO));
	InitInfo.cb=sizeof(STARTUPINFO);
	GetStartupInfo(&InitInfo);
	InitInfo.dwFlags=STARTF_USESHOWWINDOW | STARTF_USESTDHANDLES;
	InitInfo.wShowWindow=SW_HIDE;
	InitInfo.hStdInput=hStdOut;
	InitInfo.hStdError=hWritePipe;
	InitInfo.hStdOutput=hWritePipe;
	InitInfo.dwXSize=0;
	InitInfo.dwYSize=0;
	
	if(!CreateProcess(pFilePath,
				  pCmdLine,
				  NULL,
				  NULL,
				  true,
				  NULL,
				  NULL,
				  pCurrentPath,//CurrentDiectoryPath指明子进程的路径
				  &InitInfo,
				  &ProInfo))
	{
		CString temp;
		temp.Format("进行和创建失败，错误号:%d",GetLastError());
		return NULL;
	}
	else
	{
		CloseHandle(ProInfo.hThread);
		hProcess=ProInfo.hProcess;
	}
	//在这里写HTML表单传过来的POST数据
	if(*pPostParam!='\0')
	{
		DWORD dwWrite;
		WriteFile(hStdIn,pPostParam,strlen(pPostParam),&dwWrite,NULL);
	}
	CloseHandle(hStdIn);
	CloseHandle(hStdOut);
	//在新程序进程执行的时候读数据
	ULONG nTotalPipeBytes;
	char* pBuffer=new char[MaxCGIBufferSize];
	memset(pBuffer,0,MaxCGIBufferSize);
	DWORD dwRead;
	CloseHandle(hWritePipe);
	while(true)
	{
		int nRtn;
		nRtn=WaitForSingleObject(hProcess,1000);
		//通过信号量等待进行的执行，如果等待超时则退出。
		if(nRtn==WAIT_TIMEOUT)
			break;

		//如果没有找到管道，则同样退出
		if(!PeekNamedPipe(hReadPipe,NULL,0,NULL,&nTotalPipeBytes,NULL))
			break;

		if(nTotalPipeBytes>0)
		{
			dwRead=0;
			if(ReadFile(hReadPipe,pBuffer,MaxCGIBufferSize,&dwRead,NULL)==NULL)
				break;
			if(dwRead>0)
			{
				strcat(pOutMsg,pBuffer);
			}
		}
	}

	delete[] pBuffer;
	CloseHandle(ProInfo.hProcess);
	CloseHandle(hReadPipe);
	return pOutMsg;
}