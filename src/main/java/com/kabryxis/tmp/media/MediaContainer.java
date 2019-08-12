package com.kabryxis.tmp.media;

import uk.co.caprica.vlcj.player.base.MediaApi;

import java.io.File;

public class MediaContainer {
	
	private final Multimedia media;
	private final MediaApi mediaApi;
	private final File file;
	
	public MediaContainer(Multimedia media, MediaApi mediaApi, File file) {
		this.media = media;
		this.mediaApi = mediaApi;
		this.file = file;
	}
	
	public void play() {
		mediaApi.play(file.getAbsolutePath());
	}
	
}
