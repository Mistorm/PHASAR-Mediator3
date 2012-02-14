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

    List<Result> expResult;

    public ServerQueryCacheTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        List<Triple> resultTriples = new ArrayList<Triple>();
        resultTriples.add(new Triple("verhogen", "PREPmet", "kwart", "kwart"));
        resultTriples.add(new Triple("verhogen", "TEMP", "gisteren", "gisteren"));
        resultTriples.add(new Triple("zullen", "MOD", "nog", "nog"));
        resultTriples.add(new Triple("zullen", "PREPtot", "6", "6"));

        expResult = new ArrayList<Result>();
        expResult.add(new Result("Ze voorspellen dat de basisrente, die gisteren al met een kwart procentpunt tot 6,25 procent verhoogd werd, dit jaar nog tot 6,75 7 procent zal groeien.", resultTriples));
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getDocuments method, of class ServerQueryCache.
     */
    public void testGetDocuments() {
        System.out.println("getDocuments, no wildcard");

        Metadata metadata = null;
        List<Triple> triples = new ArrayList<Triple>();
        triples.add(new Triple("verhogen", "PREPmet", "kwart", "kwart"));

        ServerQueryCache instance = new ServerQueryCache(new MockDocumentSource());

        List<Result> result = instance.getDocuments(metadata, triples);

        if (result.size() == expResult.size()) {
            for (int i = 0; i < result.size(); i++) {
                if (!result.get(i).equals(expResult.get(i))) {
                    fail("Results are not equal on position " + i);
                }
            }
        } else {
            fail("Expected a different size of expResult!");
        }
    }

    public void testGetDocumentsHeadWildcard() {
        System.out.println("getDocuments, head wildcard");

        Metadata metadata = null;
        List<Triple> triples = new ArrayList<Triple>();
        triples.add(new Triple("*", "PREPmet", "kwart", "kwart"));

        ServerQueryCache instance = new ServerQueryCache(new MockDocumentSource());

        List<Result> result = instance.getDocuments(metadata, triples);

        if (result.size() == expResult.size()) {
            for (int i = 0; i < result.size(); i++) {
                if (!result.get(i).equals(expResult.get(i))) {
                    fail("Results are not equal on position " + i);
                }
            }
        } else {
            fail("Expected a different size of expResult!");
        }
    }

    public void testGetDocumentsRelatorWildcard() {
        System.out.println("getDocuments, relator wildcard");

        Metadata metadata = null;
        List<Triple> triples = new ArrayList<Triple>();
        triples.add(new Triple("verhogen", "*", "kwart", "kwart"));

        ServerQueryCache instance = new ServerQueryCache(new MockDocumentSource());

        List<Result> result = instance.getDocuments(metadata, triples);

        if (result.size() == expResult.size()) {
            for (int i = 0; i < result.size(); i++) {
                if (!result.get(i).equals(expResult.get(i))) {
                    fail("Results are not equal on position " + i);
                }
            }
        } else {
            fail("Expected a different size of expResult!");
        }
    }
    
    public void testGetDocumentsTailWildcard(){
        System.out.println("getDocuments, tail wildcard");

        Metadata metadata = null;
        List<Triple> triples = new ArrayList<Triple>();
        triples.add(new Triple("verhogen", "PREPmet", "*", "*"));

        ServerQueryCache instance = new ServerQueryCache(new MockDocumentSource());

        List<Result> result = instance.getDocuments(metadata, triples);

        if (result.size() == expResult.size()) {
            for (int i = 0; i < result.size(); i++) {
                if (!result.get(i).equals(expResult.get(i))) {
                    fail("Results are not equal on position " + i);
                }
            }
        } else {
            fail("Expected a different size of expResult!");
        }
    }
    
    public void testGetDocumentsFullWildcard(){
        System.out.println("getDocuments, full wildcard");

        Metadata metadata = null;
        List<Triple> triples = new ArrayList<Triple>();
        triples.add(new Triple("*", "*", "*", "*"));

        ServerQueryCache instance = new ServerQueryCache(new MockDocumentSource());

        List<Result> result = instance.getDocuments(metadata, triples);

        if (result.size() == expResult.size()) {
            for (int i = 0; i < result.size(); i++) {
                if (!result.get(i).equals(expResult.get(i))) {
                    fail("Results are not equal on position " + i);
                }
            }
        } else {
            fail("Expected a different size of expResult!");
        }
    }
}
