use DBI;
use MIME::Lite;
use Spreadsheet::WriteExcel;

$cnt = 1;
$sid = 'cwcamdb1';
$dbuser='stjohn_prod';
$dbpasswd='stjohn_prod';
$status1='READY';
$status2='IN_PROGRESS';
#$status3 = 'IGNOREMB';
$in_progress_cutof=15;
$ready_cutof=100;
$fileName = 'EVENT_REPORT.xls';
$col1 = 'EVENT_ID';
$col2 = 'EVENT_STATUS';
$col3 = 'ADD_DATE';
$to_email = 'msonavane@espendwise.com,tzelano@espendwise.com,jriendeau@espendwise.com';
#$to_email = 'msonavane@espendwise.com';
$from_email = 'Event Report'; 
$path_attach = "/cleanwise/xadmin3/webapp/EJBServer5/xsuite/jobs/" . $fileName;

my $dbh = DBI->connect("DBI:Oracle:$sid", $dbuser, $dbpasswd)
                or die "Couldn't connect to database: " . DBI->errstr;
        my $sth = $dbh->prepare('select count(*) from clw_event where status in(?)')
                or die "Couldn't prepare statement: " . $dbh->errstr;
          my $data;
          $sth->execute($status1)             # Execute the query
            or die "Couldn't execute statement: " . $sth->errstr;
			
			while ($data = $sth->fetchrow_array()) {
					$cnt = $data;
                       print "\t cnt=$cnt ' \n";
					if($cnt >= $ready_cutof) {
                                     print "\t 1111 status1 =$status1 and cnt =$cnt ready_cutof = $ready_cutof \n";
					my $subject_email = 'PRODUCTION EVENT Status in READY Count is greater then ' . $ready_cutof;
					my $email_body = 'Check Number of Events in READY Status which is greater then ' . $ready_cutof;
					print "\t READY Count is greater then $ready_cutof \n";
					
					$msg = MIME::Lite->new(From    =>  $from_email,
                       				To      => $to_email,
                       				Subject => $subject_email,
									Type    => 'multipart/mixed');
									$msg->attach (Type => 'TEXT',
									Data => $email_body);
					$msg->send( );
					}
					
			}
					
$sth->finish;			

				$sth = $dbh->prepare('select count(*) from clw_event where status in(?)')
								or die "Couldn't prepare statement: " . $dbh->errstr;
				
				 my $data1;
				$sth->execute($status2)             # Execute the query
						or die "Couldn't execute statement: " . $sth->errstr;
			
				while ($data1 = $sth->fetchrow_array()) {
						$cnt1 = $data1;
                       print "\t This is cnt1 =$cnt1 ' \n";
							if($cnt1 >= $in_progress_cutof) {
                                     print "\t 1111 status2 =$status2 and cnt1 =$cnt1 in_progress_cutof = $in_progress_cutof \n";
									my $subject_email = 'PRODUCTION EVENT Status in IN_PROGRESS Count is greater then ' . $in_progress_cutof;
									my $email_body = 'Check Number of Events in IN_PROGRESS Status which is greater then ' . $in_progress_cutof;
									print "\t IN_PROGRESS Count is greater then $in_progress_cutof \n";
					
									$msg = MIME::Lite->new(From    =>  $from_email,
                       				To      => $to_email,
                       				Subject => $subject_email,
									Type    => 'multipart/mixed');
									$msg->attach (Type => 'TEXT',
									Data => $email_body);
									$msg->send( );
					}
					
			}
#					print "\t $st1,$cnt \n";
				
$sth->finish;
		my($mime_type,$encoding)=('application/vnd.ms-excel','base64');
		my $sth = $dbh->prepare('select event_id,status,add_date from clw_event where status in(?,?) and add_date <= (select sysdate -1/3 from dual)')
				or die "Couldn't prepare statement: " . $dbh->errstr;
		my @data;
          $sth->execute($status1,$status2)             # Execute the query
            or die "Couldn't execute statement: " . $sth->errstr;
			
			my $workbook  = Spreadsheet::WriteExcel->new($fileName);
    		my $worksheet = $workbook->add_worksheet();
			my $format = $workbook->add_format(); # Add a format
			   $format->set_bold();
			   
			   $worksheet->write(0, 0,  $col1,$format);
			   $worksheet->write(0, 1,  $col2,$format);
			   $worksheet->write(0, 2,  $col3,$format);
			
			
#			print "\t Events that are in READY or IN_PROGRESS status for more then 8 hours\n";			
			while (@data = $sth->fetchrow_array()) {
					$event_id = $data[0];
            		$event_status = $data[1];
					$event_add_date = $data[2];
					
					$worksheet->write($cnt, 0,  $event_id);
			        $worksheet->write($cnt, 1,  $event_status);
			        $worksheet->write($cnt, 2,  $event_add_date);
					
					$cnt = $cnt + 1;
			
#					print "\t event_id = $event_id event_status = $event_status add_date = $event_add_date \n";

					
				}
				$workbook->close;
				
		if ($sth->rows == 0) {
#            print "NO RECORD FOUND.\n\n";
			}
		  
		  else {
		  my $subject_email = 'PRODUCTION Events that are in READY or IN_PROGRESS status for more then 8 hours ';
		  my $email_body = 'Events that are in READY or IN_PROGRESS status for more then 8 hours ';
		  
#		  print "RECORD FOUND.\n\n";
		  $msg = MIME::Lite->new(From    => $from_email,
                       			 To      => $to_email,
                       			 Subject => $subject_email,
                       			 Type    => 'multipart/mixed');

			$msg->attach(Type        => $mime_type,
						 Encoding 	 => $encoding,
						 Path        => $path_attach,
						 Filename    => $fileName,
						 Disposition => 'attachment');

			$msg->send( );
		}
				
				$sth->finish;
$dbh->disconnect;
