// MySocket.cpp: implementation of the MySocket class.
//
//////////////////////////////////////////////////////////////////////


#include "stdafx.h"
#include "HttpServer.h"
#include "MySocket.h"
#include "MySocketException.h"


#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

MySocket::MySocket()
{
	WORD wVersionRequested;
	WSADATA wsaData;
	int err;
	wVersionRequested = MAKEWORD( 2, 2 );
	err = WSAStartup( wVersionRequested, &wsaData );
	if ( err != 0 ) 
	{
		throw new MySocketException("Socket WSAStartup!");
	}
	this->s_socket=NULL;
}

MySocket::~MySocket()
{

}

bool MySocket::Creat(int nSocketType)
{

	

	if((this->s_socket=socket(AF_INET,nSocketType,0))==INVALID_SOCKET)
		throw new MySocketException("Socket Create!");
	return true;
}

bool MySocket::Bind(sockaddr* psocketAddress)
{
	ASSERT(s_socket!=NULL);
	
	psocketAddress->sa_family;
if(bind(s_socket,psocketAddress,sizeof(sockaddr))==SOCKET_ERROR)
	{
		throw new MySocketException("Socket Bind!");
		return false;
	}
	
	return true;
}


void MySocket::Listen()
{
	ASSERT(s_socket!=NULL);

	if(listen(s_socket,5)==SOCKET_ERROR)
		throw new MySocketException("Socket Listen!");
}

bool MySocket::Accept(MySocket& sConnect, sockaddr* saConnectAdd)
{

	ASSERT(sConnect.s_socket==NULL);
	//if(s_socket==INVALID_SOCKET||s_socket==NULL)return false;
	ASSERT(s_socket!=NULL);
	int nConnAddLength=sizeof(sockaddr);
	sConnect.s_socket=accept(s_socket,saConnectAdd,&nConnAddLength);
	if(sConnect.s_socket==INVALID_SOCKET)
	{
		//throw new MySocketException("Socket Accept!");
		return false;
	}
	return true;

}

void MySocket::Cleanup()
{
	if(this->s_socket==NULL) return;
	if(this->s_socket!=INVALID_SOCKET)
	{
		if(closesocket(this->s_socket)==SOCKET_ERROR)
			throw new MySocketException("Socket Cleanup");
		this->s_socket=NULL;
	}
}

int MySocket::Receive(char *pRecvBuffer, int nRecvSize, int nSecs)
{
	ASSERT(this->s_socket!=NULL);
	//ÅÐ¶ÏÑÓÊ±
	FD_SET fd = {1, this->s_socket};
	TIMEVAL tv = {nSecs, 0};
	if(select(0,&fd,NULL,NULL,&tv)==SOCKET_ERROR)
		throw new MySocketException("Socket Recieve timeout!");
	int nRecvdSize=0;
	if((nRecvdSize=recv(this->s_socket,pRecvBuffer,nRecvSize,0))==SOCKET_ERROR)
		throw new MySocketException("Socket Receive!");
	return nRecvdSize;
}

int MySocket::Send(const char *pBuffer, int nSendSize, int nSecs)
{
	ASSERT(this->s_socket!=NULL);
	FD_SET fd = {1, s_socket};
	TIMEVAL tv = {nSecs, 0};
	if(select(0, NULL, &fd, NULL, &tv) == 0)
		throw new MySocketException("Socket Send timeout!");
	int nSendBytes=0;
	nSendBytes=send(s_socket,pBuffer,nSendSize,NULL);
	return nSendBytes;
}

void MySocket::Close()
{
	if(s_socket==NULL||s_socket==INVALID_SOCKET)return ;
	ASSERT(s_socket!=NULL);
	if(closesocket(s_socket)==SOCKET_ERROR)
		throw new MySocketException("Socket Close!");
}

int MySocket::ConfirmSend(const char *pBuffer, int nSendSize, int nSecs)
{
	int nSendBytes=0;
	int nSendedBytes=0;
	const char*p=pBuffer; 
	do
	{
		nSendBytes=Send(p,nSendSize-nSendedBytes,nSecs);
		nSendedBytes+=nSendBytes;
		p+=nSendBytes;
	}
	while(nSendedBytes<nSendSize);
	return nSendedBytes;
}
