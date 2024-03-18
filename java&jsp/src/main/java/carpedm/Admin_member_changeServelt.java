package carpedm;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/admin_member_chginfo")
public class Admin_member_changeServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// DAO 인스턴스 생성
		Admin_member_chginfoDAO dao = new Admin_member_chginfoDAO();
			
		String M_pid = request.getParameter("M_pid");
		String date = request.getParameter("date");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String number = request.getParameter("number");
		String email1 = request.getParameter("email1");
		String email2 = request.getParameter("email2");
		String address = request.getParameter("address");

		admin_chginfo_BookDTO bookDTO = new admin_chginfo_BookDTO(); // EmpDTO 객체 생성 및 설정
		bookDTO.setM_id(M_pid);
		bookDTO.setM_id(date);
		bookDTO.setM_id(id);
		bookDTO.setM_id(pw);
		bookDTO.setM_id(number);
		bookDTO.setM_id(email1);
		bookDTO.setM_id(email2);
		bookDTO.setM_id(address);

		
		// 수정기능 구현
		int result = dao.updateBook(bookDTO);
		System.out.println("update 결과 : " + result);

	}

}
