package videoshare.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import videoshare.model.Comment;
import videoshare.model.UserService;
import videoshare.model.Video;


/**
 * Servlet implementation class AddComment
 */
@WebServlet(
		urlPatterns = { "/addcomment.do" }, 
		initParams = { 
				@WebInitParam(name = "SUCCESS_VIEW", value = "getvideo.do"), 
				@WebInitParam(name = "ERROR_VIEW", value = "getvideo.do")
		})
public class AddComment extends HttpServlet {
	private String SUCCESS_VIEW;
	private String ERROR_VIEW;

	@Override
	public void init() throws ServletException {
		SUCCESS_VIEW = getServletConfig().getInitParameter("SUCCESS_VIEW");
		ERROR_VIEW = getServletConfig().getInitParameter("ERROR_VIEW");
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String username = (String) request.getSession().getAttribute("login");
    	UserService userService = (UserService) getServletContext().getAttribute("userService");
    	int videoId = Integer.parseInt(request.getParameter("videoid"));
    	Comment comment = new Comment();
    	comment.setUsername(username);
    	
    	String text = request.getParameter("text");
		if (text != null && text.length() != 0) {
			if (text.length() <  140) {
				comment.setVideoId(videoId);
				comment.setDate(new Date());
				comment.setText(text);
				userService.addComment(comment);
			} /*else {
				request.getRequestDispatcher(ERROR_VIEW).forward(request, response);
				request.setAttribute("text", text);
			}*/
		}
		
		/*Video video = new Video();
		video.setId(videoid);
		video = userService.getVideo(video);
		List<Comment> comments = userService.getComments(comment);
		request.setAttribute("comments", comments);
		request.setAttribute("video", video);
		request.getRequestDispatcher(SUCCESS_VIEW).forward(request, response);*/
		
		response.sendRedirect(SUCCESS_VIEW + "?id=" + videoId);
    }
	


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

}
