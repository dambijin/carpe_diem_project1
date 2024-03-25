package carpedm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class admin_book_listDAO {

	private static final int EMPTY_EMPNO = -9999;
	
	private Connection con;
	
	// 데이터베이스 연결을 수행하는 메소드
	private void connDB() {
		
		try {
			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/team");
			// DataSource로부터 Connection을 얻어옴
			this.con =  dataFactory.getConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	List listbook() {
		return listbook(EMPTY_EMPNO);
	}
	public List listbook(int b_id) {
		
		long begin = System.currentTimeMillis();
		
		/* 꼭 써야함 !! */
		connDB();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List list = new ArrayList();
		
		try {
			// SQL 준비
			String query = "select * from book";
			if(b_id != EMPTY_EMPNO) {
				query += " where b_id = ?";
			}
			
			ps = con.prepareStatement(query);
			if(b_id != EMPTY_EMPNO) {
				ps.setInt(1, b_id);
			}
			
			// SQL 실행 및 결과 확보
			rs = ps.executeQuery();
			
			// 결과활용
			while(rs.next()) {
				int empno2 = rs.getInt("empno");
				String ename = rs.getString("ename");
				int sal = rs.getInt("sal");
				String job = rs.getString("job");

				System.out.println("empno2 : "+ empno2);
				
				admin_booklistDTO bDTO = new admin_booklistDTO();
//				bDTO.setEmpno(empno2);
//				bDTO.setEname(ename);
//				bDTO.setSal(sal);
//				bDTO.setJob(job);
				
				list.add(bDTO);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(this.con != null) {
				try {
					this.con.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		
		}
		long end = System.currentTimeMillis();
		System.out.println("걸린시간 : " + (end - begin));
		
		return list;
		
	}
}
