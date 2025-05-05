package game.units;

abstract class Defense implements MilitaryUnit, Variables {
	protected int armor;
	protected int initialArmor;
	protected int baseDamage;
	protected UnitType type;
	
	protected Defense(int armor, int baseDamage, UnitType type) {
	    this.armor = armor;
	    this.initialArmor = armor;
	    this.baseDamage = baseDamage;
	    this.type = type;
	}
	
	
	public int getAttack() {
	    return baseDamage;
	}
	
	public int getActualArmor() {
	    return armor;
	}
	
	public void takeDamage(int receivedDamage) {
		armor -= receivedDamage;
	}
	
	public void resetArmor() {
	    armor = initialArmor;
	}
}
