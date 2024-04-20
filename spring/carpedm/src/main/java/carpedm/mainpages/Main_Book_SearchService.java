package carpedm.mainpages;

import java.util.List;
import java.util.Map;

import carpedm.dto.LibraryDTO;
import carpedm.dto.SearchinfoDTO;

public interface Main_Book_SearchService {
	List<LibraryDTO> getLibListBookSearch();

	List<Map> getBookListBookSearch(String query);

	List<SearchinfoDTO> getPopSearchListBookSearch();

	int addSearchInfoBookSearch(Map<String, String> params);

	String getSiIdBookSearch();

	Map<String, String> getBookCountBookSearch(String query);

	int updateBookResStateBookSearch(Map<String, String> params);

	int insertBookResBookSearch(Map<String, String> params);
}
