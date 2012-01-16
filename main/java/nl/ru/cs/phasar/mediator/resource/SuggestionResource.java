
package nl.ru.cs.phasar.mediator.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONException;

/**
 *
 * @author bartvz <bvanzeeland at gmail dot com>
 */
@Path("/mediator/query/suggestion/")
public class SuggestionResource {
 
    @POST
    @Produces("application/json")
    protected Response getSuggestion(String json) throws JSONException {       
        return Response.ok("{}").header("Access-Control-Allow-Origin", "*").build();
    }
    
}
