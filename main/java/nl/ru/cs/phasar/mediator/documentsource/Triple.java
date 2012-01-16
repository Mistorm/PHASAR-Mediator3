package nl.ru.cs.phasar.mediator.documentsource;

/**
 *
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public class Triple {

    private String a;
    private String relator;
    private String b;
    private String direction;
    private static String WILDCARD = "*";

    public Triple(String a, String relator, String b, String direction) {
        this.a = a;
        this.relator = relator;
        this.b = b;
        if (direction.equals(a) || direction.equals(b)) {
            this.direction = direction;
        } else {
            throw new IllegalArgumentException(direction + " is not equal to a or b");
        }
    }

    /*
     * Create a new triple based on a string array in the following format: [a][relator][b]
     * Direction is set to [b].
     * @param String[] string
     */
    public Triple(String[] string) {
        if (string.length != 3) {
            throw new IllegalArgumentException("Argument has lenght" + string.length + " , lenght should be 3.");
        } else {
            a = string[0];
            relator = string[1];
            b = string[2];
            direction = string[2];
        }
    }

    public String getA() {
        return a;
    }

    public String getGroundA() {
        if (direction.equals(b)) {
            return a;
        } else {
            return b;
        }
    }

    public String getRelator() {
        return relator;
    }

    public String getB() {
        return b;
    }

    public String getGroundB() {
        if (direction.equals(b)) {
            return b;
        } else {
            return a;
        }
    }

    public String getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        if (direction.equals(b)) {
            return a + " " + relator + " " + b;
        } else {
            return b + " " + relator + " " + a;
        }
    }

    public boolean match(Triple triple) {
        Boolean match = false;

        if (( ( this.getGroundA().equals(triple.getGroundA()) ) || ( triple.getGroundA().equals(WILDCARD) ) )
                && ( ( this.relator.equals(triple.relator) ) || ( triple.getRelator().equals(WILDCARD) ) )
                && ( ( this.getGroundB().equals(triple.getGroundB()) ) || ( triple.getB().equals(WILDCARD) ) )) {
            match = true;
        }

        return match;
    }
}
