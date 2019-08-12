package com.kabryxis.tmp.media;

import com.kabryxis.kabutils.data.file.KFiles;
import com.kabryxis.kabutils.data.file.yaml.Config;
import uk.co.caprica.vlcj.player.base.MediaApi;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Series {
	
	private static final List<String> NO_GENRES_LIST = Collections.singletonList("No genres provided");
	
	private String name;
	private String englishName;
	private boolean displayBothNames;
	private String description;
	private List<String> genres;
	private String rating;
	private Multimedia[] mediaArray;
	
	public Series(MediaApi mediaApi, Config seriesInfo) {
		name = seriesInfo.get("name");
		englishName = seriesInfo.get("english-name");
		displayBothNames = seriesInfo.getBoolean("display-both-names", false);
		description = seriesInfo.get("description", "No description provided");
		genres = seriesInfo.getList("genres", String.class, NO_GENRES_LIST);
		rating = seriesInfo.get("rating", "No rating provided");
		mediaArray = Stream.of(KFiles.getDirectories(seriesInfo.getFile().getParentFile())).map(dir ->
				new Multimedia(this, mediaApi, new Config(new File(dir, "info.yml"), true))).toArray(Multimedia[]::new);
	}
	
}
