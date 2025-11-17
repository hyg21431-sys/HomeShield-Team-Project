package home.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import home.dto.MltItemDTO;
import home.service.MltService;

import java.util.List;

@RestController
public class MltController {

    private final MltService mltService;

    // 생성자 주입
    public MltController(MltService mltService) {
        // 변수명을 mltService로 통일하여 사용
        this.mltService = mltService;
    }

    /**
     * 연립다세대 전월세 실거래 데이터를 조회합니다.
     * 엔드포인트: /mlt
     */
    @GetMapping("/mlt")
    public List<MltItemDTO> getMltData(
        // 1. 법정동 코드 (기본값: 30000 = 대전 전체)
        @RequestParam(value = "lawdCd", defaultValue = "30000") String lawdCd,      
        
        // 2. 기준 계약 연월 (기본값: 202510)
        @RequestParam(value = "dealYmd", defaultValue = "202510") String dealYmd,  
        
        // 3. 조회 기간 (개월 수), 기본값 12개월
        @RequestParam(value = "period", defaultValue = "12") int period          
    ) {
        // Service 메서드를 호출하여 데이터를 반환
        return mltService.getMltLeaseStats(lawdCd, dealYmd, period);
    }
}