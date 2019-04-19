package ie.gmit.sw.ai;

//Used for setting and getting Weapon characteristics
public class Weapon {
	private String weaponName;
	private int damage;
	
	public Weapon(String weaponName, int damage) {
		this.weaponName = weaponName;
		this.damage = damage;
	}

	public String getWeaponName() {
		return weaponName;
	}

	public void setWeaponName(String weaponName) {
		this.weaponName = weaponName;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
}
