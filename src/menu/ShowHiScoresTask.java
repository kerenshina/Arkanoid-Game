package menu;
import animation.Animation;
import animation.AnimationRunner;

/**
 * ShowHiScoresTask class.
 * @param <T> generic.
 */
public class ShowHiScoresTask<T> implements Task {
    private AnimationRunner runner;
    private Animation highScoresAnimation;

    /**
     * constructor.
     * @param runner AnimationRunner
     * @param highScoresAnimation Animation
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
    }

    @Override
    public Void run() {
        this.runner.run(this.highScoresAnimation);
        return null;
    }
}
