package game.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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

	private DefaultListModel<Integer> battleListModel;

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
		incomingPanel.setPreferredSize(new Dimension(250, 140));
		updateIncomingPanel();
		rightPanel.add(incomingPanel);

		JPanel reportsPanel = new JPanel(new BorderLayout());
		reportsPanel.setBackground(Components.bgColor);
		reportsPanel.setBorder(Components.titledBorder("Recent Battles"));

		battleListModel = new DefaultListModel<>();
		JList<Integer> battleList = new JList<>(battleListModel);
		battleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


		// evento al hacer doble clic
		battleList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
		            Integer selected = battleList.getSelectedValue();
		            if (selected == null) return;
		            showBattleReport(selected);
		        }
		    }
		});

		// cargar batallas recientes
		loadRecentBattles(battleListModel);
		
		battleList.setBackground(Components.bgColor);
		battleList.setForeground(Components.textColor);
		battleList.setSelectionBackground(Components.planetBgColor);
		battleList.setSelectionForeground(Color.WHITE);

		JScrollPane scrollPane = new JScrollPane(battleList);
		scrollPane.setBackground(Components.bgColor);
		scrollPane.getViewport().setBackground(Components.bgColor);


		reportsPanel.add(scrollPane, BorderLayout.CENTER);
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
		    	repaintStats();
		    	updateTroopsPanel(); // actualizamos la cantidad de tropas
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

		Battle currentBattle = Main.getCurrentBattle();
		if (currentBattle == null) { // cambiamos el layout para que no se buguee
			incomingPanel.setLayout(new GridLayout(1,1));
			incomingPanel.add(Components.label(" ".repeat(15) + "There's no active battle"));
			incomingPanel.revalidate();
			incomingPanel.repaint();
			return;
		}
		
		UnitType[] types = UnitType.values();
		String[] iconPaths = {
		    "/game/view/img/icon_light.png",
		    "/game/view/img/icon_heavy.png",
		    "/game/view/img/icon_battle.png",
		    "/game/view/img/icon_armored.png"
		};
		
		// restauramos el layout normal
		incomingPanel.setLayout(new GridLayout(2, 2));
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

	private void loadRecentBattles(DefaultListModel<Integer> model) {
	    String query = "SELECT num_battle FROM Battle_stats WHERE planet_id = ? ORDER BY num_battle DESC LIMIT 5";
	    ArrayList<Object> params = new ArrayList<>();
	    params.add(Main.getPlanet().getPlanetId());

	    try (var conn = java.sql.DriverManager.getConnection("jdbc:mysql://sql7.freesqldatabase.com:3306/sql7779453?useSSL=false", "sql7779453", "vSbpssnPdD");
	         var ps = conn.prepareStatement(query)) {

	        ps.setInt(1, Main.getPlanet().getPlanetId());
	        var rs = ps.executeQuery();

	        while (rs.next()) {
	            model.addElement(rs.getInt("num_battle"));
	        }

	        rs.close();
	        ps.close();

	    } catch (Exception ex) {
	        JOptionPane.showMessageDialog(frame, "Error loading recent battles: " + ex.getMessage());
	    }
	}
	
	private void showBattleReport(int battleId) {
	    JFrame reportFrame = new JFrame("Battle Report #" + battleId);
	    reportFrame.setSize(600, 600);
	    reportFrame.setLocationRelativeTo(null);
	    reportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	    JTextArea reportArea = new JTextArea();
	    reportArea.setEditable(false);
	    reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
	    reportArea.setBackground(Color.BLACK);
	    reportArea.setForeground(Color.GREEN);

	    // obtener el informe desde la BD
	    String report = getBattleReportFromDB(battleId);
	    reportArea.setText(report);

	    JScrollPane scroll = new JScrollPane(reportArea);
	    reportFrame.add(scroll);

	    reportFrame.setVisible(true);
	}

	
	private String getBattleReportFromDB(int battleId) {
	    StringBuilder sb = new StringBuilder();
	    String query = "SELECT log_entry FROM Battle_log WHERE planet_id = ? AND num_battle = ? ORDER BY num_line ASC";

	    ArrayList<Object> params = new ArrayList<>();
	    params.add(Main.getPlanet().getPlanetId());
	    params.add(battleId);

	    try (
	        var conn = java.sql.DriverManager.getConnection("jdbc:mysql://sql7.freesqldatabase.com:3306/sql7779453?useSSL=false", "sql7779453", "vSbpssnPdD");
	        var ps = conn.prepareStatement(query)
	    ) {
	        ps.setInt(1, Main.getPlanet().getPlanetId());
	        ps.setInt(2, battleId);

	        var rs = ps.executeQuery();
	        while (rs.next()) {
	            sb.append(rs.getString("log_entry")).append("\n");
	        }

	        rs.close();
	        ps.close();
	    } catch (Exception e) {
	        sb.append("Error loading report: ").append(e.getMessage());
	    }

	    return sb.toString();
	}

	public void refreshBattleList() {
	    battleListModel.clear();
	    loadRecentBattles(battleListModel);
	}


}
