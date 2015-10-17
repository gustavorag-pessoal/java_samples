package web.samples.restful.server.restlet;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

import web.samples.restful.server.restlet.resource.EventResource;

public class RestletServerMain extends Application {

	public static void main(String[] args) throws Exception {
		
		Component c = new Component();
		c.getServers().add(Protocol.HTTP, 9192);
		// TODO Class to handle requests
		c.getDefaultHost().attach(new RestletServerMain()); 

		c.start();

	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());

		router.attach("/upload", EventResource.class);
		return router;
	}

}
