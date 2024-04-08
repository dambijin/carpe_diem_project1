package carpedm.admin;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/admin_member_update")
public class Admin_member_updateServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// DAO 인스턴스 생성
		Admin_member_chginfoDAO dao = new Admin_member_chginfoDAO();
			
		int m_pid = Integer.parseInt(request.getParameter("m_pid"));
//		int m_pid = 2;
//		System.out.println("aa:"+m_pid);
//		Date m_birthday = (Date)request.getParameter("date");
		
		Date m_birthday = null;
		//simpledateformat으로 변환할때 try catch가 필요함
		String dateParam = request.getParameter("date");

		if (dateParam != null && !dateParam.isEmpty()) {
		    try {
		        java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateParam);
		        m_birthday = new Date(utilDate.getTime()); // java.sql.Date로 변환
		    } catch (ParseException e) {
		        e.printStackTrace();
		        System.out.println("Date 형식이 잘못되었습니다");
		        // 날짜 형식이 잘못된 경우의 처리
		    }
		} else {
		    // "date" 파라미터가 전달되지 않은 경우의 처리
		    // 혹은 다른 기본값을 설정할 수 있습니다.
		}
		String m_name = request.getParameter("name");
		String m_id = request.getParameter("id");
		String m_pw = request.getParameter("pw");
		String m_tel = request.getParameter("phone_number");
		String m_email = request.getParameter("email1");
		String m_email2 = request.getParameter("email2");
		String m_address = request.getParameter("address1");

		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setM_pid(m_pid);
		memberDTO.setM_birthday(m_birthday);
		memberDTO.setM_name(m_name);
		memberDTO.setM_id(m_id);
		memberDTO.setM_pw(m_pw);
		memberDTO.setM_tel(m_tel);	
		memberDTO.setM_email(m_email + "@" + m_email2);
		memberDTO.setM_address(m_address);

		// 수정기능 구현
		int result = dao.updateBook(memberDTO);
		System.out.println("update 결과 : " + result);

		request.getRequestDispatcher("/admin_member_list").forward(request, response);
	}

}
