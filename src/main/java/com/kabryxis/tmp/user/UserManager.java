package com.kabryxis.tmp.user;

import com.kabryxis.kabutils.data.file.KFiles;
import com.kabryxis.kabutils.data.file.yaml.Config;
import com.kabryxis.tmp.swing.LoginScreen;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class UserManager {
	
	private static final Comparator<User> USER_COMPARATOR = (u1, u2) -> String.CASE_INSENSITIVE_ORDER.compare(u1.getName(), u2.getName());
	
	private final Set<Consumer<? super User>> changedUsersActions = new HashSet<>();
	
	private final File usersDirectory;
	private final List<User> users;
	
	private LoginScreen loginScreen;
	private User currentUser = null;
	
	public UserManager() {
		usersDirectory = new File("users");
		users = usersDirectory.exists() ? Config.stream(usersDirectory).map(User::new).sorted(USER_COMPARATOR).collect(Collectors.toList()) : new ArrayList<>();
	}
	
	public void setLoginScreen(LoginScreen loginScreen) {
		this.loginScreen = loginScreen;
	}
	
	public void registerChangedUserAction(Consumer<? super User> action) {
		changedUsersActions.add(action);
	}
	
	public void changeUser(User newUser) {
		currentUser = newUser;
		changedUsersActions.forEach(action -> action.accept(newUser));
	}
	
	public void createNewUser(String name, File originalFile, BufferedImage image) {
		try {
			String extension = KFiles.getExtension(originalFile);
			ImageIO.write(image, extension, new File(usersDirectory, name + "." + extension));
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		Config data = new Config(new File(usersDirectory, name + ".yml"));
		data.save();
		User user = new User(data, name, image);
		users.add(user);
		users.sort(USER_COMPARATOR);
		SwingUtilities.invokeLater(loginScreen::reloadUsers);
		changeUser(user);
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public List<User> getUsers() {
		return new ArrayList<>(users);
	}
	
}
