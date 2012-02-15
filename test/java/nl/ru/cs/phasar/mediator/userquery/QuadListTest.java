/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ru.cs.phasar.mediator.userquery;

import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public class QuadListTest extends TestCase {
    
    public QuadListTest(String testName) {
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
     * Test of getTriples method, of class QuadList.
     */
    public void testGetTriples() {
        System.out.println("getTriples");
        QuadList instance = new QuadList();
        List expResult = null;
        List result = instance.getTriples();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildQuads method, of class QuadList.
     */
    public void testBuildQuads() {
        System.out.println("buildQuads");
        String json = "";
        QuadList instance = new QuadList();
        instance.buildQuads(json);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
