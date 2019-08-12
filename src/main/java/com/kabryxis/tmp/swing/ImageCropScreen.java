package com.kabryxis.tmp.swing;

import com.kabryxis.kabutils.Images;
import com.kabryxis.tmp.TMP;
import com.kabryxis.tmp.user.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageCropScreen extends JLayeredPane {
	
	private final MouseDragListener dragListener;
	
	private JImage imagePanel;
	private String newUserName;
	private File originalFile;
	private int minX, maxX, minY, maxY;
	
	public ImageCropScreen(LoginScreen loginScreen, UserManager userManager) {
		super();
		setPreferredSize(TMP.DEFAULT_SIZE);
		setBackground(Color.RED);
		TMP.addListener((BasicKeyListener)event -> {
			if(getParent() != null) {
				if(event.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
					userManager.createNewUser(newUserName, originalFile, generateCroppedImage());
					SwingUtilities.invokeLater(() -> {
						setVisible(false);
						loginScreen.setVisible(true);
					});
				}
			}
		});
		dragListener = new MouseDragListener() {
			
			private MouseEvent lastEvent = null;
			
			@Override
			public void mousePressed(MouseEvent event) {
				if(imagePanel != null) lastEvent = event;
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				lastEvent = null;
			}
			
			@Override
			public void mouseDragged(MouseEvent event) {
				if(imagePanel == null) return;
				Point imageLoc = imagePanel.getLocation();
				int toX = imageLoc.x + event.getXOnScreen() - lastEvent.getXOnScreen();
				int toY = imageLoc.y + event.getYOnScreen() - lastEvent.getYOnScreen();
				if(toX < minX) toX = minX;
				else if(toX > maxX) toX = maxX;
				if(toY < minY) toY = minY;
				else if(toY > maxY) toY = maxY;
				imagePanel.setLocation(toX, toY);
				lastEvent = event;
			}
			
		};
		addMouseListener(dragListener);
		addMouseMotionListener(dragListener);
		JPanel topBanner = new ComponentBuilder<>(new JPanel(null)).bounds(0, 0, 1920, 340).listeners(dragListener).build();
		JPanel bottomBanner = new ComponentBuilder<>(new JPanel(null)).bounds(0, 740, 1920, 340).listeners(dragListener).build();
		JPanel leftBanner = new ComponentBuilder<>(new JPanel(null)).bounds(0, 340, 760, 400).listeners(dragListener).build();
		JPanel rightBanner = new ComponentBuilder<>(new JPanel(null)).bounds(1160, 340, 760, 400).listeners(dragListener).build();
		add(topBanner);
		setLayer(topBanner, 1);
		add(bottomBanner);
		setLayer(bottomBanner, 1);
		add(leftBanner);
		setLayer(leftBanner, 1);
		add(rightBanner);
		setLayer(rightBanner, 1);
		//setVisible(false);
	}
	
	public void setImage(File originalFile, String name) {
		newUserName = name;
		this.originalFile = originalFile;
		imagePanel = new JImage(Images.upscale(Images.read(originalFile), 400, 400));
		minX = 760 - (imagePanel.getWidth() - 400);
		maxX = 760;
		minY = 340 - (imagePanel.getHeight() - 400);
		maxY = 340;
		imagePanel.setLocation(maxX, maxY);
		imagePanel.addMouseListener(dragListener);
		imagePanel.addMouseMotionListener(dragListener);
		add(imagePanel);
		setLayer(imagePanel, 0);
	}
	
	public BufferedImage generateCroppedImage() {
		Image image = ((ImageIcon)imagePanel.getIcon()).getImage();
		BufferedImage result = new BufferedImage(400, 400, 2);
		Graphics2D graphics = result.createGraphics();
		Point imageLoc = imagePanel.getLocation();
		int x = maxX - imageLoc.x;
		int y = maxY - imageLoc.y;
		graphics.drawImage(image, 0, 0, 400, 400, x, y, x + 400, y + 400, null);
		graphics.dispose();
		return result;
	}
	
}
