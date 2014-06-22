package videoshare.model;

public interface AccountDAO {
	public boolean isUserExisted(Account account);
	public void addAccount(Account account);
	public Account getAccount(Account account);
	public String getRole(Account account);
}
