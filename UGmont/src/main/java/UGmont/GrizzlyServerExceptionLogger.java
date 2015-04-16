package UGmont;

import org.glassfish.grizzly.utils.Exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by flemoal on 15/04/15.
 */
@Provider
public class GrizzlyServerExceptionLogger implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception ex) {
        System.out.println(Exceptions.getStackTraceAsString(ex));
        return Response.status(500).entity(Exceptions.getStackTraceAsString(ex)).type("text/plain")
                .build();
    }
}