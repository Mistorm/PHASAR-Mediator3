package nl.ru.cs.phasar.mediator.documentsource.flatfile;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import nl.ru.cs.phasar.mediator.documentsource.Result;
import nl.ru.cs.phasar.mediator.documentsource.Triple;
import nl.ru.cs.phasar.mediator.userquery.Metadata;

/**
 *
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public class FlatSourceTest extends TestCase {

    private FlatSource instance;

    public FlatSourceTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        instance = new FlatSource("/home/mistorm/workspaces/netbeans/Mediator3/src/main/resources/testsents.txt");
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getDocuments method, of class FlatSource.
     */
    public void testGetDocumentsNormalTriple() {
        System.out.println("getDocuments, triple: inschakelen, OBJ, HET, HET");

        //Create the query
        Metadata metadata = null;
        List<Triple> triples = new ArrayList<Triple>();
        triples.add(new Triple("inschakelen", "OBJ", "HET", "HET"));

        //Create the expected result based on the cotent of the testsents.txt file
        List<Result> expResult = new ArrayList<Result>();

        List<Triple> searchTriples = new ArrayList<Triple>();

        searchTriples.add(new Triple("alleen", "PREPals", "het", "het"));
        searchTriples.add(new Triple("alleen", "SUBJ", "lukken", "lukken"));
        searchTriples.add(new Triple("lukken", "MOD", "niet", "niet"));
        searchTriples.add(new Triple("PTT Telecom", "PREPin", "toekomst", "toekomst"));
        searchTriples.add(new Triple("invullen", "OBJ", "vacatures", "vacatures"));
        searchTriples.add(new Triple("invullen", "SUBJ", "willen", "willen"));
        searchTriples.add(new Triple("vacatures", "PREPvia", "poema", "poema"));
        searchTriples.add(new Triple("willen", "OBJ", "PTT Telecom", "PTT Telecom"));
        searchTriples.add(new Triple("inschakelen", "OBJ", "HET", "HET"));
        searchTriples.add(new Triple("uitzendbureaus", "ATTR", "externe", "externe"));
        searchTriples.add(new Triple("uitzendbureaus", "SUBJ", "inschakelen", "inschakelen"));

        Result result = new Result("Alleen als het niet lukt (tijdelijke) vacatures via Poema in te vullen wil PTT Telecom in de toekomst externe uitzendbureaus inschakelen.", searchTriples);
        expResult.add(result);

        //Get Results and compair
        List testResult = instance.getDocuments(metadata, triples);

        if (testResult.size() == expResult.size()) {
            for (int i = 0; i < testResult.size(); i++) {
                if (!testResult.get(i).equals(expResult.get(i))) {
                    fail("Results are not equal on position " + i);
                }
            }
        } else {
            fail("Expected a different size of expResult!");
        }
    }

    public void testGetDocumentLargerTriple() {
        System.out.println("getDocuments, triple: alleen, PREPals, het, het");

        //Create the query
        Metadata metadata = null;
        List<Triple> triples = new ArrayList<Triple>();
        triples.add(new Triple("alleen", "PREPals", "het", "het"));

        //Create the expected result based on the cotent of the testsents.txt file
        List<Result> expResult = new ArrayList<Result>();

        List<Triple> searchTriples = new ArrayList<Triple>();

        searchTriples.add(new Triple("alleen", "PREPals", "het", "het"));
        searchTriples.add(new Triple("alleen", "SUBJ", "lukken", "lukken"));
        searchTriples.add(new Triple("lukken", "MOD", "niet", "niet"));
        searchTriples.add(new Triple("PTT Telecom", "PREPin", "toekomst", "toekomst"));
        searchTriples.add(new Triple("invullen", "OBJ", "vacatures", "vacatures"));
        searchTriples.add(new Triple("invullen", "SUBJ", "willen", "willen"));
        searchTriples.add(new Triple("vacatures", "PREPvia", "poema", "poema"));
        searchTriples.add(new Triple("willen", "OBJ", "PTT Telecom", "PTT Telecom"));
        searchTriples.add(new Triple("inschakelen", "OBJ", "HET", "HET"));
        searchTriples.add(new Triple("uitzendbureaus", "ATTR", "externe", "externe"));
        searchTriples.add(new Triple("uitzendbureaus", "SUBJ", "inschakelen", "inschakelen"));

        Result result = new Result("Alleen als het niet lukt (tijdelijke) vacatures via Poema in te vullen wil PTT Telecom in de toekomst externe uitzendbureaus inschakelen.", searchTriples);
        expResult.add(result);

        //Get Results and compair
        List testResult = instance.getDocuments(metadata, triples);

        if (testResult.size() == expResult.size()) {
            for (int i = 0; i < testResult.size(); i++) {
                if (!testResult.get(i).equals(expResult.get(i))) {
                    fail("Results are not equal on position " + i);
                }
            }
        } else {
            fail("Expected a different size of expResult!");
        }
    }

    public void testGetDocumentCommaInTriple() {
        System.out.println("getDocuments, triple: kopen, PREPvoor, 2,5, 2,5");

        //Create the query
        Metadata metadata = null;
        List<Triple> triples = new ArrayList<Triple>();
        triples.add(new Triple("kopen", "PREPvoor", "2,5", "2,5"));

        //Create the expected result based on the cotent of the testsents.txt file
        List<Result> expResult = new ArrayList<Result>();

        List<Triple> searchTriples = new ArrayList<Triple>();

        searchTriples.add(new Triple("Elsevier", "SUBJ", "kopen", "kopen"));
        searchTriples.add(new Triple("Lexis-Nexis", "PRED", "databank", "databank"));
        searchTriples.add(new Triple("databank", "PREPmet", "bedrijfsinformatie", "bedrijfsinformatie"));
        searchTriples.add(new Triple("databank", "PREPmet", "juridische", "juridische"));
        searchTriples.add(new Triple("kopen", "MOD", "gulden", "gulden"));
        searchTriples.add(new Triple("kopen", "OBJ", "Lexis-Nexis", "Lexis-Nexis"));
        searchTriples.add(new Triple("kopen", "PREPin", "1995", "1995"));
        searchTriples.add(new Triple("kopen", "PREPvoor", "2,5", "2,5"));
        searchTriples.add(new Triple("kopen", "PREPvoor", "miljard", "miljard"));

        Result result = new Result("Lexis-Nexis, een databank met juridische en bedrijfsinformatie, is door Elsevier in 1995 gekocht voor 2,5 miljard gulden.", searchTriples);

        expResult.add(result);

        //Get Results and compair
        List testResult = instance.getDocuments(metadata, triples);

        if (testResult.size() == expResult.size()) {
            for (int i = 0; i < testResult.size(); i++) {
                if (!testResult.get(i).equals(expResult.get(i))) {
                    fail("Results are not equal on position " + i);
                }
            }
        } else {
            fail("Expected a different size of expResult!");
        }
    }

    public void testGetDocumentQuoted() {
        System.out.println("getDocuments, triple: stakingen, SUBJ, arresteren, arresteren");

        //Create the query
        Metadata metadata = null;
        List<Triple> triples = new ArrayList<Triple>();
        triples.add(new Triple("stakingen", "SUBJ", "arresteren", "arresteren"));

        //Create the expected result based on the cotent of the testsents.txt file
        List<Result> expResult = new ArrayList<Result>();

        List<Triple> searchTriples = new ArrayList<Triple>();

        searchTriples.add(new Triple("Kwon Yong-Kil", "PRED", "leider", "leider"));
        searchTriples.add(new Triple("Kwon Yong-Kil", "SUBJ", "zeggen", "zeggen"));
        searchTriples.add(new Triple("arresteren", "OBJ", "worden", "worden"));
        searchTriples.add(new Triple("leider", "PREPvan", "KCTU", "KCTU"));
        searchTriples.add(new Triple("stakingen", "SUBJ", "arresteren", "arresteren"));
        searchTriples.add(new Triple("worden", "ATTR", "uitgebreid", "uitgebreid"));
        searchTriples.add(new Triple("worden", "MOD", "er", "er"));
        searchTriples.add(new Triple("worden", "PRED", "vakbondsleiders", "vakbondsleiders"));
        searchTriples.add(new Triple("zeggen", "OBJ", "stakingen", "stakingen"));
        searchTriples.add(new Triple("zeggen", "TEMP", "vanmorgen", "vanmorgen"));

        Result result = new Result(",,De stakingen zullen worden uitgebreid als er vakbondsleiders worden gearresteerd'', zei Kwon Yong-Kil, leider van de KCTU, vanmorgen.", searchTriples);

        expResult.add(result);

        //Get Results and compair
        List testResult = instance.getDocuments(metadata, triples);

        if (testResult.size() == expResult.size()) {
            for (int i = 0; i < testResult.size(); i++) {
                if (!testResult.get(i).equals(expResult.get(i))) {
                    fail("Results are not equal on position " + i);
                }
            }
        } else {
            fail("Expected a different size of expResult!");
        }
    }
}
