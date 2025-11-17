package home.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import home.dto.SngItemDTO;       // 단독/다가구 DTO 임포트
import home.service.SngService;    // 단독/다가구 서비스 임포트

import java.util.List;

@RestController
public class SngController {

    // 단독/다가구 전용 서비스를 주입받습니다.
    private final SngService sngService;

    public SngController(SngService sngService) {
        this.sngService = sngService;
    }

    @GetMapping("/sng") // 단독/다가구 전용 엔드포인트
    public List<SngItemDTO> getSingleData(
        // 1. 법정동 코드 (기본값: 30000 = 대전 전체)
        @RequestParam(value = "lawdCd", defaultValue = "30000") String lawdCd,

        // 2. 기준 계약 연월 (기본값: 202510)
        @RequestParam(value = "dealYmd", defaultValue = "202510") String dealYmd,

        // 3. 조회 기간 (개월 수), 기본값 12개월
        @RequestParam(value = "period", defaultValue = "12") int period
    ) {
        // SngService의 메서드를 호출합니다.
        return sngService.getSngLeaseStats(lawdCd, dealYmd, period);
    }
}