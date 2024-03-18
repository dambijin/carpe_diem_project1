package carpedm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QnADeleteServlet
 */
@WebServlet("/QnA_delete")
public class QnADeleteServlet extends HttpServlet {
	
	private static final String URL = "jdbc:oracle:thin:@112.148.46.134:51521:xe";
	private static final String USER = "carpedm";
	private static final String PASSWORD = "dm1113@";
//	DB접속 메소드
	private static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		삭제할 N_ID값 가져오기
		String nid= request.getParameter("N_ID");
		System.out.println("삭제할 N_ID : "+nid);
//		삭제버튼 누르면 할 쿼리문
		String delete_nid = "";
		delete_nid += "DELETE FROM notice";
		delete_nid += " WHERE N_ID = " + nid;
		System.out.println("삭제할 쿼리 : "+nid);
		
		ArrayList<Map<String, String>> delet_notice = deleteDB(delete_nid);
		response.sendRedirect("QnA_board");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	
	public static ArrayList<Map<String, String>> deleteDB(String notice) {
		ArrayList<Map<String, String>> result_db = new ArrayList<Map<String, String>>();
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement(notice);
			int rs = ps.executeUpdate();
			
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_db;
	}

}
