// HttpServerDlg.h : header file
//

#if !defined(AFX_HTTPSERVERDLG_H__DD0D687F_548F_404A_8982_66C19395EF8B__INCLUDED_)
#define AFX_HTTPSERVERDLG_H__DD0D687F_548F_404A_8982_66C19395EF8B__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

/////////////////////////////////////////////////////////////////////////////
// CHttpServerDlg dialog

class CHttpServerDlg : public CDialog
{
// Construction
public:
	CHttpServerDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CHttpServerDlg)
	enum { IDD = IDD_HTTPSERVER_DIALOG };
		// NOTE: the ClassWizard will add data members here
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CHttpServerDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	HICON m_hIcon;

	// Generated message map functions
	//{{AFX_MSG(CHttpServerDlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg void OnButton1();
	afx_msg void OnButton2();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_HTTPSERVERDLG_H__DD0D687F_548F_404A_8982_66C19395EF8B__INCLUDED_)
