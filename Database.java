package Database;
import java.sql.*;
import java.util.Locale;

public class Database {
	private static final String URL = "jdbc:mysql://localhost:3306/gla?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "MySQLRootPassword";

	private static Connection connection = null;

	public static void establishConnection() {
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static Statement statement = null;
	public static Statement getStatement() {
		return statement;
	}

	public static void main(String[] args) {
		try {
			establishConnection();
			statement = connection.createStatement();
						
			
			
			
			
			
			statement.close();
			closeConnection();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void registerUser(String uname, String upw, float ubalance) {
		try {
			PreparedStatement stmt = connection
					.prepareStatement("insert into users (uname, upw, ubalance) values (?,?,?);");
			stmt.setString(1, uname);
			stmt.setString(2, upw);
			stmt.setFloat(3, ubalance);
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void addGame(String gname, String gdesc, String gpub, float gprice, float gscore, boolean gmulti) {
		try {
			PreparedStatement stmt = connection.prepareStatement("insert into game (gname, gdesc, gpub, gprice, gscore, gmulti) values (?,?,?,?,?,?);");
            stmt.setString(1, gname);
            stmt.setString(2, gdesc);
            stmt.setString(3, gpub);
            stmt.setFloat(4,gprice);
            stmt.setFloat(5,gscore);
            stmt.setFloat(6,gprice);
            stmt.setBoolean(7, gmulti);
            stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	public static void addGameToUser(int uid, int gid) {
		try {
			PreparedStatement stmt = connection.prepareStatement("insert into Game_Ownership (uid, gid) values (?,?);");
            stmt.setInt(1, uid);
            stmt.setInt(2, gid);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
