package game.view;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Components {
	
	public final static Color bgColor = new Color(45, 50, 55);
	public final static Color borderColor = new Color(180, 180, 180);
	public final static Color borderTitleColor = new Color(220, 220, 220);
	public final static Color planetBgColor = new Color(30, 30, 30);
	
	public final static Color textColor = new Color(240, 240, 240);
	public final static Color secTextColor = new Color(200, 200, 200);
	public final static Color green = new Color(40, 255, 40);
	public final static Color red = new Color(255, 40, 40);

	
	public static JButton newBtn(String text, Color baseColor, int[] padding) {
		JButton button = new JButton(text);

		button.setFont(new Font("Arial", Font.BOLD, 12));
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

	
	
	// ------------------ botones predefinidos ------------------------
	public static JButton primaryBtn(String text) {
		return newBtn(text, new Color(50, 50, 150), new int[]{10, 20});
	}

	public static JButton secondaryBtn(String text) {
		return newBtn(text, new Color(100, 100, 100), new int[]{8, 16});
	}
	
	public static JButton tertiaryBtn(String text) {
		return newBtn(text, new Color(100, 100, 100), new int[]{4, 7});
	}
	
	public static TitledBorder titledBorder(String text) {
		TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(borderColor), text);
		border.setTitleColor(borderTitleColor);
		border.setTitleFont(new Font("Arial", Font.BOLD, 12));
		return border;
	}
	
	public static JLabel label(String text) {
		JLabel label = new JLabel(text);
		label.setForeground(textColor);
		return label;
	}
	
	public static JLabel secLabel(String text) {
		JLabel label = new JLabel(text);
		label.setForeground(secTextColor);
		return label;
	}
	
	public static JLabel label(ImageIcon icon) {
		JLabel label = new JLabel(icon);
		label.setForeground(textColor);
		return label;
	}
	
	
	
	// ----------------- utils --------------------------------------
	private static Color adjustBrightness(Color color, double factor) {
		int r = Math.min((int)(color.getRed() * factor), 255);
		int g = Math.min((int)(color.getGreen() * factor), 255);
		int b = Math.min((int)(color.getBlue() * factor), 255);
		return new Color(r, g, b);
	}
	
	// obtener una imagen de un path
	public static BufferedImage getImage(String path) {
	    try {
	        InputStream is = Components.class.getResourceAsStream(path);
	        if (is == null) {
	            System.err.println("No se encontró la imagen en: " + path);
	            return null;
	        }
	        return ImageIO.read(is);
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	// añdir una imagen a un panel
	public static void addImage(JPanel panel, BufferedImage imagen) {
	    panel.setLayout(new BorderLayout());
	    panel.removeAll();

	    JPanel imagePanel = new JPanel() {

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagen == null) return; // si no hay imagen return
            
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int imgWidth = imagen.getWidth();
            int imgHeight = imagen.getHeight();

            float ratio = Math.min(1f, Math.min((float) panelWidth / imgWidth, (float) panelHeight / imgHeight));

            int drawWidth = (int) (imgWidth * ratio);
            int drawHeight = (int) (imgHeight * ratio);
            int x = (panelWidth - drawWidth) / 2;
            int y = (panelHeight - drawHeight) / 2;

            g2d.drawImage(imagen, x, y, drawWidth, drawHeight, null);
        }};

	    imagePanel.setOpaque(false);
	    panel.add(imagePanel, BorderLayout.CENTER);
	    panel.revalidate();
	    panel.repaint();
	}


}
