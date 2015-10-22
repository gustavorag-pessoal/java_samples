package web.samples.restful.server.restlet.resource;

import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;

import web.samples.restful.server.restlet.RestletServerMain;

public class EventResource extends ServerResource {

	
	//EventService eventService;
	RestletServerMain application;
	
	public EventResource() throws Exception{
		
	}
	
	protected void doInit() throws ResourceException {
		//Context injec mode//
		//eventService = (EventService) getContext().getAttributes().get("EnventService");
		//Application usage
		application = (RestletServerMain) getApplication();
		
		
	};
	
	@Get
	public Representation getEvents(){
		
		Gson gson = new Gson();
		String jsonEvents = gson.toJson(application.getEventService().getEvents());
		StringRepresentation sr = new StringRepresentation(jsonEvents, MediaType.TEXT_PLAIN);
		return sr;
	}
	
}
