#!/bin/sh
lockfile=/tmp/$0.lockfile
if [ -f $lockfile ]
then
  echo "error lock file $lockfile in place."
  exit
fi

if [ -f  /apps/xadmin3/.profile ]
then
        . /apps/xadmin3/.profile 
fi

if [ -f ${XDIR}/app.sh.env ]
then
    . ${XDIR}/app.sh.env
fi


if [ -z ${XSUITE_HOME} ] 
then
  echo "XSUITE_HOME is not defined."
  
fi

logtstamp=`date '+%Y%m%d.%H%M%S'`

logDir=$XSUITE_HOME/xsuite/edi/processed_log
logFile=${logDir}/xpedx-RMS-get_$logtstamp.log


indir=$XDIR/dataexchange/inbound/XPEDXRMS.COM


if [ ! -d $indir ]
then
  mkdir $indir
fi

cd $indir

# get the file(s) from Sac-Val using ftp.
echo " === " > ${logFile}
sh ${XFTP_DIR}/xpedx_RMS_get.sh getOrderStatus.${logtstamp}.xml ${indir} getOrderStatus company "" >>${logFile} 


mail jobreporting@cleanwise.com <<EOF
subject: $0 done

--- xpedx Link file processing ---

`cat $logFile`

EOF

