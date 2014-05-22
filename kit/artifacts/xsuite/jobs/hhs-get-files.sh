#!/bin/sh
lockfile=/tmp/$0.lockfile

if [ -f $lockfile ]
then
  echo "error lock file $lockfile in place."
  exit
fi

echo $$ > $lockfile

if [ -f  /apps/xadmin3/.profile ]
then
. /apps/xadmin3/.profile 
fi

cd $XDIR
. app.sh.env

if [ -z ${XSUITE_HOME} ] 
then
  echo "XSUITE_HOME is not defined."
  exit
fi

# before continuing, make sure the site is up
expectOne=` sh ${XFTP_DIR}/hhs_check.sh | grep DONE | wc -l`
if [ $expectOne -lt 1 ]
then
   echo "Exception, HHS does not seem to be operational."
   rm $lockfile
   exit
fi

logtstamp=`date '+%Y%m%d.%H%M%S'`

#logDir=$XSUITE_HOME/xsuite/reports
logDir=$XSUITE_HOME/xsuite/edi/processed_log
logFile=${logDir}/hhs_get_$logtstamp.log



cd $XDIR/dataexchange/
processDir=inbound/HHS1.COM
indir=hhs_inbound

if [ ! -d $indir ]
then
  mkdir $indir
fi
if [ ! -d $processDir ]
then
  mkdir $processDir
fi

cd $indir



# get the file(s) using ftp.
echo " === " > ${logFile}
sh ${XFTP_DIR}/hhs_get_budget.sh >> ${logFile}

#move them over to the processing directory, processor will ruin on it's own later
cd $XDIR/dataexchange/

mv $indir/* $processDir

rm $lockfile

cd $indir

# we should be done processing files
fl=`ls -1`
if [ "x" = "x$fl" ]
then
  echo "All done, no files left."
else
  echo "Exception: files $fl were not processed in $indir" >> $logFile
fi

mail jobreporting@cleanwise.com <<EOF
subject: $0 done

--- GCA Link file processing ---

`cat $logFile`

EOF

