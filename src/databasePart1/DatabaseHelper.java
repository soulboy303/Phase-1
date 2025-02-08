package databasePart1;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import application.User;

import java.util.*;

/**
 * The DatabaseHelper class is responsible for managing the connection to the database,
 * performing operations such as user registration, login validation, and handling invitation codes.
 */
public class DatabaseHelper {

	// JDBC driver name and database URL 
	static final String JDBC_DRIVER = "org.h2.Driver";   
	static final String DB_URL = "jdbc:h2:~/FoundationDatabase";  

	//  Database credentials 
	static final String USER = "sa"; 
	static final String PASS = ""; 

	private Connection connection = null;
	private Statement statement = null; 
	//	PreparedStatement pstmt

	public void connectToDatabase() throws SQLException {
		try {
			Class.forName(JDBC_DRIVER); // Load the JDBC driver
			System.out.println("Connecting to database...");
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			statement = connection.createStatement(); 
			// You can use this command to clear the database and restart from fresh.
			//statement.execute("DROP ALL OBJECTS");

			createTables();  // Create the necessary tables if they don't exist
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC Driver not found: " + e.getMessage());
		}
	}

	private void createTables() throws SQLException {
		String userTable = "CREATE TABLE IF NOT EXISTS cse360users ("
				+ "id INT AUTO_INCREMENT PRIMARY KEY, "
				+ "userName VARCHAR(255) UNIQUE, "
				+ "password VARCHAR(255), "
				+ "firstName VARCHAR(255), "
				+ "lastName VARCHAR(255), "
				+ "emailAddress VARCHAR(255), "
				+ "isTemp BOOLEAN, "
				+ "role VARCHAR(20))";
		statement.execute(userTable);
		
		// Create the invitation codes table
	    String invitationCodesTable = "CREATE TABLE IF NOT EXISTS InvitationCodes ("
	            + "code VARCHAR(10) PRIMARY KEY, "
	            + "isUsed BOOLEAN DEFAULT FALSE)";
	    statement.execute(invitationCodesTable);
	}


	// Check if the database is empty
	public boolean isDatabaseEmpty() throws SQLException {
		String query = "SELECT COUNT(*) AS count FROM cse360users";
		ResultSet resultSet = statement.executeQuery(query);
		if (resultSet.next()) {
			return resultSet.getInt("count") == 0;
		}
		return true;
	}

	// Registers a new user in the database.
	public void register(User user) throws SQLException {
		ArrayList<String> roleSize = user.getAllRoles();
		String roles = "";
		if(roleSize.size() == 1) {
			
		roles = user.getRole(0);	
			
			
		}
		if(roleSize.size() > 1) { 
		for (int i = 0; i < roleSize.size(); i++ ) {
			roles = roles + ", " + user.getRole(i);
			
		}
		}
		String insertUser = "INSERT INTO cse360users (userName, password, firstName, lastName, emailAddress, isTemp, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(insertUser)) {
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3,  user.getFirstName());
			pstmt.setString(4,  user.getLastName());
			pstmt.setString(5,  user.getEmailAddress());
			pstmt.setBoolean(6,  user.getIsTemp());
			pstmt.setString(7, roles);
			pstmt.executeUpdate();
		}
	}

	// Validates a user's login credentials.
	public boolean login(User user) throws SQLException {
		
		ArrayList<String> roleSize = user.getAllRoles();
		String roles = "";
		if(roleSize.size() == 1) {
			
			roles = user.getRole(0);	
				
				
			}
			if(roleSize.size() > 1) { 
			for (int i = 0; i < roleSize.size(); i++ ) {
				roles = roles + ", " + user.getRole(i);
				
			}
			}
		
		String query = "SELECT * FROM cse360users WHERE userName = ? AND password = ? AND role = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, roles);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next();
			}
		}
	}
	
	// Checks if a user already exists in the database based on their userName.
	public boolean doesUserExist(String userName) {
	    String query = "SELECT COUNT(*) FROM cse360users WHERE userName = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        
	        pstmt.setString(1, userName);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            // If the count is greater than 0, the user exists
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // If an error occurs, assume user doesn't exist
	}
	
	// Retrieves the role of a user from the database using their UserName.
	public String getUserRole(String userName) {
	    String query = "SELECT role FROM cse360users WHERE userName = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, userName);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            return rs.getString("role"); // Return the role if user exists
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null; // If no user exists or an error occurs
	}
	
	public String addRole(String userName, String role) {
		String roles = "";
	    String query = "SELECT role FROM cse360users WHERE userName = ?";
	    String query2 = "UPDATE cse360users SET role = ? WHERE username = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, userName);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            roles = rs.getString("role");
	            roles = roles + ", " + role;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    try (PreparedStatement pstmt = connection.prepareStatement(query2)) {
	        pstmt.setString(1, roles);
	        pstmt.setString(2, userName);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null; // If no user exists or an error occurs
	}
	
	
	public String removeRole(String userName, String role) {
		String roles = "";
	    String query = "SELECT role FROM cse360users WHERE userName = ?";
	    String query2 = "UPDATE cse360users SET role = ? WHERE username = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, userName);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            roles = rs.getString("role");
	            String[] rolesArray = roles.split(", ");
	            ArrayList<String> rolesList = new ArrayList<>(Arrays.asList(rolesArray));
	            if(rolesList.contains(role)) {
	            	
	            	if(rolesList.size() != 1) {
	            rolesList.remove(role);
	            	roles = rolesList.get(0);
	            	if(rolesList.size() > 1) {
	            	for( int j = 1; j < rolesList.size(); j++) {
	            		roles = roles + ", " + rolesList.get(j);
	            	}
	            	
	            	}
	            	}
	            	
	            	else {return "Error: user cannot have no roles";}
	            }
	            	else {return "Error: user does not have this role";}
	            		
	            	}
	            }
	        
	
	     catch (SQLException e) {
	        e.printStackTrace();
	    }
	    try (PreparedStatement pstmt = connection.prepareStatement(query2)) {
	        pstmt.setString(1, roles);
	        pstmt.setString(2, userName);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null; // If no user exists or an error occurs
	
}
		
	// retrieves the first name of a user from the database using their UserName
		public String getUserFirstName(String userName) {
		    String query = "SELECT firstName FROM cse360users WHERE userName = ?";
		    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, userName);
		        ResultSet rs = pstmt.executeQuery();
		        
		        if (rs.next()) {
		            return rs.getString("firstName"); // Return the role if user exists
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return null; // If no user exists or an error occurs
		}
		
		
		// retrieves the last name of a user from the database using their UserName
		public String getUserLastName(String userName) {
		    String query = "SELECT lastName FROM cse360users WHERE userName = ?";
		    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, userName);
		        ResultSet rs = pstmt.executeQuery();
		        
		        if (rs.next()) {
		            return rs.getString("lastName"); // Return the last name if user exists
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return null; // If no user exists or an error occurs
		}
		
		
		public String getUserEmail(String userName) {
		    String query = "SELECT emailAddress FROM cse360users WHERE userName = ?";
		    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, userName);
		        ResultSet rs = pstmt.executeQuery();
		        
		        if (rs.next()) {
		            return rs.getString("emailAddress"); // Return the role if user exists
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return null; // If no user exists or an error occurs
		}
	
	// Retrieves all usernames in the database
    public ArrayList<String> getAllUsernames() {
        ArrayList<String> usernameArrayList = new ArrayList<>(); // Create ArrayList for usernames
        String query = "SELECT userName FROM cse360users";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            // Add usernames to ArrayList until null
            while(rs.next()) {

                String username = rs.getString("userName");
                usernameArrayList.add(username);
            }
            return usernameArrayList; // Return ArrayList of all usernames

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // If no username exists or an error occurs
    }
    
 // Deletes user based on string username received
 	public void deleteUser(String userName) {
 		String query = "DELETE FROM cse360users WHERE userName = ?";
 		
 		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
 			
 			pstmt.setString(1, userName);
 			pstmt.executeUpdate();
 			
 		} catch (SQLException e) {
 			e.printStackTrace();
 		}
 		
 	}
	
	// Generates a new invitation code and inserts it into the database.
	public String generateInvitationCode() {
	    String code = UUID.randomUUID().toString().substring(0, 4); // Generate a random 4-character code
	    String query = "INSERT INTO InvitationCodes (code) VALUES (?)";

	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, code);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return code;
	}
	//Generates temp password for user
	public String generateTempPassword(String userName) {  
	    String temp = UUID.randomUUID().toString().substring(0, 8); // Generate a random 8-character code
	    String query = "UPDATE cse360users SET password = ? WHERE username = ?";
	    String query2 = "UPDATE cse360users SET isTemp = true WHERE username = ?";

	    try (PreparedStatement pstmt = connection.prepareStatement(query)) { //query to have the temp password replace the old one
	        pstmt.setString(1, temp);
	        pstmt.setString(2, userName);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    try (PreparedStatement pstmt = connection.prepareStatement(query2)) { //sets isTemp to true
	    	pstmt.setString(1, userName);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return temp;
	}
	
	public boolean checkIfTemp(String userName, String password) {
		String query = "SELECT isTemp FROM cse360users WHERE username = ? AND password = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, userName);
	        pstmt.setString(2, password);
	        ResultSet rs = pstmt.executeQuery();
	        
	        // Recieve boolean value from isTemp
	        if (rs.next()) {
	        	boolean check = rs.getBoolean("isTemp");
	            return check; // Return the value of isTemp
	        } else {
	            return false; //returns false if login is incorrect is found
	    }
		}
		
		catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
		
		
	}
	
	//Sets new password from input and clears isTemp field
	
	public void clearTemp(String password, User user) { 
		
		String query = "UPDATE cse360users SET password = ? WHERE username = ?";
	    String query2 = "UPDATE cse360users SET isTemp = false WHERE username = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) { //Replaces temp password with the new password
	        pstmt.setString(1, password);
	        pstmt.setString(2, user.getUserName());
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    try (PreparedStatement pstmt = connection.prepareStatement(query2)) { //Sets isTemp to false
	        pstmt.setString(1, user.getUserName());
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		
	}
	
	// Validates an invitation code to check if it is unused.
	public boolean validateInvitationCode(String code) {
	    String query = "SELECT * FROM InvitationCodes WHERE code = ? AND isUsed = FALSE";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, code);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            // Mark the code as used
	            markInvitationCodeAsUsed(code);
	            return true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	// Marks the invitation code as used in the database.
	private void markInvitationCodeAsUsed(String code) {
	    String query = "UPDATE InvitationCodes SET isUsed = TRUE WHERE code = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, code);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void resetPassword(User user) throws SQLException {
		String query = "UPDATE cse360users SET password = ? WHERE USERNAME = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)){
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getUserName());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Closes the database connection and statement.
	public void closeConnection() {
		try{ 
			if(statement!=null) statement.close(); 
		} catch(SQLException se2) { 
			se2.printStackTrace();
		} 
		try { 
			if(connection!=null) connection.close(); 
		} catch(SQLException se){ 
			se.printStackTrace(); 
		} 
	}

}
