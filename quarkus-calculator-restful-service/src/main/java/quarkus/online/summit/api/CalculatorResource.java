package quarkus.online.summit.api;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quarkus.online.summit.services.CalculatorService;

@Path("/calculator")
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
public class CalculatorResource {

    final Logger LOG = LoggerFactory.getLogger(CalculatorResource.class);

    public static final String ADDITION = "+";

    public static final String MULTIPLICATION = "*";

    @Inject
    public CalculatorService calculatorService;

    @GET
    @Path("{equation}")
    public Float calculate(final @PathParam("equation") String equation) {
        LOG.info(String.format("The equation to process : %s", equation));
        String[] operands;
        Float result = 0F;
        if (equation.contains(ADDITION)) {
            operands = equation.split("\\" + ADDITION);
            result = calculatorService.add(operands[0], operands[1]);
        } else if (equation.contains(MULTIPLICATION)) {
            operands = equation.split("\\" + MULTIPLICATION);
            result = calculatorService.multiply(operands[0], operands[1]);
        }
        return result;
    }
}
