package carpedm.mainpages;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import carpedm.dto.LibraryDTO;
import carpedm.dto.SearchinfoDTO;

@Service
@Primary
public class Main_Book_SearchServiceImpl implements Main_Book_SearchService {

	@Autowired
	private Main_Book_SearchDAO main_Book_SearchDAO;

	@Override
	public List<LibraryDTO> getLibListBookSearch() {
		return main_Book_SearchDAO.selectLibListBookSearch();
	}

	@Override
	public List<Map> getBookListBookSearch(String query) {
		return main_Book_SearchDAO.selectBookListBookSearch(query);
	}

	@Override
	public List<SearchinfoDTO> getPopSearchListBookSearch() {
		return main_Book_SearchDAO.selectPopSearchListBookSearch();
	}

	@Override
	public int addSearchInfoBookSearch(SearchinfoDTO params) {
		return main_Book_SearchDAO.insertSearchInfoBookSearch(params);
	}

	@Override
	public String getSiIdBookSearch() {
		return main_Book_SearchDAO.selectSiIdBookSearch();
	}

	@Override
	public Map<String, String> getBookCountBookSearch(String query) {
		return main_Book_SearchDAO.selectBookCountBookSearch(query);
	}

	@Override
	public int updateBookResStateBookSearch(Map<String, String> params) {
		return main_Book_SearchDAO.updateBookResStateBookSearch(params);
	}

	@Override
	public int insertBookResBookSearch(Map<String, String> params) {
		return main_Book_SearchDAO.insertBookResBookSearch(params);
	}
}
