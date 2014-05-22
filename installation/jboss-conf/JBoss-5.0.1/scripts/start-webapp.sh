#!/bin/ksh


stty -hup

tstamp=`date '+Date-%Y.%m.%d-Time-%H.%M.%S'`


echo "......... starting JBoss..."
logDir=@jbossHome@/server/@serverName@/log
if [ ! -d $logDir ]
then
  mkdir $logDir
fi

logFile=${logDir}/console.log

if [ -f $logFile ]
then
  mv $logFile $logFile.$tstamp
fi

# Make sure Hypersonic lock file is deleted
rm -f @jbossHome@/server/@serverName@/data/hypersonic/localDB.lck

cd @jbossHome@/bin
sh run.sh -c@serverName@ -b0.0.0.0 1>$logFile 2>&1 &
echo "  DONE ......... starting JBoss..."

