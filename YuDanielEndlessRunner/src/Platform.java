
import java.awt.Image;

public class Platform extends GameObject {

    private static final int minSpacing = 200;
    private static final int spacingRange = 200;

    //Constuctor for platform
    public Platform(double x, double y, int width, int height, int speed, Image image) {
        super(x, y, width, height, image, speed);
    }

    /*
    *Update the speed
    */
    
    @Override
    public void update() {
        this.x -= speed;
        if (x < -getWidth()) {
            this.reset();
        }
    }

    /*
    *Resets positions
    */
    @Override
    public void reset() {
        GameObject[] objects = GameManager.getGameObjects();
        double rightmostX = 960;

        // Find the rightmost platform
        for (int i = 17; i <= 18; i++) {
            if (objects[i] != null && objects[i] != this) {
                double currentRight = objects[i].getX() + objects[i].getWidth();
                if (currentRight > rightmostX) {
                    rightmostX = currentRight;
                }
            }
        }

        // Place this platform after the rightmost one
        this.x = rightmostX + getMinSpacing() + (int) (Math.random() * getSpacingRange());
        this.y = GameManager.getPossibleYPlatformPositions()[(int) (Math.random() * GameManager.getPossibleYPlatformPositions().length)];
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public static int getMinSpacing() {
        return minSpacing;
    }

    public static int getSpacingRange() {
        return spacingRange;
    }
}
