package home.service;

import home.dao.MapDAO;
import home.dto.MapDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MapService {

    private final MapDAO mapDAO;
    private final RestTemplate restTemplate = new RestTemplate();

    public MapService(MapDAO mapDAO) {
        this.mapDAO = mapDAO;
    }

    // --- 데이터 조회 ---
    public List<MapDTO> getAptList() { return mapDAO.selectAptList(); }
    public List<MapDTO> getOpList() { return mapDAO.selectOpList(); }
    public List<MapDTO> getMltList() { return mapDAO.selectMltList(); }
    public List<MapDTO> getSngList() { return mapDAO.selectSngList(); }
    public List<MapDTO> getAllList() { return mapDAO.selectAll(); }

    // --- Geocoding 배치 ---
    @Transactional
    public void geocodeAll() {
        List<MapDTO> list = mapDAO.selectUnGeocoded();

        for (MapDTO dto : list) {
            double[] latLng = callKakaoGeocodingAPI(dto.getAddress());
            if (latLng != null) {
                mapDAO.updateCoordinates(dto.getId(), latLng[0], latLng[1]);
            }
        }
    }

    // 실제 카카오 지도 API 호출 예제
    private double[] callKakaoGeocodingAPI(String address) {
        // TODO: RestTemplate로 API 호출 후 JSON에서 lat/lng 추출
        // 예시: return new double[]{37.12345, 127.12345};
        return null;
    }
}
