/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ru.cs.phasar.mediator.documentsource;

import junit.framework.TestCase;

/**
 *
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public class TripleTest extends TestCase {
    
    public TripleTest(String testName) {
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
     * Test of getHead method, of class Triple.
     */
    public void testGetHead() {
        System.out.println("getHead");
        Triple instance = null;
        String expResult = "";
        String result = instance.getHead();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGroundHead method, of class Triple.
     */
    public void testGetGroundHead() {
        System.out.println("getGroundHead");
        Triple instance = null;
        String expResult = "";
        String result = instance.getGroundHead();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRelator method, of class Triple.
     */
    public void testGetRelator() {
        System.out.println("getRelator");
        Triple instance = null;
        String expResult = "";
        String result = instance.getRelator();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTail method, of class Triple.
     */
    public void testGetTail() {
        System.out.println("getTail");
        Triple instance = null;
        String expResult = "";
        String result = instance.getTail();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGroundTail method, of class Triple.
     */
    public void testGetGroundTail() {
        System.out.println("getGroundTail");
        Triple instance = null;
        String expResult = "";
        String result = instance.getGroundTail();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDirection method, of class Triple.
     */
    public void testGetDirection() {
        System.out.println("getDirection");
        Triple instance = null;
        String expResult = "";
        String result = instance.getDirection();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Triple.
     */
    public void testToString() {
        System.out.println("toString");
        Triple instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of match method, of class Triple.
     */
    public void testMatch() {
        System.out.println("match");
        Triple triple = null;
        Triple instance = null;
        boolean expResult = false;
        boolean result = instance.match(triple);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
