package videoshare.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import videoshare.model.Account;
import videoshare.model.Comment;
import videoshare.model.UserService;
import videoshare.model.Video;



/**
 * Servlet implementation class GetUserPage
 */
@WebServlet(
		urlPatterns = { "/user/*" }, 
		initParams = { 
				@WebInitParam(name = "USER_VIEW", value = "/userPage.jsp")
		})
public class GetUserPage extends HttpServlet {
	private String USER_VIEW;
	
	@Override
	public void init() throws ServletException {
		USER_VIEW = getServletConfig().getInitParameter("USER_VIEW");
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean userExisted;
		UserService userService = (UserService) getServletContext().getAttribute("userService");
		String username = request.getPathInfo().substring(1);
		Account account = new Account();
		account.setUsername(username);
		
		userExisted = userService.isUserExisted(account);
		if(userExisted) {
	        Video video = new Video();
	        video.setUsername(username);
	        List<Video> videos = userService.getUserVideos(video);
	        request.setAttribute("videos", videos);
	        
	        Comment comment = new Comment();
	        comment.setUsername(username);
	        List<Comment> comments = userService.getUserComments(comment);
	        request.setAttribute("comments", comments);
	    }
		request.setAttribute("userExisted", userExisted);
        request.setAttribute("username", username);
        request.getRequestDispatcher(USER_VIEW).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

}
