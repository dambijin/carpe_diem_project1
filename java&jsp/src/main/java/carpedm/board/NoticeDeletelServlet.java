package carpedm.board;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class NoticeDeletelServlet
 */
@WebServlet("/notice_delete")
public class NoticeDeletelServlet extends HttpServlet {
//	DB접속 메소드
	private static Connection getConnection() {
		Connection conn = null;
		try {
			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/carpedm");
			conn = dataFactory.getConnection();
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
		response.sendRedirect("notice_board");	
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
