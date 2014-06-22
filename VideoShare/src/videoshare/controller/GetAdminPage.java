package videoshare.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import videoshare.model.UserService;
import videoshare.model.Video;

/**
 * Servlet implementation class GetAdminPage
 */
@WebServlet(
		urlPatterns = { "/getadminpage.do" }, 
		initParams = { 
				@WebInitParam(name = "SUCCESS_VIEW", value = "/adminPage.jsp"),
				@WebInitParam(name = "ERROR_VIEW", value = "/index.jsp")
		})
public class GetAdminPage extends HttpServlet {
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
		String resultPage = ERROR_VIEW;
		String role = (String) request.getSession().getAttribute("role");
		if (role.equals("admin")) {
			resultPage = SUCCESS_VIEW;
			UserService userService = (UserService) getServletContext().getAttribute("userService");
			List<Video> videos = userService.getAllVideos();
			request.setAttribute("videos", videos);
			
		}
		request.getRequestDispatcher(resultPage).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

}
