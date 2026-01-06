
import java.awt.Image;

public class Obstacle extends GameObject {

    private static int obstacleCount = 0;
    private static final int minSpacing = 200;
    private static int activeCount = 1;

    public Obstacle(double x, double y, int width, int height, int speed, Image image) {
        super(x, y, width, height, image, speed);
        obstacleCount++;
    }

    
    /*
    *Update the speeds
    */
    @Override
    public void update() {

        this.x -= this.getSpeed();
        if (x < -getWidth()) {
            this.reset();
        }
    }

    /*
    *Resets the positions
    */
    @Override
    public void reset() {
        GameObject[] objects = GameManager.getGameObjects();
        double rightmostX = 960; // Default start position

        // ind the rightmost edge among ALL obstacles (active or not)
        for (int i = 12; i < 17; i++) {
            if (objects[i] != null && objects[i] != this) {
                double currentRight = objects[i].getX() + objects[i].getWidth();
                if (currentRight > rightmostX) {
                    rightmostX = currentRight;
                }
            }
        }

        // Place this obstacle after the rightmost one
        this.x = rightmostX + minSpacing + (int) (Math.random() * 200);
        this.y = GameManager.getPossibleYPositions()[(int) (Math.random() * GameManager.getPossibleYPositions().length)];
    }

    public static int getObstacleCount() {
        return obstacleCount;
    }

    public static void resetObstacleCount() {
        obstacleCount = 0;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public static int getActiveCount() {
        return activeCount;
    }

    public static void setActiveCount(int count) {
        activeCount = count;
    }

    public static int getMinSpacing() {
        return minSpacing;
    }
}
