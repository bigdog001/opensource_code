// MySocket.h: interface for the MySocket class.
//
//////////////////////////////////////////////////////////////////////



#if !defined(AFX_MYSOCKET_H__C9F23EAD_29FD_4A14_BCB7_F7639504E076__INCLUDED_)
#define AFX_MYSOCKET_H__C9F23EAD_29FD_4A14_BCB7_F7639504E076__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
typedef const struct sockaddr* LPCSOCKADDR;

class MySocket :public CObject
{
public:
	int ConfirmSend(const char *pBuffer, int nSendSize, int nSecs);
	void Close();
	int Send(const char* pBuffer,int nSendSize,int nSecs);
	int Receive(char* pRecvBuffer,int nRecvSize,int nSecs);
	void Cleanup();
	bool Accept(MySocket &sConnect,sockaddr * saConnectAdd);
	void Listen();
	bool Bind(sockaddr* psocketAddress);
	bool Creat(int iSocketType=SOCK_STREAM);
	MySocket();
	virtual ~MySocket();
	SOCKET s_socket;
private:
	

};

#endif // !defined(AFX_MYSOCKET_H__C9F23EAD_29FD_4A14_BCB7_F7639504E076__INCLUDED_)
