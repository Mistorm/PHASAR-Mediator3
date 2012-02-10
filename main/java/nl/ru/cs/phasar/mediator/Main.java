package nl.ru.cs.phasar.mediator;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.http.server.HttpServer;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

public class Main {

    private static String PROPERTIES_FILE = "mediator.properties";

    /**
     * Read the settings from the properties file, create a <code>URI</code> and return it
     * @return URI The URI the webservice is bound to 
     */
    private static URI getBaseURI() {

        URL url = Main.class.getProtectionDomain().getCodeSource().getLocation();

        Properties configFile = new Properties();
        try {
            configFile.load(new FileReader(url.getPath() + PROPERTIES_FILE));
        }
        catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            configFile.setProperty("FROM_URI", "http://localhost/");
            configFile.setProperty("PORT", "9998");
        }

        return UriBuilder.fromUri(configFile.getProperty("FROM_URI")).port(Integer.parseInt(configFile.getProperty("PORT"))).build();
    }
    public static final URI BASE_URI = getBaseURI();

    protected static HttpServer startServer() throws IOException {
        System.out.println("Starting grizzly...");
        ResourceConfig rc = new PackagesResourceConfig("nl.ru.cs.phasar.mediator.resource");
        return GrizzlyServerFactory.createHttpServer(BASE_URI, rc);
    }

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nTry out %smediator\nHit enter to stop it...",
                BASE_URI, BASE_URI));
        System.in.read();
        httpServer.stop();
    }
}
