
. /apps/ixtendx/.profile

. ../app.sh.env

if [ -z ${XSUITE_HOME} ] 
then
  echo "XSUITE_HOME is not defined."
  exit
fi

# send any new addresses to lagasse.
sh $XSUITE_HOME/xsuite/util/make-lagasse-address-file.sh additions

# before continuing, make sure the lagasse site is up
expectOne=` sh ${XFTP_DIR}/lag_check.sh | grep DONE | wc -l`
if [ $expectOne -lt 1 ] 
then
   echo "Exception, lagasse does not seem to be operational."
   exit
fi

cd ../edi/util

tstamp=`date '+%Y%m%d%H%M%S'`

archiveFile="clwlagorder_tmp.txt"

if [ -f ${archiveFile} ]
then
  echo "archive file ${archiveFile} already exists."
  echo "Cannot continue."
  exit
fi

outdir=outbound_lag

if [ ! -d ../${outdir} ]
then
  mkdir ../${outdir}
fi

outFile="${archiveFile}" 
if [ -f ../$outdir/$outFile ]
then
  echo "Output file: ../$outdir/$outFile already exists."
  echo "Cannot continue until the output file is moved or renamed."
  exit
fi

logDir=$XSUITE_HOME/xsuite/edi/outbound_log
logFile=${logDir}/${archiveFile}
echo " " > $logFile


## Note that the crimson.jar file must be placed at the end
## of the classpath list.

java -Xmx512m -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -Dofile=$outFile -Derpnum=1127 -Dtype=850 \
com.cleanwise.service.apps.dataexchange.OutboundTranslate >> ${logFile}  2>&1

# If there's anything in the file, move it to the outbound directory.
if [ -s $outFile ] 
then
  mv $outFile ../$outdir

cd ../$outdir
mv $outFile clwlagorder_${tstamp}.txt

# now send the file using ftp.
# to lagasse
echo " === " >> ${logFile}
sh ${XFTP_DIR}/lag_send.sh clwlagorder_${tstamp}.txt >> ${logFile}
# to our windows archive area for research and audit
echo " === " >> ${logFile}
sh ${XFTP_DIR}/cwii_send.sh clwlagorder_${tstamp}.txt >> ${logFile}

# move the file to the sent area
mv clwlagorder_${tstamp}.txt ../sent

maillist=jobreporting@cleanwise.com 
mail ${maillist} <<EOF
subject: $0 clwlagorder_${tstamp}.txt processed

---
`cat $logFile`

EOF

else
  echo "No orders generated for Lagasse (1127)"
  rm -f $outFile
fi


# Now generate the order file for JWOD items.

cd $XDIR/edi/util

archiveFile="clwjwodorder_tmp.txt"

if [ -f ${archiveFile} ]
then
  echo "archive file ${archiveFile} already exists."
  echo "Cannot continue."
  exit
fi

outdir=outbound_lag
outFile="${archiveFile}" 
if [ -f ../$outdir/$outFile ]
then
  echo "Output file: ../$outdir/$outFile already exists."
  echo "Cannot continue until the output file is moved or renamed."
  exit
fi

logDir=$XSUITE_HOME/xsuite/edi/outbound_log
logFile=${logDir}/${archiveFile}
echo " " > $logFile

if [ `uname` = "CYGWIN_NT-5.0" ]
then PSEP="\;"
else PSEP=":";
fi



## Note that the crimson.jar file must be placed at the end
## of the classpath list.

java -Xmx512m -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -Dofile=$outFile -Derpnum=1448 -Dtype=850 \
com.cleanwise.service.apps.dataexchange.OutboundTranslate >> ${logFile}  2>&1

# If there's anything in the file, move it to the outbound directory.
if [ -s $outFile ] 
then
  mv $outFile ../$outdir
else
  echo "No orders generated for Lagasse (1448)"
  rm -f $outFile
  exit
fi


cd ../$outdir
sendFileName=clwjwodorder_${tstamp}.txt 
mv $outFile ${sendFileName}

# now send the file using ftp.
# to lagasse
echo " === " >> ${logFile}
sh ${XFTP_DIR}/lag_send.sh ${sendFileName} >> ${logFile}
# to our windows archive area for research and audit
echo " === " >> ${logFile}
sh ${XFTP_DIR}/cwii_send.sh ${sendFileName} >> ${logFile}

# move the file to the sent area
mv ${sendFileName} ../sent

maillist=jobreporting@cleanwise.com 
mail ${maillist} <<EOF
subject: $0 ${sendFileName} processed

---
`cat $logFile`

EOF

