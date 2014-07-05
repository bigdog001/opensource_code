// MySocketAddress.cpp: implementation of the MySocketAddress class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "HttpServer.h"
#include "MySocketAddress.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

MySocketAddress::~MySocketAddress()
{

}

MySocketAddress::MySocketAddress( )
	{
		sin_family = AF_INET;
		sin_port = 0;
		sin_addr.S_un.S_addr = 0; 
	}
	
MySocketAddress::MySocketAddress(const sockaddr &sa)
	{
		memcpy(this,&sa,sizeof(sockaddr));
	}
	
	MySocketAddress::MySocketAddress(const sockaddr_in &sai)
	{
		memcpy(this,&sai,sizeof(sockaddr_in));
	}


	//参数是主机的字节顺序	
	MySocketAddress::MySocketAddress(const ULONG ulAddr, const USHORT ushPort /*= 0*/)
	{ 
		sin_family = AF_INET;
		sin_port = htons(ushPort);
		sin_addr.S_un.S_addr = htonl(ulAddr); 
	}

	//带点的十进制格式的IP地址
	MySocketAddress::MySocketAddress(const char* pchIP, const USHORT ushPort /*= 0*/)
	{
		sin_family = AF_INET;
		sin_port = htons(ushPort);
		sin_addr.s_addr = inet_addr(pchIP); 
	} 
	
	//网络字节顺序
	CString  MySocketAddress::DottedDecimal()
	{
		return inet_ntoa(sin_addr);
	}
	
	//获得端口和地址
	USHORT MySocketAddress::Port() const
	{
		return ntohs(sin_port);
	}
	ULONG MySocketAddress::IPAddr() const
	{
		return ntohl(sin_addr.s_addr);
	}
	
	//对操作符重载，以提高效率


sockaddr_in * MySocketAddress::GetAddress()
{
	return this;
}
