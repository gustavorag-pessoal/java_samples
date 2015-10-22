package web.samples.restful.server.restlet.resource;

import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import web.samples.restful.server.restlet.services.EventService;

public class FakeTaskResource extends ServerResource {

	EventService eventService;
	
	public FakeTaskResource() throws Exception{
		
	}
	
	@Get
	public Representation getFakeTasks(){
		
		String imageName = (String) getRequest().getAttributes().get("imageName");
		
		try {
			FileInputStream fis = new FileInputStream("/home/gustavorag/git/scheduler-dashboard/fakeTask.html");
			String response = IOUtils.toString(fis);
			response = response.replaceAll("<<name>>", imageName);
			StringRepresentation sr = new StringRepresentation(response, MediaType.TEXT_PLAIN);
			return sr;
		} catch (Exception e) {
			return new StringRepresentation("", MediaType.TEXT_PLAIN);
		}
	}
	
}
