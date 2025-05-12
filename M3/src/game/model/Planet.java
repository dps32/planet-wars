package game.model;

import java.util.ArrayList;
import java.util.Arrays;

import game.exception.ResourceException;
import game.units.*;

public class Planet implements Variables {
	private int technologyDefense;
	private int technologyAtack;
	private int metal;
	private int deuterium;
	private int upgradeDefenseTechnologyDeuteriumCost;
	private int upgradeAttackTechnologyDeuteriumCost;

	private ArrayList<MilitaryUnit>[] army = new ArrayList[7];

	public Planet(int technologyDefense, int technologyAtack, int metal, int deuterium) {
		super();
		this.technologyDefense = technologyDefense;
		this.technologyAtack = technologyAtack;
		this.metal = metal;
		this.deuterium = deuterium;
		this.upgradeDefenseTechnologyDeuteriumCost = UPGRADE_BASE_DEFENSE_TECHNOLOGY_DEUTERIUM_COST;
		this.upgradeAttackTechnologyDeuteriumCost = UPGRADE_BASE_ATTACK_TECHNOLOGY_DEUTERIUM_COST;

		for (int i = 0; i < 7; i++) {
			army[i] = new ArrayList<>();
		}
	}

	public void upgradeTechnologyDefense() {
		try {
			if (!canAfford(upgradeDefenseTechnologyDeuteriumCost)) throw new ResourceException();

			this.technologyDefense++;
			this.deuterium -= upgradeDefenseTechnologyDeuteriumCost;

			int baseCost = UPGRADE_BASE_DEFENSE_TECHNOLOGY_DEUTERIUM_COST;
			int plus = UPGRADE_PLUS_DEFENSE_TECHNOLOGY_DEUTERIUM_COST * this.technologyDefense;
			upgradeDefenseTechnologyDeuteriumCost = baseCost + (baseCost * plus) / 100;

		} catch (Exception e) {
			System.out.println("No tienes deuterio suficiente");
		}
	}

	public void upgradeTechnologyAttack() {
		try {
			if (!canAfford(upgradeAttackTechnologyDeuteriumCost)) throw new ResourceException();

			this.technologyAtack++;
			this.deuterium -= upgradeAttackTechnologyDeuteriumCost;

			int baseCost = UPGRADE_BASE_ATTACK_TECHNOLOGY_DEUTERIUM_COST;
			int plus = UPGRADE_PLUS_ATTACK_TECHNOLOGY_DEUTERIUM_COST * this.technologyAtack;
			upgradeAttackTechnologyDeuteriumCost = baseCost + (baseCost * plus) / 100;

		} catch (Exception e) {
			System.out.println("No tienes deuterio suficiente");
		}
	}

	public void collect() {
		System.out.println("Obteniendo recursos");
		this.deuterium += PLANET_DEUTERIUM_GENERATED;
		this.metal += PLANET_METAL_GENERATED;
	}

	public void newLightHunter(int n) throws ResourceException {
		int built = 0;
		for (int i = 0; i < n; i++) {
			if (!canAfford(METAL_COST_LIGTHHUNTER, DEUTERIUM_COST_LIGTHHUNTER)) break;
			this.metal -= METAL_COST_LIGTHHUNTER;
			this.deuterium -= DEUTERIUM_COST_LIGTHHUNTER;
			army[0].add(new LightHunter(technologyDefense, technologyAtack));
			built++;
		}
		if (built < n) {
			System.out.println("Solo se han construido " + built + " Light Hunters.");
			throw new ResourceException();
		}
	}

	public void newHeavyHunter(int n) throws ResourceException {
		int built = 0;
		for (int i = 0; i < n; i++) {
			if (!canAfford(METAL_COST_HEAVYHUNTER, DEUTERIUM_COST_HEAVYHUNTER)) break;
			this.metal -= METAL_COST_HEAVYHUNTER;
			this.deuterium -= DEUTERIUM_COST_HEAVYHUNTER;
			army[1].add(new HeavyHunter(technologyDefense, technologyAtack));
			built++;
		}
		if (built < n) {
			System.out.println("Solo se han construido " + built + " Heavy Hunters.");
			throw new ResourceException();
		}
	}

	public void newBattleShip(int n) throws ResourceException {
		int built = 0;
		for (int i = 0; i < n; i++) {
			if (!canAfford(METAL_COST_BATTLESHIP, DEUTERIUM_COST_BATTLESHIP)) break;
			this.metal -= METAL_COST_BATTLESHIP;
			this.deuterium -= DEUTERIUM_COST_BATTLESHIP;
			army[2].add(new BattleShip(technologyDefense, technologyAtack));
			built++;
		}
		if (built < n) {
			System.out.println("Solo se han construido " + built + " Battle Ships.");
			throw new ResourceException();
		}
	}

	public void newArmoredShip(int n) throws ResourceException {
		int built = 0;
		for (int i = 0; i < n; i++) {
			if (!canAfford(METAL_COST_ARMOREDSHIP, DEUTERIUM_COST_ARMOREDSHIP)) break;
			this.metal -= METAL_COST_ARMOREDSHIP;
			this.deuterium -= DEUTERIUM_COST_ARMOREDSHIP;
			army[3].add(new ArmoredShip(technologyDefense, technologyAtack));
			built++;
		}
		if (built < n) {
			System.out.println("Solo se han construido " + built + " Armored Ships.");
			throw new ResourceException();
		}
	}

	public void newMissileLauncher(int n) throws ResourceException {
		int built = 0;
		for (int i = 0; i < n; i++) {
			if (!canAfford(METAL_COST_MISSILELAUNCHER, DEUTERIUM_COST_MISSILELAUNCHER)) break;
			this.metal -= METAL_COST_MISSILELAUNCHER;
			this.deuterium -= DEUTERIUM_COST_MISSILELAUNCHER;
			army[4].add(new MissileLauncher(technologyDefense, technologyAtack));
			built++;
		}
		if (built < n) {
			System.out.println("Solo se han construido " + built + " Missile Launchers.");
			throw new ResourceException();
		}
	}

	public void newIonCannon(int n) throws ResourceException {
		int built = 0;
		for (int i = 0; i < n; i++) {
			if (!canAfford(METAL_COST_IONCANNON, DEUTERIUM_COST_IONCANNON)) break;
			this.metal -= METAL_COST_IONCANNON;
			this.deuterium -= DEUTERIUM_COST_IONCANNON;
			army[5].add(new IonCannon(technologyDefense, technologyAtack));
			built++;
		}
		if (built < n) {
			System.out.println("Solo se han construido " + built + " Ion Cannons.");
			throw new ResourceException();
		}
	}

	public void newPlasmaCannon(int n) throws ResourceException {
		int built = 0;
		for (int i = 0; i < n; i++) {
			if (!canAfford(METAL_COST_PLASMACANNON, DEUTERIUM_COST_PLASMACANNON)) break;
			this.metal -= METAL_COST_PLASMACANNON;
			this.deuterium -= DEUTERIUM_COST_PLASMACANNON;
			army[6].add(new PlasmaCannon(technologyDefense, technologyAtack));
			built++;
		}
		if (built < n) {
			System.out.println("Solo se han construido " + built + " Plasma Cannons.");
			throw new ResourceException();
		}
	}

	public void printStats() {
		System.out.println("------ STATS PLANETA ------");
		System.out.println("Metal: " + metal);
		System.out.println("Deuterio: " + deuterium);
		System.out.println("Tecnología Defensa: " + technologyDefense);
		System.out.println("Tecnología Ataque: " + technologyAtack);
		System.out.println("Upgrade Defensa cost: " + upgradeDefenseTechnologyDeuteriumCost);
		System.out.println("Upgrade Ataque cost: " + upgradeAttackTechnologyDeuteriumCost);
		System.out.println("Ejército:");

		String[] nombres = { "Ligth Hunter", "Heavy Hunter", "Battle Ship", "Armored Ship", "Missile Launcher", "Ion Cannon", "Plasma Cannon" };
		for (int i = 0; i < 7; i++) {
			System.out.println(nombres[i] + ": " + army[i].size());
		}
		System.out.println("---------------------------");
	}

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
}
