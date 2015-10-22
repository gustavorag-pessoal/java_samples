package web.samples.restful.server.restlet;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.restlet.service.CorsService;

import web.samples.restful.server.restlet.resource.FakeImageResource;
import web.samples.restful.server.restlet.resource.FakeTaskResource;
import web.samples.restful.server.restlet.services.EventService;

public class RestletServerMain extends Application {

	private EventService eventService;
	private Properties prop;
	
	public static void main(String[] args) throws Exception {
		
		CorsService corsService = new CorsService();         
		corsService.setAllowedOrigins(new HashSet(Arrays.asList("*")));
		corsService.setAllowedCredentials(true);
		
		Application app = new RestletServerMain();
		app.getServices().add(corsService);
		
		Component c = new Component();
		c.getServers().add(Protocol.HTTP, 9192);
		c.getDefaultHost().attach(app); 
		c.start();

	}

	@Override
	public Restlet createInboundRoot() {
		

		prop = new Properties();
		FileInputStream fis;
		eventService = null;
		try {
			fis = new FileInputStream(new File("appProperties.properties"));

			prop.load(fis);
			eventService = new EventService(20, prop);
			fis.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Router router = new Router(getContext());
		//Context inject mode
//		router.getContext().getAttributes().put("EnventService", eventService);
//		router.getContext().getAttributes().put("properties", prop);

		//router.attach("/events", EventResource.class);
		router.attach("/images", FakeImageResource.class);
		router.attach("/tasks/{imageName}", FakeTaskResource.class);
		
		return router;
	}

	public EventService getEventService() {
		return eventService;
	}

	public void setEventService(EventService eventService) {
		eventService = eventService;
	}

	public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		prop = prop;
	}
	
	

}
