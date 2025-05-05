package game.units;
import game.utils.UnitStats;

public class PlasmaCannon extends Defense {

	public PlasmaCannon(int defenseTechLevel, int attackTechLevel) {
	    super(
	        UnitStats.calculateArmor(UnitType.PLASMA_CANNON, defenseTechLevel),
	        UnitStats.calculateDamage(UnitType.PLASMA_CANNON, attackTechLevel),
	        UnitType.PLASMA_CANNON
	    );
	}
	
	public PlasmaCannon() {
	    super(ARMOR_PLASMACANNON, BASE_DAMAGE_PLASMACANNON, UnitType.PLASMA_CANNON);
	}
	
	
	public int getMetalCost() {
	    return METAL_COST_PLASMACANNON;
	}
	
	public int getDeuteriumCost() {
	    return DEUTERIUM_COST_PLASMACANNON;
	}
	
	public int getChanceGeneratingWaste() {
	    return CHANCE_GENERATNG_WASTE_PLASMACANNON;
	}
	
	public int getChanceAttackAgain() {
	    return CHANCE_ATTACK_AGAIN_PLASMACANNON;
	}
}
