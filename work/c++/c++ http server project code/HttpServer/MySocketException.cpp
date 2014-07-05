// MySocketException.cpp: implementation of the MySocketException class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "HttpServer.h"
#include "MySocketException.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

MySocketException::MySocketException()
{

}

MySocketException::~MySocketException()
{

}

MySocketException::MySocketException(char *pchMessage)
{
	m_strErrorMessage=pchMessage;
	m_nErrorNum=WSAGetLastError();
}

BOOL MySocketException::GetErrorMessage(LPTSTR lpszError, UINT nMaxError,PUINT pnHelpContext /*= NULL*/)
{
	char text[200];
	if(m_nErrorNum==0)
	{
		wsprintf(text,"´íÎó£º%s",(const char*)m_strErrorMessage);
	}
	else
		wsprintf(text,"´íÎó£º%s\n´íÎóºÅ£º%d",(const char*)m_strErrorMessage,m_nErrorNum);
	strncpy(lpszError, text, nMaxError - 1);
	return true;
}
