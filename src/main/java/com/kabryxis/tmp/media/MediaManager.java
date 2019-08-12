package com.kabryxis.tmp.media;

import com.kabryxis.kabutils.data.file.KFiles;
import com.kabryxis.kabutils.data.file.yaml.Config;
import com.kabryxis.tmp.TMP;

import java.io.File;

public class MediaManager {
	
	private final TMP tmp;
	private final File mediaDirectory;
	
	public MediaManager(TMP tmp) {
		this.tmp = tmp;
		mediaDirectory = new File("C:" + File.separator + "MDB");
		// Validate.isTrue(mediaDirectory.exists(), "Media directory (%s) does not exist!", mediaDirectory.getPath());
	}
	
	public void reloadAllMedia() {
		KFiles.forEachDirectory(mediaDirectory, dir -> {
			Config seriesInfo = new Config(new File(dir, "info.yml"), true);
			Series series = new Series(tmp.getMediaPlayer().media(), seriesInfo);
		});
	}
	
}
