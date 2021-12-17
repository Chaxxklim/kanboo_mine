package com.kanboo.www.dto.project;

import com.kanboo.www.domain.entity.project.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    private Long prjctIdx;
    private String prjctNm;
    private LocalDateTime prjctStartDate;
    private LocalDateTime prjctEndDate;
    private int prjctProgress;
    private String prjctDelAt;

    public Project dtoToEntity() {
        return Project.builder()
                .prjctIdx(prjctIdx)
                .prjctNm(prjctNm)
                .prjctStartDate(prjctStartDate)
                .prjctEndDate(prjctEndDate)
                .prjctProgress(prjctProgress)
                .prjctDelAt(prjctDelAt)
                .build();
    }


}
