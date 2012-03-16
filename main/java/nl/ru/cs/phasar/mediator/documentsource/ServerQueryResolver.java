package nl.ru.cs.phasar.mediator.documentsource;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import nl.naiaden.twistinator.client.TwistClientHandler;
import nl.naiaden.twistinator.client.TwistClientPipelineFactory;
import nl.naiaden.twistinator.indexer.document.Keyword;
import nl.naiaden.twistinator.indexer.document.Relator;
import nl.naiaden.twistinator.indexer.document.Triple;
import nl.naiaden.twistinator.indexer.document.Triples;
import nl.naiaden.twistinator.indexer.input.Sent;
import nl.naiaden.twistinator.indexer.input.Sents;
import nl.naiaden.twistinator.objects.Returnable;
import nl.naiaden.twistinator.objects.SearchQuery;
import nl.ru.cs.phasar.mediator.userquery.Metadata;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

/**
 * @author louis & bartvz
 */
public class ServerQueryResolver implements DocumentSource {

    Logger log = Logger.getLogger(ServerQueryCache.class);

    static {
	final org.apache.log4j.Logger rootLogger = org.apache.log4j.Logger.getRootLogger();
	if (!rootLogger.getAllAppenders().hasMoreElements()) {
	    rootLogger.setLevel(Level.DEBUG);
	    rootLogger.addAppender(new ConsoleAppender(new PatternLayout("%d{ABSOLUTE} [%-11t] %x %-5p %c{1} - %m%n")));
	}
    }

    @Override
    public List<Result> getDocuments(Metadata metadata, List<nl.ru.cs.phasar.mediator.documentsource.Triple> triples) {

	List<List<Result>> resultLists = new ArrayList<List<Result>>();
	List<Result> resultList = new ArrayList<Result>();
	List<Returnable> returnables = new ArrayList<Returnable>();

	Result result;

	//Searching without the triple(s) to find is imposible
	if (triples.isEmpty()) {
	    return resultList;
	}

	// Parse options.
	final String host = "localhost";
	final int port = 8123;

	// Configure the client.
	ClientBootstrap bootstrap = new ClientBootstrap(
		new NioClientSocketChannelFactory(
		Executors.newCachedThreadPool(),
		Executors.newCachedThreadPool()));

	// Set up the pipeline factory.
	bootstrap.setPipelineFactory(new TwistClientPipelineFactory());

	// Create the query
	for (nl.ru.cs.phasar.mediator.documentsource.Triple t : triples) {
	    ChannelFuture connectFuture = bootstrap.connect(new InetSocketAddress(host, port));
	    Channel channel = connectFuture.awaitUninterruptibly().getChannel();
	    TwistClientHandler handler = (TwistClientHandler) channel.getPipeline().getLast();
	    handler.addQuery(new SearchQuery(new Triple(new Keyword(t.getGroundHead()), new Relator(t.getRelator()), new Keyword(t.getGroundTail()))));
	    returnables.add(handler.getMessage().result);
	    handler.done = true;
	}

	bootstrap.releaseExternalResources();

	for (Returnable r : returnables) {

	    if (r != null && r.getClass().equals(Sents.class)) {
		Sents sents = (Sents) r;
		System.out.println("Returnable is a Sents object, proccesing...");

		resultList = new ArrayList<Result>();

		for (Sent s : sents) {

		    result = new Result(s.getSentence());
		    Triples resultTriples = s.getTriples();

		    for (Triple t : resultTriples) {
			result.addTriple(new nl.ru.cs.phasar.mediator.documentsource.Triple(t.getLeft().toString(), t.getMiddle().toString(), t.getRight().toString(), t.getRight().toString()));
		    }
		    resultList.add(result);
		}
		resultLists.add(resultList);
	    }
	}

	if (resultLists.size() <= 1) {
	    return resultList;
	} else {
	    Collection intersection = CollectionUtils.intersection(resultLists.get(0), resultLists.get(1));

	    if (resultLists.size() > 2) {
		for (int i = 2; i < resultLists.size(); i++) {
		    intersection = CollectionUtils.intersection(intersection, resultLists.get(i));
		}
	    }

	    return (List<Result>) intersection;
	}
    }
}
