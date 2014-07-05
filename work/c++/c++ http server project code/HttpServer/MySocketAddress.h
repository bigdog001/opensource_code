// MySocketAddress.h: interface for the MySocketAddress class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_MYSOCKETADDRESS_H__E0B223F1_0836_4986_8ADE_9251A31D16E2__INCLUDED_)
#define AFX_MYSOCKETADDRESS_H__E0B223F1_0836_4986_8ADE_9251A31D16E2__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class MySocketAddress  :public sockaddr_in
{
public:
	sockaddr_in * GetAddress();
	MySocketAddress( );
	
	MySocketAddress(const sockaddr &sa);
	
	MySocketAddress(const sockaddr_in &sai);
	
	MySocketAddress(const ULONG ulAddr, const USHORT ushPort = 0);

	MySocketAddress(const char* pchIP, const USHORT ushPort = 0);
	CString DottedDecimal();
	//获得端口和地址
	USHORT Port() const;
	ULONG IPAddr() const;//对操作符重载，以提高效率


		
	operator SOCKADDR_IN*()
	{
		
		return GetAddress();
	}
	operator sockaddr*()
	{
		return (sockaddr*)GetAddress();
	}
	


	virtual ~MySocketAddress();

};

#endif // !defined(AFX_MYSOCKETADDRESS_H__E0B223F1_0836_4986_8ADE_9251A31D16E2__INCLUDED_)
