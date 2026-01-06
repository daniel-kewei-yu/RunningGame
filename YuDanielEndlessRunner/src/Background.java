
import java.awt.Image;

public class Background extends GameObject {

    private final int minX;
    private final int resetX;

    public Background(int x, int y, int width, int height, int speed,
            int minX, int resetX, Image image) {
        super(x, y, width, height, image, speed);
        this.minX = minX;
        this.resetX = resetX;
    }

    //Override the update method
    /**
     *
     */
    @Override
    public void update() {
        x -= speed;
        if (x < minX) {
            this.reset();
        }
    }

    @Override
    public void reset() {
        x = resetX;
        y = 0;
    }
}
