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
logFile=${logDir}/process_user_activity_log_$logtstamp.log

if [ ! -d $logDir ]
then
  mkdir $logDir
fi

echo " === " > ${logFile}
### START process user activity log
echo "START process user activity log."

java -classpath "${TJCPATH}" \
 -DlogDir=${XSUITE_HOME}/server/defst/log \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -Xmx1046m \
com.cleanwise.service.apps.loaders.ProcessUserActivityLog >> ${logFile} 2>&1

rm $lockfile

mail jobreporting@espendwise.com <<EOF
subject: $0 done

--- BunzlPA Link file processing ---

`cat $logFile`

EOF

