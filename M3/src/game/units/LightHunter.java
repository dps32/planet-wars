package game.units;
import game.utils.UnitStats;

public class LightHunter extends Ship {

	public LightHunter(int defenseTechLevel, int attackTechLevel) {
	    super(
	        UnitStats.calculateArmor(UnitType.LIGHT_HUNTER, defenseTechLevel),
	        UnitStats.calculateDamage(UnitType.LIGHT_HUNTER, attackTechLevel),
	        UnitType.LIGHT_HUNTER
	    );
	}
	
	public LightHunter() {
	    super(ARMOR_LIGTHHUNTER, BASE_DAMAGE_LIGTHHUNTER, UnitType.LIGHT_HUNTER);
	}
	
	
	public int getMetalCost() {
	    return METAL_COST_LIGTHHUNTER;
	}
	
	public int getDeuteriumCost() {
	    return DEUTERIUM_COST_LIGTHHUNTER;
	}
	
	public int getChanceGeneratingWaste() {
	    return CHANCE_GENERATNG_WASTE_LIGTHHUNTER;
	}
	
	public int getChanceAttackAgain() {
	    return CHANCE_ATTACK_AGAIN_LIGTHHUNTER;
	}
}
