package web.samples.restful.server.restlet.services;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import web.samples.restful.server.restlet.model.Event;

public class TestEventService {

	private static final int MAX_EVENTS = 10;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	private EventService eventService;
	private Properties prop;
	
	@Before
	public void setUp() throws Exception {

		FileInputStream fis = new FileInputStream(new File("appProperties.properties"));
		prop = new Properties();
		prop.load(fis);
		
		eventService = new EventService(MAX_EVENTS, prop);
		eventService.stopEventMonitor();
		
		fis.close();
	}

	@After
	public void setDown() throws Exception {
		
		eventService.stopEventMonitor();
		prop.clear();
		prop = null;
	}

	@Test
	public void mountEventListTest() throws Exception {

		eventService.getEventGenerator().run();
		List<Event> events = eventService.getEvents();
		for(Event e : events){
			System.out.println("Event :"+e.toString());
		}
		assertEquals(MAX_EVENTS,eventService.getEvents().size());
	}
	
}
