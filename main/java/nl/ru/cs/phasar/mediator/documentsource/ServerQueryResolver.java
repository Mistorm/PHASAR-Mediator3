package nl.ru.cs.phasar.mediator.documentsource;

import java.util.ArrayList;
import java.util.List;
import nl.ru.cs.phasar.mediator.userquery.Metadata;

/**
 * Mock-up. <code>DocumentSource</code> implementation that uses the PHASAR server to resolve the server query.
 * @author bartvz
 */
public class ServerQueryResolver implements DocumentSource {

    /**
     * Mock-up.
     * @param metadata
     * @param triples
     * @return 
     */
    @Override
    public List<Result> getDocuments(Metadata metadata, List triples) {

        String answer = "De Europese Unie, die een klacht heeft ingediend bij de Wereldhandelsorganisatie WTO tegen de wet, reageerde gisteren positief op het uitstel.";

        String[] a = new String[3];
        a[0] = "indienen";
        a[1] = "OBJ";
        a[2] = "het";

        String b[] = new String[3];
        b[0] = "klacht";
        b[1] = "SUBJ";
        b[2] = "indienen";

        List<Triple> matchedTriples = new ArrayList<Triple>();
        matchedTriples.add(new Triple(b));
        matchedTriples.add(new Triple(b));

        Result result = new Result(answer, matchedTriples);

        List<Result> value = new ArrayList();
        value.add(result);

        answer = "Dit wordt voor de periode tussen 26 december en 3 januari becijferd op 403 miljoen dollar.";

        a = new String[3];
        a[0] = "becijferen";
        a[1] = "OBJ";
        a[2] = "dollar";

        matchedTriples = new ArrayList<Triple>();
        matchedTriples.add(new Triple(a));

        result = new Result(answer, matchedTriples);

        value.add(result);
        return value;
    }
}
