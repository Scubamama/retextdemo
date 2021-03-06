package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.UsersDAO;
import model2.AUser;

/**
 * Servlet implementation class RetextCreateUserServlet
 */
public class RetextCreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RetextCreateUserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		gatherNewUserInfo(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		createNewUser(request, response);
	}

	// displays all of the copies of the requested title available at user's
	// school
	private void gatherNewUserInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// calls up the form that the user fills in their data in
		// (retextCreateUser.jsp)

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/retextCreateUser.jsp");
		dispatcher.forward(request, response);

	} // end gatherNewUserInfo()

	private void createNewUser(HttpServletRequest request, HttpServletResponse response) {
		// takes the info from gatherNewUserInfo() and stores it in the database

		try {

			UsersDAO aUserDAO = new UsersDAO();
			String uCard = "";
			uCard = request.getParameter("takeCards");

			// check to see if this name is already created

			String currUserName = request.getParameter("userName");

			int card = 0; // default user does not take cards
			if (uCard.equals("y"))
				card = 1;

			AUser newU = new AUser(request.getParameter("email"), currUserName, request.getParameter("password"), card,
					request.getParameter("schoolName"));
			aUserDAO.save(newU); // put new user in db
			newU = aUserDAO.get(currUserName); // get the db id from new entry
			int currUserId = newU.getId();

			// create a session

			HttpSession session = request.getSession();
			session.setAttribute("currUserId", currUserId);

			// display a page saying that the user has been created
			request.setAttribute("newUser", request.getParameter("userName"));
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/retextUserCreated.jsp");
			dispatcher.forward(request, response);
		} catch (Exception exc) {
			throw new RuntimeException(exc);

		} finally {
		}
	} // end createUserServlet()

} // end class RetextCreateUserServlet
