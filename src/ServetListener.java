import java.io.IOException;
import java.util.Timer;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.json.JSONException;


/**
 * Application Lifecycle Listener implementation class ServetListener
 *
 */
@WebListener
public class ServetListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ServetListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    	System.out.println("contextInitialized");
    	
    	/*
    	transit tr=new transit();
    	try {
			tr.cacca();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("male male");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
    	Timer tim = new Timer();
    	refreshThread rT = new refreshThread();
    	
    	tim.scheduleAtFixedRate(rT, 0, 3600000);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
