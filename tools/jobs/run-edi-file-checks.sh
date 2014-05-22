#!/bin/sh


XSUITE_HOME=/apps/ixtendx

cd ${XSUITE_HOME}
. .profile

# check on EDI PO processing
cd ${XDIR}/edi/util
sh check-usps-edi-pofiles.sh

cd ${XDIR}/edi/util
sh check-jcp-edi-pofiles.sh

# clean up old temporary verify po files
cd /tmp
find . -name "veri*pofil*" -mtime +2 -exec rm {} \; 2>/dev/null


