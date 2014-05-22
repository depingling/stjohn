
gpgcmd=/usr/bin/gpg

infile=$1
outfile=$2


  echo "-- Processing file $infile"
  ${gpgcmd}  \
        --output $outfile \
        -r "ebuy prod <siteadm@eagnmnsu091.usps.gov>" \
        --encrypt $infile 

  echo "-- Done with file $infile"


