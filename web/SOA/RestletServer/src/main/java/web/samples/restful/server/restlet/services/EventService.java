package web.samples.restful.server.restlet.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import web.samples.restful.server.restlet.model.Event;
import web.samples.restful.server.restlet.persistence.EventRepository;
import web.samples.restful.server.restlet.util.AppPropertiesConstants;
import web.samples.restful.server.restlet.util.ManagerTimer;

public class EventService {

	private Map<String, String> eventTypeMap = new HashMap<String, String>();
	private ManagerTimer eventsMonitor = new ManagerTimer(Executors.newScheduledThreadPool(1));
	private ReentrantReadWriteLock eventsLock = new ReentrantReadWriteLock();
	private EventGenerator eventGenerator = new EventGenerator();
	
	//private EventRepository eventRepository;
	
	private Properties prop;
	
	private int maxEvents = 1;
	
	List<Event> events = new ArrayList<Event>();
	
	public EventService(int maxEvents, Properties prop) throws Exception{
		this.maxEvents = maxEvents;
		this.prop = prop;
		//this.eventRepository = new EventRepository();
		this.mountEventTypeList();
		this.startEventMonitor();
	}
	
	protected void startEventMonitor(){
		eventsMonitor.scheduleAtFixedRate(eventGenerator, 0, 2000);
	}
	
	protected void stopEventMonitor(){
		eventsMonitor.cancel();
	}
	
	private void mountEventTypeList(){
		String eventTypes = prop.getProperty(AppPropertiesConstants.EVENT_TYPE);
		String [] eventsList = eventTypes.split(";");
		for(String eventValue : eventsList){
			String[] eventAndMensage = eventValue.split("\\|");
			eventTypeMap.put(eventAndMensage[0], eventAndMensage[1]);
		}
	}
	
	private int randomNumber(int maxValue){
		Random randomGenerator = new Random();
		return randomGenerator.nextInt(maxValue);
	}
	
	protected class EventGenerator implements Runnable{

		public void run() {
			
			eventsLock.writeLock().lock();
			eventsLock.readLock().lock();
			
			//List<Event> events = eventRepository.listAllEvents();
			try {

				if (events!= null && events.size() == maxEvents) {
					events.remove(0);
					events.remove(1);
				}
				while (events.size() < maxEvents) {
					
					Event event = new Event();
					int eventIndex = randomNumber(eventTypeMap.values().size());
					int location = randomNumber(101);

					String eventKey = (String) eventTypeMap.keySet().toArray()[eventIndex];
					String eventMsg = eventTypeMap.get(eventKey);

					event.setID(UUID.randomUUID().toString());
					event.setType(eventKey);
					event.setLocation("123.443.205." + location);
					event.setDescription(eventMsg);
					event.setEventDate(new Date());
					event.setDisplayed(false);
					events.add(event);

				}
				
				//eventRepository.updateEvents(events);
				
			} finally {
				eventsLock.writeLock().unlock();
				eventsLock.readLock().unlock();

			}
		}
	}

	protected Map<String, String> getEventTypeMap() {
		return eventTypeMap;
	}

	protected void setEventTypeMap(Map<String, String> eventTypeMap) {
		this.eventTypeMap = eventTypeMap;
	}

	public List<Event> getEvents() {
		//return eventRepository.listAllEvents();
		return events;
	}


	protected ManagerTimer getEventsMonitor() {
		return eventsMonitor;
	}

	protected void setEventsMonitor(ManagerTimer eventsMonitor) {
		this.eventsMonitor = eventsMonitor;
	}

	protected EventGenerator getEventGenerator() {
		return eventGenerator;
	}

	protected void setEventGenerator(EventGenerator eventGenerator) {
		this.eventGenerator = eventGenerator;
	}
	
	

}
