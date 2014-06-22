package videoshare.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import videoshare.model.UserService;
import videoshare.model.Video;

/**
 * Servlet implementation class DeleteVideo
 */
@WebServlet(
		urlPatterns = { "/deletevideo.do" }, 
		initParams = { 
				@WebInitParam(name = "SUCCESS_VIEW", value = "index.jsp")
		})
public class DeleteVideo extends HttpServlet {
	private String SUCCESS_VIEW;
	
	@Override
	public void init() throws ServletException {
		SUCCESS_VIEW = getServletConfig().getInitParameter("SUCCESS_VIEW");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserService userService = (UserService) getServletContext().getAttribute("userService");
		
		String username = (String) request.getSession().getAttribute("login");
		int videoId = Integer.parseInt(request.getParameter("videoid"));
		Video video = new Video();
		video.setId(videoId);
		
		userService.deleteVideo(video);
		
		request.getRequestDispatcher(SUCCESS_VIEW).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
