FROM payara/micro
ADD ./target/cassandra.war /opt/payara/deployments
