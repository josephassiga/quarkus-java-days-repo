package quarkus.online.summit.calculator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/calculator")
@RegisterRestClient
public interface CalculatorService {
    
    @GET
    @Path("{equation}")
    @Produces(MediaType.APPLICATION_JSON)
    public Float calculate(final @PathParam("equation") String equation);
}
