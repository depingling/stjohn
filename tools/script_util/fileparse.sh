# make sure that the host and ports match the JBoss 
# installation that you are trying to talk to.

if [ -f $XDIR/app.sh.env ]
then
. $XDIR/app.sh.env
fi

if [ -f ../app.sh.env ]
then
. ../app.sh.env
fi


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
  if [ ! -d $1 ]
  then
    echo "No such input file: $1"
    exit
   fi
fi

tstamp=`date '+%Y%m%d%H%M%S'`
archiveFile="${tstamp}.${1}"

if [ -f ${archiveFile} ]
then
  echo "archive file ${archiveFile} already exists."
  echo "Cannot continue."
  exit
fi

if [ ! -d ../outbound ]
then
  mkdir ../outbound
fi

outFile="fa997_cleanwise.dat.${tstamp}.txt" 
if [ -f ../outbound/$outFile ]
then
  echo "Output file: ../outbound/$outFile already exists."
  echo "Cannot continue until the output file is moved or renamed."
  exit
fi

logDir=../processed_log
if [ ! -d $logDir ]
then
  mkdir $logDir
fi





## Note that the crimson.jar file must be placed at the end
## of the classpath list.
java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -Difile=$1 -Dofile=$outFile \
 -DChunkSize=$2 \
com.cleanwise.service.apps.dataexchange.InboundTranslate 2>&1 | tee ${logDir}/${archiveFile}


if [ -f $outFile ] 
then
  mv $outFile ../outbound
fi

  mv $1 ../processed/${archiveFile}

