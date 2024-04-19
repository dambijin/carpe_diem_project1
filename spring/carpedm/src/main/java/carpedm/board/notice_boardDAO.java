package carpedm.board;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import carpedm.dto.NoticeBoardDTO;

@Repository
public class notice_boardDAO  {
	
	@Autowired	
	private SqlSession sqlSession;
	
	public List getNotice(NoticeBoardDTO dto) {
		List list = null;
		try {
			if(sqlSession != null) {
				list = sqlSession.selectList("mapper.carpedm.board.n_board");
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
