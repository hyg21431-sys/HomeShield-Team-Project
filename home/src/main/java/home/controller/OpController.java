package home.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import home.dto.OpItemDTO; 
import home.service.OpService; 

import java.util.List;

@RestController
public class OpController {

    private final OpService opService; 

    public OpController(OpService opService) {
        this.opService = opService;
    }

    @GetMapping("/op") 
    public List<OpItemDTO> getOpData( 
        // 1. 법정동 코드 (기본값: 30000 = 대전 전체)
        @RequestParam(value = "lawdCd", defaultValue = "30000") String lawdCd,      
        
        // 2. 기준 계약 연월 (기본값: 202510)
        @RequestParam(value = "dealYmd", defaultValue = "202510") String dealYmd, 
        
        // 3. 조회 기간 (개월 수), 기본값 12개월
        @RequestParam(value = "period", defaultValue = "12") int period         
    ) {
        // OpService의 오피스텔 전용 메서드 호출
        return opService.getOpLeaseStats(lawdCd, dealYmd, period); 
    }
}