package videoshare.model;

import java.util.Date;

public class Comment {
	private String username;
	private int videoId;
	private Date date;
	private String text;
	
	public Comment() {
		
	}
	
	public Comment(String username, int videoId, Date date, String text) {
		super();
		this.username = username;
		this.videoId = videoId;
		this.date = date;
		this.text = text;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getVideoId() {
		return videoId;
	}
	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
