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
	char Parameter[5000];//�������ֵ���ٿ�Э���˵���ˡ�
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
//������ͨ��ͷ
void SendHead(MySocket	*msSocket,const int nStatusCode,const char *pStatusDesStr,const char* pFileModifyTime,int nHtmlSize,const char *pErrorDetailMsg);
//�����ض�λ��ͷ
void SendLocationHead(MySocket	*msSocket,const int nStatusCode,const char *pStatusDesStr,const char *pLocationUrl);
void SendFile(MySocket  *msSocket,CFile* pFile);

//�����ٽ��������Դ���������
//----���������ٽ�������
CRITICAL_SECTION cs_nMaxConnectionCount;
//----��Ϊ����������ȫ�ֹ���ģ����Դ˴�Ӧ�ô���������
CRITICAL_SECTION cs_EnvVar;

void SendFile(MySocket  *msSocket,CFile* pFile)
{
	int nFileSize=pFile->GetLength();
	//�����5K�ķ�ʽ�����ļ�����
	char* pFileContext=new char[MaxBufferSize];
	int nSizeToBeRead=nFileSize;//��Ҫ�����ļ���С
	int nReadSize=0;//��ǰҪ�������ݴ�С
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

//����ļ���չ��
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
						"<h2>������ϸ������%s</h2>\n"
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
	//������Ϊ��Ч��ʽ��ʱ�򣬲�����ᣡ
	if(!head.IsValid)
		return 0;
	delete pRequestLine;
	//���ҳ�漰·���Ƿ��
	//�����������û�д������ҳ����Ϣ
	if(!(*head.FileName))
	{
		//���Ĭ��ҳ�����������,��Ĭ��ҳ�涼���ڣ�����Ĭ��ҳ��,������403����
		if(cDefaultPage)
		{
			CString csFileFullPath;
			char* pBegin=strstr(head.Referer,head.Host);
			//�������ҳ��������������ļ�����������ͷ������Ѿ���������������·����
			//�������ҳ���ض���
			if(!strchr(head.Referer,'.')&&pBegin)
			{
				//�����ض�������
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
			SendHead(msAcceptConn,403,"��Ȩ����",NULL,NULL,"����Ȩ����Ŀ¼�б�");//����403����
		}
	}
	else
	{
		//���Ҫ���ʵ��ļ��ǲ��Ǵ��ڣ���������403���󣬷�������ļ������Ͳ�ͬ�������з���
		//���ǵ����Ŀ¼��Ӧ��,����Ӧ�����ж��������ǲ��Ǵ��ڡ�Ȼ���ļ��������ַӦ��
		//�����õĵ�ַ�ټ����ļ���ַ
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
			SendHead(msAcceptConn,404,"δ�ҵ�",NULL,NULL,"��Ҫ�鿴����ҳ�����ѱ�ɾ���������ѱ����ģ�������ʱ�����á�");//����403����
		}
		else
		{
			CFile* pFile=OpenFile(csFileFullPath.GetBuffer(0));
			if(pFile!=NULL)
			{
				//�Ժ�׺�ж�ѡ��ͬ�ķ������д���
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
					//-----------------׼��CGI��������,��һ�ζ�Ҫ�����еı������г�ʼ��,��ȡ�������ݡ�
					//-----------------ͬʱ��Ϊ����������ȫ�ֹ���ģ����Դ˴�Ӧ�ô���������
					EnterCriticalSection(&cs_EnvVar);
					CString csPrepareEnv;
					if(head.ContentLength>0)
					{
						csPrepareEnv.Format("%s%d","CONTENT_LENGTH=",head.ContentLength);
						putenv(csPrepareEnv.GetBuffer(0));
					}
					//csPrepareEnv.Format("%s%d","")
					//-----------------׼��CGI��������������
					
					//����CGI���򲢵õ����н��
					//һ��Ҫ��pOutMsg���г�ʼ������break�ĵط�����
					char *pOutMsg=new char[5000];
					memset(pOutMsg,0,5000);
					ExecCGI(csFileFullPath.GetBuffer(0),csFilePath.GetBuffer(0),NULL,pOutMsg,head.Parameter);
					LeaveCriticalSection(&cs_EnvVar);
					//����CGI����ķ����ļ�������������Ϣ��
					//MINE��Ϣ��������Ϣ֮ǰ��һ����һ�пհ��С�
					//���Դ˴����жϿ���Ϊ��ʱû�п�ȫCGI��׼�����²��ֳ������ݳ���
					char* pRtnValue=strchr(pOutMsg,'\n');
					if(pRtnValue)
					{
						while(*(++pRtnValue)!='\n'&&*pRtnValue);
						//�����ʵ����ָ��\n�ϡ�
						if(*(pRtnValue+1))
							pRtnValue++;
					}
					else
						pRtnValue=pOutMsg;
					char *pResponseHead=new char[500];
					GetResponseHead(200,"OK",(const char*)strGmtMod,strlen(pRtnValue),pResponseHead);
					msAcceptConn->ConfirmSend(pResponseHead,strlen(pResponseHead),10);
					delete[] pResponseHead;
					//�����5K�ķ�ʽ�����ļ�����
					char* pFileContext=new char[MaxBufferSize];
					int nSizeToBeRead=strlen(pRtnValue);//��Ҫ�����ڴ��С
					int nReadSize=0;//��ǰҪ�������ݴ�С
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
					;//�Զ���ű���ҳ�棬�ǵô��������Ϻ�ر��ļ�ָ��.
				}
				else//ֱ�Ӷ��ļ�����ʽhtml��ͼ��ȶ�ý���ļ�
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
				//�ڲ����������
				pFile->Close();
				SendHead(msAcceptConn,505,"�ڲ�����",NULL,NULL,"�ڲ�����������");
			}
			delete pFile;
}
}
	
	
	
	msAcceptConn->Close();
	//����������
	//�����ٽ�������
	EnterCriticalSection(&cs_nMaxConnectionCount);
	if(nConnectionCount>0)nConnectionCount--;
	LeaveCriticalSection(&cs_nMaxConnectionCount);
	delete msAcceptConn;
	return 0;
}

UINT ServerListenThread(LPVOID pParam)
{
	nConnectionCount=0;
	//��ʼ���ٽ�������
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
				//�������Լӣ��Կ���������
				//�˴���������Ӧ�ô������¼�
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
	//�÷������������Сд��ͳһ��Сд��
	strlwr(pRequest);
	HttpHeader head;
	char *pBegin=pRequest,*pEnd;
	int nCount=0;
	//��ֹ�Ƿ�������߲��ϸ����󣬼�ǰ�������ɵĿո�
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
	//���������ļ���
	//�������get�ķ������ݲ���,����?����ʽ�����ļ�·������ġ�
	pBegin=head.FilePath;
	pBegin=strchr(pBegin,'?');
	if(pBegin)
	{
		strcpy(head.QueryString,pBegin+1);
		*pBegin='\0';
	}
	//������·�������������·��
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
	//��post��ʽ���棬������������ͷ֮��һ������һ���С�
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
			strncpy(head.Parameter,pBegin+1,head.ContentLength);//ֱ�Ӹ��Ƶ�β����
			head.Parameter[head.ContentLength]='\0';
		}
	}
	else//Getʱ�����Ƿ��һ�³�ֵ��
	{
		head.ContentLength=0;
		head.Parameter[0]='\0';
	}
	delete[] pRequest;
	return head;
	
}

//CGIִ�г���
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
		temp.Format("�ܵ�����ʧ�ܣ�����ţ�%d",GetLastError());
		//MessageBox(temp);
		return NULL;
	}
	if(!CreatePipe(&hStdOut,&hStdIn,&SecuAttr,0))
	{
		CString temp;
		temp.Format("�ܵ�����ʧ�ܣ�����ţ�%d",GetLastError());
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
				  pCurrentPath,//CurrentDiectoryPathָ���ӽ��̵�·��
				  &InitInfo,
				  &ProInfo))
	{
		CString temp;
		temp.Format("���кʹ���ʧ�ܣ������:%d",GetLastError());
		return NULL;
	}
	else
	{
		CloseHandle(ProInfo.hThread);
		hProcess=ProInfo.hProcess;
	}
	//������дHTML����������POST����
	if(*pPostParam!='\0')
	{
		DWORD dwWrite;
		WriteFile(hStdIn,pPostParam,strlen(pPostParam),&dwWrite,NULL);
	}
	CloseHandle(hStdIn);
	CloseHandle(hStdOut);
	//���³������ִ�е�ʱ�������
	ULONG nTotalPipeBytes;
	char* pBuffer=new char[MaxCGIBufferSize];
	memset(pBuffer,0,MaxCGIBufferSize);
	DWORD dwRead;
	CloseHandle(hWritePipe);
	while(true)
	{
		int nRtn;
		nRtn=WaitForSingleObject(hProcess,1000);
		//ͨ���ź����ȴ����е�ִ�У�����ȴ���ʱ���˳���
		if(nRtn==WAIT_TIMEOUT)
			break;

		//���û���ҵ��ܵ�����ͬ���˳�
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