package com.kanboo.www.service.inter.project;

import com.kanboo.www.dto.project.DemandContentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DemandContentService {
    List<DemandContentDTO> loadDemandContent(Long idx);
    DemandContentDTO getDemandContent(Long idx);
    DemandContentDTO updateDemandContent(List<DemandContentDTO> demandContentDTO);


    void downloadExcel(Long idx);

    boolean importDocument(Long idx, MultipartFile file);
}
