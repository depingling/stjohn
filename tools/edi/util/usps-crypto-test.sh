
gpgcmd=/usr/bin/gpg
messege="Exception gnu privacy gaurd is either not set up or does not have the correct keys"

expectOne=` ${gpgcmd} --list-keys | grep "ebuy prod <siteadm@eagnmnsu091.usps.gov>" | wc -l`
if [ $expectOne -lt 1 ]
then
   echo ${messege}
   exit
fi

expectOne=` ${gpgcmd} --list-keys | grep "Cleanwise, Inc." | wc -l`
if [ $expectOne -lt 1 ]
then
   echo ${messege}
   exit
fi

echo DONE
