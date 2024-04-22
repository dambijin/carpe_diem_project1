package carpedm.mypages;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class My_Loan_historyDAO {
	
	@Autowired
	private SqlSession sqlSession;

}
