package videoshare.model;

import java.util.List;

public interface CommentDAO {
	public void addComment(Comment comment);
	public Comment getComment(Comment comment);
	public void deleteComment(Comment comment);
	public List<Comment> getComments(Comment comment);
	public List<Comment> getUserComments(Comment comment);
}
