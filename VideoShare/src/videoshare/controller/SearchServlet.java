package videoshare.controller;

import java.io.IOException;
import java.util.ArrayList;
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
 * Servlet implementation class SearchServlet
 */
@WebServlet(
		urlPatterns = { "/searchservlet.do" }, 
		initParams = { 
				@WebInitParam(name = "SUCCESS_VIEW", value = "searchResult.jsp")
		})
public class SearchServlet extends HttpServlet {
	private String SUCCESS_VIEW;
	

	@Override
	public void init() throws ServletException {
		SUCCESS_VIEW = getServletConfig().getInitParameter("SUCCESS_VIEW");
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String searchKey = request.getParameter("searchkey");
		UserService userService = (UserService) getServletContext().getAttribute("userService");
		List<Video> searchResult = new ArrayList<Video>();
		
		searchResult = userService.searchVideos(searchKey);
		request.setAttribute("searchResult", searchResult);
		request.getRequestDispatcher(SUCCESS_VIEW).forward(request, response);
	}

}
