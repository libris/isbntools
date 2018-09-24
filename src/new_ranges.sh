#!/bin/bash -x

curl -k -XGET "https://www.isbn-international.org/export_rangemessage.xml" -o ./isbn_ranges.xml

if [ $? -ne 0 ]; then
	echo curl failed.
	exit
fi

xsltproc ./new_ranges.xsl ./isbn_ranges.xml > ./isbn-ranges.txt

if [ $? -ne 0 ]; then
	echo xsltproc failed.
	exit
fi

#append 979-0 ranges

cat ./ismn_ranges.txt >> ./isbn-ranges.txt
