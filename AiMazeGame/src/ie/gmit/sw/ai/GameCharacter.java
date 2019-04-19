package ie.gmit.sw.ai;


public class GameCharacter {
	private int health;
    private int armor;
    private int rowPos;
    private int colPos;

    public GameCharacter() {
        setHealth(100);
        setArmor(100);
    }

    public GameCharacter(int health, int armor) {
        setHealth(health);
        setArmor(armor);
    }

    public GameCharacter(int health, int armor, int colPos, int rowPos) {
        setHealth(health);
        setArmor(armor);
        setColPos(colPos);
        setRowPos(rowPos);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getRowPos() {
        return rowPos;
    }

    public void setRowPos(int rowPos) {
        this.rowPos = rowPos;
    }

    public int getColPos() {
        return colPos;
    }

    public void setColPos(int colPos) {
        this.colPos = colPos;
    }
}