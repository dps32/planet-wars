package game.model;

import java.util.ArrayList;
import java.util.Random;

import game.units.*;

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
	private String battleReport = "";
	private int battleId;

	public Battle(int battleId) {
		super();
		this.enemyArmy = new ArrayList[7];
		for (int i = 0; i < enemyArmy.length; i++) {
			enemyArmy[i] = new ArrayList<MilitaryUnit>();
		}

		this.planetArmy = new ArrayList[7];
		for (int i = 0; i < 7; i++) {
			planetArmy[i] = new ArrayList<>();
		}

		this.armies = new ArrayList[2][7];
		this.initialCostFleet = new int[2][2];
		this.wasteMetalDeuterium = new int[2];
		this.enemyDrops = new int[7];
		this.planetDrops = new int[7];
		this.resourcesLooses = new int[2][3];
		this.initialArmies = new int[2][7];
		this.actualNumberUnitsPlanet = new int[7];
		this.actualNumberUnitsEnemy = new int[7];
		this.battleDevelopment = "";
		this.battleId = battleId;
	}

	public void start() {
		System.out.println("Empezando batalla");

		this.armies = new ArrayList[2][7];
		for (int i = 0; i < 7; i++) {
			armies[0][i] = planetArmy[i];
			armies[1][i] = enemyArmy[i];
		}

		// inicializar datos iniciales
		this.initialNumberUnitsPlanet = initialFleetNumber(planetArmy);
		this.initialNumberUnitsEnemy = initialFleetNumber(enemyArmy);
		initInitialArmies();

		battleLoop();
	}
	
	public void battleLoop() {
	    boolean attackingPlanet = Math.random() < 0.5;

	    while (remainderPercentageFleet(planetArmy) > 20 && remainderPercentageFleet(enemyArmy) > 20) {
	        battleDevelopment += "********************CHANGE ATTACKER********************\n";

	        int attackerGroup = attackingPlanet ? getPlanetGroupAttacker() : getEnemyGroupAttacker();
	        ArrayList<MilitaryUnit>[] attackerArmy = attackingPlanet ? planetArmy : enemyArmy;
	        ArrayList<MilitaryUnit>[] defenderArmy = attackingPlanet ? enemyArmy : planetArmy;

	        if (attackerArmy[attackerGroup].isEmpty()) {
	            attackingPlanet = !attackingPlanet;
	            continue;
	        }

	        MilitaryUnit attacker = attackerArmy[attackerGroup].get((int)(Math.random() * attackerArmy[attackerGroup].size()));

	        int defenderGroup = getGroupDefender(defenderArmy);
	        if (defenderGroup == -1 || defenderArmy[defenderGroup].isEmpty()) {
	            attackingPlanet = !attackingPlanet;
	            continue;
	        }

	        MilitaryUnit defender = defenderArmy[defenderGroup].get((int)(Math.random() * defenderArmy[defenderGroup].size()));

	        battleDevelopment += (attackingPlanet ? "Attacks Planet: " : "Attacks fleet enemy: ");
	        battleDevelopment += attacker.getClass().getSimpleName() + " attacks " + defender.getClass().getSimpleName() + "\n";

	        int damage = attacker.getAttack();
	        defender.takeDamage(damage);

	        battleDevelopment += attacker.getClass().getSimpleName() + " generates the damage = " + damage + "\n";
	        battleDevelopment += defender.getClass().getSimpleName() + " stays with armor = " + defender.getActualArmor() + "\n";

	        if (defender.getActualArmor() <= 0) {
	            int wasteChance = defender.getChanceGeneratingWaste();
	            if (Math.random() * 100 < wasteChance) {
	                int metal = defender.getMetalCost() * game.units.Variables.PERCENTATGE_WASTE / 100;
	                int deut = defender.getDeuteriumCost() * game.units.Variables.PERCENTATGE_WASTE / 100;
	                wasteMetalDeuterium[0] += metal;
	                wasteMetalDeuterium[1] += deut;
	            }

	            defenderArmy[defenderGroup].remove(defender);
	            if (attackingPlanet) {
	                enemyDrops[defenderGroup]++;
	                actualNumberUnitsEnemy[defenderGroup]--;
	            } else {
	                planetDrops[defenderGroup]++;
	                actualNumberUnitsPlanet[defenderGroup]--;
	            }

	            battleDevelopment += "we eliminate " + defender.getClass().getSimpleName() + "\n";
	        }

	        int attackAgainChance = attacker.getChanceAttackAgain();
	        if (Math.random() * 100 >= attackAgainChance) {
	            attackingPlanet = !attackingPlanet;
	        }
	    }

	    updateResourcesLooses();
	}

	public void setPlanetArmy(ArrayList<MilitaryUnit>[] currentArmy) {
		for (int i = 0; i < 7; i++) {
			for (MilitaryUnit mu : currentArmy[i]) {
				this.planetArmy[i].add(mu);
			}
		}
	}

	public void addEnemyUnit(MilitaryUnit.UnitType type) {
		switch (type) {
		case LIGHT_HUNTER:
			enemyArmy[type.ordinal()].add(new LightHunter());
			break;
		case HEAVY_HUNTER:
			enemyArmy[type.ordinal()].add(new HeavyHunter());
			break;
		case BATTLE_SHIP:
			enemyArmy[type.ordinal()].add(new BattleShip());
			break;
		case ARMORED_SHIP:
			enemyArmy[type.ordinal()].add(new ArmoredShip());
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
	}

	private int initialFleetNumber(ArrayList<MilitaryUnit>[] army) {
		int total = 0;
		for (ArrayList<MilitaryUnit> group : army) {
			total += group.size();
		}
		return total;
	}

	// void initInitialArmies()
	public void initInitialArmies() {
		for (int i = 0; i < 7; i++) {
			initialArmies[0][i] = planetArmy[i].size();
			initialArmies[1][i] = enemyArmy[i].size();
		}
		
	    initialCostFleet[0] = fleetResourceCost(planetArmy);
	    initialCostFleet[1] = fleetResourceCost(enemyArmy);
	}

	// void updateResourcesLooses()
	public void updateResourcesLooses() {
		int[] remainingPlanet = fleetResourceCost(planetArmy);
	    int[] remainingEnemy = fleetResourceCost(enemyArmy);

	    int lostMetalPlanet = initialCostFleet[0][0] - remainingPlanet[0];
	    int lostDeutPlanet = initialCostFleet[0][1] - remainingPlanet[1];
	    int lostMetalEnemy = initialCostFleet[1][0] - remainingEnemy[0];
	    int lostDeutEnemy = initialCostFleet[1][1] - remainingEnemy[1];

	    resourcesLooses[0][0] = lostMetalPlanet;
	    resourcesLooses[0][1] = lostDeutPlanet;
	    resourcesLooses[0][2] = lostMetalPlanet + 5 * lostDeutPlanet;

	    resourcesLooses[1][0] = lostMetalEnemy;
	    resourcesLooses[1][1] = lostDeutEnemy;
	    resourcesLooses[1][2] = lostMetalEnemy + 5 * lostDeutEnemy;
	}


	// fleetResourceCost(ArrayList<MilitaryUnit>[] army)
	public int[] fleetResourceCost(ArrayList<MilitaryUnit>[] army) {
		int metal = 0, deuterium = 0;
		for (ArrayList<MilitaryUnit> group : army) {
			for (MilitaryUnit unit : group) {
				metal += unit.getMetalCost();
				deuterium += unit.getDeuteriumCost();
			}
		}
		return new int[] { metal, deuterium };
	}

	// int remainderPercentageFleet(ArrayList<MilitaryUnit>[] army)
	public int remainderPercentageFleet(ArrayList<MilitaryUnit>[] army) {
		int total = 0;
		for (ArrayList<MilitaryUnit> group : army) {
			total += group.size();
		}
		if (army == planetArmy && initialNumberUnitsPlanet > 0)
			return (100 * total) / initialNumberUnitsPlanet;
		else if (army == enemyArmy && initialNumberUnitsEnemy > 0)
			return (100 * total) / initialNumberUnitsEnemy;
		else
			return 0;
	}

	// int getGroupDefender(ArrayList<MilitaryUnit>[] army)
	public int getGroupDefender(ArrayList<MilitaryUnit>[] army) {
	    int[] weights = new int[army.length];
	    int total = 0;

	    // Sumamos cuántas unidades hay en cada grupo y el total
	    for (int i = 0; i < army.length; i++) {
	        weights[i] = army[i].size();
	        total += weights[i];
	    }

	    if (total == 0) return -1; // sin defensores

	    // generamos un número aleatorio entre 1 y el total
	    int rand = (int)(Math.random() *total) + 1;

	    int cumulative = 0;
	    for (int i = 0; i < weights.length; i++) {
	        cumulative += weights[i];
	        if (rand <= cumulative) return i; // encontramos el grupo
	    }

	    return -1; // aqui no deberia llegar
	}

	// int getPlanetGroupAttacker()
	public int getPlanetGroupAttacker() {
		int[] chances = game.units.Variables.CHANCE_ATTACK_PLANET_UNITS;
		return selectGroupByChance(chances);
	}

	// int getEnemyGroupAttacker()
	public int getEnemyGroupAttacker() {
		int[] chances = game.units.Variables.CHANCE_ATTACK_ENEMY_UNITS;
		return selectGroupByChance(chances);
	}

	private int selectGroupByChance(int[] chances) {
	    int rand = (int)(Math.random() * 100) + 1;
	    int cumulative = 0;

	    for (int i = 0; i < chances.length; i++) {
	        cumulative += chances[i];
	        if (rand <= cumulative) return i;
	    }

	    return -1;
	}


	// void resetArmyArmor()
	public void resetArmyArmor() {
		for (int i = 0; i < 7; i++) {
			for (MilitaryUnit unit : planetArmy[i]) unit.resetArmor();
			for (MilitaryUnit unit : enemyArmy[i]) unit.resetArmor();
		}
	}

	public void enemiesPrint() {
		System.out.println("Enemy Army");
		for (ArrayList<MilitaryUnit> units : this.enemyArmy) {
			for (MilitaryUnit unit : units) {
				if (unit.getClass().equals(LightHunter.class)) {
					System.out.println("Lighthunter");
				} else if (unit.getClass().equals(HeavyHunter.class)) {
					System.out.println("Heavyhunter");
				} else if (unit.getClass().equals(BattleShip.class)) {
					System.out.println("Battleship");
				} else if (unit.getClass().equals(ArmoredShip.class)) {
					System.out.println("Armoredship");
				}
			}
		}
		System.out.println("");
		System.out.println("");
		System.out.println("");
	}
	
	public String getBattleDevelopment() {
	    return "BATTLE NUMBER: " + battleId + "\nSTART THE BATTLE\n" + battleDevelopment;
	}
	
	public String getBattleReport(int id) {
	    if (id != battleId) return "Invalid battle ID";

	    String nl = System.lineSeparator();
	    String report = "";

	    report += "BATTLE NUMBER: " + battleId + nl;
	    report += "BATTLE STATISTICS" + nl + nl;

	    // Cabecera con separación fija entre planet y enemy
	    report += String.format("%-22s %6s %6s    %-22s %6s %6s%n",
	            "Army planet", "Units", "Drops", "Initial Army Enemy", "Units", "Drops");

	    String[] names = {
	        "Ligth Hunter", "Heavy Hunter", "Battle Ship", "Armored Ship",
	        "Missile Launcher", "Ion Cannon", "Plasma Cannon"
	    };

	    for (int i = 0; i < 7; i++) {
	        // Datos del planeta
	        report += String.format("%-22s %6d %6d", names[i], initialArmies[0][i], planetDrops[i]);
	        // Datos del enemigo solo si es nave (0-3)
	        if (i < 4) {
	            report += String.format("    %-22s %6d %6d", names[i], initialArmies[1][i], enemyDrops[i]);
	        }
	        report += nl;
	    }

	    String line = "*".repeat(86) + nl;

	    report += nl + line;
	    report += String.format("%-40s %-40s%n%n", "Cost Army planet", "Cost Army Enemy");
	    report += String.format("Metal:%12d%28sMetal:%12d%n", initialCostFleet[0][0], "", initialCostFleet[1][0]);
	    report += String.format("Deuterium:%8d%28sDeuterium:%8d%n", initialCostFleet[0][1], "", initialCostFleet[1][1]);

	    report += nl + line;
	    report += String.format("%-40s %-40s%n%n", "Losses Army planet", "Losses Army Enemy");
	    report += String.format("Metal:%12d%28sMetal:%12d%n", resourcesLooses[0][0], "", resourcesLooses[1][0]);
	    report += String.format("Deuterium:%8d%28sDeuterium:%8d%n", resourcesLooses[0][1], "", resourcesLooses[1][1]);
	    report += String.format("Weighted:%9d%28sWeighted:%9d%n", resourcesLooses[0][2], "", resourcesLooses[1][2]);

	    report += nl + line;
	    report += "Waste Generated:" + nl;
	    report += String.format("Metal       %d%n", wasteMetalDeuterium[0]);
	    report += String.format("Deuterium   %d%n", wasteMetalDeuterium[1]);
	    report += nl;

	    if (planetWon()) {
	        report += "Battle won by Planet, we collect rubble";
	    } else {
	        report += "Battle lost, Enemy takes the rubble";
	    }

	    report += nl + nl + "#".repeat(74) + nl;

	    battleReport = report;
	    return battleReport;
	}
	
	public boolean planetWon() {
		return resourcesLooses[0][2] < resourcesLooses[1][2];
	}

	public ArrayList[][] getArmies() {
		return armies;
	}

	public int[] getWasteMetalDeuterium() {
		return wasteMetalDeuterium;
	}

	
}
