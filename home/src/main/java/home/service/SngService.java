package home.service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import home.client.SngClient;
import home.dto.SngItemDTO;



@Service
public class SngService {
	
	// 대전광역시의 모든 시군구 법정동 코드 (동구, 중구, 서구, 유성구, 대덕구)
    private static final List<String> DAEJEON_DISTRICT_CODES = Arrays.asList(
        "30110", // 동구
        "30140", // 중구
        "30170", // 서구
        "30200", // 유성구
        "30230"  // 대덕구
    );
    
    private final SngClient homeClient;
    
    // 생성자 주입
    public SngService(SngClient homeClient) {
        this.homeClient = homeClient;
    }
    
 public List<SngItemDTO> getSngLeaseStats(String lawdCd, String dealYmd, int period) { 
        
        // 1. 조회할 법정동 코드 리스트를 결정합니다.
        List<String> targetLawdCds;
        if ("30000".equals(lawdCd)) { // 대전 전체 요청 시
            targetLawdCds = DAEJEON_DISTRICT_CODES;
        } else {
            targetLawdCds = Collections.singletonList(lawdCd); // 단일 구역 요청 시
        }

        // 2. 조회할 YYYYMM 코드 리스트를 period 개월치 생성합니다.
        List<String> targetDealYmds = generateDealYmds(dealYmd, period);
        if (targetDealYmds.isEmpty()) {
            System.err.println("Error: Could not generate valid deal months.");
            return Collections.emptyList();
        }

        // 3. 이중 루프: 지역 코드와 월별 코드를 반복 호출하여 원본 데이터를 가져오고 합칩니다.
        List<SngItemDTO> rawData = new ArrayList<>();
        
        for (String targetCd : targetLawdCds) { // 외부 루프: 지역 코드 순회
            for (String targetYmd : targetDealYmds) { // 내부 루프: 월별 코드 순회
                // HomeClient를 호출하여 데이터를 가져옵니다.
                List<SngItemDTO> data = homeClient.fetchSngLeaseData(targetCd, targetYmd);
                rawData.addAll(data); 
            }
        }
        
        // 4. 원본 데이터를 바로 반환합니다.
        return rawData; 
    }

    /**
     * 헬퍼 메서드: 기준 YYYYMM부터 period 개월 이전까지의 YYYYMM 문자열 리스트를 생성합니다.
     */
    private List<String> generateDealYmds(String startDealYmd, int period) {
        if (period <= 0) return Collections.emptyList();
        
        List<String> ymdList = new ArrayList<>();
        // YYYYMM 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        
        try {
            // "202510" 형태의 문자열을 YearMonth 객체로 변환
            YearMonth currentMonth = YearMonth.parse(startDealYmd, formatter);
            
            for (int i = 0; i < period; i++) {
                // 현재 월을 리스트에 추가하고, 한 달씩 거슬러 올라갑니다.
                ymdList.add(currentMonth.format(formatter));
                currentMonth = currentMonth.minusMonths(1);
            }
        } catch (Exception e) {
            System.err.println("Error parsing DEAL_YMD: " + startDealYmd);
            return Collections.emptyList();
        }
        
        return ymdList;
    }
    
}
