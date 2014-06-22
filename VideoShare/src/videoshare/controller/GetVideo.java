package videoshare.controller;

import java.io.IOException;
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
 * Servlet implementation class GetVideo
 */
@WebServlet(
		urlPatterns = { "/getvideo.do" }, 
		initParams = { 
				@WebInitParam(name = "VIDEO_VIEW", value = "/video.jsp")
		})
public class GetVideo extends HttpServlet {
	private String VIDEO_VIEW;
	@Override
	public void init() throws ServletException {
		VIDEO_VIEW = getServletConfig().getInitParameter("VIDEO_VIEW");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserService userService = (UserService) getServletContext().getAttribute("userService");
		int id = Integer.parseInt(request.getParameter("id"));
		
		Video video = new Video();
		video.setId(id);
		video = userService.getVideo(video);
		
		Comment comment = new Comment();
		comment.setVideoId(id);
		List<Comment> comments = userService.getComments(comment); 
		
		request.setAttribute("video", video);
		request.setAttribute("comments", comments);
		request.getRequestDispatcher(VIDEO_VIEW).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
