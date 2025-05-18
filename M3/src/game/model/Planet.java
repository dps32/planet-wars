package game.model;

import java.util.ArrayList;
import java.util.Arrays;

import game.application.Database;
import game.exception.ResourceException;
import game.units.*;

public class Planet implements Variables {
	private int technologyDefense;
	private int technologyAttack;
	private int metal;
	private int deuterium;
	private int upgradeDefenseTechnologyDeuteriumCost;
	private int upgradeAttackTechnologyDeuteriumCost;

	private ArrayList<MilitaryUnit>[] army = new ArrayList[7];
	private int planetId;


	public Planet(int technologyDefense, int technologyAttack, int metal, int deuterium) {
		super();
		this.technologyDefense = technologyDefense;
		this.technologyAttack = technologyAttack;
		this.metal = metal;
		this.deuterium = deuterium;
		this.upgradeDefenseTechnologyDeuteriumCost = UPGRADE_BASE_DEFENSE_TECHNOLOGY_DEUTERIUM_COST;
		this.upgradeAttackTechnologyDeuteriumCost = UPGRADE_BASE_ATTACK_TECHNOLOGY_DEUTERIUM_COST;

		for (int i = 0; i < 7; i++) {
			army[i] = new ArrayList<>();
		}
		
		ArrayList<Object> params = new ArrayList<>();
		params.add(metal);
		params.add(deuterium);
		params.add(technologyDefense);
		params.add(technologyAttack);

		String insert = "INSERT INTO Planet_stats (resource_metal_amount, resource_deuterium_amount, technology_defense_level, technology_attack_level) VALUES (?, ?, ?, ?)";

		this.planetId = Database.insertAndReturnId(insert, params);
	}

	public void saveResources() {
	    String update = "UPDATE Planet_stats SET resource_metal_amount = ?, resource_deuterium_amount = ? WHERE planet_id = ?";
	    ArrayList<Object> params = new ArrayList<>();
	    params.add(this.metal);
	    params.add(this.deuterium);
	    params.add(this.planetId);
	    Database.query(update, params);
	}

	
	public void upgradeTechnologyDefense() throws ResourceException {
		if (!canAfford(upgradeDefenseTechnologyDeuteriumCost)) throw new ResourceException("No tienes recursos suficientes");

		this.technologyDefense++;
		this.deuterium -= upgradeDefenseTechnologyDeuteriumCost;

		int baseCost = UPGRADE_BASE_DEFENSE_TECHNOLOGY_DEUTERIUM_COST;
		int plus = UPGRADE_PLUS_DEFENSE_TECHNOLOGY_DEUTERIUM_COST * this.technologyDefense;
		upgradeDefenseTechnologyDeuteriumCost = baseCost + (baseCost * plus) / 100;
		saveResources();
	}

	public void upgradeTechnologyAttack() throws ResourceException {
		if (!canAfford(upgradeAttackTechnologyDeuteriumCost)) throw new ResourceException("No tienes recursos suficientes");

		this.technologyAttack++;
		this.deuterium -= upgradeAttackTechnologyDeuteriumCost;

		int baseCost = UPGRADE_BASE_ATTACK_TECHNOLOGY_DEUTERIUM_COST;
		int plus = UPGRADE_PLUS_ATTACK_TECHNOLOGY_DEUTERIUM_COST * this.technologyAttack;
		upgradeAttackTechnologyDeuteriumCost = baseCost + (baseCost * plus) / 100;
		saveResources();
	}

	public void collect() {
//		System.out.println("Obteniendo recursos");
		this.deuterium += PLANET_DEUTERIUM_GENERATED;
		this.metal += PLANET_METAL_GENERATED;
		saveResources();
	}
	
	
	///////////////////      CREACIÓN DE TROPAS PARA EL PLANETA       ///////////////////////

	public void newLightHunter(int n) throws ResourceException {
		int built = 0;
		for (int i = 0; i < n; i++) {
			if (!canAfford(METAL_COST_LIGTHHUNTER, DEUTERIUM_COST_LIGTHHUNTER)) break;
			this.metal -= METAL_COST_LIGTHHUNTER;
			this.deuterium -= DEUTERIUM_COST_LIGTHHUNTER;
			army[0].add(new LightHunter(technologyDefense, technologyAttack));
			built++;
		}
		if (built < n) {
			throw new ResourceException("No more resources available. We've built " + built + " Light Hunters.");
		}
		saveResources();
	}

	public void newHeavyHunter(int n) throws ResourceException {
		int built = 0;
		for (int i = 0; i < n; i++) {
			if (!canAfford(METAL_COST_HEAVYHUNTER, DEUTERIUM_COST_HEAVYHUNTER)) break;
			this.metal -= METAL_COST_HEAVYHUNTER;
			this.deuterium -= DEUTERIUM_COST_HEAVYHUNTER;
			army[1].add(new HeavyHunter(technologyDefense, technologyAttack));
			built++;
		}
		if (built < n) {
			throw new ResourceException("No more resources available. We've built " + built + " Heavy Hunters.");
		}
		saveResources();
	}

	public void newBattleShip(int n) throws ResourceException {
		int built = 0;
		for (int i = 0; i < n; i++) {
			if (!canAfford(METAL_COST_BATTLESHIP, DEUTERIUM_COST_BATTLESHIP)) break;
			this.metal -= METAL_COST_BATTLESHIP;
			this.deuterium -= DEUTERIUM_COST_BATTLESHIP;
			army[2].add(new BattleShip(technologyDefense, technologyAttack));
			built++;
		}
		if (built < n) {
			throw new ResourceException("No more resources available. We've built " + built + " Battle Ships.");
		}
		saveResources();
	}

	public void newArmoredShip(int n) throws ResourceException {
		int built = 0;
		for (int i = 0; i < n; i++) {
			if (!canAfford(METAL_COST_ARMOREDSHIP, DEUTERIUM_COST_ARMOREDSHIP)) break;
			this.metal -= METAL_COST_ARMOREDSHIP;
			this.deuterium -= DEUTERIUM_COST_ARMOREDSHIP;
			army[3].add(new ArmoredShip(technologyDefense, technologyAttack));
			built++;
		}
		if (built < n) {
			throw new ResourceException("No more resources available. We've built " + built + " Armored Ships.");
		}
		saveResources();
	}

	public void newMissileLauncher(int n) throws ResourceException {
		int built = 0;
		for (int i = 0; i < n; i++) {
			if (!canAfford(METAL_COST_MISSILELAUNCHER, DEUTERIUM_COST_MISSILELAUNCHER)) break;
			this.metal -= METAL_COST_MISSILELAUNCHER;
			this.deuterium -= DEUTERIUM_COST_MISSILELAUNCHER;
			army[4].add(new MissileLauncher(technologyDefense, technologyAttack));
			built++;
		}
		if (built < n) {
			throw new ResourceException("No more resources available. We've built " + built + " Missile Launchers.");
		}
		saveResources();
	}

	public void newIonCannon(int n) throws ResourceException {
		int built = 0;
		for (int i = 0; i < n; i++) {
			if (!canAfford(METAL_COST_IONCANNON, DEUTERIUM_COST_IONCANNON)) break;
			this.metal -= METAL_COST_IONCANNON;
			this.deuterium -= DEUTERIUM_COST_IONCANNON;
			army[5].add(new IonCannon(technologyDefense, technologyAttack));
			built++;
		}
		if (built < n) {
			throw new ResourceException("No more resources available. We've built " + built + " Ion Cannons.");
		}
		saveResources();
	}

	public void newPlasmaCannon(int n) throws ResourceException {
		int built = 0;
		for (int i = 0; i < n; i++) {
			if (!canAfford(METAL_COST_PLASMACANNON, DEUTERIUM_COST_PLASMACANNON)) break;
			this.metal -= METAL_COST_PLASMACANNON;
			this.deuterium -= DEUTERIUM_COST_PLASMACANNON;
			army[6].add(new PlasmaCannon(technologyDefense, technologyAttack));
			built++;
		}
		if (built < n) {
			throw new ResourceException("No more resources available. We've built " + built + " Plasma Cannons.");
		}
		saveResources();
	}
	
	
	
	///// ------------

	
	

	// debuggear stats del planeta
	public void printStats() {
		System.out.println("------ STATS PLANETA ------");
		System.out.println("Metal: " + metal);
		System.out.println("Deuterio: " + deuterium);
		System.out.println("Tecnología Defensa: " + technologyDefense);
		System.out.println("Tecnología Ataque: " + technologyAttack);
		System.out.println("Upgrade Defensa cost: " + upgradeDefenseTechnologyDeuteriumCost);
		System.out.println("Upgrade Ataque cost: " + upgradeAttackTechnologyDeuteriumCost);
		System.out.println("Ejército:");

		String[] nombres = { "Ligth Hunter", "Heavy Hunter", "Battle Ship", "Armored Ship", "Missile Launcher", "Ion Cannon", "Plasma Cannon" };
		for (int i = 0; i < 7; i++) {
			System.out.println(nombres[i] + ": " + army[i].size());
		}
		System.out.println("---------------------------");
	}
	

	/// utils y getters/setters
	
	private boolean canAfford(int deuterio) {
		return this.deuterium >= deuterio;
	}

	private boolean canAfford(int metal, int deuterio) {
		return this.metal >= metal && this.deuterium >= deuterio;
	}

	public int getMetal() {
		return metal;
	}

	public int getDeuterium() {
		return deuterium;
	}
	

	public void setMetal(int metal) {
		this.metal = metal;
	}

	public void setDeuterium(int deuterium) {
		this.deuterium = deuterium;
	}

	public ArrayList<MilitaryUnit>[] getArmy() {
		return army;
	}

	public void setArmy(ArrayList<MilitaryUnit>[] army) {
		this.army = army;
	}

	public int getTechnologyDefense() {
		return technologyDefense;
	}

	public int getTechnologyAttack() {
		return technologyAttack;
	}

	public int getPlanetId() {
		return planetId;
	}
	
	
}
