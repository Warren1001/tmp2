package com.kabryxis.tmp.user;

import com.kabryxis.kabutils.Images;
import com.kabryxis.kabutils.data.file.KFiles;
import com.kabryxis.kabutils.data.file.yaml.Config;

import java.awt.*;

public class User {
	
	private static final String[] IMAGE_EXTENSIONS = { "png", "jpg" };
	
	private final Config data;
	private final String name;
	private final Image image;
	
	public User(Config data) {
		this.data = data;
		name = data.getName();
		image = Images.read(KFiles.getFileWithPossibleEndings(data.getFile().getParentFile(), name, IMAGE_EXTENSIONS));
	}
	
	public User(Config data, String name, Image image) {
		this.data = data;
		this.name = name;
		this.image = image;
	}
	
	public String getName() {
		return name;
	}
	
	public Image getImage() {
		return image;
	}
	
}
