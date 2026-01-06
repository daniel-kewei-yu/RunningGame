
import java.awt.Toolkit;

public final class GameManager {

    private GameManager() {
    }

    //Points
    private static int points = 0;

    //GameObjects array
    private static GameObject[] gameObjects = new GameObject[19];

    //Possible y positions array used for collectibles and obstacles
    private static final int[] possibleYPositions = {100, 238, 425};

    //Possible y positions arary used for platforms
    private static final int[] possibleYPlatformPositions = {150, 288};

    //Variables for timer
    private static long startTime = System.currentTimeMillis();
    //Every ten seconds, the speed increases
    private static final int difficultyTimeInterval = 10000;
    //Max difficulty is 5 since there are 5 obstacles in total
    private static final int maxDifficulty = 5;

    public static void setup() {

        //Create the array to store the gameObjects
        gameObjects = new GameObject[19];

        // Background layers
        gameObjects[0] = new Background(0, 0, 960, 600, 2, -960, 0, Toolkit.getDefaultToolkit().getImage("BackgroundBack.png"));
        gameObjects[1] = new Background(960, 0, 960, 600, 2, 0, 960, Toolkit.getDefaultToolkit().getImage("BackgroundBack.png"));

        // Middle layers
        gameObjects[2] = new Background(0, 0, 960, 600, 4, -960, 0, Toolkit.getDefaultToolkit().getImage("BackgroundMiddle.png"));
        gameObjects[3] = new Background(960, 0, 960, 600, 4, 0, 960, Toolkit.getDefaultToolkit().getImage("BackgroundMiddle.png"));

        // Front layers
        gameObjects[4] = new Background(0, 0, 960, 600, 6, -960, 0, Toolkit.getDefaultToolkit().getImage("BackgroundFront.png"));
        gameObjects[5] = new Background(960, 0, 960, 600, 6, 0, 960, Toolkit.getDefaultToolkit().getImage("BackgroundFront.png"));

        // Player
        gameObjects[6] = new Player(50, 425, 60, 60, 0, Toolkit.getDefaultToolkit().getImage("SpongeBobPlayer.png"), 3);

        // Collectibles
        setupCollectibles();
        setupObstacles();
        setupPlatforms();
    }

    //Getters for the subclasses
    public static int[] getPossibleYPositions() {
        return possibleYPositions;
    }

    public static int[] getPossibleYPlatformPositions() {
        return possibleYPlatformPositions;
    }

    //Create seperate setUp for orgnization and easier to find
    private static void setupCollectibles() {

        //Randomize the x-value
        int randomX;
        //Randomize the index
        int randomYIndex;
        //Set the collectible's y value to that random index of the possible y values array
        int randomY;

        //For loop to set the speed for the obstacles
        for (int i = 7; i < 12; i++) {
            // Find rightmost edge of all existing collectibles
            double rightmostX = 960;
            for (int j = 7; j < i; j++) {
                double currentRight = gameObjects[j].getX() + gameObjects[j].getWidth();
                if (currentRight > rightmostX) {
                    rightmostX = currentRight;
                }
            }

            //Random coordinates for the subsequent collectible
            randomX = (int) (rightmostX + 50 + (int) (Math.random() * 200));
            randomYIndex = (int) (Math.random() * possibleYPositions.length);
            randomY = possibleYPositions[randomYIndex];
            gameObjects[i] = new Collectible(randomX, randomY, 50, 50,
                    6, Toolkit.getDefaultToolkit().getImage("KrabbyPattyCollectible.png"));
        }
    }

    private static void setupObstacles() {

        //Random x int
        int randomX = 960 + 50 + Obstacle.getMinSpacing() + (int) (Math.random() * 200);
        int randomYIndex = (int) (Math.random() * possibleYPositions.length);
        int randomY = possibleYPositions[randomYIndex];
        gameObjects[12] = new Obstacle(randomX, randomY, 50, 50,
                6, Toolkit.getDefaultToolkit().getImage("BubbleBassObstacle.png"));

        //Subsequent obstacles
        double rightmostX = 960;
        for (int i = 13; i < 17; i++) {
            // Find rightmost edge of all existing obstacles
            for (int j = 12; j < i; j++) {
                double currentRight = gameObjects[j].getX() + gameObjects[j].getWidth();
                if (currentRight > rightmostX) {
                    rightmostX = currentRight;
                }
            }

            //Random spacing between 200 and 400
            randomX = (int) (rightmostX + Obstacle.getMinSpacing() + (int) (Math.random() * 200));
            randomYIndex = (int) (Math.random() * possibleYPositions.length);
            randomY = possibleYPositions[randomYIndex];
            gameObjects[i] = new Obstacle(randomX, randomY, 50, 50, 0, Toolkit.getDefaultToolkit().getImage("BubbleBassObstacle.png"));
        }
    }

    private static void setupPlatforms() {
        //Set up the x value of the platform to be 200 to 400 pixels of the right edge of the screen
        int firstPlatformX = 960 + Platform.getMinSpacing() + (int) (Math.random() * Platform.getSpacingRange());
        int firstPlatformYIndex = (int) (Math.random() * possibleYPlatformPositions.length);
        int firstPlatformY = possibleYPlatformPositions[firstPlatformYIndex];
        gameObjects[17] = new Platform(firstPlatformX, firstPlatformY, 300, 50, 6, Toolkit.getDefaultToolkit().getImage("Platform.png"));

        //Set up the x value of the platform to be 200 to 400 pixels of the right edge of the screen
        int secondPlatformX = firstPlatformX + 300 + Platform.getMinSpacing() + (int) (Math.random() * Platform.getSpacingRange());
        int secondPlatformYIndex = (int) (Math.random() * possibleYPlatformPositions.length);
        int secondPlatformY = possibleYPlatformPositions[secondPlatformYIndex];
        gameObjects[18] = new Platform(secondPlatformX, secondPlatformY, 300, 50, 6, Toolkit.getDefaultToolkit().getImage("Platform.png"));
    }

    static void update() {

        //Update all game objects, which includes the player, the objects, the collectibles, and the platforms
        for (int i = 0; i < gameObjects.length; i++) {
            gameObjects[i].update();
        }
        points++;
        updateDifficulty();
    }

    //Difficulty system
    private static void updateDifficulty() {

        //Takes the current time
        double currentTime = System.currentTimeMillis();
        double elapsedTime = currentTime - startTime;
        int difficultyLevel = Math.min((int) (elapsedTime / difficultyTimeInterval), maxDifficulty);

        /*If the difficulty level is greater than the number of active obstacles - 1, 
          then it increases difficulty by 1 and the number of active obstacles by 1*/
        if (difficultyLevel > (Obstacle.getActiveCount() - 1)) {
            Obstacle.setActiveCount(difficultyLevel + 1);

            //Update the speed of the backgrounds
            for (int i = 0; i < 6; i++) {
                if (i < 2) {
                    gameObjects[i].setSpeed(4 + difficultyLevel);
                } else if (i < 4) {
                    gameObjects[i].setSpeed(5 + difficultyLevel);
                } else {
                    gameObjects[i].setSpeed(6 + difficultyLevel);
                }
            }

            //Update the speed of the collectibles
            for (int i = 7; i < 12; i++) {
                gameObjects[i].setSpeed(6 + difficultyLevel);
            }

            //Update the speeds of the obstacles
            for (int i = 12; i < 12 + Obstacle.getActiveCount(); i++) {
                gameObjects[i].setSpeed(6 + difficultyLevel);
            }

            //Update the speeds of the platforms
            for (int i = 17; i < 19; i++) {
                gameObjects[i].setSpeed(6 + difficultyLevel);
            }
            updateObjectSpeeds();
        }
    }
    /*
    * Update the object speeds
    */
    private static void updateObjectSpeeds() {
        //Update background speeds
        for (int i = 0; i < 6; i++) {
            if (i < 2) {
                gameObjects[i].setSpeed(gameObjects[i].getSpeed());
            } else if (i < 4) {
                gameObjects[i].setSpeed(gameObjects[i].getSpeed());
            } else {
                gameObjects[i].setSpeed(gameObjects[i].getSpeed());
            }
        }

        //Update collectible speeds
        for (int i = 7; i < 12; i++) {
            gameObjects[i].setSpeed(gameObjects[i].getSpeed());
        }

        //Update obstacle speeds
        for (int i = 12; i < 12 + Obstacle.getActiveCount(); i++) {
            if (i < gameObjects.length) {
                gameObjects[i].setSpeed(gameObjects[i].getSpeed());
            }
        }

        //Update the platform speeds
        for (int i = 17; i < 19; i++) {
            gameObjects[i].setSpeed(gameObjects[i].getSpeed());
        }
    }

    //Restart game
    public static void restartGame() {
        // Reset game state
        points = 0;
        Player.setLives(3);
        Obstacle.setActiveCount(1);
        startTime = System.currentTimeMillis();

        // Reset counts
        Collectible.resetCollectibleCount();
        Obstacle.resetObstacleCount();

        // Reinitialize all game objects
        setup();
    }

    public static void gameOver() {
        // Set all default speeds to 0
        for (int i = 0; i < gameObjects.length; i++) {
            gameObjects[i].setSpeed(0);
        }
    }

    //Getter for gameobjects used in the Main drawing area
    public static GameObject[] getGameObjects() {
        return gameObjects;
    }

    //Getter to get points
    public static int getPoints() {
        return points;
    }

    //Add points method
    public static void addPoints(int amount) {
        points += amount;
    }
}
