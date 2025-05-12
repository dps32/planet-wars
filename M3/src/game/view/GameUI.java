package game.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.view.Components;

public class GameUI {

	private JFrame frame;

	public void init() {
		frame = new JFrame("Planet Wars");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 500);
		frame.setBackground(new Color(28, 32, 36));
		frame.setTitle("Planet Wars");
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		JButton btnCreateUnit = Components.secondaryBtn("primario");
		JButton btnStats = Components.primaryBtn("sec");

		btnCreateUnit.addActionListener(e -> System.out.println("asdsd"));
		btnStats.addActionListener(e -> System.out.println("123123"));

		panel.add(btnCreateUnit);
		panel.add(btnStats);

		frame.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
