package game.utils;
import game.units.MilitaryUnit.UnitType;
import game.units.Variables;

public class UnitStats implements Variables {
	
	// calcular armadura final con el tipo de tropa y nivel de la tecnología
	public static int calculateArmor(UnitType type, int defenseTech) {
		int base, plus;
		switch (type) {
		case LIGHT_HUNTER:
			base = ARMOR_LIGTHHUNTER;
			plus = PLUS_ARMOR_LIGTHHUNTER_BY_TECHNOLOGY;
			break;
		case HEAVY_HUNTER:
			base = ARMOR_HEAVYHUNTER;
			plus = PLUS_ARMOR_HEAVYHUNTER_BY_TECHNOLOGY;
			break;
		case BATTLE_SHIP:
			base = ARMOR_BATTLESHIP;
			plus = PLUS_ARMOR_BATTLESHIP_BY_TECHNOLOGY;
			break;
		case ARMORED_SHIP:
			base = ARMOR_ARMOREDSHIP;
			plus = PLUS_ARMOR_ARMOREDSHIP_BY_TECHNOLOGY;
			break;
		case MISSILE_LAUNCHER:
			base = ARMOR_MISSILELAUNCHER;
			plus = PLUS_ARMOR_MISSILELAUNCHER_BY_TECHNOLOGY;
			break;
		case ION_CANNON:
			base = ARMOR_IONCANNON;
			plus = PLUS_ARMOR_IONCANNON_BY_TECHNOLOGY;
			break;
		case PLASMA_CANNON:
			base = ARMOR_PLASMACANNON;
			plus = PLUS_ARMOR_PLASMACANNON_BY_TECHNOLOGY;
			break;
				
		default:
			throw new IllegalArgumentException("Invalid ship type for armor");
		}
		
		return base + (defenseTech*plus * base) / 100;
	}
	
	
	// calcular el daño final con el tipo de tropa y nivel de la tecnología
	public static int calculateDamage(UnitType type, int attackTech) {
		int base, plus;
		switch (type) {
		case LIGHT_HUNTER:
			base = BASE_DAMAGE_LIGTHHUNTER;
			plus = PLUS_ATTACK_LIGTHHUNTER_BY_TECHNOLOGY;
			break;
		case HEAVY_HUNTER:
			base = BASE_DAMAGE_HEAVYHUNTER;
			plus = PLUS_ATTACK_HEAVYHUNTER_BY_TECHNOLOGY;
			break;
		case BATTLE_SHIP:
			base = BASE_DAMAGE_BATTLESHIP;
			plus = PLUS_ATTACK_BATTLESHIP_BY_TECHNOLOGY;
			break;
		case ARMORED_SHIP:
			base = BASE_DAMAGE_ARMOREDSHIP;
			plus = PLUS_ATTACK_ARMOREDSHIP_BY_TECHNOLOGY;
			break;
		case MISSILE_LAUNCHER:
			base = BASE_DAMAGE_MISSILELAUNCHER;
			plus = PLUS_ATTACK_MISSILELAUNCHER_BY_TECHNOLOGY;
			break;
		case ION_CANNON:
			base = BASE_DAMAGE_IONCANNON;
			plus = PLUS_ATTACK_IONCANNON_BY_TECHNOLOGY;
			break;
		case PLASMA_CANNON:
			base = BASE_DAMAGE_PLASMACANNON;
			plus = PLUS_ATTACK_PLASMACANNON_BY_TECHNOLOGY;
			break;
				
		default:
			throw new IllegalArgumentException("Invalid ship type for damage");
		}
		
		return base + (attackTech*plus * base) / 100;
	}
}
