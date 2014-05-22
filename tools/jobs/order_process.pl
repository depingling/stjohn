use DBI;
use MIME::Lite;
use Spreadsheet::WriteExcel;

$to_email = 'msonavane@espendwise.com,tzelano@espendwise.com,jriendeau@espendwise.com,bstevens@espendwise.com,9782019199@messaging.sprintpcs.com';
$from_email = 'msonavane@espendwise.com'; 
#$to_email = 'msonavane@espendwise.com';
$cnt = 1;
$fileName = 'order_process_time.xls';
$subject_email = 'Orders not Getting Processed in';
$path_attach = "/cleanwise/xadmin3/webapp/EJBServer5/xsuite/jobs/" . $fileName;
$email_body = 'Check these orders since they are in Received or Ordered status for more than 45 Minutes';
$col1 = 'STORE_ID';
$col2 = 'STORE_NAME';
$col3 = 'ORDER_ID';
$col4 = 'ORDER_NUM';
$col5 = 'ADD_DATE';
$col6 = 'MOD_DATE';
$col7 = 'ORDER_STATUS_CD';
$sid = $ARGV[0];
$dbuser = $ARGV[1];
$dbpasswd = $ARGV[2];


if ($sid eq "cwdev") {
$subject_email = $subject_email . " TEST";
print "This is TEST $sid";
}

if ($sid eq "cwcamdb1") {
$subject_email = $subject_email . " PRODUCTION";
print "This is prod $sid";
}
print "sid====== $sid , user=$dbuser, pass= $dbpasswd";
my($mime_type,$encoding)=('application/vnd.ms-excel','base64');
       
	   my $dbh = DBI->connect("DBI:Oracle:$sid", $dbuser, $dbpasswd)
                or die "Couldn't connect to database: " . DBI->errstr;
        my $sth = $dbh->prepare("select store_id,store_name,order_id,order_num,add_date,mod_date,order_status_cd from 
									(select co.store_id,cbe.short_desc store_name,co.order_id,co.order_num,TO_CHAR(co.ADD_DATE, 'DD-MON-YYYY HH24:MI:SS') ADD_DATE,TO_CHAR(co.MOD_DATE, 'DD-MON-YYYY HH24:MI:SS') mod_date,co.order_status_cd,
									trunc((86400*(sysdate-co.mod_date))/60)-60*(trunc(((86400*(sysdate-co.mod_date))/60)/60)) Min_diff
									from clw_order co,clw_bus_entity cbe
									where co.order_status_cd in(?,?)
									and co.store_id = cbe.bus_entity_id) where min_diff >45")
                or die "Couldn't prepare statement: " . $dbh->errstr;
          my @data;
		  $sth->bind_param( 1, "Received");
		  $sth->bind_param( 2, "Ordered");
          $sth->execute()             # Execute the query
            or die "Couldn't execute statement: " . $sth->errstr;
			
			my $workbook  = Spreadsheet::WriteExcel->new($fileName);
    		my $worksheet = $workbook->add_worksheet();
			my $format = $workbook->add_format(); # Add a format
			$format->set_bold();

			#open (MYFILE, '>CONTRACT_DATA_REPORT.csv');
			#print MYFILE "Bob\n";
			print MYFILE "STORE_ID,STORE_NAME,ORDER_ID,MOD_DATE,MIN_DIFF\n";
			$worksheet->write(0, 0,  $col1,$format);
			$worksheet->write(0, 1,  $col2,$format);
			$worksheet->write(0, 2,  $col3,$format);
			$worksheet->write(0, 3,  $col4,$format);
			$worksheet->write(0, 4,  $col5,$format);
			$worksheet->write(0, 5,  $col6,$format);
			$worksheet->write(0, 6,  $col7,$format);
									
          # Read the matching records and print them out          
          while (@data = $sth->fetchrow_array()) {
            		my $cid = $data[0];
            		my $cin = $data[1];
			my $cn  = $data[2];
			my $cd  = $data[3];
			my $md  = $data[4];
			my $ct  = $data[5];
			my $os  = $data[6];
           # print "\t $cid,$cin,$cn,$cd,$md,$ct\n";
	   #print MYFILE "$cid,$cin,$cn,$cd,$md,$ct\n";
			$worksheet->write($cnt, 0,  $cid);
			$worksheet->write($cnt, 1,  $cin);
			$worksheet->write($cnt, 2,  $cn);
			$worksheet->write($cnt, 3,  $cd);
			$worksheet->write($cnt, 4,  $md);
			$worksheet->write($cnt, 5,  $ct); 
			$worksheet->write($cnt, 6,  $os); 
	
			$cnt = $cnt + 1; 
		#	print "\t $cnt\n";
		#	print "ORDER_ID == $cn , MOD_DATE=$md";
          }
		#close (MYFILE);
$workbook->close;

          if ($sth->rows == 0) {
            print "NO RECORD FOUND.\n\n";
			#$msg = MIME::Lite->new(From    =>  $from_email,
            #           				To      => $to_email,
            #           				Subject => $subject_email,
			#						Type    => 'multipart/mixed');
			#			$msg->attach (Type => 'TEXT',
			#			Data => $email_body);
			#$msg->send( );
          }
		  
		  else {
		  $msg = MIME::Lite->new(From    => $from_email,
                       			 To      => $to_email,
                       			 Subject => $subject_email,
                       			 Type    => 'multipart/mixed');
								 
			$msg->attach (Type => 'TEXT',
						  Data => $email_body
						);					 

			$msg->attach(Type        => $mime_type,
						 Encoding 	 => $encoding,
						 Path        => $path_attach,
						 Filename    => $fileName,
						 Disposition => 'attachment');
			$msg->add('X-Priority' => 1);
			$msg->add('X-MSMail-Priority' => 'High');
			$msg->send( );
		  }

          $sth->finish;
          
        $dbh->disconnect;
		
		
