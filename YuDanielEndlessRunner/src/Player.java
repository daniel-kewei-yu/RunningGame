
import java.awt.Image;

public class Player extends GameObject {

    private static int lives = 3;
    
    private boolean isJumping = false;
    private int jumpForce = 0;
    
    private final int velDecay = 1;
    private final int groundY;
    
    private boolean onPlatform = false;
    
    //Total number of  jump
    private int maxJumps = 2;
    //Initially, number of jumps is at 2, so it is equal to max jumps
    private int numJumps = maxJumps;

    public Player(int x, int y, int width, int height, int speed, Image image, int lives) {
        super(x, y, width, height, image, speed);
        this.lives = lives;
        this.groundY = 485;
    }

    /*
    * Logic for platform
    * Checks collisions for ground, collectibles, obstacles, and platform
    */
    
    @Override
    public void update() {
        onPlatform = false;

        // If not on platform, it falls
        if (!onPlatform) {
            y -= jumpForce;
            jumpForce -= velDecay;
        }

        // Check ground collision
        if (y >= groundY - height && jumpForce < 0) {
            y = groundY - height;
            isJumping = false;
            jumpForce = 0;
            onPlatform = false;
            numJumps = maxJumps;
        }

        //Checks the collisions with the gameObjects
        checkAllCollisions();
    }

    //Jump method
    public void jump() {
        if (numJumps > 0) {
            isJumping = true;
            jumpForce = 22;
            onPlatform = false;
            y -= jumpForce;
            jumpForce -= velDecay;
            //Decrease jump count
            numJumps--;
        }
    }

    /*
    *Resets the player's y positions, booleans, speed, and number of jumps
    */
    @Override
    public void reset() {
        y = groundY - height;
        isJumping = false;
        jumpForce = 0;
        onPlatform = false;
        numJumps = maxJumps;  // Reset jumps when on ground
    }

    //For loop to check collisions of the differen types of objects, each one having their own handling method
    private void checkAllCollisions() {
        GameObject[] objects = GameManager.getGameObjects();

        //Checks collectible collisions
        for (int i = 7; i < 12; i++) {
            GameObject obj = objects[i];
            if (isCollidingWith(obj) == true) {
                handleCollectibleCollision((Collectible) obj);
            }
        }
        //Checks obstacle collisions
        for (int i = 12; i < 17; i++) {
            GameObject obj = objects[i];
            if (isCollidingWith(obj) == true) {
                handleObstacleCollision((Obstacle) obj);
            }
        }
        //Checks platform collisions
        for (int i = 17; i < 19; i++) {
            GameObject obj = objects[i];
            Platform platform = (Platform) obj;
            if (isCollidingWith(platform) == true) {
                handlePlatformCollision((Platform) obj);
            }
        }
    }

    private boolean isCollidingWith(GameObject other) {
        return x < other.getX() + other.getWidth()
                && x + width > other.getX()
                && y < other.getY() + other.getHeight()
                && y + height > other.getY();
    }

    //Overloading
    private boolean isCollidingWith(Platform platform) {
        // Player's bottom position
        double playerBottom = y + height;

        //Only allow landing if:
        //Player is above platform
        //Player is moving downward
        //Player is within the platform x positions
        return playerBottom <= platform.y
                && this.getJumpForce() < 0
                && (playerBottom - this.getJumpForce()) >= platform.y
                //The x values of the player is within the platform
                && ((this.getX() + this.getWidth() > platform.x && this.getX() < platform.x + platform.width)
                || //The right side is within the the platform but not the left side of the player
                (this.getX() + this.getWidth() > platform.x && this.getX() < platform.x)
                || //The left side is within the the platform but not the left side of the player
                (this.getX() + this.getWidth() > platform.x + platform.width && this.getX() < platform.x + platform.width));
    }

    private void handleObstacleCollision(Obstacle obstacle) {
        lives--;
        obstacle.reset();
        //In case the player somehow hits two obstacles at the same time
        if (lives <= 0) {
            lives = 0;
        }
    }

    private void handlePlatformCollision(Platform platform) {
        y = platform.getY() - height;
        onPlatform = true;
        isJumping = false;
        jumpForce = 0;
        numJumps = maxJumps;  // Reset jumps on player reset

    }

    private void handleCollectibleCollision(Collectible collectible) {
        GameManager.addPoints(collectible.collect());
    }

    //Update the validity of the player being on the platform
    public boolean isOnPlatform() {
        return onPlatform;
    }

    // Getters and setters 
    public boolean isJumping() {
        return isJumping;
    }

    public int getJumpForce() {
        return jumpForce;
    }

    public static int getLives() {
        return lives;
    }

    public int getVelDecay() {
        return velDecay;
    }

    public int getGroundY() {
        return groundY;
    }

    public static void setLives(int lives) {
        Player.lives = lives;
    }

    public void setOnPlatform(boolean onPlatform) {
        this.onPlatform = onPlatform;
    }

    public int getMaxJumps() {
        return maxJumps;
    }

    public void setMaxJumps(int maxJumps) {
        this.maxJumps = maxJumps;
    }

    public int getNumJumps() {
        return numJumps;
    }

    public void setNumJumps(int numJumps) {
        this.numJumps = numJumps;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    public void setJumpForce(int jumpForce) {
        this.jumpForce = jumpForce;
    }
}