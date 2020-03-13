/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ati.cassandra.controller;

import com.ati.cassandra.driver.ClusterService;
import com.ati.cassandra.model.Person;
import com.datastax.driver.core.ResultSet;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 * @author jocpi
 */
@Singleton
public class PersonController {
    
    private final static Logger LOGGER = Logger.getLogger(PersonController.class.getName());

    private static final String SCHEMA = "demo";
    private static final String TABLE = "person";
    
    @Inject
    ClusterService cluster;
    
    @PostConstruct
    private void init(){
            StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(SCHEMA +"." + TABLE).append("(")
                .append("id text, ")
                .append("firstname text,")
                .append("lastname text, ")
                .append("PRIMARY KEY (id));");
            LOGGER.info(sb.toString());
            cluster.getSession().execute(sb.toString());
    } 
    
    public Person findById(String id){
        String qry = "select id,firstname,lastname from demo.person where id = '" + id + "';";
        LOGGER.info(qry);
        ResultSet rs = cluster.getSession().execute(qry);
        return rs
                 .all()
                 .stream()
                 .map( r -> new Person( r.getString("id"),r.getString("firstname"), r.getString("lastname")))
                 .findFirst().orElse(new Person());
    }
    
    public List<Person> findAll(){
        String qry = "select id,firstname,lastname from demo.person;";
        LOGGER.info(qry);
        ResultSet rs = cluster.getSession().execute(qry);
        return rs
                 .all()
                 .stream()
                 .map( r -> new Person( r.getString("id"),r.getString("firstname"), r.getString("lastname")))
                 .collect(Collectors.toList());
    }  
    
    public Person create(Person person){
        UUID uuid = UUID.randomUUID();
        person.setId(uuid.toString());
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(SCHEMA +"." + TABLE)
                .append("(id, firstname, lastname) ")
                .append(" VALUES(")
                .append(" '"+ uuid.toString() +"',")
                .append(" '"+ person.getFirstName() +"',")
                .append(" '"+ person.getLastName() +"'")
                .append(");");
        LOGGER.info(sb.toString());
        cluster.getSession().execute(sb.toString());
        return person;
    }
    
    public Person update(Person person){
        StringBuilder sb = new StringBuilder("UPDATE ")
                .append(SCHEMA +"." + TABLE)
                .append(" set  ");
                if(person.getFirstName() != null){
                    sb.append(" firstname = '"+ person.getFirstName() +"' ");
                }
                if(person.getLastName() != null){
                    sb.append( person.getFirstName() != null ? ", " : " ");
                    sb.append(" lastname = '"+ person.getLastName() +"' ");
                }
                sb.append(" WHERE id = '" + person.getId() +"';");
        LOGGER.info(sb.toString());
        ResultSet rs = cluster.getSession().execute(sb.toString());
         return findById(person.getId());
    }
    
    public void delete(String id){
         StringBuilder sb = new StringBuilder("DELETE FROM ")
                .append(SCHEMA +"." + TABLE)
                .append(" WHERE ID = '" + id + "';")
                .append(";");
            LOGGER.info(sb.toString());
            cluster.getSession().execute(sb.toString());
    }
    
}
