package carpedm.mainpages;

import java.util.List;
import java.util.Map;

import carpedm.dto.LibraryDTO;
import carpedm.dto.SearchinfoDTO;

public interface Main_Book_SearchDAO {
	List<LibraryDTO> selectLibListBookSearch();

	List<Map> selectBookListBookSearch(String query);

	List<SearchinfoDTO> selectPopSearchListBookSearch();

	int insertSearchInfoBookSearch(SearchinfoDTO params);

	String selectSiIdBookSearch();

	Map<String, String> selectBookCountBookSearch(String query);

	int updateBookResStateBookSearch(Map<String, String> params);

	int insertBookResBookSearch(Map<String, String> params);
}
