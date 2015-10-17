package web.samples.restful.server.restlet.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.apache.log4j.Logger;

import org.h2.jdbcx.JdbcConnectionPool;

public abstract class AbstractRepository {

	private static final Logger LOGGER = Logger.getLogger(AbstractRepository.class);

	private JdbcConnectionPool cp;
	private Connection connection = null;
	
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_MEMORY_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String DB_FILE_CONNECTION = "jdbc:h2:./src/main/resources/serveDataBase.db";
    
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

	public AbstractRepository(String tableCreation) throws Exception {

		String tableDdl = "CREATE TABLE IF NOT EXISTS " + tableCreation;
		
		LOGGER.debug("DatastoreURL: " + DB_FILE_CONNECTION);
		
		Statement statement = null;

		Class.forName(DB_DRIVER);
		this.cp = JdbcConnectionPool.create(DB_FILE_CONNECTION, DB_USER, DB_PASSWORD);
		
		connection = getConnection();

		statement = connection.createStatement();
		
		LOGGER.info("Creating table: [ "+tableDdl+" ]");
		statement.execute(tableDdl);
		statement.close();

	}

	public Connection getConnection() throws SQLException {
		try {
			return cp.getConnection();
		} catch (SQLException e) {
			LOGGER.error("Error while getting a new connection from the connection pool.", e);
			throw e;
		}
	}
	
	protected void close(Statement statement, Connection conn) {
		if (statement != null) {
			try {
				if (!statement.isClosed()) {
					statement.close();
				}
			} catch (SQLException e) {
				LOGGER.error("Couldn't close statement");
			}
		}

		if (conn != null) {
			try {
				if (!conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				LOGGER.error("Couldn't close connection");
			}
		}
	}
}
