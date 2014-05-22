@echo off
set JAVA_OPTS=-javaagent:@jrebel.home@/jrebel.jar -Xms256m -Xmx512m -XX:MaxPermSize=256m %JAVA_OPTS%
call "%~dp0\run.bat" %*