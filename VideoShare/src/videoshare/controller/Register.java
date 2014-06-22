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

import videoshare.model.Account;
import videoshare.model.UserService;

/**
 * Servlet implementation class Register
 */
@WebServlet(
		urlPatterns={"/register.do"},
		initParams={
				@WebInitParam(name = "SUCCESS_VIEW", value = "registerSuccess.jsp"),
				@WebInitParam(name = "ERROR_VIEW", value = "register.jsp")
		}
)
public class Register extends HttpServlet {
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
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmedPasswd = request.getParameter("confirmedPasswd");
		Account account = new Account(username, password, email);
		
		UserService userService = (UserService) getServletContext().getAttribute("userService");
		
		List<String> errors = new ArrayList<String>();
		
		if (isInvalidEmail(email)) {
			errors.add("未填写邮件或邮件格式不正确");
		}
		if (userService.isUserExisted(account)) {
			errors.add("用户名称为空或已存在");
		}
		if (isInvalidPassword(password, confirmedPasswd)) {
			errors.add("请确认密码符合格式并再次确认密码");
		}
		String resultPage = ERROR_VIEW;
		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
		} else {
			userService.addAccount(account);
			resultPage = SUCCESS_VIEW;
		}
		
		request.getRequestDispatcher(resultPage).forward(request, response);
	}
	
	private boolean isInvalidEmail(String email) {
		return email == null || !email.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	}

	private boolean isInvalidPassword(String password, String confirmedPasswd) {
		return password == null ||
				password.length() < 6 ||
				password.length() > 16 ||
				!password.equals(confirmedPasswd);
	}
	
}
