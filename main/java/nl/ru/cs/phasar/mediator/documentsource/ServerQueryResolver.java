package nl.ru.cs.phasar.mediator.documentsource;

import java.util.ArrayList;
import java.util.List;
import nl.naiaden.twistinator.client.TwistClient;
import nl.ru.cs.phasar.mediator.userquery.Metadata;

/**
 *
 * @author bartvz
 */
public class ServerQueryResolver implements DocumentSource {

    @Override
    public List<Result> getDocuments(Metadata metadata, List triples) {
        String serverAnswer = TwistClient.resolveQuery(triples);

        String answer = "This is not the string you are looking for...";

        String[] a = new String[3];
        a[0] = "test";
        a[1] = "relator";
        a[2] = "test";

        String b[] = new String[3];
        b[0] = "test";
        b[1] = "relator";
        b[2] = "test";

        List<Triple> matchedTriples = new ArrayList<Triple>();
        matchedTriples.add(new Triple(b));
        matchedTriples.add(new Triple(b));

        Result result = new Result(answer, matchedTriples);

        List<Result> value = new ArrayList();
        value.add(result);

        answer = ".. or is it?";

        a = new String[3];
        a[0] = "test";
        a[1] = "relator";
        a[2] = "test";

        matchedTriples = new ArrayList<Triple>();
        matchedTriples.add(new Triple(a));

        result = new Result(answer, matchedTriples);

        value.add(result);
        return value;
    }
}
