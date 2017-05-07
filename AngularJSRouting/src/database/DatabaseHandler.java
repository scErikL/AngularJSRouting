package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import book.Book;

public class DatabaseHandler {
	
	static String user = "root";
	static String password = "root";
	static String dbUri = "jdbc:mysql://localhost:3306/servletcodealong?useSSL=false";
	
	public Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection(dbUri, user, password);
		
	}
	
	public List<Book> getBooks() throws ClassNotFoundException, SQLException{
		List<Book> books = new ArrayList<Book>();
		
		try( 
				Connection cn = getConnection();
				PreparedStatement pstmt = cn.prepareStatement("SELECT * FROM book");
				ResultSet rs = pstmt.executeQuery();
			)	{
				while(rs.next()){
					Book b = new Book();
					b.setId(rs.getInt("id"));
					b.setTitle(rs.getString("title"));
					b.setAuthor(rs.getString("author"));
					books.add(b);
					
				}
			return books;
		}
		
		
	}
	
	public boolean addBook(String title, String author) throws SQLException{
		Connection cn = null;
		PreparedStatement pstmt = null;
		boolean retVal = true;
			try{
				String sql = "INSERT INTO book VALUES (null, ?, ?)";
				cn = getConnection();
				pstmt = cn.prepareStatement(sql);
				pstmt.setString(1,title);
				pstmt.setString(2,author);
				System.out.println(pstmt.executeUpdate());
			} catch(SQLException | ClassNotFoundException e){
				retVal = false;
				e.printStackTrace();
			} finally{
				if (cn!=null)
					cn.close();
				if (pstmt!=null)
					pstmt.close();
			}
		return retVal;		
	}
}
