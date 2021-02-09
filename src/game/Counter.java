package game;

/**
 * Counter class.
 *
 * @author shinake
 */
public class Counter {

    private int count;

    /**
     * Constructor.
     *
     * @param count the start number of counter.
     */
    public Counter(int count) {
        this.count = count;
    }

    /**
     * add number to current count.
     *
     * @param number the number we want to add to count.
     */
    public void increase(int number) {
        this.count += number;
    }

    /**
     * subtract number from current count.
     *
     * @param number the number we want to subtract from count.
     */
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * get current count.
     *
     * @return the current count.
     */
    public int getValue() {
        return this.count;
    }
}
