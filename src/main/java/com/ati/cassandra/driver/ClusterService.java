/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ati.cassandra.driver;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Session;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author jocpi
 */
@Singleton
public class ClusterService {
    
    private Cluster cluster;
    private Session session;
    
    @Inject 
    @ConfigProperty(name = "cassandra.hostname", defaultValue = "localhost")
    private String hostName;
    
    @Inject 
    @ConfigProperty(name = "cassandra.port", defaultValue = "9042")
    private String port;
    
    @Inject 
    @ConfigProperty(name = "cassandra.user", defaultValue = "cassandra")
    private String user;
        
    @Inject 
    @ConfigProperty(name = "cassandra.password", defaultValue = "cassandra")
    private String password;    

    @PostConstruct
    private void init() {
        Builder b = Cluster.builder().addContactPoint(hostName);
        if (port != null) {
            b.withPort(Integer.parseInt(port));
        }
        cluster = b.build();
        session = cluster.connect();
    }

    @PreDestroy
    private void tearDown() {
            if (session != null) {
                    session.close();
            }
            if (cluster != null) {
                    cluster.close();
            }
    }

    public Session getSession() {
        if (session==null) {
            init();
        }
        return session;
    }
}
