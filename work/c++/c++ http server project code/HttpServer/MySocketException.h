// MySocketException.h: interface for the MySocketException class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_MYSOCKETEXCEPTION_H__47AB825E_19A3_4974_B0C0_FD843FF49646__INCLUDED_)
#define AFX_MYSOCKETEXCEPTION_H__47AB825E_19A3_4974_B0C0_FD843FF49646__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class MySocketException  :public CException
{
public:
	virtual BOOL GetErrorMessage(LPTSTR lpszError, UINT nMaxError,PUINT pnHelpContext = NULL);

	MySocketException(char* pchMessage);
	MySocketException();
	
	virtual ~MySocketException();
private:
	CString m_strErrorMessage;
	int m_nErrorNum;
};

#endif // !defined(AFX_MYSOCKETEXCEPTION_H__47AB825E_19A3_4974_B0C0_FD843FF49646__INCLUDED_)
