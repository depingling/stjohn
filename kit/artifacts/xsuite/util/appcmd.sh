
# make sure that the host and ports match the JBoss 
# installation that you are trying to talk to.

if [ -f /apps/ixtendx/.profile ] 
then
. /apps/ixtendx/.profile
fi

if [ -f ../app.sh.env ]
then
. ../app.sh.env
fi

if [ -f ../../app.sh.env ]
then
. ../../app.sh.env
fi

if [ -f ${XDIR}/app.sh.env ]
then
. ${XDIR}/app.sh.env
fi

if [ -z ${XSUITE_HOME} ] 
then
  echo "XSUITE_HOME is not defined."
  exit
fi



java -classpath "${TJCPATH}" \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties"  $* \
com.cleanwise.service.apps.AppCmd


