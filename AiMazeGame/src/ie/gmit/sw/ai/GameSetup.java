package ie.gmit.sw.ai;

import java.util.ArrayList;
import java.util.Random;

public class GameSetup {
	
	private Maze model;
    private Node[][] maze;
    private ControlledSprite player;
    private ArrayList<Spider> spiders;
    
    public GameSetup(Maze model, Node[][] maze, ControlledSprite player, ArrayList<Spider> enemies) {
        setModel(model);
        setMaze(maze);
        setPlayer(player);
        setEnemies(enemies);
    }
    
    public GameSetup() {
    	
    }

    /**
     * Places the player randomly in the maze
     * @param goalPos The goal position in the maze, depending on the number from zero to
     * three it'll select the opposite position in the maze
     */
    public void placePlayer(int goalPos){

        Random random = new Random();
        boolean playerPosSet = false;

        // Continue to loop until a good position is found
        while(playerPosSet != true){

            switch(goalPos){
                case 0:
                    // Creates the player on the top side of the maze
                    getPlayer().setRowPos(random.nextInt((3 - 2) + 1) + 2);
                    getPlayer().setColPos(random.nextInt((getMaze()[0].length - 5) + 1) + 5);
                    break;
                case 1:
                    // Creates the player on the left side of the maze
                    getPlayer().setRowPos(random.nextInt(((getMaze().length - 15) - 1) + 1) + 1);
                    getPlayer().setColPos(random.nextInt((3 - 2) + 1) + 2);
                    break;
                case 2:
                    // Creates the player on the bottom side of the maze
                    getPlayer().setRowPos(random.nextInt(((getMaze().length - 15) - (getMaze().length - 15)) + 1) + (getMaze().length - 15));
                    getPlayer().setColPos(random.nextInt((getMaze()[0].length - 5) + 1) + 5);
                    break;
                default:
                    getPlayer().setRowPos(random.nextInt(((getMaze().length - 15) - 1) + 1) + 1);
                    getPlayer().setColPos(random.nextInt((3 - 2) + 1) + 2);
                    break;
            }

            // Checking if the area is walkable, if true then place the player and setting the node type
            try {
                if(getMaze()[getPlayer().getRowPos()][getPlayer().getColPos()].isWalkable()){
                    getMaze()[getPlayer().getRowPos()][getPlayer().getColPos()].setNodeType('P');
                    getMaze()[getPlayer().getRowPos()][getPlayer().getColPos()].setGoalNode(true);
                    playerPosSet = true;
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * Creates and spawns the enemies in the maze, enemy strength depends on the game difficulty
     * @param gameDifficulty The game difficulty setting
     */
    public void setupEnemies(String gameDifficulty){

        int amount;
        int health;
        int armor;
        int strength;
        int difficulty;
        int bosses;
        boolean isBoss = false;

        Random random = new Random();

        // Randomly choosing the enemy's strengths, depending on difficulty setting
        switch(gameDifficulty){
            case "Easy":
                amount = 35;
                health = random.nextInt((60 - 25) + 1) + 25;
                armor = random.nextInt((35 - 5) + 1) + 5;
                strength = random.nextInt((60 - 30) + 1) + 30;
                difficulty = 0;
                bosses = 3;
                break;
            case "Normal":
                amount = 50;
                health = random.nextInt((75 - 55) + 1) + 55;
                armor = random.nextInt((45 - 25) + 1) + 25;
                strength = random.nextInt((70 - 30) + 1) + 30;
                difficulty = 1;
                bosses = 5;
                break;
            case "Hard":
                amount = 65;
                health = random.nextInt((100 - 40) + 1) + 40;
                armor = random.nextInt((75 - 50) + 1) + 50;
                strength = random.nextInt((80 - 30) + 1) + 30;
                difficulty = 2;
                bosses = 10;
                break;
            default:
                amount = 50;
                health = random.nextInt((75 - 55) + 1) + 55;
                armor = random.nextInt((45 - 25) + 1) + 25;
                strength = random.nextInt((70 - 30) + 1) + 30;
                difficulty = 1;
                bosses = 5;
                break;
        }

        setEnemies(new ArrayList<Spider>());

	/* Creating new enemies with separate threads, game difficult defines
	 * how each enemy object is created, individual enemy has their own strengths and weaknesses
	 */
        for(int i = 0; i < amount; i++){

            if(bosses > 0){
                isBoss = true;
                health = 100;
                armor = 100;
                strength = 1000;
                difficulty = 5;
                bosses--;
            }

            // Create an enemy object & create new thread
            Runnable enemy = new Spider(i, health, armor, strength, difficulty, isBoss);
            Thread thread = new Thread(enemy);
            getEnemies().add((Spider) enemy);
            getEnemies().get(i).setInstance(thread);
            getEnemies().get(i).setMaze(getMaze());
            getEnemies().get(i).setPlayer(getPlayer());
            getEnemies().get(i).setRun(true);
            getEnemies().get(i).setMinUpdateTime(600);
            getEnemies().get(i).setMaxUpdateTime(750);

            // Set the boss objects to be able to use A star algorithm. Change this to use other algorithms too.
            if(isBoss)
                getEnemies().get(i).setAlgorithm(random.nextInt((1 - 1) + 1) + 1);

            boolean enemyPosSet = false;

            // Continue to loop until a good position is found
            while(enemyPosSet != true){
                getEnemies().get(i).setRowPos((int)(GameRunner.MAZE_DIMENSION * Math.random()));
                getEnemies().get(i).setColPos((int)(GameRunner.MAZE_DIMENSION * Math.random()));

                // Checking if the area is walkable, if true then place enemy
                if(getMaze()[getEnemies().get(i).getRowPos()][getEnemies().get(i).getColPos()].isWalkable()){
                    getMaze()[getEnemies().get(i).getRowPos()][getEnemies().get(i).getColPos()].setNodeType('E');
                    if(getEnemies().get(i).isBoss())
                        getMaze()[getEnemies().get(i).getRowPos()][getEnemies().get(i).getColPos()].setNodeType('F');
                    getMaze()[getEnemies().get(i).getRowPos()][getEnemies().get(i).getColPos()].setEnemyID(i);
                    enemyPosSet = true;
                }
            }

            isBoss = false;
            thread.start();
        }
    }

    /**
     * Kills the previously running enemy threads
     */
    public void killEnemyThreads() {
        if(getEnemies() == null || getEnemies().size() <= 0) return;
        for(int i = 0; i < getEnemies().size(); i++){
            getEnemies().get(i).setHealth(0);
            getEnemies().get(i).setRun(false);
        }
    }

    public Maze getModel() {
        return model;
    }

    public void setModel(Maze model) {
        this.model = model;
    }

    public Node[][] getMaze() {
        return maze;
    }

    public void setMaze(Node[][] maze) {
        this.maze = maze;
    }

    public ControlledSprite getPlayer() {
        return player;
    }

    public void setPlayer(ControlledSprite player) {
        this.player = player;
    }

    public ArrayList<Spider> getEnemies() {
        return spiders;
    }

    public void setEnemies(ArrayList<Spider> enemies) {
        this.spiders = enemies;
    }

}
