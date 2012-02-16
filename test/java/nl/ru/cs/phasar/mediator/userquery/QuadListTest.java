/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ru.cs.phasar.mediator.userquery;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import nl.ru.cs.phasar.mediator.documentsource.Triple;

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
     * Test of buildQuads method, of class QuadList.
     */
    public void testBuildQuads() {
        System.out.println("buildQuads");

        String json = "{\"boxes\":[{\"nr\":\"0\", \"content\":\"damage\"},{\"nr\":\"1\", \"content\":\"mucosal\"},"
                + "{\"nr\":\"2\", \"content\":\"prevent\"},{\"nr\":\"3\", \"content\":\"human\"},{\"nr\":\"4\", \"content\":\"PGs\"},"
                + "{\"nr\":\"6\", \"content\":\"cause\"},{\"nr\":\"7\", \"content\":\"ethanol\"},{\"nr\":\"8\", \"content\":\"Aspirin\"}"
                + "], \"arrows\":[{\"a\":\"0\", \"relator\":\"ATTR\", \"b\":\"1\", \"direction\":\"1\"},"
                + "{\"a\":\"2\", \"relator\":\"OBJ\", \"b\":\"0\", \"direction\":\"0\"},"
                + "{\"a\":\"2\", \"relator\":\"IN\", \"b\":\"3\", \"direction\":\"3\"},"
                + "{\"a\":\"4\", \"relator\":\"SUBJ\", \"b\":\"2\", \"direction\":\"2\"},"
                + "{\"a\":\"6\", \"relator\":\"OBJ\", \"b\":\"0\", \"direction\":\"0\"},"
                + "{\"a\":\"7\", \"relator\":\"SUBJ\", \"b\":\"6\", \"direction\":\"6\"}"
                + ",{\"a\":\"8\", \"relator\":\"SUBJ\", \"b\":\"6\", \"direction\":\"6\"}]}";

        QuadList instance = new QuadList();
        instance.buildQuads(json);
        List<Triple> foundTriples = instance.getTriples();

        List<Triple> triples = new ArrayList<Triple>();

        triples.add(new Triple("damage", "ATTR", "mucosal", "mucosal"));
        triples.add(new Triple("prevent", "OBJ", "damage", "damage"));
        triples.add(new Triple("prevent", "IN", "human", "human"));
        triples.add(new Triple("PGs", "SUBJ", "prevent", "prevent"));
        triples.add(new Triple("cause", "OBJ", "damage", "damage"));
        triples.add(new Triple("ethanol", "SUBJ", "cause", "cause"));
        triples.add(new Triple("Aspirin", "SUBJ", "cause", "cause"));

        if (foundTriples.size() == triples.size()) {
            for (int i = 0; i < foundTriples.size(); i++) {
                if (!foundTriples.get(i).equals(triples.get(i))) {
                    fail("Results are not equal on position " + i);
                }
            }
        } else {
            fail("Expected a different size of expResult!");
        }
    }
}
