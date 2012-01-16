package nl.ru.cs.phasar.mediator.userquery;

import java.util.Date;

/**
 *
 * @author bartz
 */
public class Metadata {

    private Date year;
    private String matchingType;
    private String browsingType;

    public Metadata(Date year, String matchingType, String browsingType) {
        this.browsingType = browsingType;
        this.matchingType = matchingType;
        this.year = year;
    }

    public Date getYear() {
        return this.year;
    }

    public String getMatchingType() {
        return this.matchingType;
    }

    public String getBrowsingType() {
        return this.browsingType;
    }
}
