package game.units;

public interface MilitaryUnit {
	public enum UnitType {
	    LIGHT_HUNTER,
	    HEAVY_HUNTER,
	    BATTLE_SHIP,
	    ARMORED_SHIP,
	    MISSILE_LAUNCHER,
	    ION_CANNON,
	    PLASMA_CANNON
	}
	
	abstract int getAttack();
	abstract int getActualArmor();
	
	abstract int getMetalCost();
	abstract int getDeuteriumCost();
	abstract int getChanceGeneratingWaste(); // probabilidad de generar residuos
	abstract int getChanceAttackAgain(); // probabilidad de atacar de nuevo
	
	abstract void takeDamage(int receivedDamage);
	abstract void resetArmor();
}
