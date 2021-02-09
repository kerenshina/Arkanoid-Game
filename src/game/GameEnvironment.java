package game;

import collision.Collidable;
import collision.CollisionInfo;
import geometry.Line;
import geometry.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * GameEnvironment class.
 *
 * @author shinake
 */
public class GameEnvironment {
    private List<Collidable> collidables = new ArrayList<Collidable>();
    private Iterator<Collidable> iterator;

    // add the given collidable to the environment.

    /**
     * Adds the given collidable to the environment.
     *
     * @param c the given collidable.
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Get the closest collision information.
     * this includes the collision point and the block the collision happens with.
     *
     * @param trajectory the "line" that the object moves on.
     * @return if this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        List<Point> collidablePoints = new ArrayList<Point>();
        List<Collidable> collidableObjects = new ArrayList<Collidable>();
        int indexCounter = -1;
        Point temp;
        double min = Double.MAX_VALUE;

        for (Collidable i : collidables) {
            temp = trajectory.closestIntersectionToStartOfLine(i.getCollisionRectangle());
            if (temp != null) {
                collidablePoints.add(temp);
                collidableObjects.add(i);
            }
        }

        if (collidablePoints.size() == 0) {
            return null;
        }

        for (int j = 0; j < collidablePoints.size(); j++) {
            if (collidablePoints.get(j) != null) {
                if (trajectory.start().distance(collidablePoints.get(j)) < min) {
                    min = trajectory.start().distance(collidablePoints.get(j));
                    indexCounter = j;
                }
            }
        }

        if (indexCounter == -1) {
            return null;
        }

        CollisionInfo closestCollision = new CollisionInfo(collidableObjects.get(indexCounter), collidablePoints.
                get(indexCounter));
        return closestCollision;
    }

    /**
     * @return the Collidable list that the game environment holds.
     */
    public List<Collidable> collidablesList() {
        return this.collidables;
    }

    /**
     * Removing a Collidable from list.
     *
     * @param c the Collidable we want to remove.
     */
    public void removeCollidable(Collidable c) {
        makeItr();
        while (iterator.hasNext()) {
            Collidable c1 = iterator.next();
            if (c1.equals(c)) {
                iterator.remove();
                break;
            }
        }
    }

    /**
     * Creating an iterator for this Collidables list.
     */
    private void makeItr() {
        Iterator<Collidable> itr = collidables.iterator();
        this.iterator = itr;
    }
}