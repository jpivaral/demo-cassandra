# Demo cassandra application and payara micro

The generation of the executable jar file can be performed by issuing the following command

    mvn clean package

Create infrastructure

    sudo docker-compose up -d

Create schema in cassandra cluster 

```
CREATE KEYSPACE demo
WITH REPLICATION = {
'class' : 'SimpleStrategy',
'replication_factor' : 1
}
```

To launch the test page, open your browser at the following URL

    http://localhost:8080/api/persons
