package carpedm.admin;

import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


public class Admin_book_listDAO {

	private static SqlSessionFactory sqlMapper = null;
	
//	private static final int EMPTY_EMPNO = -9999;
	
	// 정적으로 호출 가능한 메소드
	public static SqlSessionFactory getInstance() {
		if(sqlMapper == null) { // SqlSessionFactory 객체가 생성되지 않을 때
			try {
				String res = "mybatis/SqlMapConfig.xml";
				Reader reader = Resources.getResourceAsReader(res);
//				sqlMapper = new SqlSessionFactoryBuilder().build(reader);
					
				SqlSessionFactoryBuilder ssfb = new SqlSessionFactoryBuilder();
				sqlMapper = ssfb.build(reader);
					
				reader.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
			
		return sqlMapper;
	}
		
	// 데이터베이스에서 목록을 가져오는 기능
	public List getBookList(BookDTO dto) {
		List list = null;
		try {
			sqlMapper = getInstance();
				
			if(sqlMapper != null) {
				System.out.println("연결됨");

				SqlSession sqlSession = sqlMapper.openSession();
				// select 결과가 없으면 null
				list = sqlSession.selectList("mapper.book_list.selectBook", dto);
			} else {
				System.out.println("DB 접속 실패");
			}
				
		}catch (Exception e) {
			e.printStackTrace();
		}
			
		return list;
	}
	
	
}
