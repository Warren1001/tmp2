package com.kabryxis.tmp.swing;

import com.kabryxis.kabutils.Images;
import com.kabryxis.tmp.TMP;
import com.kabryxis.tmp.user.User;
import com.kabryxis.tmp.user.UserManager;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

public class LoginScreen extends JPanel {
	
	private final UserManager userManager;
	private final ImageCropScreen imageCropScreen;
	private final JFileChooser fileChooser;
	private final JPanel usersPanel;
	
	public LoginScreen(UserManager userManager) {
		super(null);
		this.userManager = userManager;
		imageCropScreen = new ImageCropScreen(this, userManager);
		Dimension screenSize = TMP.DEFAULT_SIZE;
		setPreferredSize(screenSize);
		setBackground(TMP.DEFAULT_BG_COLOR);
		//setOpaque(false);
		
		usersPanel = new JPanel(null);
		usersPanel.setBounds(0, (TMP.DEFAULT_SIZE.height - 67 - 440) / 2, TMP.DEFAULT_SIZE.width, 440);
		usersPanel.setBackground(TMP.DEFAULT_BG_COLOR);
		reloadUsers();
		add(usersPanel);
		
		JPanel bottomPanel = new JPanel(null);
		bottomPanel.setBounds(0, screenSize.height - 67, screenSize.width, 67);
		bottomPanel.setBackground(TMP.DEFAULT_BG_COLOR);
		JTextArea newUserText = new TextAreaBuilder("Create a new user..").wrap(false).font(new Font(null, Font.PLAIN, 25))
				.bounds((screenSize.width - 225) / 2, (67 - 35) / 2, 225, 35).build();
		newUserText.addMouseListener(new BasicMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				startNewUserCreation();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				newUserText.setForeground(Color.GRAY);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				newUserText.setForeground(Color.WHITE);
			}
			
		});
		bottomPanel.add(newUserText);
		add(bottomPanel);
		
		fileChooser = new JFileChooser(new File(System.getProperty("user.home"), "Pictures"));
		fileChooser.addChoosableFileFilter(new FileFilter() {
			
			@Override
			public boolean accept(File file) {
				String name = file.getName().toLowerCase();
				return file.isDirectory() || name.endsWith(".png") || name.endsWith(".jpg");
			}
			
			@Override
			public String getDescription() {
				return "jpg/png images";
			}
			
		});
		fileChooser.setAcceptAllFileFilterUsed(false);
	}
	
	public void reloadUsers() {
		usersPanel.removeAll();
		List<User> users = userManager.getUsers();
		int userCount = users.size();
		int imageSize = 440;
		int spacing = (TMP.DEFAULT_SIZE.width - imageSize * userCount) / (userCount + 1);
		usersPanel.setLayout(new FlowLayout(FlowLayout.CENTER, spacing, 0));
		users.forEach(user -> usersPanel.add(new JImage(Images.downscale(user.getImage(), imageSize, imageSize))));
	}
	
	public ImageCropScreen getImageCropScreen() {
		return imageCropScreen;
	}
	
	public void startNewUserCreation() {
		String name = JOptionPane.showInputDialog("Name?");
		if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File imageFile = fileChooser.getSelectedFile();
			imageCropScreen.setImage(imageFile, name);
			SwingUtilities.invokeLater(() -> {
				setVisible(false);
				imageCropScreen.setVisible(true);
			});
			//userManager.createNewUser(name, file);
			//reloadUsers();
		}
		else {
			// TODO use default image
		}
	}
	
}
