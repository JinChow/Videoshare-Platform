package videoshare.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import videoshare.model.Comment;
import videoshare.model.UserService;

/**
 * Servlet implementation class DeleteComment
 */

@WebServlet(
		urlPatterns = { "/deletecomment.do" }, 
		initParams = { 
				@WebInitParam(name = "SUCCESS_VIEW", value = "getvideo.do"), 
				@WebInitParam(name = "ERROR_VIEW", value = "getvideo.do")
		})
public class DeleteComment extends HttpServlet {
	private String SUCCESS_VIEW;
	private String ERROR_VIEW;
	
	@Override
	public void init() throws ServletException {
		SUCCESS_VIEW = getServletConfig().getInitParameter("SUCCESS_VIEW");
		ERROR_VIEW = getServletConfig().getInitParameter("ERROR_VIEW");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserService userService = (UserService) getServletContext().getAttribute("userService");
		
		String username = (String) request.getSession().getAttribute("login");
		int videoId = Integer.parseInt(request.getParameter("videoid"));
		String date = request.getParameter("date");
		Comment comment = new Comment();
		comment.setUsername(username);
		comment.setVideoId(videoId);
		comment.setDate(new Date(Long.parseLong(date)));
		
		userService.deleteComment(comment);
		
		response.sendRedirect(SUCCESS_VIEW + "?id=" + videoId);
	}

}
