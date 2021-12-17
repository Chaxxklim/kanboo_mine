package com.kanboo.www.controller.access;

import com.kanboo.www.dto.project.DemandContentDTO;
import com.kanboo.www.service.inter.project.DemandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DemandController {
    private final DemandService demandService;

    @PostMapping("/demand/postRows")
    public void saveDemand(@RequestBody Map<String, List<DemandContentDTO>> list){
        list.forEach((k, v) -> {
            v.forEach(System.out::println);
        });
    }

//    @PostMapping("/demand/postRows")
//    public void saveDemand(DemandContentDTO list){
//        System.out.println(list);
//    }

}
