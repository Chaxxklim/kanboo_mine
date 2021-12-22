package com.kanboo.www.controller.access;

import com.kanboo.www.dto.project.DemandContentDTO;
import com.kanboo.www.service.inter.project.DemandContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/demand")
public class DemandController {
    private final DemandContentService demandContentService;

    @PostMapping("/postRows")
    public void updateDemandContent(@RequestBody Map<String, List<DemandContentDTO>> map){

        List<DemandContentDTO> params = map.get("params");
        demandContentService.updateDemandContent(params);
    }

    @PostMapping("/load")
    public List<DemandContentDTO> loadDemandContent(@RequestBody Map<String, String> map){
        String mapIdx = (String) map.get("idx");
        Long idx = Long.parseLong(mapIdx);
        List<DemandContentDTO> list = demandContentService.loadDemandContent(idx);
        System.out.println(list.toString());
        return list;
    }

//    @PostMapping("/downDocument")
//    public void downloadExcel(@RequestBody Map<String, String> map, HttpServletResponse response) throws Exception{
//        String extension = (String) map.get("extension");
//        String mapIdx = (String) map.get("idx");
//        String prjctNm = (String) map.get("prjctNm");
//        Long idx = Long.parseLong(mapIdx);
//
//        demandContentService.downloadExcel(idx, extension);
//        try {
//            String path = "src/main/resources/storage/" + prjctNm + "-" + idx + ".xlsx";
//
//            File file = new File(path);
//            response.setHeader("Content-Disposition", "attachment;filename=" + file.getName()); // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더
//
//            FileInputStream fileInputStream = new FileInputStream(path); // 파일 읽어오기
//            OutputStream out = response.getOutputStream();
//
//            int read = 0;
//            byte[] buffer = new byte[1024];
//            while ((read = fileInputStream.read(buffer)) != -1) { // 1024바이트씩 계속 읽으면서 outputStream에 저장, -1이 나오면 더이상 읽을 파일이 없음
//                out.write(buffer, 0, read);
//            }
//        } catch (Exception e){
//            throw new Exception("download Error");
//        }
//    }

    @PostMapping("/importDocument")
    public void importDocument(@RequestBody Map<String, String> map, MultipartFile file){
        System.out.println("임포트옴");
        String mapIdx = (String) map.get("idx");
        Long idx = Long.parseLong(mapIdx);
        demandContentService.importDocument(idx, file);

    }

    @PostMapping("/downDocument")
    public ResponseEntity<Resource> downloadFile(@RequestBody Map<String, String> map,
                                                 @RequestHeader("User-Agent") String userAgent) {

            File f = new File("");
            String absolutePath = f.getAbsolutePath();
            String localPath = "C:\\Users\\PC\\Desktop\\LCK\\FinalProject\\kanbooFinal\\" +
                    "kanboo_final\\src\\main\\resources\\storage\\demand\\excel\\save";
            String mapIdx = (String) map.get("idx");
            String prjctNm = (String) map.get("prjctNm"); // prjctNm vue에서 받아야디ㅗ고 확장자도 받아야한다 균창아
            Long idx = Long.parseLong(mapIdx);
            demandContentService.downloadExcel(idx);
            String fileName = prjctNm + "-" + idx + ".xlsx";
//        String uploadFolder = "src\\main\\resources\\storage";
            Resource resource = new FileSystemResource(localPath + "\\" +  fileName);

            System.out.println("뭐노 " + absolutePath);
            System.out.println(resource.toString());
            //해당 파일이 없을 때
            if (!resource.exists()) {
                System.out.println("파일이 없누");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            String resourceName = resource.getFilename();
            HttpHeaders headers = new HttpHeaders();

            try {
                String downloadName = null;
                if(userAgent.contains("Trident")) { //IE 11
                    downloadName = URLEncoder.encode(resourceName, "UTF-8").replaceAll("\\+", " ");
                }else if(userAgent.contains("Edge")) {
                    downloadName = URLEncoder.encode(resourceName, "UTF-8");
                }else {
                    downloadName = new String(resourceName.getBytes("UTF-8"), "ISO-8859-1");
                }
                headers.add("Content-disposition", "attachment;fileName=" +
                        new String(resourceName.getBytes("UTF-8"),"ISO-8859-1"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            // resource : 첨부파일 객체
            // headers : 파일명 처리 정보
            // ...OK : 200(성공)
            System.out.println(fileName);
            return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);


    }





}
