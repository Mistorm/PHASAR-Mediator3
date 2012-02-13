package nl.ru.cs.phasar.mediator.documentsource;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import nl.ru.cs.phasar.mediator.userquery.Metadata;

/**
 *
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public class ServerQueryCacheTest extends TestCase {

    public ServerQueryCacheTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getDocuments method, of class ServerQueryCache.
     */
    public void testGetDocuments() {
        System.out.println("getDocuments");

        List<Triple> resultTriples = new ArrayList<Triple>();
        resultTriples.add(new Triple("verhogen", "PREPmet", "kwart", "kwart"));
        resultTriples.add(new Triple("verhogen", "TEMP", "gisteren", "gisteren"));
        resultTriples.add(new Triple("zullen", "MOD", "nog", "nog"));
        resultTriples.add(new Triple("zullen", "PREPtot", "6", "6"));

        List<Result> expResult = new ArrayList<Result>();
        expResult.add(new Result("Ze voorspellen dat de basisrente, die gisteren al met een kwart procentpunt tot 6,25 procent verhoogd werd, dit jaar nog tot 6,75 7 procent zal groeien.", resultTriples));

        Metadata metadata = null;
        List<Triple> triples = new ArrayList<Triple>();
        triples.add(new Triple("verhogen", "PREPmet", "kwart", "kwart"));

        ServerQueryCache instance = new ServerQueryCache(new MockDocumentSource());

        List<Result> result = instance.getDocuments(metadata, triples);
        assertEquals(expResult, result);
    }
}
