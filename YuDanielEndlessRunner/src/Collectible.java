
import java.awt.Image;

public class Collectible extends GameObject {

    private static int collectibleCount = 0;
    private static int defaultSpeed = 6;

    public Collectible(double x, double y, int width, int height, int speed, Image image) {
        super(x, y, width, height, image, speed);
        collectibleCount++;
    }

    /*
    *Update object speeds
    */
    @Override
    public void update() {
        this.x -= speed;
        if (x < -getWidth()) {
            this.reset();
        }
    }

    /*
    *Reset the object coordinates
    */
    @Override
    public void reset() {
        GameObject[] objects = GameManager.getGameObjects();
        double rightmostX = 960;

        //Find the rightmost collectible
        for (int i = 7; i < 12; i++) {
            //Not to compare itself
            if (objects[i] != null && objects[i] != this) {
                //Set the right x to the object x position and width
                double currentRight = objects[i].getX() + objects[i].getWidth();
                if (currentRight > rightmostX) {
                    rightmostX = currentRight;
                }
            }
        }

        // Place this collectible after the rightmost one
        this.x = rightmostX + getWidth() + (int) (Math.random() * 200);
        this.y = GameManager.getPossibleYPositions()[(int) (Math.random() * GameManager.getPossibleYPositions().length)];
    }

// Remove unused field:
// private static int defaultSpeed = 6; 
    //Collects the collectible, so the position resets and the points increases
    public int collect() {
        this.reset();
        return 1000;
    }

    //Get the collectibles
    public static int getCollectibleCount() {
        return collectibleCount;
    }

    
    //Reset collectibles
    public static void resetCollectibleCount() {
        collectibleCount = 0;
    }

    //Set speed
    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
