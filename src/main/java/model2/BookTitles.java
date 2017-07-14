package model2;

/**
 * The BookTitles entity represents a book title used in the reText app 
 *  and the book titles table in the retext db
 * 
 * @author Holly Williams
 *
 */
public class BookTitles {

	private int id = 0;
	
	private String title = "";
	private String author = "";
	private String edition = "";
	private String dept = "";
	private String courseNum = "";
	private String isbn = "";
	
	
	public BookTitles(int id, String title, String author, String edition, String dept, String courseNum, String isbn) {
		this(title,author,edition,isbn);
		this.id = id;
		this.dept = dept;
		this.courseNum = courseNum;
	
	}
	
	public BookTitles(int id, String title, String author, String edition, String isbn) {
		this(title,author,edition,isbn);
		this.id = id;
	}

	public BookTitles(String title, String author, String edition, String isbn) {
		this.title = title;
		this.author = author;
		this.edition = edition;
		this.isbn = isbn;
	}
	
	public BookTitles() {
		//super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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


} // end class BookTitles
