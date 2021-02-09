package menu;

import animation.Animation;

/**
 * Menu interface.
 * @author shinake
 * @param <T> generic
 */
public interface Menu<T> extends Animation {
    /**
     * @param key String
     * @param message String
     * @param returnVal T
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * @return the status.
     */
    T getStatus();

    /**
     * adding sub-menus to main menu.
     * @param key String
     * @param message String
     * @param subMenu Menu<T>
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
}
