package nl.ru.cs.phasar.mediator.documentsource;

import java.util.List;
import nl.ru.cs.phasar.mediator.userquery.Metadata;

/**
 * Mock-up cache for Server Queries, is a <code>DocumentSource</code>. 
 * A Server Query consists from metadata and ground-triples 
 * @author bartvz
 */
public class ServerQueryCache implements DocumentSource {

    private DocumentSource source;      //The source this cache uses if a the result can't be found in the internal cache.

    public ServerQueryCache(DocumentSource documentSource) {
        this.source = documentSource;
    }

    /**
     * Implementation of <code>DocumentSource</code>
     * @param metadata
     * @param triples
     * @return List<Result> List of <code>Result</code>
     */
    @Override
    public List<Result> getDocuments(Metadata metadata, List<Triple> triples) {

        List<Result> resultList = this.findInCache(metadata, triples);

        if (resultList == null) {
            resultList = source.getDocuments(metadata, triples);
            this.addToCache(metadata, triples, resultList);
        }

        return resultList;
    }

    /**
     * Mock-up. See if the server query is cached.
     * @param metadata
     * @param triples
     * @return List<Result> List of <code>Result</code>
     */
    private List<Result> findInCache(Metadata metadata, List<Triple> triples) {
        return null;
    }

    /**
     * Mock-up. Add the server query plus result tot he cache.
     * @param metadata
     * @param triples
     * @param result
     */
    private void addToCache(Metadata metadata, List triples, List<Result> result) {
    }
}
