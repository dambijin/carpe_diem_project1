package carpedm.mainpages;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import carpedm.dto.LibraryDTO;
import carpedm.dto.SearchinfoDTO;

@Repository
@Primary
public class Main_Book_SearchDAOImpl implements Main_Book_SearchDAO {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<LibraryDTO> selectLibListBookSearch() {
		return sqlSession.selectList("mapper.carpedm.mainpages.selectLibList_book_search");
	}

	@Override
	public List<Map> selectBookListBookSearch(String query) {
		return sqlSession.selectList("mapper.carpedm.mainpages.selectBookList_book_search", query);
	}

	@Override
	public List<SearchinfoDTO> selectPopSearchListBookSearch() {
		return sqlSession.selectList("mapper.carpedm.mainpages.selectPopSearchList_book_search");
	}

	@Override
	public int insertSearchInfoBookSearch(SearchinfoDTO params) {
		return sqlSession.insert("mapper.carpedm.mainpages.insertSearchInfo_book_search", params);
	}

	@Override
	public String selectSiIdBookSearch() {
		return sqlSession.selectOne("mapper.carpedm.mainpages.selectSiId_book_search");
	}

	@Override
	public Map<String, String> selectBookCountBookSearch(String query) {
		return sqlSession.selectOne("mapper.carpedm.mainpages.selectBookList_book_search",
				query.replace("SELECT b.*, l.lb_name ", "SELECT count(*) "));
	}

	@Override
	public int updateBookResStateBookSearch(Map<String, String> params) {
		return sqlSession.update("mapper.carpedm.mainpages.updateBookResState_book_search", params);
	}

	@Override
	public int insertBookResBookSearch(Map<String, String> params) {
		return sqlSession.insert("mapper.carpedm.mainpages.insertBookRes_book_search", params);
	}
}
