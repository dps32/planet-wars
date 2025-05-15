package game.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import game.application.Main;
import game.exception.ResourceException;
import game.model.Battle;
import game.units.MilitaryUnit.UnitType;
import game.units.Variables;
import game.view.Components;

public class GameUI implements Variables {

	private JFrame frame;
	
	private JPanel centerPanel;
	private JPanel statsPanel;
	private JPanel troopsPanel;
	private JPanel incomingPanel;
	
	private JLabel metalLabel;
	private JLabel metalChangeLabel;

	private JLabel deuteriumLabel;
	private JLabel deuteriumChangeLabel;

	private JLabel attackTechLabel;
	private JLabel defenseTechLabel;

	private int lastMetal = -1;
	private int lastDeuterium = -1;


	public void init() {
		frame = new JFrame("Planet Wars");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 600);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBackground(Components.bgColor);
		leftPanel.setPreferredSize(new Dimension(250, 0));

		// STATS PANEL
		statsPanel = new JPanel(new GridLayout(5, 1));
		statsPanel.setBorder(Components.titledBorder("Planet Stats"));
		statsPanel.setBackground(Components.bgColor);

		// Metal
		lastMetal = Main.getPlanet().getMetal();
		JPanel metalPanel = new JPanel();
		metalPanel.setLayout(new BoxLayout(metalPanel, BoxLayout.X_AXIS));
		metalPanel.setBackground(Components.bgColor);
		metalLabel = Components.label("Metal: " + lastMetal);
		metalChangeLabel = Components.label("");
		metalPanel.add(metalLabel);
		metalPanel.add(Box.createHorizontalStrut(5));
		metalPanel.add(metalChangeLabel);
		statsPanel.add(metalPanel);

		// Deuterio
		lastDeuterium = Main.getPlanet().getDeuterium();
		JPanel deutPanel = new JPanel();
		deutPanel.setLayout(new BoxLayout(deutPanel, BoxLayout.X_AXIS));
		deutPanel.setBackground(Components.bgColor);
		deuteriumLabel = Components.label("Deuterio: " + lastDeuterium);
		deuteriumChangeLabel = Components.label("");
		deutPanel.add(deuteriumLabel);
		deutPanel.add(Box.createHorizontalStrut(5));
		deutPanel.add(deuteriumChangeLabel);
		statsPanel.add(deutPanel);

		// Tech Attack
		JPanel techAttackPanel = new JPanel();
		techAttackPanel.setLayout(new BoxLayout(techAttackPanel, BoxLayout.X_AXIS));
		techAttackPanel.setBackground(Components.bgColor);
		attackTechLabel = Components.label("Tech Attack: " + Main.getPlanet().getTechnologyAttack());
		JButton btnUpgradeAttack = Components.tertiaryBtn("Upgrade");
		techAttackPanel.add(attackTechLabel);
		techAttackPanel.add(Box.createHorizontalStrut(10));
		techAttackPanel.add(btnUpgradeAttack);
		statsPanel.add(techAttackPanel);

		// Tech Defense
		JPanel techDefensePanel = new JPanel();
		techDefensePanel.setLayout(new BoxLayout(techDefensePanel, BoxLayout.X_AXIS));
		techDefensePanel.setBackground(Components.bgColor);
		defenseTechLabel = Components.label("Tech Defense: " + Main.getPlanet().getTechnologyDefense());
		JButton btnUpgradeDefense = Components.tertiaryBtn("Upgrade");
		techDefensePanel.add(defenseTechLabel);
		techDefensePanel.add(Box.createHorizontalStrut(10));
		techDefensePanel.add(btnUpgradeDefense);
		statsPanel.add(techDefensePanel);

		leftPanel.add(statsPanel);

		
		
		// Build panel
		JPanel buildPanel = new JPanel(new GridLayout(7, 1, 5, 5));
		buildPanel.setBackground(Components.bgColor);
		buildPanel.setBorder(Components.titledBorder("Build"));
		buildPanel.add(createBuyButton("Light Hunter", UnitType.LIGHT_HUNTER));
		buildPanel.add(createBuyButton("Heavy Hunter", UnitType.HEAVY_HUNTER));
		buildPanel.add(createBuyButton("Battle Ship", UnitType.BATTLE_SHIP));
		buildPanel.add(createBuyButton("Armored Ship", UnitType.ARMORED_SHIP));
		buildPanel.add(createBuyButton("Missile Launcher", UnitType.MISSILE_LAUNCHER));
		buildPanel.add(createBuyButton("Ion Cannon", UnitType.ION_CANNON));
		buildPanel.add(createBuyButton("Plasma Cannon", UnitType.PLASMA_CANNON));
		leftPanel.add(buildPanel);

		frame.add(leftPanel, BorderLayout.WEST);

		// Imagen del planeta
		centerPanel = new JPanel();
		centerPanel.setBackground(Components.planetBgColor);
		centerPanel.setBorder(Components.titledBorder("Planet View"));
		Components.addImage(centerPanel, Components.getImage("/game/view/img/planet.png"));
		frame.add(centerPanel, BorderLayout.CENTER);
		
		

		// Right Panel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setPreferredSize(new Dimension(250, 0));
		rightPanel.setBackground(Components.bgColor);
		

		// Troop status panel
		troopsPanel = new JPanel(new GridLayout(4,2));
		troopsPanel.setBackground(Components.bgColor);
		troopsPanel.setBorder(Components.titledBorder("Current Troops"));
		updateTroopsPanel();
		rightPanel.add(troopsPanel);


		incomingPanel = new JPanel(new GridLayout(2, 2));
		incomingPanel.setBackground(Components.bgColor);
		incomingPanel.setBorder(Components.titledBorder("Incoming Attack"));
		updateIncomingPanel();
		rightPanel.add(incomingPanel);

		JPanel reportsPanel = new JPanel();
		reportsPanel.setBackground(Components.bgColor);
		reportsPanel.setBorder(Components.titledBorder("Battle Reports"));
		JButton btnReports = new JButton("Battle Reports");
		reportsPanel.add(btnReports);
		rightPanel.add(reportsPanel);

		frame.add(rightPanel, BorderLayout.EAST);
		frame.setVisible(true);

		// Listeners
		btnUpgradeAttack.addActionListener(e -> {
		    try {
		        Main.getPlanet().upgradeTechnologyAttack();
		    } catch (ResourceException ex) {
		        showError(ex.getMessage());
		    }
		    repaintStats();
		});

		btnUpgradeDefense.addActionListener(e -> {
		    try {
		        Main.getPlanet().upgradeTechnologyDefense();
		    } catch (ResourceException ex) {
		        showError(ex.getMessage());
		    }
		    repaintStats();
		});
	}

	
	// cambia la imagen al planeta con naves
	public void setAttackingImg() {
		setPlanetImage("/game/view/img/planet_attacking.png");
	}
	
	// planeta normal
	public void setNormalImg() {
		setPlanetImage("/game/view/img/planet.png");
	}
	
	private JButton createBuyButton(String label, UnitType unitType) {
		// icono dependiendo de la unidad
		String path;
		switch(unitType) {
		    case LIGHT_HUNTER: path = "/game/view/img/icon_light.png"; break;
		    case HEAVY_HUNTER: path = "/game/view/img/icon_heavy.png"; break;
		    case BATTLE_SHIP: path = "/game/view/img/icon_battle.png"; break;
		    case ARMORED_SHIP: path = "/game/view/img/icon_armored.png"; break;
		    case MISSILE_LAUNCHER: path = "/game/view/img/icon_missile.png"; break;
		    case ION_CANNON: path = "/game/view/img/icon_ion.png"; break;
		    case PLASMA_CANNON: path = "/game/view/img/icon_plasma.png"; break;
		    default: path = null;
		}

		ImageIcon unitIcon = null;
		if (path != null) {
		    BufferedImage img = Components.getImage(path);
		    Image scaledImg = img.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		    unitIcon = new ImageIcon(scaledImg);
		}


		JButton btn = Components.tertiaryBtn("");
		btn.setLayout(new BorderLayout());

		
		// panel del icono
		JPanel west = new JPanel();
		west.setOpaque(false);
		if (unitIcon != null) {
		    west.add(new JLabel(unitIcon));
		}
		
		// nombre
		JLabel name = new JLabel(label);
		name.setForeground(Components.textColor);
		west.add(name);
		btn.add(west, BorderLayout.WEST);

		// Precios
		JPanel east = new JPanel(new GridLayout(2, 2, 4, 0));
		east.setOpaque(false);
		ImageIcon metalIcon = new ImageIcon(
		    Components.getImage("/game/view/img/icon_metal.png").getScaledInstance(22, 22, Image.SCALE_SMOOTH)
		);
		ImageIcon deutIcon = new ImageIcon(
		    Components.getImage("/game/view/img/icon_deuterium.png").getScaledInstance(22, 22, Image.SCALE_SMOOTH)
		);
		
		// Metal
		east.add(Components.label(metalIcon));
		east.add(Components.secLabel(METAL_COST_UNITS[unitType.ordinal()]+""));
		
		// Deuterio
		east.add(Components.label(deutIcon));
		east.add(Components.secLabel(DEUTERIUM_COST_UNITS[unitType.ordinal()]+""));
		btn.add(east, BorderLayout.EAST);

		// listner de compras
		btn.addActionListener(e -> {
		    try {
		        int n = askAmount(label);
		        buildUnit(unitType, n);
		        repaintStats();
		        updateTroopsPanel(); // actualizamos la cantidad de tropas
		    } catch (ResourceException ex) {
		        showError(ex.getMessage());
		    } catch (NumberFormatException ex) {
		        showError("Cantidad no válida.");
		    }
		});

		return btn;
	}

	// construir unidades
	private void buildUnit(UnitType unitType, int n) throws ResourceException {
		switch (unitType) {
		    case UnitType.LIGHT_HUNTER : Main.getPlanet().newLightHunter(n); break;
		    case UnitType.HEAVY_HUNTER : Main.getPlanet().newHeavyHunter(n); break;
		    case UnitType.BATTLE_SHIP : Main.getPlanet().newBattleShip(n); break;
		    case UnitType.ARMORED_SHIP: Main.getPlanet().newArmoredShip(n); break;
		    case UnitType.MISSILE_LAUNCHER: Main.getPlanet().newMissileLauncher(n); break;
		    case UnitType.ION_CANNON: Main.getPlanet().newIonCannon(n); break;
		    case UnitType.PLASMA_CANNON: Main.getPlanet().newPlasmaCannon(n); break;
		}
	}
	
	private void showError(String msg) {
		JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}

	private int askAmount(String title) throws NumberFormatException {
		String input = JOptionPane.showInputDialog(frame, "Quantity to build:", title, JOptionPane.PLAIN_MESSAGE);
		if (input == null) return 0; // si cancelan
		return Integer.parseInt(input.trim());
	}


	// repintar stats del planeta
	public void repaintStats() {
		int currentMetal = Main.getPlanet().getMetal();
		int currentDeut = Main.getPlanet().getDeuterium();

		updateChangeLabel(metalLabel, metalChangeLabel, "Metal: ", lastMetal, currentMetal);
		updateChangeLabel(deuteriumLabel, deuteriumChangeLabel, "Deuterio: ", lastDeuterium, currentDeut);

		lastMetal = currentMetal;
		lastDeuterium = currentDeut;

		attackTechLabel.setText("Tech Attack: " + Main.getPlanet().getTechnologyAttack());
		defenseTechLabel.setText("Tech Defense: " + Main.getPlanet().getTechnologyDefense());

		statsPanel.revalidate();
		statsPanel.repaint();
	}
	
	// actualizar tropas del planeta
	public void updateTroopsPanel() {
		troopsPanel.removeAll();

		UnitType[] types = UnitType.values();
		String[] iconPaths = {
		    "/game/view/img/icon_light.png",
		    "/game/view/img/icon_heavy.png",
		    "/game/view/img/icon_battle.png",
		    "/game/view/img/icon_armored.png",
		    "/game/view/img/icon_missile.png",
		    "/game/view/img/icon_ion.png",
		    "/game/view/img/icon_plasma.png"
		};

		for (int i = 0; i < types.length; i++) {
		    JPanel row = new JPanel();
		    row.setBackground(Components.bgColor);

		    BufferedImage img = Components.getImage(iconPaths[i]);
		    if (img != null) {
		        ImageIcon icon = new ImageIcon(img.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		        row.add(new JLabel(icon));
		    }

		    int count = Main.getPlanet().getArmy()[i].size();
		    JLabel label = Components.label(count+"");
		    row.add(label);

		    troopsPanel.add(row);
		}

		troopsPanel.revalidate();
		troopsPanel.repaint();
	}
	
	// tropas del enemigo
	public void updateIncomingPanel() {
		incomingPanel.removeAll();
		
		UnitType[] types = UnitType.values();
		String[] iconPaths = {
		    "/game/view/img/icon_light.png",
		    "/game/view/img/icon_heavy.png",
		    "/game/view/img/icon_battle.png",
		    "/game/view/img/icon_armored.png"
		};

		Battle currentBattle = Main.getCurrentBattle();
		if (currentBattle == null) {
			incomingPanel.add(Components.label("There's no active battle"));
			return;
		}
		
		for (int i = 0; i < types.length; i++) {
			if (i > 3) break;
			
		    JPanel row = new JPanel();
		    row.setBackground(Components.bgColor);

		    BufferedImage img = Components.getImage(iconPaths[i]);
		    if (img != null) {
		        ImageIcon icon = new ImageIcon(img.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		        row.add(new JLabel(icon));
		    }

		    int count = currentBattle.getEnemyArmy()[i].size();
		    JLabel label = Components.label(count+"");
		    row.add(label);

		    incomingPanel.add(row);
		}

		incomingPanel.revalidate();
		incomingPanel.repaint();
	}



	// actualizar los porcentajes
	private void updateChangeLabel(JLabel valueLabel, JLabel changeLabel, String prefix, int last, int current) {
		valueLabel.setText(prefix + current);

		int diff = current - last;
		if (diff == 0) return;

		String symbol = diff > 0 ? "+" : ""; // en negativo se pone solo -
		changeLabel.setText("(" + symbol + diff + ")");
		changeLabel.setForeground(diff > 0 ? Components.green : Components.red);

		Timer timer = new Timer(2500, e -> changeLabel.setText(""));
		timer.setRepeats(false);
		timer.start();
	}

	private void setPlanetImage(String path) {
		BufferedImage img = Components.getImage(path);
		Components.addImage(centerPanel, img);
	}


}
