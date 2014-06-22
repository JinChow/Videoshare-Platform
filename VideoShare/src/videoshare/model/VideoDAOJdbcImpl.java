package videoshare.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


public class VideoDAOJdbcImpl implements VideoDAO {
	private DataSource dataSource;
	
	public VideoDAOJdbcImpl(DataSource dataSource) {
		this.dataSource = dataSource;		
	}

	@Override
	public void addVideo(Video video) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		SQLException ex = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("INSERT INTO vs_video(title, username, date, description, filename) VALUE(?, ?, ?, ?, ?)");
			stmt.setString(1, video.getTitle());
			stmt.setString(2, video.getUsername());
			stmt.setTimestamp(3, new Timestamp(video.getDate().getTime()));
			stmt.setString(4, video.getDescription());
			stmt.setString(5, video.getFilename());
			stmt.executeUpdate();
		} catch (SQLException e) {
			ex = e;
		}
		finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					if (ex != null)
						ex = e;
				}
			}
			
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					if (ex != null)
						ex = e;
				}
			}
			
			if (ex != null)
				throw new RuntimeException(ex);
		}
	}

	@Override
	public Video getVideo(Video v) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		SQLException ex = null;
		int id = v.getId();
		Video video = new Video();
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT title, username, date, description, filename FROM vs_video WHERE id = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				video.setId(id);
				video.setTitle(rs.getString(1));
				video.setUsername(rs.getString(2));
				video.setDate(rs.getTimestamp(3));
				video.setDescription(rs.getString(4));
				video.setFilename(rs.getString(5));
				video.setLocation(this.getVideoLocation(video));
			}
		} catch (SQLException e) {
			ex = e;
		}
		finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					if (ex != null)
						ex = e;
				}
			}
			
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					if (ex != null)
						ex = e;
				}
			}
			
			if (ex != null)
				throw new RuntimeException(ex);
		}	
		return video;
	}

	@Override
	public void deleteVideo(Video video) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		SQLException ex = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("DELETE FROM vs_video WHERE id = ?");
			stmt.setInt(1, video.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			ex = e;
		}
		finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					if (ex != null)
						ex = e;
				}
			}
			
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					if (ex != null)
						ex = e;
				}
			}
			
			if (ex != null)
				throw new RuntimeException(ex);
		}
	}

	public List<Video> getNewestVideos() {
		Connection conn = null;
		PreparedStatement stmt = null;
		SQLException ex = null;
		List<Video> newest = null;
		Video video = new Video();
		int id = 0;
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT max(id) FROM vs_video");
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				id = rs.getInt(1);
			}
			
			newest = new ArrayList<Video>();
			
			//取出5个最新上传的视频
			for(int i = 0; id >= 1 && i < 5; ) {
				video.setId(id);
				video = getVideo(video);
				if (video.getLocation() != null) {
					newest.add(getVideo(video));
					i++;
				}
				id--;
			}
		} catch (SQLException e) {
			ex = e;
		}
		finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					if (ex != null)
						ex = e;
				}
			}
			
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					if (ex != null)
						ex = e;
				}
			}
			
			if (ex != null)
				throw new RuntimeException(ex);
		}	
		
		return newest;
	}
	
	private String getVideoLocation(Video video) {
		return "video/" + video.getUsername() + "/" + video.getFilename();
	}

	@Override
	public List<Video> getSearchResult(String searchKey) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		SQLException ex = null;
		List<Video> searchResult = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT id, title FROM vs_video WHERE title like ?");
			stmt.setString(1, "%" + searchKey.trim() + "%");
			ResultSet rs = stmt.executeQuery();
			searchResult = new ArrayList<Video>();
			while (rs.next()) {
				Video video = new Video();
				video.setId(rs.getInt(1));
				video.setTitle(rs.getString(2));
				
				searchResult.add(video);
			}
		} catch (SQLException e) {
			ex = e;
		}
		finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					if (ex != null)
						ex = e;
				}
			}
			
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					if (ex != null)
						ex = e;
				}
			}
			
			if (ex != null)
				throw new RuntimeException(ex);
		}	
		return searchResult;
	}

	@Override
	public List<Video> getUserVideos(Video video) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		SQLException ex = null;
		List<Video> userVideos = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT id, title, date, description, filename FROM vs_video WHERE username = ?");
			stmt.setString(1, video.getUsername());
			ResultSet rs = stmt.executeQuery();
			userVideos = new ArrayList<Video>();
			String username = video.getUsername();
			while (rs.next()) {
				Video v = new Video();
				v.setUsername(username);
				v.setId(rs.getInt(1));
				v.setTitle(rs.getString(2));
				v.setDate(rs.getTimestamp(3));
				v.setDescription(rs.getString(4));
				v.setFilename(rs.getString(5));
				v.setLocation(this.getVideoLocation(v));
				userVideos.add(v);
			}
		} catch (SQLException e) {
			ex = e;
		}
		finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					if (ex != null)
						ex = e;
				}
			}
			
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					if (ex != null)
						ex = e;
				}
			}
			
			if (ex != null)
				throw new RuntimeException(ex);
		}	
		return userVideos;
	}

	@Override
	public List<Video> getAllVideo() {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		SQLException ex = null;
		List<Video> videos = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT id, title, username, date, description, filename FROM vs_video");
			ResultSet rs = stmt.executeQuery();
			videos = new ArrayList<Video>();
			while (rs.next()) {
				Video v = new Video();
				v.setId(rs.getInt(1));
				v.setTitle(rs.getString(2));
				v.setUsername(rs.getString(3));
				v.setDate(rs.getTimestamp(4));
				v.setDescription(rs.getString(5));
				v.setFilename(rs.getString(6));
				v.setLocation(this.getVideoLocation(v));
				videos.add(v);
			}
		} catch (SQLException e) {
			ex = e;
		}
		finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					if (ex != null)
						ex = e;
				}
			}
			
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					if (ex != null)
						ex = e;
				}
			}
			
			if (ex != null)
				throw new RuntimeException(ex);
		}	
		return videos;
	}
}
