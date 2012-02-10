package nl.ru.cs.phasar.mediator.userquery;

/**
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public class SuggestionItem implements Comparable<SuggestionItem> {

    private String key;
    private Integer count;

    public SuggestionItem() {
    }

    public SuggestionItem(String key, Integer count) {
        this.key = key;
        this.count = count;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void incrementCount() {
        this.count = this.count + 1;
    }

    @Override
    public int compareTo(SuggestionItem item) {
        int compair = this.getCount().compareTo(item.getCount());
        if (compair == 0) {
            compair = this.getKey().compareTo(item.getKey());
        }
        return compair;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj.getClass() == this.getClass()) {
            if (this.compareTo((SuggestionItem) obj) == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + ( this.key != null ? this.key.hashCode() : 0 );
        hash = 89 * hash + ( this.count != null ? this.count.hashCode() : 0 );
        return hash;
    }
}
