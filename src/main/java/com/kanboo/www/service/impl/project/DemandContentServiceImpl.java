package com.kanboo.www.service.impl.project;

import com.kanboo.www.domain.entity.project.DemandContent;
import com.kanboo.www.domain.repository.project.DemandContentRepository;
import com.kanboo.www.dto.project.DemandContentDTO;
import com.kanboo.www.service.inter.project.DemandContentService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DemandContentServiceImpl implements DemandContentService {
    private final DemandContentRepository demandContentRepository;


    @Override
    public DemandContentDTO getDemandContent(Long idx) {
        DemandContent byDemandContentIdx = demandContentRepository.findByDemandContentIdx(idx);
        return byDemandContentIdx.entityToDto();
    }

    @Override
    public List<DemandContentDTO> loadDemandContent(Long idx) {
        List<DemandContentDTO> demandContentDTOList = new ArrayList<>();
        List<DemandContent> demandContentList = demandContentRepository.findByDemandIdx(idx);

        for(DemandContent demandContent : demandContentList){
            demandContentDTOList.add(demandContent.entityToDto());
        }

        return demandContentDTOList;
    }

    @Override
    public DemandContentDTO updateDemandContent(List<DemandContentDTO> demandContentDTO) {
        demandContentDTO.forEach(item -> {
            demandContentRepository.save(item.dtoToEntity());
        });
        return null;
    }

    @Override
    public void downloadExcel(Long idx, String extension) {
        XSSFWorkbook workBook = new XSSFWorkbook();
        XSSFSheet sheet = workBook.createSheet("demand");
        idx = Long.valueOf(1);
        List<DemandContentDTO> demandContentDTOList = new ArrayList<>();
        List<DemandContent> demandContentList = demandContentRepository.findByDemandIdx(idx);
        for(DemandContent demandContent : demandContentList){
            demandContentDTOList.add(demandContent.entityToDto());
        }
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        CellStyle titleCellStyle = workBook.createCellStyle();
        XSSFFont font = workBook.createFont();
        font.setFontName("gothic");
        font.setBold(true);
        titleCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleCellStyle.setFont(font);
        cell.setCellValue(demandContentDTOList.get(0).getDemand().getProject().getPrjctNm() + " - " + "demand");
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,6));
        cell.setCellStyle(titleCellStyle);
        row = sheet.createRow(1);
        cell = row.createCell(0);
        sheet.setColumnWidth(0, 2048);
        cell.setCellValue("No");
        cell = row.createCell(1);
        sheet.setColumnWidth(1, 4096);
        cell.setCellValue("Category");
        cell = row.createCell(2);
        sheet.setColumnWidth(2, 4096);
        cell.setCellValue("Demand ID");
        cell = row.createCell(3);
        sheet.setColumnWidth(3, 6144);
        cell.setCellValue("Demand Name");
        cell = row.createCell(4);
        sheet.setColumnWidth(4, 20480);
        cell.setCellValue("Demand Description");
        cell = row.createCell(5);
        sheet.setColumnWidth(5, 3072);
        cell.setCellValue("Requester");
        cell = row.createCell(6);
        sheet.setColumnWidth(6, 8192);
        cell.setCellValue("Remark");

        for (int i = 0; i < demandContentDTOList.size(); i++) {
            row = sheet.createRow(i+2);
            cell = row.createCell(0);
            cell.setCellValue(demandContentDTOList.get(i).getDemandCnNum());
            cell = row.createCell(1);
            cell.setCellValue(demandContentDTOList.get(i).getDemandCnSe());
            cell = row.createCell(2);
            cell.setCellValue(demandContentDTOList.get(i).getDemandCnId());
            cell = row.createCell(3);
            cell.setCellValue(demandContentDTOList.get(i).getDemandCnNm());
            cell = row.createCell(4);
            cell.setCellValue(demandContentDTOList.get(i).getDemandCnDetail());
            cell = row.createCell(5);
            cell.setCellValue(demandContentDTOList.get(i).getDemandCnRequstNm());
            cell = row.createCell(6);
            cell.setCellValue(demandContentDTOList.get(i).getDemandCnRm());
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/storage/"+
                    demandContentDTOList.get(0).getDemand().getProject().getPrjctNm() + "-" +
                    demandContentDTOList.get(0).getDemand().getProject().getPrjctIdx() +  ".xlsx");
            workBook.write(fileOutputStream);

        } catch (IOException e) {
            System.out.println("에러 ㅜ");
            e.printStackTrace();
        }


    }

    @Override
    public void downloadPdf(Long idx, String extension) {
        XSSFWorkbook workBook = new XSSFWorkbook();
        XSSFSheet sheet = workBook.createSheet("demand");
        idx = Long.valueOf(1);
        List<DemandContentDTO> demandContentDTOList = new ArrayList<>();
        List<DemandContent> demandContentList = demandContentRepository.findByDemandIdx(idx);
        for(DemandContent demandContent : demandContentList){
            demandContentDTOList.add(demandContent.entityToDto());
        }
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        CellStyle titleCellStyle = workBook.createCellStyle();
        XSSFFont font = workBook.createFont();
        font.setFontName("gothic");
        font.setBold(true);
        titleCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleCellStyle.setFont(font);
        cell.setCellValue(demandContentDTOList.get(0).getDemand().getProject().getPrjctNm() + " - " + "demand");
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,6));
        cell.setCellStyle(titleCellStyle);
        row = sheet.createRow(1);
        cell = row.createCell(0);
        sheet.setColumnWidth(0, 2048);
        cell.setCellValue("No");
        cell = row.createCell(1);
        sheet.setColumnWidth(1, 4096);
        cell.setCellValue("Category");
        cell = row.createCell(2);
        sheet.setColumnWidth(2, 4096);
        cell.setCellValue("Demand ID");
        cell = row.createCell(3);
        sheet.setColumnWidth(3, 6144);
        cell.setCellValue("Demand Name");
        cell = row.createCell(4);
        sheet.setColumnWidth(4, 20480);
        cell.setCellValue("Demand Description");
        cell = row.createCell(5);
        sheet.setColumnWidth(5, 3072);
        cell.setCellValue("Requester");
        cell = row.createCell(6);
        sheet.setColumnWidth(6, 8192);
        cell.setCellValue("Remark");

        for (int i = 0; i < demandContentDTOList.size(); i++) {
            row = sheet.createRow(i+2);
            cell = row.createCell(0);
            cell.setCellValue(demandContentDTOList.get(i).getDemandCnNum());
            cell = row.createCell(1);
            cell.setCellValue(demandContentDTOList.get(i).getDemandCnSe());
            cell = row.createCell(2);
            cell.setCellValue(demandContentDTOList.get(i).getDemandCnId());
            cell = row.createCell(3);
            cell.setCellValue(demandContentDTOList.get(i).getDemandCnNm());
            cell = row.createCell(4);
            cell.setCellValue(demandContentDTOList.get(i).getDemandCnDetail());
            cell = row.createCell(5);
            cell.setCellValue(demandContentDTOList.get(i).getDemandCnRequstNm());
            cell = row.createCell(6);
            cell.setCellValue(demandContentDTOList.get(i).getDemandCnRm());
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/storage/"+
                    demandContentDTOList.get(0).getDemand().getProject().getPrjctNm() + "-" +
                    demandContentDTOList.get(0).getDemand().getProject().getPrjctIdx() +  ".xlsx");
            workBook.write(fileOutputStream);

        } catch (IOException e) {
            System.out.println("에러 ㅜ");
            e.printStackTrace();
        }

    }

    @Override
    public boolean importDocument(Long idx, MultipartFile file) {
        boolean flag = false;
        List<DemandContentDTO> demandContentDTOList = new ArrayList<>();
        List<DemandContent> demandContentList = demandContentRepository.findByDemandIdx(idx);

        for(DemandContent demandContent : demandContentList){
            demandContentDTOList.add(demandContent.entityToDto());
        }
        //여기에 idx 값 파일명 검사해야함
        String userInputFileName = file.getOriginalFilename();

        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/storage/"
                    + userInputFileName + ".xlsx");
            flag = true;

            try {
                XSSFWorkbook workbook = new XSSFWorkbook((InputStream) file);
                String sheetName = "";
                XSSFRow row;
                XSSFCell cell;
                int rows = 0;
                int cells = 0;
                int sheetCn = workbook.getNumberOfSheets();

                for (int i = 0; i < sheetCn; i++) {
                    sheetName = workbook.getSheetName(i);
                    XSSFSheet sheet = workbook.getSheetAt(i);
                    rows = sheet.getPhysicalNumberOfRows();
                    cells = sheet.getRow(i).getPhysicalNumberOfCells();
                    for (int j = 0; j < rows; j++) {
                        row = sheet.getRow(j);
                        if(row != null){
                            for (int k = 0; k < cells; k++) {
                                cell = row.getCell(k);
                                if(cell != null){
                                    String value = cell.getStringCellValue();
                                    System.out.println(value);

                                } else {
                                    System.out.println("null임");
                                }
                            }
                            System.out.println("\n");
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (FileNotFoundException e) {
            System.out.println("파일음슴 ㅋㅋ 너 파일명 변경했지?ㅋㅋ");
            e.printStackTrace();
        }
    return flag;
    }


}
