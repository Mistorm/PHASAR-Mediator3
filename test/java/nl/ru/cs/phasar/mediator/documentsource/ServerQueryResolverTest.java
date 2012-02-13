/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ru.cs.phasar.mediator.documentsource;

import java.util.List;
import junit.framework.TestCase;
import nl.ru.cs.phasar.mediator.userquery.Metadata;

/**
 *
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public class ServerQueryResolverTest extends TestCase {
    
    public ServerQueryResolverTest(String testName) {
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
     * Test of getDocuments method, of class ServerQueryResolver.
     */
    public void testGetDocuments() {
        System.out.println("getDocuments");
        Metadata metadata = null;
        List triples = null;
        ServerQueryResolver instance = new ServerQueryResolver();
        List expResult = null;
        List result = instance.getDocuments(metadata, triples);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
