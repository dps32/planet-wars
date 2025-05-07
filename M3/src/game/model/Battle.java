package game.model;

import java.util.ArrayList;
import game.units.MilitaryUnit;

public class Battle {
	private ArrayList<MilitaryUnit>[] planetArmy;
	private ArrayList<MilitaryUnit>[] enemyArmy;
	private ArrayList[][] armies;
	
	String battleDevelopment;
	
	// [0] team / [1] enemy
	
	private int[][] initialCostFleet; // (metal, deuterio)
	private int initialNumberUnitsPlanet, initialNumberUnitsEnemy;
	private int[] wasteMetalDeuterium; // (metal, deuterio)
	private int[] enemyDrops, planetDrops;
	private int[][] resourcesLooses; // (metal, deuterio, metal + 5*deuterio) perdidos
	private int[][] initialArmies; // cantidad de tropas
	private int[] actualNumberUnitsPlanet, actualNumberUnitsEnemy;
	
	
	private void start() {
		
	}
}
