package nl.ru.cs.phasar.mediator.documentsource;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.json.JSONObject;

/**
 * 
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public class ResultTest extends TestCase {
    
    public ResultTest(String testName) {
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
     * Test of addTriple method, of class Result.
     */
    public void testAddTriple() {
        System.out.println("addTriple");
        try {
            Triple triple = new Triple("dat", "PRED", "minister", "minister");
            Result instance = new Result("Het Verbond wijst erop dat minister Zalm (Financien) op Kamervragen naar aanleiding van dit onderzoek heeft geantwoord dat op de verzekeringsmarkt een hoge mate van concurrentie bestaat tussen aanbieders.");
        }
        catch (Exception e) {
            fail("Exception occurred!");
        }
    }

    /**
     * Test of getContent method, of class Result.
     */
    public void testGetContent() {
        System.out.println("getContent");
        Result instance = new Result("TCR bewerkstelligde volgens de curatoren dat minister Kroes (Verkeer en Waterstaat) haar eis introk dat een derde van de door het bedrijf te bouwen havenontvangstinstallatie zelf gefinancierd diende te worden.");
        String expResult = "TCR bewerkstelligde volgens de curatoren dat minister Kroes (Verkeer en Waterstaat) haar eis introk dat een derde van de door het bedrijf te bouwen havenontvangstinstallatie zelf gefinancierd diende te worden.";
        String result = instance.getContent();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTriples method, of class Result.
     */
    public void testGetTriples() {
        System.out.println("getTriples");
        
        List<Triple> triples = new ArrayList<Triple>();
        triples.add(new Triple("head", "relator", "tail", "tail"));
        triples.add(new Triple("head", "relator", "tail", "head"));
        triples.add(new Triple("financieren", "MOD", "zelf", "zelf"));
        triples.add(new Triple("duidelijk", "ATTR", "vruchten", "duidelijk"));
        
        Result instance = new Result("Handelaar J. Bestebreurtje van de Mild Coffee Company verwacht dat de prijs met nog 80 cent zal stijgen.", triples);
        
        List<Triple> expResult = new ArrayList<Triple>();
        triples.add(new Triple("head", "relator", "tail", "tail"));
        triples.add(new Triple("head", "relator", "tail", "head"));
        triples.add(new Triple("financieren", "MOD", "zelf", "zelf"));
        triples.add(new Triple("duidelijk", "ATTR", "vruchten", "duidelijk"));
        
        List result = instance.getTriples();
        assertEquals(expResult, result);
    }

    /**
     * Test of matchTriple method, of class Result.
     */
    public void testMatchingTriple() {
        System.out.println("matchTriple: Same triples");
        
        List<Triple> triples = new ArrayList<Triple>();
        triples.add(new Triple("zijn", "PRED", "dat", "dat"));
        Result instance = new Result("Het Interim-Comite is het er over eens dat globalisering", triples);
        
        Triple triple = new Triple("zijn", "PRED", "dat", "dat");
        
        boolean expResult = true;
        boolean result = instance.matchTriple(triple);
        assertEquals(expResult, result);
    }
    
    public void testUnmatchingTriple (){
        System.out.println("matchTriple: different triples");
        
        List<Triple> triples = new ArrayList<Triple>();
        triples.add(new Triple("zijn", "PRED", "dat", "dat"));
        Result instance = new Result("Het Interim-Comite is het er over eens dat globalisering", triples);
        
        Triple triple = new Triple("vinden", "OBJ", "dat", "dat");
        
        boolean expResult = false;
        boolean result = instance.matchTriple(triple);
        assertEquals(expResult, result);
    }
    
    public void testNongroudMatchingTriple(){
        System.out.println("matchTriple: Nonground matching triples");
        
        List<Triple> triples = new ArrayList<Triple>();
        triples.add(new Triple("zijn", "PRED", "dat", "dat"));
        Result instance = new Result("Het Interim-Comite is het er over eens dat globalisering", triples);
        
        Triple triple = new Triple("dat", "PRED", "zijn", "dat");
        
        boolean expResult = true;
        boolean result = instance.matchTriple(triple);
        assertEquals(expResult, result);
    }
    
    public void testPartlyWildcardMatchingTriple(){
        System.out.println("matchTriple: Partial wildcard triple");
        
        List<Triple> triples = new ArrayList<Triple>();
        triples.add(new Triple("zijn", "PRED", "dat", "dat"));
        Result instance = new Result("Het Interim-Comite is het er over eens dat globalisering", triples);
        
        Triple triple = new Triple("*", "PRED", "zijn", "dat");
        
        boolean expResult = true;
        boolean result = instance.matchTriple(triple);
        assertEquals(expResult, result);
    }
    
    public void testFullWildcardMatchingTriple(){
        System.out.println("matchTriple: Full wildcard triple");
        
        List<Triple> triples = new ArrayList<Triple>();
        triples.add(new Triple("zijn", "PRED", "dat", "dat"));
        Result instance = new Result("Het Interim-Comite is het er over eens dat globalisering", triples);
        
        Triple triple = new Triple("*", "*", "*", "dat");
        
        boolean expResult = true;
        boolean result = instance.matchTriple(triple);
        assertEquals(expResult, result);
    }

    /**
     * Test of getJSONObject method, of class Result.
     */
    public void testGetJSONObject() throws Exception {
        System.out.println("getJSONObject");
        
        List<Triple> triples = new ArrayList<Triple>();
        triples.add(new Triple("zijn", "PRED", "dat", "dat"));
        Result instance = new Result("Het Interim-Comite is het er over eens dat globalisering", triples);
        
        
        JSONObject expResult = new JSONObject();
        expResult.put("content", "Het Interim-Comite is het er over eens dat globalisering");
        expResult.put("triples", triples);

        JSONObject result = instance.getJSONObject();
        assertEquals(expResult, result);
    }
}
