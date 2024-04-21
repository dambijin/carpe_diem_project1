package carpedm.mainpages;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import carpedm.dto.ChaseBookDTO;

@Repository
@Primary
public class Main_Book_DetailDAOImpl implements Main_Book_DetailDAO {

	@Autowired
	private SqlSession sqlSession;

	@Override
    public List<Map<String, String>> selectBookDetail(String bId) {
        return sqlSession.selectList("mapper.carpedm.mainpages.selectBookDetail_book_detail", bId);
    }
	
	@Override
    public int insertChaseBook(ChaseBookDTO updateParams) {
        return sqlSession.insert("mapper.carpedm.mainpages.insertChaseBook_book_detail", updateParams);
    }
}
