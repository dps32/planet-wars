package game.units;
import game.utils.UnitStats;

public class MissileLauncher extends Defense {

	public MissileLauncher(int defenseTechLevel, int attackTechLevel) {
	    super(
	        UnitStats.calculateArmor(UnitType.MISSILE_LAUNCHER, defenseTechLevel),
	        UnitStats.calculateDamage(UnitType.MISSILE_LAUNCHER, attackTechLevel),
	        UnitType.MISSILE_LAUNCHER
	    );
	}
	
	public MissileLauncher() {
	    super(ARMOR_MISSILELAUNCHER, BASE_DAMAGE_MISSILELAUNCHER, UnitType.MISSILE_LAUNCHER);
	}
	
	
	public int getMetalCost() {
	    return METAL_COST_MISSILELAUNCHER;
	}
	
	public int getDeuteriumCost() {
	    return DEUTERIUM_COST_MISSILELAUNCHER;
	}
	
	public int getChanceGeneratingWaste() {
	    return CHANCE_GENERATNG_WASTE_MISSILELAUNCHER;
	}
	
	public int getChanceAttackAgain() {
	    return CHANCE_ATTACK_AGAIN_MISSILELAUNCHER;
	}
}
