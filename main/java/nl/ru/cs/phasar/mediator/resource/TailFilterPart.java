package nl.ru.cs.phasar.mediator.resource;

import nl.ru.cs.phasar.mediator.documentsource.Triple;

/**
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public class TailFilterPart extends AbstractFilterPart {

    public TailFilterPart() {
	super();
    }

    @Override
    protected void setfilterPartResult(Triple triple) {
	super.filterPartResult = triple.getGroundTail();
    }
}
