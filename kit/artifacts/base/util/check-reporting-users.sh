. ${XDIR}/app.sh.env

XCP="ojdbc14.jar${PSEP}."

f_setuser() {
  uname=$1
  accttoken=$2
  storelist=$3
  echo "uname=$uname accttoken=$accttoken storelist=$storelist"

echo ":::::::::::::::: Start: "; date
# look for items that may have been purchased without a cost center (0)
# and update the budget entries is a cost center has been specified.

 java -cp $XCP \
  -DdbUrl=jdbc:oracle:thin:@192.168.3.123:1521:cwcamdb1 \
  -DdbUser=stjohn_prod  -DdbPwd=stjohn_prod \
  -Duname=$uname -Dacctsearch=$accttoken  -DstoreIdList=$storelist  \
  CheckUserAssociation
}

userlist="ptrunk jeffwalters freichelt 
  jcireport "

for uname in $userlist
do
  # make sure the users have access to all JCI accounts.
  f_setuser $uname JCI% 1
  f_setuser $uname %Schwab% 1
  f_setuser $uname %Pfizer% 1
done



#NON-JD
#157520	Cintas
#1	Cleanwise.com
#114304	Enterprise Store
#101250	GCA Services Group, Inc.
#112020	JanPak Defender
#99370	Kranz PASSTHRU

#JD
#114530	ABM Janitorial Services -Greg Owens
#114799	Executive Management Services
#114500	FBG Service Corporation-Greg Owens
#104517	Hospital Housekeeping Systems-Kevin Brown
#108367	LaCosta Facility Support Svcs-Eileen Hartnett
#109220	Marsden-Greg Owens
#114365	SBM, Inc.-Greg Owens
#114512	The Budd Group-Jim Schisler
#157984    SCC Cleanwing Company
#161104      Red Coats

#JD Self Service users accross all stores
userlist="
jdreport dhigdon2 sgoodwin2 jravaris mshortreport
"
for uname in $userlist
do
  # make sure the users have access to ALL accounts.
  echo "ALL accounts uname=$uname"
  f_setuser $uname _% 114530,114799,114500,104517,108367,109220,114365,114512,157984,161104,114365,159763,159763,159763,171470,170191,172427,180023,177747,172267,108367,112020,172419,173243,167530
done

#individual JD account reps
f_setuser jschisler _% 114512
f_setuser ehartnett2 _% 108367,157984
f_setuser kbrown2 _% 104517
f_setuser gowens _% 114530,114500,109220,114365
f_setuser bscott _% 161104
