
fl=`find ../processed -mtime -30 | grep po850 | grep decrypted `    

if [ ! -d bk ]
then
  mkdir bk
fi

vf=bk/uspsverifyout.txt
echo "------------------------------ start" > $vf
for f in $fl
do
  sh verify-usps-pofile.sh $f >> $vf
done
echo "------------------------------ end" >> $vf

mf=/tmp/$0.mail
echo "POs missing in the database" > $mf
grep File= $vf | grep NOT >> $mf

echo "\nPOs In the database" >> $mf 
grep File= $vf | grep IS >> $mf 

mail edi-order-file-check@cleanwise.com<<EOF  
subject: $0

`cat $mf`

EOF

