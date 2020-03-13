# Demo cassandra application and payarar micro

## Introduction

Create a cassandra docker container (only for demo purpose)

sudo docker run --name cassandra -p 9042:9042 -d cassandra:latest

Create schema in cassandra cluster 

```
CREATE KEYSPACE demo
WITH REPLICATION = {
'class' : 'SimpleStrategy',
'replication_factor' : 1
}
```

The generation of the executable jar file can be performed by issuing the following command

    mvn clean package

This will create an executable jar file **cassandra-microbundle.jar** within the _target_ maven folder. This can be started by executing the following command

    java -jar target/cassandra-microbundle.jar


To launch the test page, open your browser at the following URL

    http://localhost:8080/index.html


### Config
