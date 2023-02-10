package quarkus.online.summit.api;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import quarkus.online.summit.entity.Person;
import quarkus.online.summit.service.PersonService;

@Path("/person")
// Indicates that the services will read the request’s body by deserializing
// JSON. This will make
// the service accept requests with the header Content-Type and value
// application/json.
@Consumes(MediaType.APPLICATION_JSON)
// Automates JSON serialization for you. Your services can return objects, which
// will be
// analyzed and automatically generate the JSON output. Sets the value of the
// Content-Type
// header on the response to application/json.
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    @Inject
    public PersonService personService;

    @GET
    public List<Person> list() {
        return personService.list();
    }

    @POST
    @Transactional
    public Person create(final Person person) {
        return personService.create(person);
    }

    @DELETE
    @Transactional
    @Path("{uuid}")
    public List<Person> delete(final @PathParam("uuid") UUID uuid) {
        if (!personService.delete(uuid)) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return personService.list();
    }

    @PUT
    @Transactional
    public void update(final Person person) {
        personService.update(person);
    }

}
