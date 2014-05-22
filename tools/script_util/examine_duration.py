import os
import re
import string

def f_proc_line( pline ):
	m = pline.split( '<' )
	oline = {}
	oline['session'] = '000' 
	oline['jsessionid'] = "-";
	oline['ServerKey'] = "-";
	oline['ORIG_IP'] = "-";

	for v in range(len(m)):
		if ( v+1 <= len(m) ) :
			if ( m[v].find('Started at:') >= 0 ) :
				d= m[v+1].split('>')
				oline['start time'] = d[0] 

			if ( m[v].find('OK> Duration') >= 0 ) :
				d= m[v+1].split('>')
				oline['duration'] = d[0] 

			if ( m[v].find('Session id :') >= 0 ) :
				d= m[v+1].split('>')
				if ( 'null' != d[0] ):
					oline['session'] = d[0] 

			if ( m[v].find('Request URI:') >= 0 ) :
				d= m[v+1].split('>')
				if ( 'null' != d[0] ):
					oline['uri'] = d[0] 

			if ( m[v].find('User:') >= 0 ) :
				d= m[v+1].split('>')
				if ( 'null' != d[0] ):
					oline['user'] = d[0] 

			if ( m[v].find('ServerKey') >= 0 ) :
				oline['ServerKey'] = m[v];
				d= m[v].split('>')
				if ( 'null' != d[0] ):
					oline['ServerKey'] = d[0];

			if ( m[v].find('ORIG_IP') >= 0 ) :
				oline['ORIG_IP'] = m[v];
				d= m[v].split('>')
				if ( 'null' != d[0] ):
					oline['ORIG_IP'] = d[0];

			if ( m[v].find('jsessionid') >= 0 ) :
				oline['jsessionid'] = m[v];
				d= m[v].split('>')
				if ( 'null' != d[0] ):
					oline['jsessionid'] = d[0];
			
	if ( oline.has_key('start time') &
		oline.has_key('user') &
		oline.has_key('uri') &
		oline.has_key('duration') ):
		print oline['start time'] , \
			'	', oline['user'], \
			'	', oline['duration'],\
			'	', oline['ServerKey'],\
			'	', oline['jsessionid'],\
			'	', oline['ORIG_IP'],\
			'	', oline['uri']

rh = open('dur.log')
#rh = open('console.log')

print 'start_time	user	duration	ServerKey	jsessionid	ORIG_IP	uri'
for line in rh.readlines():
	f_proc_line(line)
