#!/bin/sh
if [ -f  /apps/xadmin3/.profile ]
then
        . /apps/xadmin3/.profile 
fi

cd $XDIR
. app.sh.env

cd $XDIR/edi/processed
flist=`find . -name "aa824*.decrypted" -ctime -1`
for frcvd in $flist
do
tes=${frcvd:0:7}
#echo "This is tes $tes"
if [ $tes = "./aa824" ]
then
echo "send this file $frcvd"
sh ${XDIR}/util/send-email.sh "USPS 824 Files" "msonavane@espendwise.com,jriendeau@espendwise.com,tzelano@espendwise.com" "This is a USPS 824  file that we received today" "${frcvd}"
fi
done

echo "end of script"
