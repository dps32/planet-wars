package game.units;
import game.utils.UnitStats;

public class IonCannon extends Defense {

	public IonCannon(int defenseTechLevel, int attackTechLevel) {
	    super(
	        UnitStats.calculateArmor(UnitType.ION_CANNON, defenseTechLevel),
	        UnitStats.calculateDamage(UnitType.ION_CANNON, attackTechLevel),
	        UnitType.ION_CANNON
	    );
	}
	
	public IonCannon() {
	    super(ARMOR_IONCANNON, BASE_DAMAGE_IONCANNON, UnitType.ION_CANNON);
	}
	
	
	public int getMetalCost() {
	    return METAL_COST_IONCANNON;
	}
	
	public int getDeuteriumCost() {
	    return DEUTERIUM_COST_IONCANNON;
	}
	
	public int getChanceGeneratingWaste() {
	    return CHANCE_GENERATNG_WASTE_IONCANNON;
	}
	
	public int getChanceAttackAgain() {
	    return CHANCE_ATTACK_AGAIN_IONCANNON;
	}
}
