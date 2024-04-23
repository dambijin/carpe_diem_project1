package carpedm.mainpages;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class Main_PageDAOImpl implements Main_PageDAO{

    @Autowired
    private SqlSession sqlSession;
    
	@Override
	 public List selectLib_libinfo() {
       return sqlSession.selectList("mapper.carpedm.mainpages.selectLib_libinfo");
   }
	@Override
	 public List selectNotice_main() {
      return sqlSession.selectList("mapper.carpedm.mainpages.selectNotice_main");
  }
	@Override
	 public List selectBook_main() {
      return sqlSession.selectList("mapper.carpedm.mainpages.selectBook_main");
  }
	@Override
	 public List selectBanner_main(String now_date) {
      return sqlSession.selectList("mapper.carpedm.mainpages.selectBanner_main",now_date);
  }	
	@Override
	 public List selectRecommendBook_main() {
     return sqlSession.selectList("mapper.carpedm.mainpages.selectRecommendBook_main");
 }
}
