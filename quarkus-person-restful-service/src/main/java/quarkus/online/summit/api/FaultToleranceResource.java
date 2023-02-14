package quarkus.online.summit.api;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Random;
import java.util.UUID;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quarkus.online.summit.entity.Person;

@Path("/fault")
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
public class FaultToleranceResource {
    

    final Logger LOGGER = LoggerFactory.getLogger(FaultToleranceResource.class);

    private AtomicLong counter = new AtomicLong(0);

    @GET
    @Retry(maxRetries = 4)
    public List<Person> persons() {
        final Long invocationNumber = counter.getAndIncrement();

        maybeFail(String.format("FaultToleranceResource#persons() invocation #%d failed", invocationNumber));

        LOGGER.info("FaultToleranceResource#persons() invocation #%d returning successfully", invocationNumber);
        return Person.listAll();
    }

    private void maybeFail(String failureLogMessage) {
        if (new Random().nextBoolean()) {
            LOGGER.error(failureLogMessage);
            throw new RuntimeException("Resource failure.");
        }
    }

    @GET
    @Path("person/{id}")
    @Timeout(250)
    @Fallback(fallbackMethod = "fallbackGetPerson")
    public List<Person> getPerson(final@PathParam("id") Long personID) {
        long started = System.currentTimeMillis();
        final long invocationNumber = counter.getAndIncrement();

        try {
            randomDelay();
            LOGGER.info("FaultToleranceResource#getPerson() invocation #%d returning successfully", invocationNumber);
            return List.of(Person.findById(personID));
        } catch (InterruptedException e) {
            LOGGER.error("FaultToleranceResource#getPerson() invocation #%d timed out after %d ms", invocationNumber, System.currentTimeMillis() - started);
            return null;
        }
    }

    private void randomDelay() throws InterruptedException {
        Thread.sleep(new Random().nextInt(500));
    }

    public List<Person> fallbackGetPerson(Long personID) {
        LOGGER.info("Falling back to FaultToleranceResource#getPerson()");
        // safe bet, return something that everybody likes
        return Collections.singletonList(Person.findById(1l));
    }
}
