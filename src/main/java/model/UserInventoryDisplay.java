package model;

/**
 * The UserInventoryDisplay entity represents a both book and user data used to display 
 *   books owned by the user as well as their title, author, edition, dept, course number,
 *   isbn, and price.
 *   Used in the reText app and the user_inventory table in the retext db
 * 
 * @author Holly Williams
 *
 */
public class UserInventoryDisplay {

	private int id = 0;
	private String title = "";
	private String author = "";
	private String edition = "";
	private String dept = "";
	private String courseNum = "";
	private String isbn = "";
	private Double price = 0.0;
	
	public UserInventoryDisplay(Integer id, String title, String author, String edition, String dept, String courseNum,
			String isbn, Double price) {
	
		this.id = id;
		this.title = title;
		this.author = author;
		this.edition = edition;
		this.dept = dept;
		this.courseNum = courseNum;
		this.isbn = isbn;
		this.price = price;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public UserInventoryDisplay(int id, String title, String author, String edition, String isbn, Double price) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.edition = edition;
		this.isbn = isbn;
		this.price = price;
	}


	public UserInventoryDisplay(String title, String author, String edition, String isbn, Double price) {
		super();
		this.title = title;
		this.author = author;
		this.edition = edition;
		this.isbn = isbn;
		this.price = price;
	}


	public UserInventoryDisplay(String title, String author, String edition, String dept, String courseNum, String isbn,
			Double price) {
		super();
		this.title = title;
		this.author = author;
		this.edition = edition;
		this.dept = dept;
		this.courseNum = courseNum;
		this.isbn = isbn;
		this.price = price;
	}

	public UserInventoryDisplay() {
		//super();
	}

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public String getEdition() {
		return edition;
	}


	public void setEdition(String edition) {
		this.edition = edition;
	}


	public String getDept() {
		return dept;
	}


	public void setDept(String dept) {
		this.dept = dept;
	}


	public String getCourseNum() {
		return courseNum;
	}


	public void setCourseNum(String courseNum) {
		this.courseNum = courseNum;
	}


	public String getIsbn() {
		return isbn;
	}


	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}
	
} // end class DisplayUserInventory
