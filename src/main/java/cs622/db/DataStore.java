package cs622.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * DataStore encapsulates database access logic. It leverages two tables
 * GOPHERJ_AUDIT_HEADER and GOPHERJ_AUDIT_DETAIL used to capture the results of
 * a run of GopherJ.
 *
 */
public class DataStore {

	// fields for the records GOPHERJ_AUDIT_HEADER and GOPHERJ_AUDIT_DETAIL
	private final String ID = "ID";
	private final String MESSAGE = "MESSAGE";
	private final String TIME = "TIME";
	private final String INPUT_FILE_NAME = "INPUT_FILE_NAME";
	private final String OUT_FILE_NAME = "OUT_FILE_NAME";

	// database connection info
	private String protocal = "jdbc:derby";
	private String databaseName = "derbyDB";

	// connection to database
	private Connection conn;

	// singleton instance of a data store
	private static DataStore dataStore;

	/**
	 * Private DataStore constructor.
	 * 
	 * Only want to create one of these.
	 */
	private DataStore() {
		// create tables
		createTables();
	}

	/**
	 * Gets the singleton instance of this data store.
	 * 
	 * @return DataStore
	 */
	public static DataStore getInstance() {
		if (dataStore == null) {
			dataStore = new DataStore();
		}
		return dataStore;
	}

	/**
	 * Fetches result records.
	 * 
	 * @param size
	 * @return List of ResultRecords
	 */
	public List<ResultRecord> fetchResultRecords() {

		// return value
		List<ResultRecord> records = new ArrayList<>();

		try {

			// create statement for query
			Statement stmt = getConnection().createStatement();

			// execute select joining two tables on primary id
			ResultSet set = stmt.executeQuery("SELECT * FROM GOPHERJ_AUDIT_HEADER header "
					+ "JOIN GOPHERJ_AUDIT_DETAIL detail on header.id = detail.id ORDER BY header.time DESC");

			// process result set
			while (set.next()) {
				String id = set.getString(ID);
				String inputFileName = set.getString(INPUT_FILE_NAME);
				String outputFileName = set.getString(OUT_FILE_NAME);
				String message = set.getString(MESSAGE);
				Long time = set.getLong(TIME);

				// add records to return list
				records.add(new ResultRecord(id, inputFileName, outputFileName, message, time));
			}

		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}

		return records;
	}

	/**
	 * Records a run of GopherJ to the database.
	 * 
	 * @param inputFileName
	 * @param outputFileName
	 * @param message
	 */
	public void recordResults(String inputFileName, String outputFileName, String message) {

		try {

			// common id for header and detail record
			UUID id = UUID.randomUUID();

			PreparedStatement prepstmt = conn
					.prepareStatement("INSERT INTO GOPHERJ_AUDIT_HEADER (ID, MESSAGE, TIME) VALUES (?,?,?)");
			prepstmt.setString(1, id.toString());
			prepstmt.setString(2, message);
			prepstmt.setLong(3, System.currentTimeMillis());
			prepstmt.executeUpdate();

			prepstmt = conn.prepareStatement(
					"INSERT INTO GOPHERJ_AUDIT_DETAIL (ID, INPUT_FILE_NAME, OUT_FILE_NAME) VALUES (?,?,?)");
			prepstmt.setString(1, id.toString());
			prepstmt.setString(2, inputFileName);
			prepstmt.setString(3, outputFileName);
			prepstmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Gets a connection to the db, creates one if one if it does not exits.
	 * 
	 * @return Connection to db.
	 * @throws SQLException
	 */
	private Connection getConnection() throws SQLException {
		// create the connection to the database if null
		if (conn == null) {
			conn = DriverManager.getConnection(String.format("%s:%s;create=true", protocal, databaseName));
		}
		return conn;
	}

	/**
	 * Create two audit tables during start up of GopherJ if it doesn't exist.
	 * 
	 * AUDIT_HEADER_TABLE, AUDIT_DETAIL_TABLE
	 */
	private void createTables() {

		try {
			// check to see if table needs to be created

			// meta-data for the database
			DatabaseMetaData metaData = getConnection().getMetaData();

			// result of table creating search
			ResultSet rs = metaData.getTables(null, "APP", "GOPHERJ_AUDIT_HEADER", null);

			// if the table is not created
			if (!rs.next()) {

				// get statement from connection that was created at startup
				Statement stmt = getConnection().createStatement();

				// create table GOPHERJ_AUDIT_HEADER
				stmt.executeUpdate(String.format("CREATE TABLE GOPHERJ_AUDIT_HEADER (%s VARCHAR(40) PRIMARY KEY, "
						+ "%s VARCHAR(100), %s BIGINT)", ID, MESSAGE, TIME));

				// create table GOPHREJ_AUDIT_DETAIL
				stmt.executeUpdate(String.format(
						"CREATE TABLE GOPHERJ_AUDIT_DETAIL (%s VARCHAR(40) PRIMARY KEY, %s VARCHAR(100), %s VARCHAR(100))",
						ID, INPUT_FILE_NAME, OUT_FILE_NAME));
			}

		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
