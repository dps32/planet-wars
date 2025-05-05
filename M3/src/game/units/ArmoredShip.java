package game.units;
import game.utils.UnitStats;

public class ArmoredShip extends Ship {

	public ArmoredShip(int defenseTechLevel, int attackTechLevel) {
	    super(
	        UnitStats.calculateArmor(UnitType.ARMORED_SHIP, defenseTechLevel),
	        UnitStats.calculateDamage(UnitType.ARMORED_SHIP, attackTechLevel),
	        UnitType.ARMORED_SHIP
	    );
	}
	
	public ArmoredShip() {
	    super(ARMOR_ARMOREDSHIP, BASE_DAMAGE_ARMOREDSHIP, UnitType.ARMORED_SHIP);
	}
	
	
	
	public int getMetalCost() {
	    return METAL_COST_ARMOREDSHIP;
	}
	
	public int getDeuteriumCost() {
	    return DEUTERIUM_COST_ARMOREDSHIP;
	}
	
	public int getChanceGeneratingWaste() {
	    return CHANCE_GENERATNG_WASTE_ARMOREDSHIP;
	}
	
	public int getChanceAttackAgain() {
	    return CHANCE_ATTACK_AGAIN_ARMOREDSHIP;
	}
}
