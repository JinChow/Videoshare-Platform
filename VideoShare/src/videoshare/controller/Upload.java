package videoshare.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import videoshare.model.UserService;
import videoshare.model.Video;

/**
 * Servlet implementation class Upload
 */
//@MultipartConfig
@WebServlet(
		urlPatterns = { "/upload.do"},
		initParams = { 
				@WebInitParam(name = "SUCCESS_VIEW", value = "upload.jsp"), 
				@WebInitParam(name = "ERROR_VIEW", value = "upload.jsp")
		})
public class Upload extends HttpServlet {
	private String SUCCESS_VIEW;
	private String ERROR_VIEW;

	@Override
	public void init() throws ServletException {
		SUCCESS_VIEW = getServletConfig().getInitParameter("SUCCESS_VIEW");
		ERROR_VIEW = getServletConfig().getInitParameter("ERROR_VIEW");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
		System.out.println("对不起，当前请求不是一个多媒体文件上传请求!");
		return;
		}
		//Get username from session
		String username = (String) request.getSession().getAttribute("login");
		String title = null; 
		String description = null;
    	UserService userService = (UserService) getServletContext().getAttribute("userService");
    	
		Video video = new Video();	
		List<String> errors = new ArrayList<String>();
		String resultPage = ERROR_VIEW;
		String filename = null;
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		/*factory.setRepository(new File("d:/workspace/VideoShare/WebContent/cache"));
		factory.setSizeThreshold(100*1024*1024); //100MB
*/		
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		upload.setProgressListener(new UploadProgressListener(request));
		
		List<FileItem> items = null;
		try {
			items = upload.parseRequest(request);
			Iterator<FileItem> itr = items.iterator();
			while(itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (item.isFormField()) {
					String field = item.getFieldName();
					if(field.equals("title")) {
						title = item.getString();
						if (isInvalidTitle(title))
							errors.add("Title格式不正确");
					}
					else if(field.equals("description")) {
						description = item.getString();
						if (description == null) {
							description = " ";
						}
						else if (description.length() > 200)
							errors.add("描述超过200个字符");
					}
				} else {
					filename = item.getName(); //获得文件名	
					//上传文件为空
					if (filename == null || filename.length() <= 0)
						errors.add("上传文件不能为空");
					
					if (!errors.isEmpty()) {
						request.setAttribute("errors", errors);
						break;
					}
					
					Date date = new Date();
					video.setTitle(title);
					video.setUsername(username);
					video.setDate(date);
					video.setDescription(description);
					String fileSavingFolder = "d:/workspace/VideoShare/WebContent/video"  + File.separator + username;			
					File f = new File(fileSavingFolder + File.separator);  
					if(!f.exists()){  
				        f.mkdirs();  
				    } 
						
					//文件写入路径：d:/workspace/VideoShare/WebContent/video/username/date.getTime()+format
					String format = filename.substring(filename.lastIndexOf("."));
					String fileSavingPath = fileSavingFolder + File.separator + date.getTime() + format; 
					
					File uploadFile = new File(fileSavingPath);
					item.write(uploadFile);
					
					video.setFilename(date.getTime() + format);
					userService.addVideo(video);
					resultPage = SUCCESS_VIEW;
				}
			}
		}catch (FileUploadException e) {   
            e.printStackTrace();  
        }  
		catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher(resultPage).forward(request, response);
	}
	
	public class UploadProgressListener implements ProgressListener {//设置上传进度条监听器
		private long megaBytes = -1;
		private HttpServletRequest request;

		UploadProgressListener(HttpServletRequest request) { 
		      this.request = request; 
		} 
		
		@Override
        public void update(long pBytesRead, long pContentLength, int pItems) {
        	long mBytes = pBytesRead / (1024*1024);//每隔1M更新一次
        	if (megaBytes == mBytes) {
        		return;
        	}
        	megaBytes = mBytes;
            double percent = (double) (((float)pBytesRead / (float)pContentLength) * 100);
            System.out.println(percent); 
            request.getSession().setAttribute("percent", percent);
        }
    };
	
	/*
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
		//Get username from session
		String username = (String) request.getSession().getAttribute("login");
		String title = request.getParameter("title");
		String description = request.getParameter("description");
    	UserService userService = (UserService) getServletContext().getAttribute("userService");
    	
		Part part = request.getPart("video");
		Video video = new Video();
		String filename = getFilename(part);
		
		List<String> errors = new ArrayList<String>();
		
		if (isInvalidTitle(title))
			errors.add("Title格式不正确");
		
		if (description.length() > 200)
			errors.add("描述超过200个字符");
		//上传文件为空
		if (filename == null || filename.length() <= 0)
			errors.add("上传文件不能为空");
		
		String resultPage = ERROR_VIEW;
		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
		}
		else {
			Date date = new Date();
			video.setTitle(title);
			video.setUsername(username);
			video.setDate(date);
			video.setDescription(description);
			
			String fileSavingFolder = "d:/workspace/VideoShare/WebContent/video"  + File.separator + username;			
			File f = new File(fileSavingFolder + File.separator);  
			if(!f.exists()){  
	            f.mkdirs();  
	        } 
			
			//文件写入路径：d:/workspace/VideoShare/WebContent/video/username/date.getTime()+format
			String format = filename.substring(filename.lastIndexOf("."));
			String fileSavingPath = fileSavingFolder + File.separator + date.getTime() + format; 
			
			part.write(fileSavingPath);
			resultPage = SUCCESS_VIEW;
			
			video.setFilename(date.getTime() + format);
			userService.addVideo(video);
		}
		
		request.getRequestDispatcher(resultPage).forward(request, response);
	}

	private String getFilename(Part part) {
		String header = part.getHeader("Content-Disposition");
		String filename = header.substring(header.indexOf("filename=\"") + 10, 
				header.lastIndexOf("\""));
		return filename;
	}*/
	
	private boolean isInvalidTitle(String title) {
		return title == null || title.length() <= 0 || title.length() > 50;
	}
}
