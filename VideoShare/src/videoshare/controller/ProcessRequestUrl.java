package videoshare.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProcessRequestUrl {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String uri;
	private String[] paraName;
	private String[] paraValue;	

	public ProcessRequestUrl(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

}
