package com.koreait.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreait.project.DTO.MapDTO;
import com.koreait.project.service.MapService;

@Controller
@RequestMapping("/api")
public class MapController {
	
	@Autowired
	private MapService service;
	
	 // 기간별 오피스텔 임대 데이터 조회
	@GetMapping("/rental")
	@ResponseBody
	public List<MapDTO> getRental(
	        @RequestParam(value="startYmd", required=false) String startYmd,
	        @RequestParam(value="endYmd", required=false) String endYmd,
	        @RequestParam(value="numOfRows", defaultValue="100") int numOfRows) {

	    // startYmd만 있으면 endYmd와 동일하게 처리
	    if (startYmd != null && (endYmd == null || endYmd.isEmpty())) {
	        endYmd = startYmd;
	    }

	    if (startYmd != null && endYmd != null) {
	        return service.getRentalList(startYmd, endYmd, numOfRows);
	    } else {
	        return new ArrayList<>();
	    }
	}


}
