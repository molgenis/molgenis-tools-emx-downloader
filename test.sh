#!/bin/sh
clear

echo "== MOLGENIS 2 metadata"
java -jar target/downloader-1.0-SNAPSHOT.jar \
	-o target/molgenis-2.xlsx \
	-u https://molgenis01.target.rug.nl/ \
	-m -U admin \
	org_molgenis_test_TypeTest

echo "== MOLGENIS 1 metadata"
java -jar target/downloader-1.0-SNAPSHOT.jar \
	-o target/molgenis-1.zip \
	-u https://molgenis52.target.rug.nl/ \
	-m -U admin \
	tmf_biobank

