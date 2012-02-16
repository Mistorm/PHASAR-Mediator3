package nl.ru.cs.phasar.mediator.userquery;

import junit.framework.TestCase;

/**
 *
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public class SuggestionItemTest extends TestCase {
    
    public SuggestionItemTest(String testName) {
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
     * Test of compareTo method, of class SuggestionItem.
     */
    public void testCompareTo() {
        System.out.println("compareTo");
        SuggestionItem item = new SuggestionItem("kopen", 9);
        
        SuggestionItem instance = new SuggestionItem("liegen", 9);
        int expResult = 1;
        int result = instance.compareTo(item);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class SuggestionItem.
     */
    public void testEquals() {
        System.out.println("equals");
        SuggestionItem item = new SuggestionItem("kopen", 9);
        SuggestionItem instance = new SuggestionItem("liegen", 9);
        boolean expResult = false;
        boolean result = instance.equals(item);
        assertEquals(expResult, result);
    }

}
