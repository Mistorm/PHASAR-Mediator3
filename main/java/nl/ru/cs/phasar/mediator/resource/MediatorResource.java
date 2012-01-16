package nl.ru.cs.phasar.mediator.resource;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import nl.ru.cs.phasar.mediator.documentsource.DocumentSource;
import nl.ru.cs.phasar.mediator.documentsource.ServerQueryCache;
import nl.ru.cs.phasar.mediator.documentsource.flatfile.FlatSource;
import nl.ru.cs.phasar.mediator.userquery.Metadata;
import nl.ru.cs.phasar.mediator.userquery.Query;
import nl.ru.cs.phasar.mediator.userquery.UserQueryCache;
import org.json.JSONException;

/**
 *
 * @author bartvz
 */
@Path("/mediator/query")
public class MediatorResource {

    private DocumentSource documentSource;
    private UserQueryCache cache;

    public MediatorResource() {
        documentSource = new ServerQueryCache(new FlatSource());
        //documentSource =  new ServerQueryCache(new ServerQueryResolver());
        cache = new UserQueryCache(documentSource);
    }

    @GET
    @Produces("application/json")
    public String getMessage() {

        String json = "{boxes:[{id:0, content:aspirin},{id:1, content:ethanol},{id:2, content:cause},{id:3, content:damage},{id:4, content:muscosal},{id:5, content:prevent},{id:6, content:PGs},{id:7, content:human}],arrows:[{a:0, relator:subject, b:2, direction:b},{a:1, relator:subject, b:2, direction:b},{a:2, relator:object, b:3, direction:b},{a:3, relator:attribute, b:4, direction:b},{a:5, relator:object, b:3, direction:b},{a:5, relator:in, b:7, direction:b},{a:6, relator:subject, b:5, direction:b}]}";
        Date year = new Date();
        String matchingType = "";
        String browsingType = "sentence";

        Metadata metadata = new Metadata(year, matchingType, browsingType);

        Query query = cache.getQuery(metadata, json);

        try {
            return query.getJSONResult();
        }
        catch (JSONException ex) {
            Logger.getLogger(MediatorResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "Iternal JSON error!";
    }

    @POST
    @Produces("application/json")
    public Response getResult(String json) {

        System.out.println("Received Json: " + json);

        Date year = new Date();
        String matchingType = "";
        String browsingType = "sentence";

        Metadata metadata = new Metadata(year, matchingType, browsingType);

        Query query = cache.getQuery(metadata, json);

        try {
            System.out.println("Sending Json: " + query.getJSONResult());
            return Response.ok(query.getJSONResult()).header("Access-Control-Allow-Origin", "*").build();
        }
        catch (JSONException ex) {
            Logger.getLogger(MediatorResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }
}
