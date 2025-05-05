package game.units;
import game.utils.UnitStats;

public class HeavyHunter extends Ship {

	public HeavyHunter(int defenseTechLevel, int attackTechLevel) {
	    super(
	        UnitStats.calculateArmor(UnitType.HEAVY_HUNTER, defenseTechLevel),
	        UnitStats.calculateDamage(UnitType.HEAVY_HUNTER, attackTechLevel),
	        UnitType.HEAVY_HUNTER
	    );
	}
	
	public HeavyHunter() {
	    super(ARMOR_HEAVYHUNTER, BASE_DAMAGE_HEAVYHUNTER, UnitType.HEAVY_HUNTER);
	}
	
	
	public int getMetalCost() {
	    return METAL_COST_HEAVYHUNTER;
	}
	
	public int getDeuteriumCost() {
	    return DEUTERIUM_COST_HEAVYHUNTER;
	}
	
	public int getChanceGeneratingWaste() {
	    return CHANCE_GENERATNG_WASTE_HEAVYHUNTER;
	}
	
	public int getChanceAttackAgain() {
	    return CHANCE_ATTACK_AGAIN_HEAVYHUNTER;
	}

}
