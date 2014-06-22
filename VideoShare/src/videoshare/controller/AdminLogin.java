package videoshare.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import videoshare.model.Account;

/**
 * Servlet implementation class AdminLogin
 */
@WebServlet(
		urlPatterns = { "/adminLogin.do" }, 
		initParams = { 
				@WebInitParam(name = "SUCCESS_VIEW", value = "admin.jsp"), 
				@WebInitParam(name = "ERROR_VIEW", value = "adminLogin.jsp")
		})
public class AdminLogin extends HttpServlet {
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String page = null;
		
		Account account = new Account();
		account.setUsername(username);
		account.setPassword(password);
		
		try {
			request.login(username, password);
			request.getSession().setAttribute("login", username);
			page = SUCCESS_VIEW;
		} catch(ServletException ex) {
			request.setAttribute("error", "名称或密码错误");
			page = ERROR_VIEW;
		}
		
		request.getRequestDispatcher(page).forward(request, response);
	}

}
