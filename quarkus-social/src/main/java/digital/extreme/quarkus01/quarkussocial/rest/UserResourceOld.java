package digital.extreme.quarkus01.quarkussocial.rest;

import digital.extreme.quarkus01.quarkussocial.domain.model.UserOld;
import digital.extreme.quarkus01.quarkussocial.rest.dto.CreateUserRequest;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResourceOld {

    @POST
    @Transactional
    public Response createUser(CreateUserRequest userRequest)
    {
        UserOld user = new UserOld();
        user.setAge(userRequest.getAge());
        user.setName(userRequest.getName());
        user.persist();

        return Response.ok(user).build();
    }

    @GET
    public Response listAllUsers(){
        PanacheQuery<UserOld> query = UserOld.findAll();
        return Response.ok(query.list()).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteUser(@PathParam("id") Long id){
        UserOld user  = UserOld.findById(id);
        if (user != null){
            user.delete();
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, CreateUserRequest userData){

        UserOld user  = UserOld.findById(id);
        if (user != null){
            user.setAge(userData.getAge());
            user.setName(userData.getName());
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
