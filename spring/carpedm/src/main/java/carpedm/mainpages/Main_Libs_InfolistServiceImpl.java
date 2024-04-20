package carpedm.mainpages;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class Main_Libs_InfolistServiceImpl implements Main_Libs_InfolistService{

    @Autowired
    private Main_Libs_InfolistDAO main_Libs_infolistDAO;
	
    @Override
    public List getLibInfoList() {
        return main_Libs_infolistDAO.selectLibInfoList();
    }
}
