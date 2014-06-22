package videoshare.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Logout
 */
@WebServlet(
		urlPatterns = { "/logout.do" }, 
		initParams = { 
				@WebInitParam(name = "INDEX_VIEW", value = "index.jsp")
		})
public class Logout extends HttpServlet {
	private String INDEX_VIEW;
	
	@Override
	public void init() throws ServletException {
		INDEX_VIEW = getServletConfig().getInitParameter("INDEX_VIEW");
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		request.logout();
        response.sendRedirect(INDEX_VIEW);
	}


}
