package quarkus.online.summit;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Specifies the base path "/example" in the URL, which will be used to route the requests to this service.
@Path("/hello")
// Indicates that the services will read the request’s body by deserializing JSON. This will make
// the service accept requests with the header Content-Type and value application/json.
@Consumes(MediaType.APPLICATION_JSON)
//Automates JSON serialization for you. Your services can return objects, which will be
// analyzed and automatically generate the JSON output. Sets the value of the Content-Type
// header on the response to application/json.
@Produces(MediaType.APPLICATION_JSON)
public class GreetingResource {

    // Routes the HTTP GET requests to the annotated method.
    @GET
    public String hello() {
        return "Hello from Java Days Online Summit 2023";
    }

    @GET
    @Path("/{name}")
    public String greet(@PathParam("name") String name) {
        return String.format("Hello %s", name);
    }
}