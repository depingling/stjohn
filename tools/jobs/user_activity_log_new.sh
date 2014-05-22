#!/bin/sh
lockfile=/tmp/$0.lockfile
if [ -f $lockfile ]
then
  echo "error lock file $lockfile in place."
  exit
fi

echo $$ > $lockfile

. ../app.sh.env

if [ -z ${XSUITE_HOME} ] 
then
  echo "XSUITE_HOME is not defined."
  exit
fi

logtstamp=`date '+%Y%m%d.%H%M%S'`

logDir=$XSUITE_HOME/xsuite/useractivitylog
logFile=${logDir}/user_activity_log_new_$logtstamp.log

if [ ! -d $logDir ]
then
  mkdir $logDir
fi

echo " === " > ${logFile}
### START process user activity log
echo "START process user activity log."

java -classpath "${TJCPATH}:${XSUITE_HOME}/server/defst/lib/bzip2.jar:${XSUITE_HOME}/xsuite/lib/ojdbc14.jar" \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -DinfoDbUrl=$1 \
 -DinfoDbUser=$2 \
 -DinfoDbPassword=$3 \
 -DjbossLogDir="${XSUITE_HOME}/server/defst/log" \
 -DprocessFile=$4 \
 -Xmx512m \
com.cleanwise.service.apps.ProcessUserActivityLogInfo >> ${logFile} 2>&1

rm $lockfile

