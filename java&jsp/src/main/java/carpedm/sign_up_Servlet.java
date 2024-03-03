package carpedm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/sign_up_Servlet")
public class sign_up_Servlet extends HttpServlet {
	private static final String URL = "jdbc:oracle:thin:@112.148.46.134:51521:xe";
	private static final String USER = "carpedm";
	private static final String PASSWORD = "dm1113@";
	
//	DB접속 메소드
	private static Connection getConnection() {
		Connection conn= null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("포스트접근성공");
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8;");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	
		System.out.println(getDBUpdate(request, response));
		
	}

	private int getDBUpdate(HttpServletRequest request , HttpServletResponse response) {
		int result = -1;
		try {
			Connection conn = getConnection();
			// SQL준비
			String m_id = request.getParameter("id"); // 아이디
			String m_pw = request.getParameter("password"); // 비밀번호
			String m_pw2= request.getParameter("password2"); // 비밀번호 확인
			String m_name= request.getParameter("name"); // 이름
			String m_birthday= request.getParameter("birthday"); // 생년월일
			String m_tel= request.getParameter("tel"); // 휴대폰번호
			String m_email= request.getParameter("email"); // 이메일
			String m_address= request.getParameter("address"); // 주소
			String n_pid= request.getParameter("mpid"); // 관리자 번호(회원번호) 로그인한거 불러와야함

//			trim() : 앞뒤 공백 제거, 스페이스바 적을 수 있으니까 필요
			if(m_id==null || m_id.trim().equals("")
					|| m_address == null || m_address.trim().equals("")) {
				response.sendRedirect("sign_up");
			}else {
				String member_in = "";
				member_in += " insert into member";
				member_in += " (";
				member_in += "M_PID";
				member_in += " , M_ID";
				member_in += " , M_PW";
				member_in += " , M_NAME";
				member_in += " , M_TEL";
				member_in += " , M_EMAIL";
				member_in += " , M_BIRTHDAY";
				member_in += " , M_ADDRESS";
				member_in += " , M_EMAIL_AGREE";
				member_in += " , M_LOANSTATE";
				member_in += " , M_MANAGERCHK";
				member_in += " , LB_ID)";
				member_in += " values(";
				member_in += " member_seq.NEXTVAL";
				
				
				System.out.println(member_in);
				// SQL 실행준비
				PreparedStatement ps = conn.prepareStatement(member_in);
                ps.setString(1, m_id);
                ps.setString(2, m_pw);
                ps.setString(3, m_name);
                ps.setString(4, m_tel);
                ps.setString(5, m_email);
                ps.setString(6, m_birthday);
                ps.setString(7, m_address);
				
				result = ps.executeUpdate();
				System.out.println("바뀐 행 수:" + result);

				ps.close();
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
