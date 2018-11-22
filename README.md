- Clone project:
 
git clone git@github.com:big-data-europe/docker-hbase.git
docker pull bde2020/hbase-standalone

 - Start docker-hbase:
 
docker-compose -f docker-compose-distributed-local.yml up -d

 - Stop docker-hbase:
 
docker-compose -f docker-compose-distributed-local.yml stop

use hosts-file !!!