package web.samples.restful.server.restlet.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import web.samples.restful.server.restlet.model.Event;


public class EventRepository extends AbstractRepository{

	private static final Logger LOGGER = Logger.getLogger(EventRepository.class);
	
	private static final String TABLE_NAME = "TB_EVENT";
	private static final String COLUMN_ID = "EVENT_ID";
	private static final String COLUMN_TYPE = "TYPE";
	private static final String COLUMN_LOCATION = "LOCATION";
	private static final String COLUMN_DESC = "DESC";
	private static final String COLUMN_EVENT_DATE = "DT_EVENT";
	private static final String COLUMN_DISPLAYED = "DISPLAYED";
	
	private static final String CREATE_TABLE = TABLE_NAME + "(" + COLUMN_ID + " VARCHAR(255) PRIMARY KEY, "
			+ COLUMN_TYPE + " VARCHAR(50), " + COLUMN_LOCATION + " VARCHAR(150), " 
			+ COLUMN_DESC + " VARCHAR(255), " + COLUMN_EVENT_DATE + " DATE, "+ COLUMN_DISPLAYED +" CHAR(1) )";
	private static final String INSERT_EVENT_SQL = "INSERT INTO " + TABLE_NAME	+ " VALUES(?,?,?,?,?,?)";
	private static final String SELECT_ALL_EVENT_SQL = "SELECT * FROM " + TABLE_NAME;
	private static final String DELETE_ALL_EVENTS = "DELETE FROM " + TABLE_NAME;
	
	public EventRepository() throws Exception{
		super(CREATE_TABLE);
	}
	
	public boolean updateEvents(List<Event> events){
		
		LOGGER.debug("Updating current list of Events");
		PreparedStatement deleteOldContent = null;
		PreparedStatement updateRequestList = null;
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			deleteOldContent = connection.prepareStatement(DELETE_ALL_EVENTS);
			deleteOldContent.addBatch();
			if (hasBatchExecutionError(deleteOldContent.executeBatch())){
				connection.rollback();
				return false;
			}
			updateRequestList = prepare(connection, INSERT_EVENT_SQL);
			for (Event e : events){
				updateRequestList.setString(1, e.getID());
				updateRequestList.setString(2, e.getType());
				updateRequestList.setString(3, e.getLocation());
				updateRequestList.setString(4, e.getDescription());
				updateRequestList.setDate(5, new java.sql.Date(e.getEventDate().getTime()));
				updateRequestList.setInt(6, e.isDisplayed() ? 1 : 0);
				updateRequestList.addBatch();
			}
			
			
			if (hasBatchExecutionError(updateRequestList.executeBatch())){
				connection.rollback();
				return false;
			}
			connection.commit();
			return true;
		} catch (SQLException e) {
			LOGGER.error("Couldn't store the current orders", e);
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				LOGGER.error("Couldn't rollback transaction.", e1);
			}
			return false;
		} finally {
			close(updateRequestList, connection);
			close(deleteOldContent, connection);

		}
	}
	
	public List<Event> listAllEvents(){
		
		List<Event> events = new ArrayList<Event>();
		Statement getRequestIdStatement = null;
		Connection connection = null;
		try {
			connection =  getConnection();
			getRequestIdStatement = connection.createStatement();
			getRequestIdStatement.execute(SELECT_ALL_EVENT_SQL);
			ResultSet result = getRequestIdStatement.getResultSet();
			while(result.next()){
				Event event = new Event();
				event.setID(result.getString(COLUMN_ID));
				event.setType(result.getString(COLUMN_TYPE));
				event.setLocation(result.getString(COLUMN_LOCATION));
				event.setDescription(result.getString(COLUMN_DESC));
				java.sql.Date eventDate = result.getDate(COLUMN_EVENT_DATE);
				if(eventDate != null){
					event.setEventDate(new Date(eventDate.getTime()));
				}
				event.setDisplayed(result.getBoolean(COLUMN_DISPLAYED));
				events.add(event);
				
			}
			return events;
		} catch (SQLException e){
			LOGGER.error("Couldn't recover request Ids from DB", e);	
			return new ArrayList<Event>();
		} finally {
			close(getRequestIdStatement, connection);
		}
		
	}
	
	protected PreparedStatement prepare(Connection connection, String statement) throws SQLException {
		return connection.prepareStatement(statement);
	}

	private boolean hasBatchExecutionError(int[] executeBatch) {
		for (int i : executeBatch) {
			if (i == PreparedStatement.EXECUTE_FAILED) {
				return true;
			}
		}
		return false;
	}
}
