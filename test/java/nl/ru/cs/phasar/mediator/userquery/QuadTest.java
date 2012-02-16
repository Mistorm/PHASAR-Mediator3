
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
        
        Quad instance = new Quad(0, "MOD", 2, 2);
        
        String[] expResult = new String[3];
        expResult[0] = "aansluiting";
        expResult[1] = "MOD";
        expResult[2] = "heel";
        
        String[] result = instance.toTriple(boxes);
        
        for(int i = 0; i < result.length; i++){
            assertTrue("Result on position " + i + " is not equal.", result[i].equals(expResult[i]));
        }
    }
}
