package com.kabryxis.tmp.media;

import com.kabryxis.kabutils.data.file.KFiles;
import com.kabryxis.kabutils.data.file.yaml.Config;
import uk.co.caprica.vlcj.player.base.MediaApi;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Multimedia {
	
	public static final String[] MEDIA_EXTENSIONS = { "mp4", "mkv" };
	
	private final Series series;
	private final boolean isShow;
	
	private File directory;
	private String name;
	private List<MediaContainer> mediaContainers;
	
	public Multimedia(Series series, MediaApi mediaApi, Config mediaInfo) {
		this.series = series;
		isShow = mediaInfo.getBoolean("is-show");
		directory = mediaInfo.getFile().getParentFile();
		name = mediaInfo.get("name");
		if(name == null && isShow) name = "Season " + mediaInfo.getFile().getParentFile().getName();
		mediaContainers = Stream.of(KFiles.getFilesWithEndings(directory, MEDIA_EXTENSIONS)).map(file -> new MediaContainer(this, mediaApi, file)).collect(Collectors.toList());
	}
	
	public Series getSeries() {
		return series;
	}
	
	public boolean isShow() {
		return isShow;
	}
	
	
	
}
