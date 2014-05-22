#!/bin/sh
lockfile=/tmp/$0.lockfile

if [ -f $lockfile ]
then
  echo "ERROR lock file $lockfile in place."
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
  echo "Error XSUITE_HOME is not defined."
  rm $lockfile
  exit
fi

# before continuing, make sure the site is up
expectOne=` sh ${XFTP_DIR}/gca_check.sh | grep DONE | wc -l`
if [ $expectOne -lt 1 ]
then
   echo "Exception, GCA does not seem to be operational."
   rm $lockfile
   exit
fi

logtstamp=`date '+%Y%m%d.%H%M%S'`


#logDir=$XSUITE_HOME/xsuite/reports
logDir=$XSUITE_HOME/xsuite/edi/processed_log
logFile=${logDir}/gca_get_$logtstamp.log


cd $XDIR/dataexchange/
processDir=$XDIR/dataexchange/inbound.chunck/gcaservices.com
indir=$XDIR/dataexchange/gca_inbound

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
sh ${XFTP_DIR}/gca_get.sh >> ${logFile}

#move them over to the processing directory, processor will run on it's own later
cd $XDIR/dataexchange/

cd $indir

fl=`ls -1`
if [ "x" = "x$fl" ]
then
  echo "nothing downloaded"
else
   mv * $processDir
fi

rm $lockfile


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

