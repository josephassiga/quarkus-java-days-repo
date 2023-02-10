package quarkus.online.summit.api;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
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

import quarkus.online.summit.entity.Expense;
import quarkus.online.summit.service.ExpenseService;

@Path("/expenses")
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
public class ExpenseResource {

    @Inject
    public ExpenseService expenseService;

    @GET
    public List<Expense> list() {
        return expenseService.list();
    }

    @POST
    public Expense create(final Expense expense) {
        return expenseService.create(expense);
    }

    @DELETE
    @Path("{uuid}")
    public List<Expense> delete(final @PathParam("uuid") UUID uuid) {
        if (!expenseService.delete(uuid)) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return expenseService.list();
    }

    @PUT
    public void update(final Expense expense) {
        expenseService.update(expense);
    }

}
