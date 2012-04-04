package nl.ru.cs.phasar.mediator.userquery;

import java.util.HashMap;

/**
 *
 * @author bartvz
 */
public class Quad {

    private Integer a;
    private String relator;
    private Integer b;
    private Integer direction;
    private static String WILDCARD = "*";

    public Quad(Integer a, String relator, Integer b, Integer direction) {
	this.a = a;
	this.relator = relator;
	this.b = b;
	this.direction = direction;
    }

    public Integer getDirection() {
	return this.direction;
    }

    public void setRelator(String relator) {
	this.relator = relator;
    }

    public String getRelator() {
	return this.relator;
    }

    public Integer getA() {
	return this.a;
    }

    public Integer getB() {
	return this.b;
    }

    public String getPosition(Integer boxNr) {

	if (this.a == boxNr || this.b == boxNr) {
	    if (boxNr == this.direction) {
		return "tail";
	    } else {
		return "head";
	    }
	}

	return null;
    }

    public String[] toTriple(HashMap<Integer, String> boxes) {

	String[] triple = new String[3];
	triple[1] = this.getRelator();

	if (this.getDirection() == this.getB()) {
	    triple[0] = boxes.get(this.getA());
	    triple[2] = boxes.get(this.getB());
	} else if (this.getDirection() == this.getA()) {
	    triple[0] = boxes.get(this.getB());
	    triple[2] = boxes.get(this.getA());
	}

	return triple;
    }
}
