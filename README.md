# molgenis-downloader

Standalone tool to download data in EMX or RDF format from molgenis servers.
Download latest releases [here](https://github.com/molgenis/molgenis-EMX-downloader/releases).

## Example usages
```
java -jar downloader-1.2.jar -f filename.xlsx -u https://molgenis##.gcc.rug.nl/ -a account -p password my_test_entity
java -jar downloader-1.2.jar -f filename.zip -u https://molgenis##.gcc.rug.nl/ -a account -p password -o -s 1000 my_test_entity
java -jar downloader-1.2.jar -f filename.ttl --rdf --defaultNamespace ex:http://example.org/ -u https://molgenis##.gcc.rug.nl/ -a account -p password -o -s 1000 my_test_entity
```

## Available options:

|Option (* = required)|      Description|                            
|---------------------|      -----------|                            
|-D, --dataOnly              | Write only the data for the entities to the output file.|                  
| -a, --account              | MOLGENIS username to login with to download the data.   |                   
| -d, --debug                | print debug logging to console                          |         
| --defaultNamespace         | The default namespace for newly created IRIs in RDF download. Format is prefix:namespace. Default value is `mlg:http://molgenis.org/` |          
| * -f, --outputFile <File>  | Name of the file to write the data to.| 
| -i, --insecureSSL          | Ignore SSL certicate chain errors and hostname mismatches.|                 
| --namespaces <File>        | A properties file containing namespace prefixes to add to the defaults. |     
| -o, --overwrite            | Overwrite the file if it exists.|       
| -p, --password             | Password for the MOLGENIS user to login|
| --rdf                      | Specifies that the output should be in RDF format instead of EMX. Implies that only data gets exported.|             
| -s, --pageSize <Integer>   | The pagesize for the REST responses, increase in case of large datasets, maximum value=10000                  
| -t, --timeout <Integer>    | The socket timeout in seconds, default value is 60|                          
| * -u, --url                | URL of the MOLGENIS instance|           
| -v, --version              | Overrides the result from `/api/v2/version`|

* Entities to be downloaded should be added at the end of the command (see `my_test_entity` in examples above).
* Multiple entities can be specified separated by a whitespace.
* Entities linked to the specified entities (for example by XREF or MREF) are downloaded automatically in the case of an
EMX export
