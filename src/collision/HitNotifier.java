package collision;

/**
 * HitNotifier interface.
 *
 * @author shinake
 */
public interface HitNotifier {
    /**
     * Add hl as a listener to hit events.
     *
     * @param hl the wanted listener we want to add.
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl the wanted listener we want to remove.
     */
    void removeHitListener(HitListener hl);
}