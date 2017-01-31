#molgenis-EMX-downloader

Stand alone tool to download data in EMX format from molgenis servers. Download latest releases [here](https://github.com/molgenis/molgenis-EMX-downloader/releases).

##Example usages
```
java -jar downloader-1.0.jar -f filename.xlsx -u https://molgenis##.gcc.rug.nl/ -a account -p password my_test_entity
```

```
java -jar downloader-1.0.jar -f filename.zip -u https://molgenis##.gcc.rug.nl/ -a account -p password -o -s 1000 my_test_entity
```

##Available options:

|Option (* = required)|      Description|                            
|---------------------|      -----------|                            
|* -a, --account|            MOLGENIS username to login with to download the data.|                   
|* -f, --outputFile <File>|  Name of the file to write the data to. |
|-i, --insecureSSL|          Ignore SSL certicate chain errors and hostname mismatches.  |               
|-m, --meta|                 Write the metadata for the entities to the output file.  |                   
|-o, --overwrite|            Overwrite the exisiting file if it exists.  |                            
|-p, --password|             Password for the MOLGENIS user to login|
|-s, --pageSize <Integer>|   The pagesize for the REST responses, increase in case of large datasets |  
|* -u, --url|                URL of the MOLGENIS instance  |
