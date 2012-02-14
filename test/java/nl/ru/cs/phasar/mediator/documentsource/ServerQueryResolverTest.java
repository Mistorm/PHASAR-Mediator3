/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ru.cs.phasar.mediator.documentsource;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import nl.ru.cs.phasar.mediator.userquery.Metadata;

/**
 *
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public class ServerQueryResolverTest extends TestCase {

    List<Result> expResult;

    public ServerQueryResolverTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        expResult = new ArrayList<Result>();

        String answer = "De Europese Unie, die een klacht heeft ingediend bij de Wereldhandelsorganisatie WTO tegen de wet, reageerde gisteren positief op het uitstel.";

        String[] a = new String[3];
        a[0] = "indienen";
        a[1] = "OBJ";
        a[2] = "het";

        String b[] = new String[3];
        b[0] = "klacht";
        b[1] = "SUBJ";
        b[2] = "indienen";

        List<Triple> matchedTriples = new ArrayList<Triple>();
        matchedTriples.add(new Triple(b));
        matchedTriples.add(new Triple(b));

        expResult.add(new Result(answer, matchedTriples));

        answer = "Dit wordt voor de periode tussen 26 december en 3 januari becijferd op 403 miljoen dollar.";

        a = new String[3];
        a[0] = "becijferen";
        a[1] = "OBJ";
        a[2] = "dollar";

        matchedTriples = new ArrayList<Triple>();
        matchedTriples.add(new Triple(a));

        expResult.add(new Result(answer, matchedTriples));
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getDocuments method, of class ServerQueryResolver.
     */
    public boolean GetDocuments(Triple triple) {
        System.out.println("getDocuments, triple: " + triple.toString());

        Metadata metadata = null;
        List<Triple> triples = new ArrayList<Triple>();
        triples.add(triple);

        ServerQueryResolver instance = new ServerQueryResolver();

        List result = instance.getDocuments(metadata, triples);

        if (result.size() == expResult.size()) {
            for (int i = 0; i < result.size(); i++) {
                if (!result.get(i).equals(expResult.get(i))) {
                    System.out.println("Results are not equal on position " + i);
                    return false;
                }
            }
        } else {
            System.out.println("Expected a different size of expResult!");
            return false;
        }
        return true;
    }

    public void testGetDocuments() {
        assertTrue("testGetDocuments failed!", GetDocuments(new Triple("klacht", "SUBJ", "indienen", "indienen")));
    }

    public void testGetDocumentsHeadWildcard() {
        assertTrue("testGetDocumentsHeadWildcard failed!", GetDocuments(new Triple("*", "SUBJ", "indienen", "indienen")));
    }

    public void testGetDocumentsRelatorWildcard() {
        assertTrue("testGetDocumentsRelatorWildcard failed!", GetDocuments(new Triple("klacht", "*", "indienen", "indienen")));
    }

    public void testGetDocumentsTailWildcard() {
        assertTrue("testGetDocumentsTailWildcard failed!", GetDocuments(new Triple("klacht", "SUBJ", "*", "*")));
    }

    public void testGetDocumentsFullWildcard() {
        assertTrue("testGetDocumentsFullWildcard failed!", GetDocuments(new Triple("*", "*", "*", "*")));
    }
}
