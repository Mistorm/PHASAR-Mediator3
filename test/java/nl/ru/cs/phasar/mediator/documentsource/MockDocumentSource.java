
package nl.ru.cs.phasar.mediator.documentsource;

import java.util.ArrayList;
import java.util.List;
import nl.ru.cs.phasar.mediator.userquery.Metadata;

/**
 *
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public class MockDocumentSource implements DocumentSource{

    @Override
    public List<Result> getDocuments(Metadata metadata, List<Triple> triples) {
        
        List<Triple> resultTriples = new ArrayList<Triple>();
        resultTriples.add(new Triple("verhogen", "PREPmet", "kwart", "kwart"));
        resultTriples.add(new Triple("verhogen", "TEMP", "gisteren", "gisteren"));
        resultTriples.add(new Triple("zullen", "MOD", "nog", "nog"));
        resultTriples.add(new Triple("zullen", "PREPtot", "6", "6"));
        
        List<Result> result = new ArrayList<Result>();
        result.add(new Result("Ze voorspellen dat de basisrente, die gisteren al met een kwart procentpunt tot 6,25 procent verhoogd werd, dit jaar nog tot 6,75 7 procent zal groeien.", resultTriples));
        
        return result;
    }
    
}
