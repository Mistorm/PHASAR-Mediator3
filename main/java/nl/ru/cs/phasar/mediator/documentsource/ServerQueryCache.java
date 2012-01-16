package nl.ru.cs.phasar.mediator.documentsource;

import java.util.List;
import nl.ru.cs.phasar.mediator.userquery.Metadata;

/**
 *
 * @author bartvz
 */
public class ServerQueryCache implements DocumentSource {

    private DocumentSource source;

    public ServerQueryCache(DocumentSource documentSource) {
        this.source = documentSource;
    }

    @Override
    public List<Hit> getDocuments(Metadata metadata, List<Triple> triples) {

        List<Hit> hitlist = this.findInCache(metadata, triples);

        if (hitlist == null) {
            hitlist = source.getDocuments(metadata, triples);
            this.addToCache(metadata, triples, hitlist);
        }

        return hitlist;
    }

    private List<Hit> findInCache(Metadata metadata, List<Triple> triples) {
        return null;
    }

    private void addToCache(Metadata metadata, List triples, List<Hit> result) {
    }
}
