#!/bin/ksh
#
# St. John build script for Solaris
#

#
# Likely want to change these - the directory where the sandbox is built
# and the user to whom the results get sent
#

BUILD_HOME=/directory/tbesser/nightly
CVSROOT=":pserver:tim@192.168.1.165:/cleanwise/master/proj"
ORACLE_HOME="/apps/home/oracle/OraHome1"

# Needs access to a running X server when starting JBoss.  This is for
# InetSoft reporting - doesn't actually display anything, but gets fonts, etc.
# for Java AWT.  Without it, reporting will not work.  The X virtual
# frame buffer, Xvfb, is assumed to be running to act as a 'dummy'
# X server with console '1' 
DISPLAY="localhost:1.0"

#
# For lack of a better place, this is where it is picking up the
# zip file with JBoss.  It's a copy of what is in the St. John
# tools directory on CWI.  
#
JBOSS_VERSION=JBoss-2.4.4_Jetty-3.1.5-1
JBOSS_ZIP=/usr/local/src/${JBOSS_VERSION}.zip

#
# OBOE package
#
OBOE_VERSION=oboe-2.5.8-1
OBOE_ZIP=/usr/local/src/${OBOE_VERSION}.zip

#
# Required environment variables
#
export ORACLE_HOME=${ORACLE_HOME}
export DISPLAY=${DISPLAY}
export ANT_HOME=/usr/local/jakarta-ant
export JAVA_HOME=/usr/j2se
export PATH=.:$JAVA_HOME/bin:/usr/bin:/usr/local/bin:$ORACLE_HOME/bin:/usr/ccs/bin:$ANT_HOME/bin
export CVSROOT=${CVSROOT}

#
# Interpret any passed args
#
HELP=N
UNIT_TESTS=Y
KEEP_DB=N
PRODUCTION_DB=N
DEMO_DB=N
MAIL=Y
SERVER=N
SERVER_START=N
SERVER_STOP=N
while [ $# -gt 0 ]
do
    case $1 in
	-h) HELP=Y;;
	-k) KEEP_DB=Y; UNIT_TESTS=N;;
	-m) DEMO_DB=Y;;
	-p) PRODUCTION_DB=Y;;
	-x) MAIL=N;;
	-start) SERVER=Y; SERVER_START=Y;;
	-stop) SERVER=Y; SERVER_STOP=Y;;
	-restart) SERVER=Y; SERVER_START=Y; SERVER_STOP=Y;;
	*)  HELP=Y;;
    esac
    shift			  # shift to next parameter
done

if [ "$HELP" = "Y" ]
then
    echo "build.sh [-h][-k][-m][-p][-x]"
    echo "  -h  Print this usage message"
    echo "  -k  Do a full build, keeping the existing database."
    echo "      No unit tests are run."
    echo "  -m  Do a full build, but as final step make a 'demo database'."
    echo "  -p  Do a full build, but as final step make a 'production database'."
    echo "  -x  No email notification when done."
    echo " No build is done for the following options - used only"
    echo " for server control:"
    echo "  -start    Start the JBoss server"
    echo "  -stop     Stop the JBoss server"
    echo "  -restart  Restart the JBoss server"
    exit 0
fi

cd ${BUILD_HOME}

if [ ! -r ${BUILD_HOME}/installation.properties ]
then
    echo "*** Missing installation.properties file in '${BUILD_HOME}'"
    exit 1
fi

# get the port we need to shutdown JBoss
TMPFILE=/tmp/port$$
grep HtmlAdaptorServerPort ${BUILD_HOME}/installation.properties | grep -v \# > $TMPFILE
. $TMPFILE
rm $TMPFILE
JBOSS_SHUTDOWN_PORT=${HtmlAdaptorServerPort}

#
# If only starting/stoping the server
#
if [ "$SERVER" = "Y" ]
then
    if [ "$SERVER_STOP" = "Y" ]
    then
	echo "*** stopping JBoss..." >> ${BUILD_LOG}
	$JAVA_HOME/bin/java -cp ${JBOSS_VERSION}/jboss/lib/ext/jboss.jar org.jboss.Shutdown localhost ${JBOSS_SHUTDOWN_PORT} 1>>${BUILD_LOG} 2>&1
    fi
    if [ "$SERVER_START" = "Y" ]
    then
	echo "*** starting JBoss..." >> ${BUILD_LOG}
	cd ${BUILD_HOME}/${JBOSS_VERSION}/jboss/bin
	run.sh cleanwise 1>../log/console.log 2>&1 &
	cd ${BUILD_HOME}
    fi
    exit 0
fi

#
# Start of the build
#

BUILD_LOG=${BUILD_HOME}/build.log

echo "St. John build : `date`" > ${BUILD_LOG}

echo "*** stopping existing JBoss server if any..." >> ${BUILD_LOG}

$JAVA_HOME/bin/java -cp ${JBOSS_VERSION}/jboss/lib/ext/jboss.jar org.jboss.Shutdown localhost ${JBOSS_SHUTDOWN_PORT} 1>>${BUILD_LOG} 2>&1

echo "*** removing any existing build/install..." >> ${BUILD_LOG}

rm -rf ${JBOSS_VERSION} ${OBOE_VERSION} stjohn

echo "*** unzipping new JBoss install..." >> ${BUILD_LOG}

jar xf $JBOSS_ZIP
chmod u+x ${JBOSS_VERSION}/jboss/bin/run.sh

echo "*** unzipping new OBOE..." >> ${BUILD_LOG}

jar xf $OBOE_ZIP

echo "*** checking out stjohn source..." >> ${BUILD_LOG}

cvs -Q checkout stjohn
cd ${BUILD_HOME}/stjohn

echo "*** copying installation.properties file..." >> ${BUILD_LOG}

cp ${BUILD_HOME}/installation.properties .

if [ "$KEEP_DB" = "N" ]
then
    echo "*** ant database ..." >> ${BUILD_LOG}
    ant database 1>>${BUILD_LOG} 2>&1
fi
    
echo "*** ant install ..." >> ${BUILD_LOG}
ant install 1>>${BUILD_LOG} 2>&1

echo "*** ant ejbdeploy ..." >> ${BUILD_LOG}
ant ejbdeploy 1>>${BUILD_LOG} 2>&1

echo "*** ant webapp ..." >> ${BUILD_LOG}
ant webapp 1>>${BUILD_LOG} 2>&1

echo "*** ant reporting ..." >> ${BUILD_LOG}
ant reporting 1>>${BUILD_LOG} 2>&1

echo "*** starting JBoss..." >> ${BUILD_LOG}

cd ${BUILD_HOME}/${JBOSS_VERSION}/jboss/bin
run.sh cleanwise 1>../log/console.log 2>&1 &

# wait 120s to give JBoss a chance to startup
sleep 120

cd ${BUILD_HOME}/stjohn

if [ "$UNIT_TESTS" = "Y" ]
then
    echo "*** ant runtests ..." >> ${BUILD_LOG}
    ant runtests 1>>${BUILD_LOG} 2>&1
fi

if [ "$KEEP_DB" = "N" ]
then
    cd ${BUILD_HOME}

    echo "*** stop JBoss server..." >> ${BUILD_LOG}
    $JAVA_HOME/bin/java -cp ${JBOSS_VERSION}/jboss/lib/ext/jboss.jar org.jboss.Shutdown localhost ${JBOSS_SHUTDOWN_PORT} 1>>${BUILD_LOG} 2>&1

    cd ${BUILD_HOME}/stjohn

    if [ "$PRODUCTION_DB" = "Y" ]
    then
        echo "*** ant prod_database ..." >> ${BUILD_LOG}
        ant prod_database 1>>${BUILD_LOG} 2>&1
    elif [ "$DEMO_DB" = "Y" ]
    then
        echo "*** ant database ..." >> ${BUILD_LOG}
        ant database 1>>${BUILD_LOG} 2>&1
    fi

    cd ${BUILD_HOME}

    echo "*** start JBoss..." >> ${BUILD_LOG}
    cd ${BUILD_HOME}/${JBOSS_VERSION}/jboss/bin
    run.sh cleanwise 1>../log/console.log 2>&1 &
fi
    
echo "*** Done `date`" >> ${BUILD_LOG}

if [ "$MAIL" = "Y" ]
then
    /usr/ucb/mail -s "St John Daily Build for `date`" `cat ${BUILD_HOME}/stjohn/tools/mail.lst` < ${BUILD_LOG}
fi

exit
