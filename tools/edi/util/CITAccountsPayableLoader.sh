# make sure that the host and ports match the JBoss 
# installation that you are trying to talk to.
#from edi util
. ../../app.sh.env

if [ -z ${XSUITE_HOME} ] 
then
  echo "XSUITE_HOME is not defined."
  exit
fi

if [ -z $1 ] 
then
  echo "usage: $0 file-name"
  exit
fi

if [ ! -f $1 ]
then
  echo "No such input file: $1"
  exit
fi

tstamp=`date '+%Y%m%d%H%M%S'`
archiveFile="${tstamp}.${1}"

if [ -f ${archiveFile} ]
then
  echo "archive file ${archiveFile} already exists."
  echo "Cannot continue."
  exit
fi



logDir=../processed_log
if [ ! -d $logDir ]
then
  mkdir $logDir
fi


java -classpath "${TJCPATH}"  \
-Dconf="${XSUITE_HOME}/xsuite/app.properties" -Difile=$1 -Destore="1" \
com.cleanwise.service.apps.loaders.CITAccountsPayableLoader | tee ${logDir}/${archiveFile}


mv $1 ../processed/${archiveFile}

