package game.view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.*;

public class Components {

	public static JButton newBtn(String text, Color baseColor, int[] padding) {
		JButton button = new JButton(text);

		button.setFont(new Font("Arial", Font.BOLD, 14));
		button.setBackground(baseColor);
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setContentAreaFilled(true);
		button.setOpaque(true);
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		int vertical = padding[0];
		int horizontal = padding[1];
		button.setBorder(BorderFactory.createEmptyBorder(vertical, horizontal, vertical, horizontal));

		Color hoverColor = adjustBrightness(baseColor, 1.10);

		button.addMouseListener(new MouseAdapter() {
			// hover
			public void mouseEntered(MouseEvent e) {
				button.setBackground(hoverColor);
			}

			// unhover
			public void mouseExited(MouseEvent e) {
				button.setBackground(baseColor);
			}
		});

		return button;
	}

	// botones predefinidos
	public static JButton primaryBtn(String text) {
		return newBtn(text, new Color(50, 50, 150), new int[]{10, 20});
	}

	public static JButton secondaryBtn(String text) {
		return newBtn(text, new Color(100, 100, 100), new int[]{8, 16});
	}
	
	public static JButton tertiaryBtn(String text) {
		return newBtn(text, new Color(150, 150, 150), new int[]{6, 14});
	}

	
	// utils
	private static Color adjustBrightness(Color color, double factor) {
		int r = Math.min((int)(color.getRed() * factor), 255);
		int g = Math.min((int)(color.getGreen() * factor), 255);
		int b = Math.min((int)(color.getBlue() * factor), 255);
		return new Color(r, g, b);
	}
}
