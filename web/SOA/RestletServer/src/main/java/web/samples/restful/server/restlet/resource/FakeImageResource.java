package web.samples.restful.server.restlet.resource;

import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import web.samples.restful.server.restlet.services.EventService;

public class FakeImageResource extends ServerResource {

	EventService eventService;
	
	public FakeImageResource() throws Exception{
		
	}
	
	@Get
	public Representation getFakeImages(){
		
		try {
			FileInputStream fis = new FileInputStream("/home/gustavorag/git/scheduler-dashboard/fakeImages.html");
			String response = IOUtils.toString(fis);
			StringRepresentation sr = new StringRepresentation(response, MediaType.TEXT_PLAIN);
			return sr;
		} catch (Exception e) {
			return new StringRepresentation("", MediaType.TEXT_PLAIN);
		}
	}
	
}
