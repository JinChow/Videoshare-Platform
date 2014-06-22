package videoshare.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class UserService {
	private AccountDAO accountDAO;
	private VideoDAO videoDAO;
	private CommentDAO commentDAO;
	
	//将评论按时间排序
	private class CommentDateComparator implements Comparator<Comment> {
		@Override
		public int compare(Comment d1, Comment d2) {
			return -d1.getDate().compareTo(d2.getDate());
		}		
	}
	
	private class VideoDateComparator implements Comparator<Video> {
		@Override
		public int compare(Video d1, Video d2) {
			return -d1.getDate().compareTo(d2.getDate());
		}		
	}
	
	private CommentDateComparator commentComparator = new CommentDateComparator();
	private VideoDateComparator videoComparator = new VideoDateComparator();
	
	

	public UserService(AccountDAO accountDAO, VideoDAO videoDAO, CommentDAO commentDAO) {
		this.accountDAO = accountDAO;
		this.videoDAO = videoDAO;
		this.commentDAO = commentDAO;
	}
	
	public boolean isUserExisted(Account account) {
		return accountDAO.isUserExisted(account);
	}
	
	//Add an account to the database
	public void addAccount(Account account) {
		accountDAO.addAccount(account);
	}
	
	//Get the role of the account(member/admin)
	public String getRole(Account account) {
		return accountDAO.getRole(account);
	}
	
	//Add a video to the database
	public void addVideo(Video video) {
		videoDAO.addVideo(video);
	}
	
	//Get a video by id
	public Video getVideo(Video video) {
		return videoDAO.getVideo(video);
	}
	
	public List<Video> getNewestVideos() {
		List<Video> newest = videoDAO.getNewestVideos();
		return newest;
	}
	
	public List<Video> getUserVideos(Video video) {
		List<Video> userVideos = videoDAO.getUserVideos(video);
		Collections.sort(userVideos, videoComparator);
		return userVideos;
	}
	
	public List<Video> getAllVideos() {
		return videoDAO.getAllVideo();
	}
	
	public void deleteVideo(Video video) {
		videoDAO.deleteVideo(video);
	}
	
	public void addComment(Comment comment) {
		commentDAO.addComment(comment);
	}
	
	public List<Comment> getComments(Comment comment) {
		List<Comment> comments = commentDAO.getComments(comment);
		Collections.sort(comments, commentComparator);
		return comments;
	}
	
	public List<Comment> getUserComments(Comment comment) {
		List<Comment> comments = commentDAO.getUserComments(comment);
		Collections.sort(comments, commentComparator);
		return comments;
	}
	
	public void deleteComment(Comment comment) {
		commentDAO.deleteComment(comment);
	}
	
	public List<Video> searchVideos(String searchKey) {
		List<Video> searchResult = videoDAO.getSearchResult(searchKey);
		return searchResult;
	}
}
