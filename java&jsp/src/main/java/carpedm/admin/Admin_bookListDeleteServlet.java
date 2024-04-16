package carpedm.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Admin_bookListDeleteServlet
 */
@WebServlet("/Admin_bookListDelete")
public class Admin_bookListDeleteServlet extends HttpServlet {
	
	Admin_book_listDAO dao = new Admin_book_listDAO();
     
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BookDTO dto = new BookDTO();
		
		// 책 목록을 삭제합니다.
		dao.deleteBooks(dto);
		
		request.getRequestDispatcher("/admin/admin_book_list").forward(request, response);
	}

}
