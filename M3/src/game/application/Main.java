package game.application;
import game.model.Planet;
import game.units.MilitaryUnit.UnitType;
import game.units.Variables;
import game.exception.ResourceException;
import game.model.Battle;
import game.view.GameUI;

import java.util.Timer;
import java.util.TimerTask;


public class Main implements Variables {
	private static Planet planet;
	private static GameUI ui;
	private static int battleId;
	
	public static void main(String[] args) {
		planet = new Planet(0, 0, 200000, 54000);
		ui = new GameUI();
		ui.init();
		
		Timer timer = new Timer();
		
		try {
			planet.newArmoredShip(1);
			planet.newBattleShip(1);
			planet.newHeavyHunter(1);
			planet.newLightHunter(3);
			planet.newMissileLauncher(1);
			planet.newIonCannon(1);
			planet.newPlasmaCannon(1);
		} catch (ResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		timer.schedule(collectResources(), 5000, 10000);
		timer.schedule(newBattle(), 1000, 200000);
	}
	
	
	// obtener recursos
	private static TimerTask collectResources() {
		return new TimerTask() {
			public void run() {
				planet.collect();
			}
		};
	}
	
	// se crea una nueva batalla
	private static TimerTask newBattle() {
		return new TimerTask() {
			public void run() {
				System.out.println("Nueva batalla");
				
				battleId += 1;
				Battle battle = new Battle(battleId);
				createEnemyArmy(battle);
				battle.enemiesPrint();

				// la batalla inicia un minuto después
				new Timer().schedule(new TimerTask() {
					public void run() {
						planet.printStats();
						battle.setPlanetArmy(planet.getArmy());
						battle.start();
						System.out.println(battle.getBattleReport(battleId));
						System.out.println("---------");
//						System.out.println(battle.getBattleDevelopment());
						planet.setArmy(battle.getArmies()[0]);
						
						// si el planeta gana se queda con los restos
						if (battle.planetWon()) {
							planet.setMetal(planet.getMetal() + battle.getWasteMetalDeuterium()[0]);
							planet.setDeuterium(planet.getDeuterium() + battle.getWasteMetalDeuterium()[1]);
						}
						
						planet.printStats();
					}
				}, 6000);
			}
		};
	}

	
	private static void createEnemyArmy(Battle battle) {
		int metal = METAL_BASE_ENEMY_ARMY + ENEMY_FLEET_INCREASE * battleId * (METAL_BASE_ENEMY_ARMY / 100);
		int deuterium = DEUTERIUM_BASE_ENEMY_ARMY + ENEMY_FLEET_INCREASE * battleId * (DEUTERIUM_BASE_ENEMY_ARMY / 100);
		System.out.println("[ENEMY] Metal: " + metal + " | Deuterium: " + deuterium);

		// mientras el enemigo se pueda permitir el lighthunter generamos unidades
		while (metal - METAL_COST_LIGTHHUNTER > 0 && deuterium - DEUTERIUM_COST_LIGTHHUNTER > 0) {
			int roll = (int)(Math.random() * 100);

			if (roll < 35) {
				// light hunter
				battle.addEnemyUnit(UnitType.LIGHT_HUNTER);
				metal -= METAL_COST_LIGTHHUNTER;
				deuterium -= DEUTERIUM_COST_LIGTHHUNTER;
			} else if (roll < 60) {
				// heavy hunter
				battle.addEnemyUnit(UnitType.HEAVY_HUNTER);
				metal -= METAL_COST_HEAVYHUNTER;
				deuterium -= DEUTERIUM_COST_HEAVYHUNTER;
			} else if (roll < 80) {
				// battle ship
				battle.addEnemyUnit(UnitType.BATTLE_SHIP);
				metal -= METAL_COST_BATTLESHIP;
				deuterium -= DEUTERIUM_COST_BATTLESHIP;
			} else {
				// armored ship
				battle.addEnemyUnit(UnitType.ARMORED_SHIP);
				metal -= METAL_COST_ARMOREDSHIP;
				deuterium -= DEUTERIUM_COST_ARMOREDSHIP;
			}
			
			System.out.println("[ENEMY] Metal: " + metal + " | Deuterium: " + deuterium);
		}
	}
	
	
	public void viewThreat() {
		
	}
}
