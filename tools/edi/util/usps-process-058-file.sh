
lockfile=/tmp/usps-058-process.lockfile
if [ -f $lockfile ]
then
  echo "lock file $lockfile in place."
  exit
fi

echo $$ > $lockfile

. /apps/xadmin3/.profile

. $XDIR/app.sh.env

if [ -z ${XSUITE_HOME} ] 
then
  echo "XSUITE_HOME is not defined."
  exit
fi

logtstamp=`date '+%Y%m%d.%H%M%S'`

logDir=$XSUITE_HOME/xsuite/edi/processed_log
logFile=${logDir}/$0_$logtstamp.log


java  \
-classpath "${TJCPATH}" \
-Dconf=${XDIR}/app.properties  \
-Difile=$1 \
com.cleanwise.service.apps.loaders.FedstripLoader

XSUITE_HOME=""; export XSUITE_HOME
sh $XDIR/jobs/xsuite-run-generic-reports.job xsuite-fedstrip-update-report.xml 


rm $lockfile


