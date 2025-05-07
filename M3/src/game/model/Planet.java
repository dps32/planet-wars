package game.model;

import java.util.ArrayList;
import java.util.Arrays;

import game.exception.ResourceException;
import game.units.MilitaryUnit;
import game.units.Variables;

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
	}
	
	

	@Override
	public String toString() {
		return "Planet [technologyDefense=" + technologyDefense + ", technologyAtack=" + technologyAtack + ", metal="
				+ metal + ", deuterium=" + deuterium + ", upgradeDefenseTechnologyDeuteriumCost="
				+ upgradeDefenseTechnologyDeuteriumCost + ", upgradeAttackTechnologyDeuteriumCost="
				+ upgradeAttackTechnologyDeuteriumCost + ", army=" + Arrays.toString(army) + "]";
	}



	public void upgradeTechnologyDefense () {
		try {
			if (!canAfford(upgradeDefenseTechnologyDeuteriumCost)) throw new ResourceException();
			
			this.technologyDefense += 1;
			this.deuterium -= upgradeDefenseTechnologyDeuteriumCost;
			
			final int baseCost = UPGRADE_BASE_DEFENSE_TECHNOLOGY_DEUTERIUM_COST;
			final int plus = UPGRADE_PLUS_DEFENSE_TECHNOLOGY_DEUTERIUM_COST* this.technologyDefense;
			upgradeDefenseTechnologyDeuteriumCost = baseCost + (baseCost * plus) / 100;
		} catch (Exception e) {
			System.out.println("No tienes deuterio suficiente");
		}

	}
	
	public void upgradeTechnologyAttack () {
		
	}
	
	
	public void collect() {
		System.out.println("Obteniendo recursos");
		this.deuterium += PLANET_DEUTERIUM_GENERATED;
		this.metal += PLANET_METAL_GENERATED;
	}
	
	
	public void newLightHunter(int n) {
		
	}
	
	public void newHeavyHunter(int n) {
		
	}
	
	public void newBattleShip(int n) {
		
	}
	
	public void newArmoredShip(int n) {
		
	}
	
	public void newMissileLauncher(int n) {
		
	}
	
	public void newIonCannon(int n) {
		
	}
	
	public void newPlasmaCannon(int n) {
		
	}
	
	
	public void printStats() {
		
	}
	
	private boolean canAfford(int deuterio) {
		if (this.deuterium - deuterio < 0) return false;
		return true;
	}
	
	private boolean canAfford(int metal, int deuterio) {
		if (this.metal - metal < 0) return false;
		if (this.deuterium - deuterio < 0) return false;
		return true;
	}
}
