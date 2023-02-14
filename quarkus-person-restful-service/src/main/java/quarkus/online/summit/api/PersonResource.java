package quarkus.online.summit.api;

import java.util.List;
import java.util.UUID;

import javax.annotation.security.RolesAllowed;
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

import org.eclipse.microprofile.rest.client.inject.RestClient;

import quarkus.online.summit.calculator.CalculatorService;
import quarkus.online.summit.entity.Person;
import quarkus.online.summit.featuretoggles.Features;
import quarkus.online.summit.service.PersonService;
import quarkus.online.summit.utils.FeatureEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.core.annotation.Counted;

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

    final Logger LOG = LoggerFactory.getLogger(PersonResource.class);

    @Inject
    public PersonService personService;

    @Inject
    public Features appFeatures;

    @Inject
    @RestClient
    public CalculatorService calculatorService;

    @GET
    @Counted(value="all_persons", description = "count number of time we retrieve all persons")
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

    @GET
    @Path("/featureToggles/{feature}")
    public Response featureToggles(final @PathParam("feature") String feature) {

        String aFeature = String.format(FeatureEnum.NONE.getName(), feature);
        if (FeatureEnum.INTERFACE.toString().equalsIgnoreCase(feature) && appFeatures.newUserInterfacceEnbaled()) {
            aFeature = FeatureEnum.INTERFACE.getName().toString();
        } else if (FeatureEnum.ALGORITHM.toString().equalsIgnoreCase(feature)
                && appFeatures.alternativeAlgorithmEnabled()) {
            aFeature = FeatureEnum.ALGORITHM.getName().toString();
        }
        return Response.ok().entity(aFeature).build();
    }

    @GET
    @Path("/calculator/{equation}")
    public Response calculate(final @PathParam("equation") String equation) {
        LOG.info(String.format("The equation to process : %s", equation));
        return Response.ok(calculatorService.calculate(equation)).build();
    }

    @GET
    @RolesAllowed("admin")
    @Path("/admin")
    public List<Person> listPersonsWithSecurity() {
        return personService.list();
    }
}
