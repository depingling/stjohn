
gpgcmd=/usr/bin/gpg

infile=$1


if [ -f ${infile}.decrypted ]
then
  echo "------ Already decrypted file $infile"
else
  echo "-- Processing file $infile"
  ${gpgcmd}  \
    --output ${infile}.decrypted --decrypt $infile
  echo "-- Done with file $infile"
fi


