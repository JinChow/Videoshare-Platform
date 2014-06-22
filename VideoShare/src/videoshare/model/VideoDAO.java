package videoshare.model;

import java.util.List;

public interface VideoDAO {
	public void addVideo(Video video);
	public Video getVideo(Video video);
	public void deleteVideo(Video video);
	public List<Video> getNewestVideos();
	public List<Video> getUserVideos(Video video);
	public List<Video> getSearchResult(String searchKey);
	public List<Video> getAllVideo();
}
