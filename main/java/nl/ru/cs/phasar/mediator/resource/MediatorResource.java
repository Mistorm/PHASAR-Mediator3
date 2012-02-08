package nl.ru.cs.phasar.mediator.resource;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import nl.ru.cs.phasar.mediator.documentsource.DocumentSource;
import nl.ru.cs.phasar.mediator.documentsource.ServerQueryCache;
import nl.ru.cs.phasar.mediator.documentsource.ServerQueryResolver;
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

    private static String PROPERTIES_FILE = "mediator.properties";
    private static String MATCHINGTYPE = "";
    private static String BROWSINGTYPE = "sentence";
    private DocumentSource documentSource;
    private UserQueryCache cache;

    public MediatorResource() {

        URL url = MediatorResource.class.getProtectionDomain().getCodeSource().getLocation();

        Properties configFile = new Properties();
        try {
            configFile.load(new FileReader(url.getPath() + PROPERTIES_FILE));
        }
        catch (IOException ex) {
            Logger.getLogger(MediatorResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Caveat: configFile.getProperty() only returns String, that explains this code
        if (configFile.getProperty("INTERNAL_DOCSOURCE", "true").equals("true")) {
            documentSource = new ServerQueryCache(new FlatSource());
        } else {
            documentSource = new ServerQueryCache(new ServerQueryResolver());
        }

        cache = new UserQueryCache(documentSource);
    }

    @POST
    @Produces("application/json")
    public Response getResult(String json) {

        System.out.println("Received Json: " + json);

        Date year = new Date();

        Metadata metadata = new Metadata(year, MATCHINGTYPE, BROWSINGTYPE);

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
