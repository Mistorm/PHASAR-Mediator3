package nl.ru.cs.phasar.mediator.documentsource;

/**
 * Class representation of a triple. A triple has a head, relator and tail value
 * and represents two words and the relationship between these words plus the direction
 * of the relationship.
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public class Triple {

    private String head;
    private String relator;
    private String tail;
    private String direction;
    private static String WILDCARD = "*";

    /**
     * Create a new <code>Triple</code>
     * @param head
     * @param relator
     * @param tail
     * @param direction Should equal the value of <code>head</code> or <code>tail<code>
     * @throws IllegalArgumentException if direction is not equal to <code>head</code> or <code>tail<code>
     */
    public Triple(String head, String relator, String tail, String direction) {
        this.head = head;
        this.relator = relator;
        this.tail = tail;
        if (direction.equals(head) || direction.equals(tail)) {
            this.direction = direction;
        } else {
            throw new IllegalArgumentException(direction + " is not equal to head or tail");
        }
    }

    /**
     * Create a new triple based on a <code>String</code> array in the following format: [head][relator][tail]
     * Direction is set to [tail].
     * @param String[] string
     * @throws IllegalArgumentException If the <code>String</code> array has more of less then three values.
     */
    public Triple(String[] string) {
        if (string.length != 3) {
            throw new IllegalArgumentException("Argument has lenght" + string.length + " , lenght should be 3.");
        } else {
            head = string[0];
            relator = string[1];
            tail = string[2];
            direction = string[2];
        }
    }

    public String getHead() {
        return head;
    }

    /**
     * Return the real(ground) head of the triple.
     * @return head
     */
    public String getGroundHead() {
        if (direction.equals(tail)) {
            return head;
        } else {
            return tail;
        }
    }

    public String getRelator() {
        return relator;
    }

    public String getTail() {
        return tail;
    }

    /**
     * Return the real(ground) tail of the triple.
     * @return tail
     */
    public String getGroundTail() {
        if (direction.equals(tail)) {
            return tail;
        } else {
            return head;
        }
    }

    public String getDirection() {
        return direction;
    }

    /**
     * Return a human readable <code>String</code> representing the triple. 
     * @return 
     */
    @Override
    public String toString() {
        if (direction.equals(tail)) {
            return head + " " + relator + " " + tail;
        } else {
            return tail + " " + relator + " " + head;
        }
    }

    /**
     * Check if the given <code>Triple</code> matches the current <code>Triple</code>
     * @param triple
     * @return boolean True is a match, false otherwise.
     */
    public boolean match(Triple triple) {
        Boolean match = false;

        if (( ( this.getGroundHead().equals(triple.getGroundHead()) ) || ( triple.getGroundHead().equals(WILDCARD) ) )
                && ( ( this.relator.equals(triple.relator) ) || ( triple.getRelator().equals(WILDCARD) ) )
                && ( ( this.getGroundTail().equals(triple.getGroundTail()) ) || ( triple.getTail().equals(WILDCARD) ) )) {
            match = true;
        }

        return match;
    }
}
