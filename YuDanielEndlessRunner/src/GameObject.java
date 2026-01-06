
import java.awt.Image;

/**
 * Abstract base class for all game objects in the endless runner game. Provides
 * common functionality and requires subclasses to implement reset behavior.
 */
public abstract class GameObject {

    //Protected fields for encapsulation. Therefore, it is accessible to subclasses
    protected double x;          // X position
    protected double y;          // Y position
    protected int width;         // Object width
    protected int height;        // Object height
    protected Image image;       // Visual representation
    protected int speed;
    
    public GameObject(double x, double y, int w, int h, Image image, int speed){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.image = image;
        this.speed = speed;
    }
    
    /**
     * Updates the object's state each frame
     */
    public abstract void update();

    /**
     * Resets the object's position
     * Implementation occurs in the subclasses: Player, Collectible, Obstacle, Background, Platform
     */
    public abstract void reset();

    //Getters and Setters for all fields
    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
