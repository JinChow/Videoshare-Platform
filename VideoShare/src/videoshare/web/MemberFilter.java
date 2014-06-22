package videoshare.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class MemberFilter
 */
@WebFilter(
		urlPatterns = { 
				"/upload.jsp", 
				"/logout.do",
				"/addcomment.do"
		}, 
		initParams = { 
				@WebInitParam(name = "LOGIN_VIEW", value = "login.jsp")
		})
public class MemberFilter implements Filter {
	private String LOGIN_VIEW;
	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.LOGIN_VIEW = fConfig.getInitParameter("LOGIN_VIEW");
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req = (HttpServletRequest) request;
		/*String requestUri = req.getRequestURI();
		System.out.println("filter uri: " + requestUri);*/
		if (req.getSession().getAttribute("login") != null) {
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}
		else {
			HttpServletResponse res = (HttpServletResponse) response;
			res.sendRedirect(LOGIN_VIEW);
			/*if (!requestUri.endsWith("index.jsp")) {
				String requestPara = req.getQueryString();
				System.out.println(requestUri + "?" + requestPara);
				String requestUrl = URLEncoder.encode(requestUri + "?" + requestPara, "GBK");			
				res.sendRedirect(LOGIN_VIEW + "?returnUrl=" + requestUrl);
			} else {
				res.sendRedirect(LOGIN_VIEW);
			}*/
		}
	}

}
