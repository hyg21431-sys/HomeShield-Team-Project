package home.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import home.dto.ItemDTO; 
import home.service.HomeService; 

import java.util.List;

@RestController
public class HomeController {

    private final HomeService homeService; 

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/apt") 
    public List<ItemDTO> getAptData( 
        // 1. 법정동 코드 (기본값: 30000 = 대전 전체)
        @RequestParam(value = "lawdCd", defaultValue = "30000") String lawdCd,     
        
        // 2. 기준 계약 연월 (기본값: 202510)
        @RequestParam(value = "dealYmd", defaultValue = "202510") String dealYmd, 
        
        // 3. 조회 기간 (개월 수), 기본값 12개월
        @RequestParam(value = "period", defaultValue = "12") int period          
    ) {
        // Service 메서드를 호출할 때 lawdCd, dealYmd, period 세 가지 인자를 모두 전달
        return homeService.getAptLeaseStats(lawdCd, dealYmd, period); 
    }
}