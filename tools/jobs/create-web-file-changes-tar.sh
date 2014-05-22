 cd ../../deploy 

find cleanwise.war/en -type f -mtime -4 \
 | grep -v all_images.tar > /tmp/tt_changes.list

flc=`cat /tmp/tt_changes.list  | wc -l`

if [ ! $flc = "0" ]
then
 tar -cvf /tmp/xsuite_recent_web_file_changes.tar -I /tmp/tt_changes.list 
fi

if [ ! -f /tmp/xsuite_recent_web_file_changes.tar ]
then
  echo "no changes" > /tmp/changes-note.txt
  tar -cvf /tmp/xsuite_recent_web_file_changes.tar /tmp/changes-note.txt
fi


