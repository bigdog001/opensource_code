1:mvn clean
2:mvn jetty:run
3:maven run time configuration in maven bat file named mvn.bat or mvn sh:
set MAVEN_OPTS=-XX:MaxPermSize=512M    

  
4:CMS console endpoint :http://localhost:8080/SmartCMS/admin.action   ,username:admin,password:admin

5:maven static resource unlock:
$maven_repo$/org/mortbay/jetty/jetty/6.1.10/jetty-6.x.jar/(org\mortbay\jetty\webapp\webdefault.xml) ,修改useFileMappedBuffer参数的值为false 