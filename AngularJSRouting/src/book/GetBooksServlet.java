package book;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import database.DatabaseHandler;

/**
 * Servlet implementation class GetBooksServlet
 */
@WebServlet("/GetBooks")
public class GetBooksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBooksServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DatabaseHandler db = new DatabaseHandler();
		List<Book> bookList = null;
		try {
			
			bookList = db.getBooks();
			
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(new Gson().toJson(bookList));
			out.flush();
			
	/*		
			response.setContentType("text/html");
			String tmp="<html><body>";
			for(Book b : bookList){
				tmp+="<p>"+b.getTitle()+" : " +b.getAuthor() +"</p>";
			}
			tmp+="</body></html>";			
			response.getWriter().write(tmp);
		*/	
			//request.setAttribute("books", bookList);
			//request.getRequestDispatcher("/WEB-INF/books.jsp").forward(request, response);
			
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			response.getWriter().write("Fel: "+e.getMessage());
		}
		
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
