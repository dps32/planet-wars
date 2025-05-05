package game.units;

abstract class Ship implements MilitaryUnit, Variables {
	protected int armor;
	protected int initialArmor;
	protected int baseDamage;
	protected UnitType type;
	
	protected Ship(int armor, int baseDamage, UnitType type) {
		this.armor = armor;
		this.initialArmor = armor;
		this.baseDamage = baseDamage;
		this.type = type;
	}
	
	
	public int getAttack() {
		return baseDamage;
	}
	
	public int getActualArmor() {
		return this.armor;
	}
	
	public void takeDamage(int receivedDamage) {
		this.armor -= receivedDamage;
	}
	
	public void resetArmor() {
		armor = initialArmor;
	}
}
