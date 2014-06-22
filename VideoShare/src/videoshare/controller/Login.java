package videoshare.controller;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import videoshare.model.Account;
import videoshare.model.UserService;

/**
 * Servlet implementation class Login
 */
@WebServlet(
		urlPatterns = { "/login.do" }, 
		initParams = { 
				@WebInitParam(name = "SUCCESS_VIEW", value = "index.jsp"), 
				@WebInitParam(name = "ERROR_VIEW", value = "login.jsp")
		})
public class Login extends HttpServlet {
	private String SUCCESS_VIEW;
	private String ERROR_VIEW;
	
	@Override
	public void init() throws ServletException {
		SUCCESS_VIEW = getServletConfig().getInitParameter("SUCCESS_VIEW");
		ERROR_VIEW = getServletConfig().getInitParameter("ERROR_VIEW");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		UserService userService = (UserService) getServletContext().getAttribute("userService");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String page = null;
		String returnUrl = null;
		String queryString = request.getQueryString();
		
		if (queryString != null) {
			returnUrl = URLDecoder.decode(queryString, "GBK");
			System.out.println("loginServlet returnUrl: " + returnUrl);
			
			String[] paramPairs = queryString.split("&");  
            String[] nameValue = paramPairs[0].split("=");  
            if (nameValue[0].equalsIgnoreCase("ReturnUrl"))  
            {  
                returnUrl = nameValue[1];  
            }  
            System.out.println("loginServlet returnUrl: " + returnUrl);
			
		}
		
		Account account = new Account();
		account.setUsername(username);
		account.setPassword(password);
		
		try {
			request.login(username, password);		
			String role = userService.getRole(account);
			request.getSession().setAttribute("login", username);
			request.getSession().setAttribute("role", role);
			if (returnUrl == null) {
				page = SUCCESS_VIEW;
			} else {
				page = returnUrl;
			}
		} catch(ServletException ex) {
			request.setAttribute("error", "名称或密码错误");
			if (returnUrl == null) {
				page = ERROR_VIEW;
			} else {
				page = returnUrl + queryString;
			}	
		}
		response.sendRedirect(page);
		//request.getRequestDispatcher(page).forward(request, response);
	}

}
