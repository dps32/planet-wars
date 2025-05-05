package game.units;
import game.utils.UnitStats;

public class BattleShip extends Ship {

	public BattleShip(int defenseTechLevel, int attackTechLevel) {
	    super(
	        UnitStats.calculateArmor(UnitType.BATTLE_SHIP, defenseTechLevel),
	        UnitStats.calculateDamage(UnitType.BATTLE_SHIP, attackTechLevel),
	        UnitType.BATTLE_SHIP
	    );
	}
	
	public BattleShip() {
	    super(ARMOR_BATTLESHIP, BASE_DAMAGE_BATTLESHIP, UnitType.BATTLE_SHIP);
	}
	
	
	
	public int getMetalCost() {
	    return METAL_COST_BATTLESHIP;
	}
	
	public int getDeuteriumCost() {
	    return DEUTERIUM_COST_BATTLESHIP;
	}
	
	public int getChanceGeneratingWaste() {
	    return CHANCE_GENERATNG_WASTE_BATTLESHIP;
	}
	
	public int getChanceAttackAgain() {
	    return CHANCE_ATTACK_AGAIN_BATTLESHIP;
	}
}
