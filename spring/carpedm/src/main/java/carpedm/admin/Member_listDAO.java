package carpedm.admin;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import carpedm.dto.MemberDTO;

@Repository
public class Member_listDAO {

	@Autowired	
	private SqlSession sqlSession;
	
	// 데이터베이스에서 목록을 가져오는 기능
	public List getMemberList(MemberDTO dto) {
		List list = null;
		try {

			if(sqlSession != null) {
				// select 결과가 없으면 null
//				list = sqlSession.selectList("mapper.emp.dynamic.selectEmp", dto);
				list = sqlSession.selectList("mapper.carpedm.admin.selectMember", dto);
				System.out.println("list" +list);
			} else {
				System.out.println("DB 접속 실패");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
}
