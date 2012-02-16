/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ru.cs.phasar.mediator.userquery;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import nl.ru.cs.phasar.mediator.documentsource.MockDocumentSource;
import nl.ru.cs.phasar.mediator.documentsource.Result;
import nl.ru.cs.phasar.mediator.documentsource.Triple;

/**
 *
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public class UserQueryCacheTest extends TestCase {
    
    public UserQueryCacheTest(String testName) {
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
     * Test of getQuery method, of class UserQueryCache.
     */
    public void testGetQuery() {
        System.out.println("getQuery");
        Metadata metadata = null;
        MockDocumentSource source = new MockDocumentSource();
        String json = "{\"boxes\":[{\"nr\":\"0\", \"content\":\"zullen\"},{\"nr\":\"1\", \"content\":\"nog\"}], \"arrows\":[{\"a\":\"0\", \"relator\":\"MOD\", \"b\":\"1\", \"direction\":\"1\"}]}";
        UserQueryCache instance = new UserQueryCache(source);
        
        
        List<Result> hitlist = new ArrayList<Result>();
        List<Triple> resultTriples = new ArrayList<Triple>();
        resultTriples.add(new Triple("verhogen", "PREPmet", "kwart", "kwart"));
        resultTriples.add(new Triple("verhogen", "TEMP", "gisteren", "gisteren"));
        resultTriples.add(new Triple("zullen", "MOD", "nog", "nog"));
        resultTriples.add(new Triple("zullen", "PREPtot", "6", "6"));
        
        hitlist.add(new Result("Ze voorspellen dat de basisrente, die gisteren al met een kwart procentpunt tot 6,25 procent verhoogd werd, dit jaar nog tot 6,75 7 procent zal groeien.", resultTriples));
        
        Query expResult = new Query(metadata, source, json, hitlist);
        
        Query result = instance.getQuery(metadata, json);
        assertTrue(result.equals(expResult));
    }
}
