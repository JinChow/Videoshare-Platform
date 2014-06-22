package videoshare.model;

import java.sql.*;

import javax.sql.DataSource;

import videoshare.model.Account;

public class AccountDAOJdbcImpl implements AccountDAO {
	private DataSource dataSource;

	public AccountDAOJdbcImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public boolean isUserExisted(Account account) {
		Connection conn = null;
		PreparedStatement stmt = null;
		SQLException ex = null;
		boolean existed = false;
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT COUNT(1) FROM vs_account WHERE name = ?");
			stmt.setString(1, account.getUsername());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				existed = (rs.getInt(1) == 1);
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
		
		return existed;
	}

	@Override
	public void addAccount(Account account) {
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		SQLException ex = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("INSERT INTO vs_account(name, password, email) VALUES(?, ?, ?)");
			stmt2 = conn.prepareStatement("INSERT INTO vs_account_role(name, role) VALUES(?, 'member')");
			stmt.setString(1, account.getUsername());
			stmt.setString(2, account.getPassword());
			stmt.setString(3, account.getEmail());
			stmt2.setString(1, account.getUsername());
			
			conn.setAutoCommit(false);
			stmt.executeUpdate();
			stmt2.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			ex = e;
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException el) {
					ex.setNextException(el);
				}
			}
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
					conn.setAutoCommit(true);
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
	public Account getAccount(Account account) {
		Connection conn = null;
		PreparedStatement stmt = null;
		SQLException ex = null;
		Account acct = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT password, email FROM vs_account WHERE name = ?");
			stmt.setString(1, account.getUsername());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				acct = new Account(account.getUsername(), rs.getString(1), rs.getString(2));
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
		return acct;
	}


	@Override
	public String getRole(Account account) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		SQLException ex = null;
		String role = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT role FROM vs_account_role WHERE name = ?");
			stmt.setString(1, account.getUsername());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				role = rs.getString(1);
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
		
		return role;
	}

}
