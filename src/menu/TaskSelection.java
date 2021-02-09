package menu;

import biuoop.DrawSurface;

import java.awt.Color;

/**
 * TaskSelection class.
 * @author shinake
 * @param <T> generic
 */
public class TaskSelection<T> {
    private String key;
    private String message;
    private T returnValue;

    /**
     * constructor.
     * @param key the key that is connected to this task.
     * @param message the message that will be displayed in the menuAnimation.
     * @param returnValue the value that will be returned when choosing this task.
     */
    public TaskSelection(String key, String message, T returnValue) {
        this.key = key;
        this.message = message;
        this.returnValue = returnValue;
    }

    /**
     * @return returnValue.
     */
    public T getReturnValue() {
        return this.returnValue;
    }

    /**
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * write the task on the menu screen.
     * @param d DrawSurface
     * @param rank the position of the task in the menu
     * @param size the number of tasks that are in the menu.
     */
    public void writeTask(DrawSurface d, int rank, int size) {
        d.setColor(new Color(88, 139, 139));
        d.drawText(250, 200 + (200 / size) * rank, "press   \"" + key + "\"   to " + message, 20);
    }
}
