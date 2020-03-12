package com.ati.cassandra.rest;

import com.ati.cassandra.controller.PersonController;
import com.ati.cassandra.model.Person;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author jocpi
 */
@Path("/persons")
@Singleton
public class PersonEndpoint {
    
    @Inject
    PersonController personController;
    
    @GET
    public Response findAll() {
        return Response.ok(personController.findAll()).build(); 
    }   
    
    @POST
    public Response create(final Person person) {
        return Response.ok( personController.create(person)).build();
    }  
    
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") final String id) {
        return Response.ok(personController.findById(id)).build(); 
    }
    
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") final String id, final Person person) {
        person.setId(id);
        return Response.accepted(personController.update(person)).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") final String id) {
        personController.delete(id);
        return Response.noContent().build();
    }
}
