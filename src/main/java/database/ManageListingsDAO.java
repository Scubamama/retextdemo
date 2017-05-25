package database;

import static java.lang.System.out;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import database.DataSource;
import model2.AUser;
import model2.UserInventory;
import model2.UserInventoryDisplay;

/**
 * A class that manages listings of a particular user
 * Uses prepared statements to access a database
 * 
 * @author Holly Williams
 *
 */

public class ManageListingsDAO {

	DataSource ds;
	
	public ManageListingsDAO() {
		this.ds = DataSource.getInstance();
	}

	public List<UserInventoryDisplay> searchMyBooks(String text) throws SQLException {
	//	DatabaseManager mgr = new DatabaseManager();
		List<UserInventoryDisplay> myBookList = new ArrayList<UserInventoryDisplay>();
		//List<AUser> userList = new ArrayList<AUser>();
		//String sql = "SELECT * FROM Users where UserName LIKE ? ";
		
		String sql = "select i.id, b.title, b.author, b.edition, b.isbn," + 
				"i.price " + 
				"from retext.book_titles b join retext.user_inventory i " +
			"where b.id = i.Book_id and i.User_id = ? and b.Title LIKE ? ";
		
		//String sql = "SELECT * FROM Book_Titles where Title LIKE ? ";  from booksdao
		int currUserId = 1;
		
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Connection myConn = null;
		
		try {
			// 1. Get a connection to the database
				myConn = ds.getConnection();
			// 2. Create a statement object
				myStmt = myConn.prepareStatement(sql);
				myStmt.setInt(1,currUserId);
				myStmt.setString(2, "%" + text + "%");
				myRs = myStmt.executeQuery();

				// 4. Process the result set - put it into the ArrayList
				
				while (myRs.next()) {
				//	myBookList.add(new DisplayUserInventory(myRs.getInt("Id"), myRs.getString("Email"), myRs.getString("UserName"), myRs.getString("UserPassword"), myRs.getInt("TakeCards"), myRs.getString("school") ));
					myBookList.add(new UserInventoryDisplay(myRs.getInt("Id"),myRs.getString("Title"), 
							myRs.getString("author"), myRs.getString("edition"), 
							myRs.getString("isbn"), myRs.getDouble("price") ));	
					//out.println("inv id = " + getId());
				}
				return myBookList;
	
			} //end try
			finally {
	//			mgr.silentClose(myConn, myStmt, myRs);
				DataSource.silentClose(myConn);
				DataSource.silentClose(myStmt);
				DataSource.silentClose(myRs);

			}

		} // end searchUsers

	public List<UserInventoryDisplay> listMyBooks() throws SQLException {
	//	DatabaseManager mgr = new DatabaseManager();
		List<UserInventoryDisplay> invList = new ArrayList<UserInventoryDisplay>();
		// String sql = "SELECT * FROM User_Inventory WHERE User_Id = ? AND Book_Id = ?";
		
		// this was working in mysql workbench
		String sql = "select i.id, b.title, b.author, b.edition, b.isbn," + 
						"i.price " + 
						"from retext.book_titles b join retext.user_inventory i " +
					"where b.id = i.Book_id and i.User_id = ?";
		
		int currUserId = 1;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Connection myConn = null;
		
		try {
			// 1. Get a connection to the database
				myConn = ds.getConnection();
			// 2. Create a statement object
				myStmt = myConn.prepareStatement(sql);
				myStmt.setInt(1,currUserId);
				myRs = myStmt.executeQuery();
				
			// 4. Process the result set - put it into the ArrayList
				while (myRs.next()) {							
					invList.add(new UserInventoryDisplay(myRs.getInt("Id"),myRs.getString("Title"), 
							myRs.getString("author"), myRs.getString("edition"), 
							myRs.getString("isbn"), myRs.getDouble("price") ));
				}
				return invList;
			} //end try
			finally {
	//			mgr.silentClose(myConn, myStmt, myRs);
				DataSource.silentClose(myConn);
				DataSource.silentClose(myStmt);
				DataSource.silentClose(myRs);

			}

	} // end listMyBooks

	
	public void save(UserInventory inv) {
		// save a user if one like this does not exist 
		// otherwise update it
		
		if(inv.getId() == 0){
			insert(inv);
		}else {
			update(inv);
		}
	
	} // end save()
	
	private void update (UserInventory inv) {
		// this is going to update price and sold
		out.println("UPDATING New User book... ");
		
		String sql = "UPDATE User_Inventory SET Price=?,Sold=? WHERE id=?";

	//	DatabaseManager mgr = new DatabaseManager();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Connection myConn = null;
		
		try {
			// 1. Get a connection to the database
				myConn = ds.getConnection();
			// 2. Create a statement object
				myStmt = myConn.prepareStatement(sql);

				myStmt.setDouble(1,inv.getPrice());
				myStmt.setInt(2,inv.getSold());
				myStmt.setInt(3,inv.getId());
				
				myStmt.executeUpdate();
			} //end try
			catch (Exception exc) {
				exc.printStackTrace();
			}
			finally {
	//			mgr.silentClose(myConn, myStmt, myRs);
				DataSource.silentClose(myConn);
				DataSource.silentClose(myStmt);
				DataSource.silentClose(myRs);

			}
		
	} // end update()
	
	
	private void insert (UserInventory inv) {
		
		out.println("INSERTING New User book... ");
		
		String sql = "INSERT INTO User_Inventory "
				+ "(User_Id, Book_Id, Price, Sold)"
				+ "VALUES (?, ?, ?, ?)";
		
	//	DatabaseManager mgr = new DatabaseManager();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Connection myConn = null;
		
		try {
			// 1. Get a connection to the database
				myConn = ds.getConnection();
			// 2. Create a statement object
				myStmt = myConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				myStmt.setInt(1,inv.getUserId()); //pulls email from object
				myStmt.setInt(2,inv.getBookId());
				myStmt.setDouble(3,inv.getPrice());
				myStmt.setInt(4,inv.getSold());

				
				myStmt.executeUpdate();

				try (ResultSet generatedKeys = myStmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						inv.setId(generatedKeys.getInt(1));
					} else {
						throw new SQLException("Insertion failed, no new id created.");
					}
						
				} // end inner try

			} //end try
			catch (Exception exc) {
				exc.printStackTrace();
				
			}
			finally {
	//			mgr.silentClose(myConn, myStmt, myRs);
				DataSource.silentClose(myConn);
				DataSource.silentClose(myStmt);
				DataSource.silentClose(myRs);

			}

	} // end insert()

	
	public UserInventory get(Integer id) throws SQLException {
		
	String sql = "SELECT * FROM user_inventory where id=?";
	
//	DatabaseManager mgr = new DatabaseManager();
	PreparedStatement myStmt = null;
	ResultSet myRs = null;
	Connection myConn = null;
	
	try {
		// 1. Get a connection to the database
			myConn = ds.getConnection();
		// 2. Create a statement object
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1,id);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {
			//	UserInventory inv = new AUser(myRs.getInt("Id"), myRs.getString("Email"), myRs.getString("UserName"), myRs.getString("UserPassword"), myRs.getInt("TakeCards"), myRs.getString("school") );
				UserInventory inv = new UserInventory(myRs.getInt("Id"), 
						myRs.getInt("User_ID"), myRs.getInt("Book_ID"), 
						myRs.getDouble("price"), myRs.getInt("Sold") );
				
				return inv;
				
			} else {
				return null;
			}

		} //end try
		finally {
	//		mgr.silentClose(myConn, myStmt, myRs);
			DataSource.silentClose(myConn);
			DataSource.silentClose(myStmt);
			DataSource.silentClose(myRs);

		}

	} // end get()
	
	public void delete(Integer currUserId, Integer bookId) throws SQLException {
		
	String sql = "DELETE FROM User_Inventory WHERE User_Id=? AND Book_Id=?";
	
//	DatabaseManager mgr = new DatabaseManager();
	PreparedStatement myStmt = null;
	ResultSet myRs = null;
	Connection myConn = null;
	
	try {
		// 1. Get a connection to the database
			myConn = ds.getConnection();
		// 2. Create a statement object
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, currUserId);
			myStmt.setInt(2, bookId);
			
			myStmt.executeUpdate();

		} //end try
		catch (Exception exc) {
			exc.printStackTrace();
			
		}
		finally {
	//		mgr.silentClose(myConn, myStmt, myRs);
			DataSource.silentClose(myConn);
			DataSource.silentClose(myStmt);
			DataSource.silentClose(myRs);

		}

	} // end delete
	
} // end class UsersDAO
