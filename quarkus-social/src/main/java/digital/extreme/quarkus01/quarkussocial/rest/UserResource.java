package digital.extreme.quarkus01.quarkussocial.rest;

import digital.extreme.quarkus01.quarkussocial.domain.model.User;
import digital.extreme.quarkus01.quarkussocial.domain.repository.UserRepository;
import digital.extreme.quarkus01.quarkussocial.rest.dto.CreateUserRequest;
import digital.extreme.quarkus01.quarkussocial.rest.dto.ResponseError;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.Set;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource<StatusFull> {

    private UserRepository repository;
    private Validator validator;

    @Inject
    public UserResource(UserRepository repository, Validator validator){
        this.repository = repository;
        this.validator = validator;
    }

    @POST
    @Transactional
    public Response createUser(CreateUserRequest userRequest)
    {
        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(userRequest);
        if(!violations.isEmpty()) {

//            ConstraintViolation<CreateUserRequest> erro = violations.stream().findAny().get();
//            String errorMessage = erro.getMessage();
            return ResponseError
                    .createFromValidation(violations)
                    .withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }
        User user = new User();
        user.setAge(userRequest.getAge());
        user.setName(userRequest.getName());
        repository.persist(user);

        return Response.status(Status.CREATED.getStatusCode())
                .entity(user)
                .build();
    }



//    @POST
//    @Transactional
    public Response createUserOld(CreateUserRequest userRequest)
    {
        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(userRequest);
        if(!violations.isEmpty()) {
            ConstraintViolation<CreateUserRequest> erro = violations.stream().findAny().get();
            String errorMessage = erro.getMessage();
            return Response.status(400).entity(errorMessage).build();
        }
        User user = new User();
        user.setAge(userRequest.getAge());
        user.setName(userRequest.getName());
        repository.persist(user);

        return Response.ok(user).build();
    }

    @GET
    public Response listAllUsers(){
        PanacheQuery<User> query = repository.findAll();
        return Response.ok(query.list()).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteUser(@PathParam("id") Long id){
        User user  = repository.findById(id);
        if (user != null){
            repository.delete(user);
            return Response.noContent().build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, CreateUserRequest userData){

        User user  = repository.findById(id);
        if (user != null){
            user.setAge(userData.getAge());
            user.setName(userData.getName());
            return Response.noContent().build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }
}
