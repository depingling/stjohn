
# make sure that the host and ports match the JBoss 
# installation that you are trying to talk to.


if [ -z ${XSUITE_HOME} ] 
then
  echo "XSUITE_HOME is not defined."
  exit
fi

. $XSUITE_HOME/xsuite/app.sh.env

if [ `uname` = "CYGWIN_NT-5.0" ]
then
  TJCPATH="${XSUITE_HOME}/lib/ext/xsuite-apps.jar" 
else
  TJCPATH="${XSUITE_HOME}/lib/ext/xsuite-apps.jar" 
fi

java -classpath "${TJCPATH}" \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties"   \
com.cleanwise.service.apps.TextWrap $*


