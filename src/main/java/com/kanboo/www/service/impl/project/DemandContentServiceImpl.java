package com.kanboo.www.service.impl.project;

import com.kanboo.www.domain.entity.project.DemandContent;
import com.kanboo.www.domain.repository.project.DemandContentRepository;
import com.kanboo.www.dto.project.DemandContentDTO;
import com.kanboo.www.service.inter.project.DemandContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DemandContentServiceImpl implements DemandContentService {
    private final DemandContentRepository demandContentRepository;

    @Override
    public DemandContentDTO getDemandContent(Long idx) {
        DemandContent byDemandContentIdx = demandContentRepository.findByDemandContentIdx(idx);
        return byDemandContentIdx.entityToDto();
    }
}
