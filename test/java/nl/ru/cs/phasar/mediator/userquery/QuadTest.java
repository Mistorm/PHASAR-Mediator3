
package nl.ru.cs.phasar.mediator.userquery;

import java.util.HashMap;
import junit.framework.TestCase;

/**
 *
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public class QuadTest extends TestCase {
    
    public QuadTest(String testName) {
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
     * Test of toTriple method, of class Quad.
     */
    public void testToTriple() {
        System.out.println("toTriple");
        
        HashMap<Integer, String> boxes = new HashMap<Integer, String>();
        boxes.put(0, "aansluiting");
        boxes.put(1, "logische");
        boxes.put(2, "heel");
        boxes.put(3, "activiteiten");
        boxes.put(4, "bestaande");
        
        Quad instance = null;
        String[] expResult = null;
        String[] result = instance.toTriple(boxes);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
