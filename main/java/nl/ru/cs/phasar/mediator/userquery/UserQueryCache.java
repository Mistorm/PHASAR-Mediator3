package nl.ru.cs.phasar.mediator.userquery;

import nl.ru.cs.phasar.mediator.documentsource.DocumentSource;

/**
 *
 * @author bartvz
 */
public class UserQueryCache {

    private DocumentSource source;

    public UserQueryCache(DocumentSource source) {
        this.source = source;
    }

    public Query getQuery(Metadata metadata, String json) {
        Query query = this.findInCache(metadata, json);

        if (query == null) {
            query = new Query(metadata, source, json);
            this.addToCache(metadata, json, query);
        }

        return query;
    }

    private Query findInCache(Metadata metadata, String json) {
        return null;
    }

    private void addToCache(Metadata metadata, String json, Query query) {
    }
}