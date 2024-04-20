package carpedm.mainpages;

import java.util.List;
import java.util.Map;

import carpedm.dto.LibraryDTO;
import carpedm.dto.SearchinfoDTO;

public interface Main_Book_DetailDAO {
	List<Map<String, String>> selectBookDetail(String bId);

	int updateSearchInfo(Map<String, String> updateParams);
}
