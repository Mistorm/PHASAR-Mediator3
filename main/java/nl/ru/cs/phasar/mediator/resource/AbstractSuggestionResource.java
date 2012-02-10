package nl.ru.cs.phasar.mediator.resource;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.ru.cs.phasar.mediator.documentsource.DocumentSource;
import nl.ru.cs.phasar.mediator.documentsource.ServerQueryCache;
import nl.ru.cs.phasar.mediator.documentsource.ServerQueryResolver;
import nl.ru.cs.phasar.mediator.documentsource.flatfile.FlatSource;
import nl.ru.cs.phasar.mediator.userquery.Metadata;
import nl.ru.cs.phasar.mediator.userquery.Query;
import nl.ru.cs.phasar.mediator.userquery.UserQueryCache;

/**
 *
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public abstract class AbstractSuggestionResource {

    private DocumentSource documentSource;
    private UserQueryCache cache;
    private static String PROPERTIES_FILE = "mediator.properties";
    public static String WILDCARD = "*";

    public AbstractSuggestionResource() {

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

    protected Query getResult(String json) {

        Date year = new Date();
        String matchingType = "";
        String browsingType = "sentence";

        Metadata metadata = new Metadata(year, matchingType, browsingType);

        Query query = cache.getQuery(metadata, json);

        return query;

    }
}
