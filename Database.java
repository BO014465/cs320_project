package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Database {
	private static Connection connection = null;
private static boolean cond=true;
	public static void establishConnection() {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:Database.db");
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

	public static void main(String[] args) {
		try {
			establishConnection();
			Statement statement = connection.createStatement();

			String createTableQueryUsers = "CREATE TABLE IF NOT EXISTS Users ("
					+ "    uid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, username VARCHAR(20),"
					+ "    password VARCHAR(20), balance FLOAT(5)  CHECK (balance >= 0),  logged_in BOOL);";
			statement.executeUpdate(createTableQueryUsers);
			String createTableQueryGames = "CREATE TABLE IF NOT EXISTS Games ("
					+ "    gid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR(20),"
					+ "    description VARCHAR(40), publisher VARCHAR(20),"
					+ "    price FLOAT(5)  CHECK (price >= 0), score FLOAT(5)  CHECK (score >= 0),"
					+ "    is_multi BOOL);";
			statement.executeUpdate(createTableQueryGames);
			String createTableQueryGame_Ownership = "CREATE TABLE IF NOT EXISTS Game_Ownership ("
					+ "    uid INT(5) NOT NULL, gid INT(5) NOT NULL, PRIMARY KEY (uid,gid),"
					+ "    FOREIGN KEY (uid) REFERENCES Users (uid) ON DELETE CASCADE,"
					+ "    FOREIGN KEY (gid) REFERENCES Games (gid) ON DELETE CASCADE);";
			statement.executeUpdate(createTableQueryGame_Ownership);

			Scanner input = new Scanner(System.in);
			Scanner input2 = new Scanner(System.in);
			Scanner input3 = new Scanner(System.in);
			Scanner input4 = new Scanner(System.in);
			input4.useLocale(Locale.US);
			while (cond) {
				System.out.println(
						"(1)Login\n(2)Register\n(3)Add Admin(Not Done)\n(4)Add Example Users and Games\n(5)A User's Games\n(6)List Game Info\n(7)List Game Info of User\n(8)List All Users\n(9)List All Games\n(10)List All Ownership\n(11)Clear Tables\n(12)Exit");
				System.out.print("Enter a code number for the operation: ");
				String code = input.nextLine();
				switch (code) {
				case "1": {
					while (true) {
						System.out.print("Enter username: ");
						String username = input.nextLine();
						System.out.print("Enter password: ");
						String password = input.nextLine();
						boolean login = login(username, password);
						if (login) {
							System.out.println("Hello " + username);
							setLoggedAllOut();
							System.out.println("a" + whoLoggedIn());
							setLoggedIn(username);
							System.out.println("" + whoLoggedIn());

							int uid = getUid(username);
							System.out.println("User ID: " + uid);
							float balance = getBalance(uid);
							System.out.println("Balance: " + balance);
							ArrayList<Integer> gids = listGamesOfUser(uid);
							for (int i = 0; i < gids.size(); i++) {
								ArrayList<String> info = listGameInfo(gids.get(i));
								for (int j = 0; j < info.size(); j++) {
									System.out.print(info.get(j) + " ");
								}
								System.out.println(";");
							}
							System.out.println("(1)Buy Game\n(2)Refund Game\n(3)Add funds\n(4)Logout");
							System.out.print("Enter a code number for the operation: ");
							String code2 = input.nextLine();
							switch (code2) {
							case "1": {
								ArrayList<Integer> gids2 = listGamesOfNotOwnedByUser(uid);
								System.out.println("Store:");
								for (int i = 0; i < gids2.size(); i++) {
									ArrayList<String> info = listGameInfo(gids2.get(i));
									for (int j = 0; j < info.size(); j++) {
										System.out.print(info.get(j) + " ");
									}
									System.out.println(";");
								}
								System.out.print("Enter the game ID: ");
								int gid = input2.nextInt();
								if (getPrice(gid) <= balance) {
									addGameToUser(uid, gid);
									setBalance(uid, balance - getPrice(gid));
									System.out
											.println("Game " + getName(gid) + " purchased for " + getPrice(gid) + ".");
								} else {
									System.out.println("Insufficent balance.");
								}
								break;
							}
							case "2": {
								System.out.print("Enter the game ID: ");
								int gid = input2.nextInt();
								PreparedStatement stmt = connection
										.prepareStatement("DELETE FROM Game_Ownership WHERE uid=? AND gid=?;");
								stmt.setInt(1, uid);
								stmt.setInt(2, gid);
								stmt.executeUpdate();
								setBalance(uid, balance + getPrice(gid));
								System.out.println("Game " + getName(gid) + " refunded for " + getPrice(gid) + ".");
								break;
							}
							case "3": {
								System.out.print("Add funds: ");
								float funds = input4.nextFloat();
								setBalance(uid, balance + funds);
								break;
							}
							case "4": {
								System.out.println("Logged Out.");
								return;
							}
							}
							break;
						} else {
							System.out.println("Incorrect Combination");
						}
					}
					break;
				}

				case "2": {
					System.out.print("Enter username: ");
					String username = input.nextLine();
					System.out.print("Enter password: ");
					String password = input.nextLine();
					if (!usernameExists(username)) {
						addUser(username, password, 0);
						System.out.println("Succesfully Regitstered.");
					} else {
						System.out.println("Username " + username + " already exists.");
					}
					break;
				}
				case "4": {
					System.out.print("Enter the amount of example users to add: ");
					int count = input2.nextInt();
					addExampleUsers(count);
					System.out.print("Enter the amount of example games to add: ");
					int count2 = input2.nextInt();
					addExampleGames(count2);
					int maxUid = getMaxUid();
					System.out.println(maxUid);
					int maxGid = getMaxGid();
					System.out.println(maxGid);
					System.out.print("Add random games to users (true or false): ");
					boolean choice = input3.nextBoolean();
					if (choice)
						addRandomGamesToUsers();
					break;
				}
				case "5": {
					System.out.print("Enter the user ID: ");
					int uid = input2.nextInt();
					ArrayList<Integer> gids = listGamesOfUser(uid);
					for (int i = 0; i < gids.size(); i++) {
						System.out.println(gids.get(i));
					}

					break;
				}

				case "6": {
					System.out.print("Enter the game ID: ");
					int gid = input2.nextInt();
					ArrayList<String> info = listGameInfo(gid);
					for (int i = 0; i < info.size(); i++) {
						System.out.println(info.get(i));
					}

					break;
				}
				case "7": {
					System.out.print("Enter the user ID: ");
					int uid = input2.nextInt();
					ArrayList<Integer> gids = listGamesOfUser(uid);
					for (int i = 0; i < gids.size(); i++) {
						ArrayList<String> info = listGameInfo(gids.get(i));
						for (int j = 0; j < info.size(); j++) {
							System.out.print(info.get(j) + " ");
						}
						System.out.println(";");
					}

					break;
				}
				case "8": {
					ArrayList<Integer> uids = listAllUsers();
					for (int i = 0; i < uids.size(); i++) {
						System.out.println(uids.get(i));
					}

					break;
				}
				case "9": {
					ArrayList<Integer> gids = listAllGames();
					for (int i = 0; i < gids.size(); i++) {
						System.out.println(gids.get(i));
					}

					break;
				}
				case "10": {
					ArrayList<Integer> uids = listAllUsers();
					for (int i = 0; i < uids.size(); i++) {
						System.out.println("Uid: " + uids.get(i));
						ArrayList<Integer> gids = listGamesOfUser(uids.get(i));
						for (int j = 0; j < gids.size(); j++) {
							System.out.println(uids.get(i) + ": " + gids.get(j));
						}
					}

					break;
				}
				case "11": {
					statement.executeUpdate("DELETE FROM Users WHERE uid>0;");
					statement.executeUpdate("UPDATE `sqlite_sequence` SET `seq` = 0 WHERE `name` = 'Users'");
					statement.executeUpdate("drop table if exists Users");
//					statement.executeUpdate("DELETE FROM Games WHERE gid>0;");
//					statement.executeUpdate("UPDATE `sqlite_sequence` SET `seq` = 0 WHERE `name` = 'Games'");
//					statement.executeUpdate("drop table if exists Games");
					statement.executeUpdate("DELETE FROM Game_Ownership WHERE uid>0 AND gid>0;");
					statement.executeUpdate("UPDATE `sqlite_sequence` SET `seq` = 0 WHERE `name` = 'Game_Ownership'");
					statement.executeUpdate("drop table if exists Game_Ownership");
					break;
				}

				case "12": {
					input.close();
					input2.close();
					input3.close();
					input4.close();
					statement.close();
					closeConnection();
					return;
				}
				default: {
					System.out.println("Try again.");
					break;
				}
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	
	public static boolean login(String username, String password) {
		boolean exists = false;
		try {
			PreparedStatement stmt = connection
					.prepareStatement("SELECT COUNT(*) FROM Users WHERE username=? AND password=?;");
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				if (rs.getInt(1) > 0) {
					exists = true;
				}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return exists;
	}


public static ArrayList<Integer> listGamesOfUser(int uid) {
		ArrayList<Integer> gids = new ArrayList<Integer>();
		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT gid FROM Game_Ownership WHERE uid=?;");
			stmt.setInt(1, uid);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				gids.add(rs.getInt("gid"));
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return gids;
	}

public static ArrayList<String> listAllUsersInfo() {
		ArrayList<String> info = new ArrayList<String>();
		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Users;");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				info.add("" + rs.getInt("uid"));
				info.add(rs.getString("username"));
				info.add("" + rs.getString("description"));
				info.add("" + rs.getFloat("balance"));
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return info;
	}

public static void setUsername(int uid, String username) {
		try {
			PreparedStatement stmt = connection.prepareStatement("UPDATE Users SET username=? WHERE uid=?;");
			stmt.setString(1, username);
			stmt.setInt(2, uid);
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

public static void setBalance(int uid, float balance) {
		try {
			PreparedStatement stmt = connection.prepareStatement("UPDATE Users SET balance=? WHERE uid=?;");
			stmt.setFloat(1, balance);
			stmt.setInt(2, uid);
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}


public static void setDescription(int gid, String description) {
		try {
			PreparedStatement stmt = connection.prepareStatement("UPDATE Games SET description=? WHERE gid=?;");
			stmt.setString(1, description);
			stmt.setInt(2, gid);
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

public static void setPrice(int gid, float price) {
		try {
			PreparedStatement stmt = connection.prepareStatement("UPDATE Games SET price=? WHERE gid=?;");
			stmt.setFloat(1, price);
			stmt.setInt(2, gid);
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

public static void setIsMulti(int gid, boolean is_multi) {
		try {
			PreparedStatement stmt = connection.prepareStatement("UPDATE Games SET is_multi=? WHERE gid=?;");
			stmt.setBoolean(1, is_multi);
			stmt.setInt(2, gid);
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

public static void deleteGameWithName(String name) {
		try {
			PreparedStatement stmt = connection.prepareStatement("DELETE FROM Games WHERE name=?;");
			stmt.setString(1, name);
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

public static int getMaxGid() {
		int gid = 0;
		try {

			PreparedStatement stmt = connection.prepareStatement("SELECT MAX(gid) FROM Games;");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				gid = rs.getInt(1);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return gid;
	}
	}

