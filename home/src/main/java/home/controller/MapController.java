package home.controller;

import home.dto.MapDTO;
import home.service.MapService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MapController {

    private final MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    // --- 지도용 데이터 조회 ---
    @GetMapping("/map")
    public List<MapDTO> getMapData() { return mapService.getAllList(); }

    @GetMapping("/map/apt")
    public List<MapDTO> getAptData() { return mapService.getAptList(); }

    @GetMapping("/map/op")
    public List<MapDTO> getOpData() { return mapService.getOpList(); }

    @GetMapping("/map/mlt")
    public List<MapDTO> getMltData() { return mapService.getMltList(); }

    @GetMapping("/map/sng")
    public List<MapDTO> getSngData() { return mapService.getSngList(); }

    @PostMapping("/map/geocode")
    public String runGeocodeBatch() {
        mapService.geocodeAll();
        return "Geocoding batch completed";
    }
}
