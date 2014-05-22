#1/bin/sh
lockfile=/tmp/$0.lockfile
logfile='usps_855.sh.log'

if [ -f $lockfile ]
then
  echo "error lock file $lockfile in place."
  exit
fi

echo $$ > $lockfile

if [ -f ~/.bashrc ]; then
        . ~/.bashrc
fi

# User specific environment and startup programs

PATH=$PATH:$HOME/bin

export PATH
unset USERNAME

MYHOME=/espendwise/xapp

export CVSROOT=:pserver:xadmin3@cvsserver:/cleanwise/master/proj
export JAVA_HOME=${MYHOME}/software/java
export ANT_HOME=${MYHOME}/software/ant
export ORACLE_HOME=/home/oracle/oracle/product/10.2.0/client_2
export PATH=$JAVA_HOME/bin:$ANT_HOME/bin:$ORACLE_HOME/bin:$PATH:.

export ANT_OPTS=-Xmx512m


unset NLS_LANG
# clean out the shared memory pool of unused data.
SID='cwcamdb1'
sqlplus stjohn_prod/stjohn_prod@cwcamdb1 @usps_855 >${logfile}

rm $lockfile

mail -s "usps_855 script" msonavane@espendwise.com,tzelano@espendwise.com <<EOF
#mail -s "usps_855 script" msonavane@espendwise.com <<EOF
--- USPS 855 data processing ---

`cat $logfile`

EOF

exit 0
