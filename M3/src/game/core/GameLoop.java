package game.core;
import game.model.Planet;
import game.model.Battle;
import game.view.GameUI;

import java.util.Timer;
import java.util.TimerTask;

import game.core.Database;

public class GameLoop {

	private Planet planet;
	private GameUI ui;

	public GameLoop() {
		this.planet = new Planet(0, 0, 0, 0);
		this.ui = new GameUI(this);
		this.ui.init();
		
//		Timer timer = new Timer();
//		timer.schedule(collectResources(), 10000);
//		timer.schedule(newBattle(), 60000);
		
		
	}

	// obtener recursos
	private TimerTask collectResources() {
		return new TimerTask() {
			public void run() {
				planet.collect();
			}
		};
	}
	
	private TimerTask newBattle() {
		return new TimerTask() {
			public void run() {
				Battle battle = new Battle();
				createEnemyArmy();

				// la batalla inicia un minuto después
				new Timer().schedule(new TimerTask() {
					public void run() {
						battle.start();
					}
				}, 60000);
			}
		};
	}

	
//	● Cazador ligero 35%
//	● Cazador pesado 25%
//	● Nave de Batalla 20%
//	● Acorazado 20%.
	
	private void createEnemyArmy() {
		int roll = (int)(Math.random() * 100);

		if (roll < 35) {
			// Ligero
		} else if (roll < 60) {
			// Pesado
		} else if (roll < 80) {
			// Batalla
		} else {
			// Acorazado
		}

	}
	
}
