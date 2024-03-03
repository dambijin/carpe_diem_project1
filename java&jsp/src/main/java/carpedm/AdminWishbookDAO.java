//package carpedm;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//public class AdminWishbookDAO {
//	
//	private Connection conn;
//	private PreparedStatement psmt;
//	private ResultSet rs;
//	
//	public AdminWishbookDAO() {
//		try {
//			String URL = "jdbc:oracle:thin:@112.148.46.134:51521:xe";
//			String USER = "carpedm";
//			String PASSWORD = "dm1113@";
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			conn = DriverManager.getConnection(URL, USER, PASSWORD);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public ArrayList<Wishlist> search(String w_id){
//		
//		String query = "";
//		query += " select";
//		query += " w_id, w_title, w_author, w_isbn, w_pubyear, m_pid, w_name, w_pubyear, w_content";
//		query += " from";
//		query += " wishlist";
//		query += " where w_id like ?";
//		
//		ArrayList<Wishlist> list = new ArrayList<Wishlist>();
//		
//		try {
//			psmt = conn.prepareStatement(query);
//			psmt.setString(1, "%" + w_id + "%");
//			rs = psmt.executeQuery();
//			while (rs.next()) {
//				Wishlist wishlist = new Wishlist();
//				wishlist.setW_id(rs.getInt(1));
//				wishlist.setW_title(rs.getString(2));
//				wishlist.setW_author(rs.getString(3));
//				wishlist.setW_isbn(rs.getInt(4));
//				wishlist.setW_pubyear(rs.getInt(5));
//				wishlist.setM_pid(rs.getInt(6));
//				wishlist.setW_name(rs.getString(7));
//				wishlist.setW_content(rs.getString(8));
//				list.add(wishlist);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return list;
//	}
//	
//}
