package videoshare.web;


import java.util.List;

import javax.naming.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import videoshare.model.*;

/**
 * Application Lifecycle Listener implementation class VideoShareListener
 *
 */
@WebListener
public class VideoShareListener implements ServletContextListener {

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {
        try {
        	Context initContext = new InitialContext();
        	Context envContext = (Context) initContext.lookup("java:/comp/env");
        	DataSource dataSource = (DataSource) envContext.lookup("jdbc/videoshare");
        	ServletContext context = sce.getServletContext();
        	
        	UserService userService = new UserService(new AccountDAOJdbcImpl(dataSource),
        			new VideoDAOJdbcImpl(dataSource), new CommentDAOJdbcImpl(dataSource));
        	//List<Video> newest = userService.getNewestVideos();
        	
        	context.setAttribute("userService", userService);
        	//context.setAttribute("newest", newest);
        } catch (NamingException ex) {
        	throw new RuntimeException(ex);
    	}
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
    }
	
}
