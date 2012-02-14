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
     * Test of getGroundHead method, of class Triple.
     */
    public void testGetGroundHead() {
        System.out.println("getGroundHead");
        Triple instance = new Triple("vakbondsleiders", "PREPvan", "arrestatie", "vakbondsleiders");
        String expResult = "arrestatie";
        String result = instance.getGroundHead();
        assertEquals(expResult, result);
    }

    /**
     * Test of getGroundTail method, of class Triple.
     */
    public void testGetGroundTail() {
        System.out.println("getGroundTail");
        Triple instance = new Triple("vakbondsleiders", "PREPvan", "arrestatie", "vakbondsleiders");;
        String expResult = "vakbondsleiders";
        String result = instance.getGroundTail();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Triple.
     */
    public void testToString() {
        System.out.println("toString");
        Triple instance = new Triple("economie", "PREPom", "manier", "economie");
        String expResult = "manier PREPom economie";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of match method, of class Triple.
     */
    public boolean match(Triple match) {
        System.out.println("match with: " + match.toString());

        Triple triple = new Triple("economie", "PREPom", "manier", "economie");

        return triple.match(match);
    }

    public void testMatch() {
        assertTrue("testMatch failed!", match(new Triple("economie", "PREPom", "manier", "economie")));
    }

    public void testMatchShuffle() {
        assertTrue("testMatchShuffle failed!", match(new Triple("manier", "PREPom", "economie", "economie")));
    }

    public void testMatchHeadWildcard() {
        assertTrue("testMatchHeadWildcard failed!", match(new Triple("*", "PREPom", "economie", "economie")));
    }

    public void testMatchRelatorWildcard() {
        assertTrue("testMatchRelatorWildcard failed!", match(new Triple("manier", "*", "economie", "economie")));
    }

    public void testMatchTailWildcard() {
        assertTrue("testMatchTailWildcard failed!", match(new Triple("manier", "PREPom", "*", "*")));
    }

    public void testMatchFullWildcard() {
        assertTrue("testMatchFullWildcard failed!", match(new Triple("*", "*", "*", "*")));
    }
}
