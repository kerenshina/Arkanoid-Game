package menu;

import animation.AnimationRunner;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import sprites.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * MenuAnimation class.
 * @param <T> generic
 */
public class MenuAnimation<T> implements Menu<T> {
    private boolean runSubMenu;
    private String menuTitle;
    private boolean stop;
    private boolean isAlreadyPressed;
    private T status;
    private Sprite background;
    private KeyboardSensor sensor;
    private AnimationRunner runner;
    private List<TaskSelection> taskSelectionList;
    private Menu<T> subMenu;

    /**
     * constructor.
     * @param menuTitle the title of the menu
     * @param sensor KeyboardSensor
     * @param runner AnimationRunner
     */
    public MenuAnimation(String menuTitle, KeyboardSensor sensor, AnimationRunner runner) {
        this.menuTitle = menuTitle;
        this.sensor = sensor;
        this.runner = runner;
        this.taskSelectionList = new ArrayList<TaskSelection>();
        this.stop = false;
        this.isAlreadyPressed = true;
        this.runSubMenu = false;
    }

    @Override
    public void addSelection(String key, String message, Object returnVal) {
        this.taskSelectionList.add(new TaskSelection(key, message, (T) returnVal));
    }

    @Override
    public T getStatus() {
        if (this.runSubMenu) {
            this.stop = subMenu.shouldStop();
            this.runSubMenu = false;
            return this.subMenu.getStatus();
        } else {
            this.stop = false;
            return this.status;
        }
    }

    @Override
    public void addSubMenu(String key, String message, Menu<T> sub) {
        this.taskSelectionList.add(new TaskSelection(key, message, sub));
        this.subMenu = sub;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        int i = 1;

        this.background = new MenuBackground(taskSelectionList.size());
        this.background.drawOn(d);

        for (TaskSelection t : this.taskSelectionList) {
            t.writeTask(d, i, this.taskSelectionList.size());
            i++;
        }

        if (runSubMenu) {
            subMenu.doOneFrame(d);
            this.stop = subMenu.shouldStop();
        } else {
            for (TaskSelection task : this.taskSelectionList) {
                if (this.sensor.isPressed(task.getKey())) {
                    if (!isAlreadyPressed) {
                        if (task.getReturnValue() == subMenu) {
                            this.runSubMenu = true;
                        } else {
                            this.status = (T) task.getReturnValue();
                            this.stop = true;
                        }
                        this.isAlreadyPressed = false;
                    } else {
                        this.isAlreadyPressed = false;
                    }
                }
            }
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
