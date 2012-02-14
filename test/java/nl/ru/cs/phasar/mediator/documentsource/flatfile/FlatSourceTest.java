package nl.ru.cs.phasar.mediator.documentsource.flatfile;

import java.util.List;
import junit.framework.TestCase;
import nl.ru.cs.phasar.mediator.documentsource.Triple;
import nl.ru.cs.phasar.mediator.userquery.Metadata;

/**
 *
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public class FlatSourceTest extends TestCase {
    
    public FlatSourceTest(String testName) {
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
     * Test of getDocuments method, of class FlatSource.
     */
    public void testGetDocuments() {
        System.out.println("getDocuments");
        Metadata metadata = null;
        List<Triple> triples = null;
        FlatSource instance = new FlatSource();
        List expResult = null;
        List result = instance.getDocuments(metadata, triples);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
