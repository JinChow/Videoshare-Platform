package videoshare.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class CommentDAOJdbcImpl implements CommentDAO {
	private DataSource dataSource;
	
	public CommentDAOJdbcImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void addComment(Comment comment) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		SQLException ex = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("INSERT INTO vs_comment(username, videoid, date, comment) VALUE(?, ?, ?, ?)");
			stmt.setString(1, comment.getUsername());
			stmt.setInt(2, comment.getVideoId());
			stmt.setTimestamp(3, new Timestamp(comment.getDate().getTime()));;
			stmt.setString(4, comment.getText());
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
	public Comment getComment(Comment comment) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		SQLException ex = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT username, date, comment FROM vs_comment WHERE videoid = ?");
			stmt.setInt(1, comment.getVideoId());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				comment.setUsername(rs.getString(1));
				comment.setDate(rs.getTimestamp(2));
				comment.setText(rs.getString(3));
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
		return comment;
	}

	@Override
	public void deleteComment(Comment comment) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		SQLException ex = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("DELETE FROM vs_comment WHERE username = ? and videoid = ? and date = ?");
			stmt.setString(1, comment.getUsername());
			stmt.setInt(2, comment.getVideoId());
			stmt.setTimestamp(3, new Timestamp(comment.getDate().getTime()));
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

	public List<Comment> getComments(Comment comment) {
		// TODO Auto-generated method stub
		List<Comment> comments = null;
		int videoId = comment.getVideoId();
		Connection conn = null;
		PreparedStatement stmt = null;
		SQLException ex = null;
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT username, date, comment FROM vs_comment WHERE videoid = ?");
			stmt.setInt(1, videoId);
			ResultSet rs = stmt.executeQuery();
			comments = new ArrayList<Comment>();
			while (rs.next()) {
				comments.add(new Comment(rs.getString(1), videoId, rs.getTimestamp(2), rs.getString(3)));
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
		
		return comments;	
	}

	@Override
	public List<Comment> getUserComments(Comment comment) {
		// TODO Auto-generated method stub
		List<Comment> comments = null;
		String username = comment.getUsername();
		Connection conn = null;
		PreparedStatement stmt = null;
		SQLException ex = null;
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT videoid, date, comment FROM vs_comment WHERE username = ?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			comments = new ArrayList<Comment>();
			while (rs.next()) {
				comments.add(new Comment(username, rs.getInt(1), rs.getTimestamp(2), rs.getString(3)));
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
		
		return comments;
	}

}
