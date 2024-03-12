package carpedm;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin_member_change")
public class Admin_member_changeServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		String m_pid = request.getParameter("m_pid");
		String date = request.getParameter("date");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String number = request.getParameter("number");
		String email1 = request.getParameter("email1");
		String email2 = request.getParameter("email2");
		String address = request.getParameter("address");

		// DAO 인스턴스 생성
		Admin_member_chginfoDAO DAO = new Admin_member_chginfoDAO();

		// 수정기능 구현
		List list = DAO.change(m_pid, date, id, pw, number, email1, email2, address);

	}

}
