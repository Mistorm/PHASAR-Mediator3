package nl.ru.cs.phasar.mediator.userquery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import junit.framework.TestCase;
import nl.ru.cs.phasar.mediator.documentsource.DocumentSource;
import nl.ru.cs.phasar.mediator.documentsource.MockDocumentSource;
import nl.ru.cs.phasar.mediator.documentsource.Triple;

/**
 *
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public class QueryTest extends TestCase {

    private Metadata metadata;
    private Query query;
    private DocumentSource source;

    public QueryTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        metadata = new Metadata(new Date(), null, "sentence");
        source = new MockDocumentSource();
        String json = "{\"boxes\":[{\"nr\":\"0\", \"content\":\"zullen\"},{\"nr\":\"1\", \"content\":\"nog\"},"
                + "], \"arrows\":[{\"a\":\"0\", \"relator\":\"MOD\", \"b\":\"1\", \"direction\":\"1\"}]}";
        query = new Query(metadata, source, json);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getSuggestion method, of class Query.
     */
    public boolean GetSuggestion(Triple triple, String position, List<SuggestionItem> expected) {
        System.out.println("getSuggestion");
        List<SuggestionItem> result = query.getSuggestion(triple, position);
        return expected.equals(result);
    }

    public void testGetHeadSuggestion() {
        Triple triple = new Triple("*", "MOD", "nog", "nog");

        List<SuggestionItem> expectedItems = new ArrayList<SuggestionItem>();
        expectedItems.add(new SuggestionItem("zullen", 1));

        assertTrue(GetSuggestion(triple, "head", expectedItems));
    }
    
    public void testGetRelatorSuggestion() {
        Triple triple = new Triple("verhogen", "*", "gisteren", "gisteren");

        List<SuggestionItem> expectedItems = new ArrayList<SuggestionItem>();
        expectedItems.add(new SuggestionItem("TEMP", 1));

        assertTrue(GetSuggestion(triple, "relator", expectedItems));
    }
    
    public void testGetTailSuggestion() {
        Triple triple = new Triple("verhogen", "*", "*", "*");

        List<SuggestionItem> expectedItems = new ArrayList<SuggestionItem>();
        expectedItems.add(new SuggestionItem("kwart", 1));
        expectedItems.add(new SuggestionItem("gisteren", 1));

        assertTrue(GetSuggestion(triple, "tail", expectedItems));
    }
}
