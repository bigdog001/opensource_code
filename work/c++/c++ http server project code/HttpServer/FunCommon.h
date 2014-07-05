class MySocket;
extern MySocket msServerListenSocket;
extern volatile int nConnectionCount;
extern volatile int nMaxConnectionCount;
extern volatile UINT nListenPort;

extern char cHostBasePath[255];
extern char cHostName[100];
extern char cDefaultPage[50];
extern volatile bool bServerListenStatus;


extern CFile* OpenFile(const char *pFileName);
extern void ReadFile(const char *pFileName,char* pFileContext);
extern void ReadConfigProperty(const char *pCPropertyName,char* pConfigPropertyValue);
extern UINT ServerListenThread(LPVOID pParam);
extern UINT HttpCommunicate(LPVOID pParam);
