package cs622.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataStore {

	// fields for the record
	private final String ID = "ID";
	private final String INPUT_FILE_NAME = "INPUT_FILE_NAME";
	private final String OUT_FILE_NAME = "OUT_FILE_NAME";
	private final String MESSAGE = "MESSAGE";
	private final String TIME = "TIME";

	// database connection info
	private String protocal = "jdbc:derby";
	private String databaseName = "derbyDB";

	private String AUDIT_HEADER_TABLE = "GOPHERJ_AUDIT_HEADER";
	private String AUDIT_DETAIL_TABLE = "GOPHERJ_AUDIT_DETAIL";

	// connection to database
	private Connection conn;

	public static void main(String[] args) throws SQLException {

		DataStore test = new DataStore();

		// test.createTables();

		test.recordResults("test0", "test1", "test2");

		List<ResultRecord> list = test.fetchResultRecords(1);

		for (ResultRecord resultRecord : list) {
			System.out.println(resultRecord);
		}
	}

	/**
	 * DataStore constuctor.
	 */
	public DataStore() {
		// create tables
		createTables();
	}

	// Gets a connection. Creates one if one does not exits
	private Connection getConnection() throws SQLException {
		// create the connection to the database if null
		if (conn == null) {
			conn = DriverManager.getConnection(String.format("%s:%s;create=true", protocal, databaseName));
		}
		return conn;
	}

	/**
	 * Fetches result records.
	 * 
	 * @param size
	 * @return List of ResultRecords
	 */
	public List<ResultRecord> fetchResultRecords(int size) {

		// return value
		List<ResultRecord> records = new ArrayList<>();

		try {

			// create statement for query
			Statement stmt = getConnection().createStatement();

			// execute select
			ResultSet set = stmt
					.executeQuery(String.format("SELECT * FROM %s ORDER BY %s DESC", AUDIT_HEADER_TABLE, TIME));

			// ResultSet set2 = stmt
			// .executeQuery(String.format("SELECT * FROM %s ORDER BY %s DESC",
			// AUDIT_HEADER_TABLE, TIME));

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

			// create statement with connection created at start up
			Statement stmt = getConnection().createStatement();

			// common id for header and detail record
			UUID id = UUID.randomUUID();

			// insert header record into database using UUID as ID
			stmt.executeUpdate(String.format("INSERT INTO %s (%s, %s, %s) VALUES ('%s','%s',%d)", AUDIT_HEADER_TABLE,
					ID, MESSAGE, TIME, id, message, System.currentTimeMillis()));

			// insert detail record into database using UUID as ID
			stmt.executeUpdate(String.format("INSERT INTO %s (%s, %s, %s) VALUES ('%s','%s','%s')", AUDIT_DETAIL_TABLE,
					ID, INPUT_FILE_NAME, OUT_FILE_NAME, id, inputFileName, outputFileName));

		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Create two audit tables during start up of GopherJ if it doesn't exist.
	 * 
	 * AUDIT_HEADER_TABLE, AUDIT_DETAIL_TABLE
	 */
	private void createTables() {

		try {
			// metadata for the database
			DatabaseMetaData metaData = getConnection().getMetaData();

			// result of table creating search
			ResultSet rs = metaData.getTables(null, "APP", AUDIT_HEADER_TABLE, null);

			// if the table is not created
			if (!rs.next()) {

				// get statement from connection that was created at startup
				Statement stmt = getConnection().createStatement();

				// create table GOPHREJ_AUDIT
				stmt.executeUpdate(
						String.format("CREATE TABLE %s (%s VARCHAR(40) PRIMARY KEY, " + "%s VARCHAR(100), %s BIGINT)",
								AUDIT_HEADER_TABLE, ID, MESSAGE, TIME));

				// create table GOPHREJ_AUDIT_DETAIL
				stmt.executeUpdate(
						String.format("CREATE TABLE %s (%s VARCHAR(40) PRIMARY KEY, %s VARCHAR(100), %s VARCHAR(100))",
								AUDIT_DETAIL_TABLE, ID, INPUT_FILE_NAME, OUT_FILE_NAME));
			}

		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
