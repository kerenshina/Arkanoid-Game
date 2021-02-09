package menu;

/**
 * Task interface.
 * @author shinake
 * @param <T> generic
 */
public interface Task<T> {
    /**
     * run the task. return T.
     * @return the return value of the task.
     */
    T run();
}
