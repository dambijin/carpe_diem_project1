package carpedm.mainpages;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class Main_Libs_InfolistDAOImpl implements Main_Libs_InfolistDAO{

    @Autowired
    private SqlSession sqlSession;
    
	@Override
	 public List selectLibInfoList() {
       return sqlSession.selectList("mapper.carpedm.mainpages.selectLib_libinfo");
   }
}
