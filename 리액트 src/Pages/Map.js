import React, { useEffect, useRef } from "react";
import axios from "axios";

const Map = () => {
  const mapContainer = useRef(null);
  const mapInstance = useRef(null);

  // 조회 기간
  const startDate = "202401"; // 2024년 1월
  const endDate = "202511";   // 2025년 11월

  useEffect(() => {
    const loadMap = () => {
      const map = new window.kakao.maps.Map(mapContainer.current, {
        center: new window.kakao.maps.LatLng(36.3504, 127.3845),
        level: 8,
      });
      mapInstance.current = map;
      return map;
    };

    const addMarker = (map, lat, lng, content, currentInfoWindowRef, markerImage) => {


      const marker = new window.kakao.maps.Marker({
        position: new window.kakao.maps.LatLng(lat, lng),
        map,
        image: markerImage,
      });

      const infowindow = new window.kakao.maps.InfoWindow({ content });

      window.kakao.maps.event.addListener(marker, "click", () => {

        // 기존 InfoWindow(켜져 있던 마커) 닫기
        if (currentInfoWindowRef.current) {
          currentInfoWindowRef.current.close();
        }
        infowindow.open(map, marker);
        currentInfoWindowRef.current = infowindow;
      });
    };

    async function initMap() {
      const map = loadMap();

      try {
        // API 요청 (기간별 데이터) - 컨트롤러
        const res = await axios.get(
          `http://192.168.1.9:8080/api/rental?startYmd=${startDate}&endYmd=${endDate}`
        );

        const rentalData = Array.isArray(res.data) ? res.data : [];
        console.log("오피스텔 데이터:", rentalData);

        const bounds = new window.kakao.maps.LatLngBounds();

          bounds.extend(new window.kakao.maps.LatLng(36.250, 127.250));
          bounds.extend(new window.kakao.maps.LatLng(36.450, 127.500));

        const currentInfoWindow = { current: null };
        
        const addedBuildings = new Set(); // 중복 마커 방지

          rentalData.forEach((item) => {
            if (!item.lat || !item.lng) return;

            const buildingKey = `${item.buildingName}-${item.lat}-${item.lng}`;
            if (addedBuildings.has(buildingKey)) return;

             // --- 같은 좌표 중복 방지를 위한 offset 추가 ---
                const latOffset = (Math.random() - 0.5) * 0.0003; // 약 ±30m 범위
                const lngOffset = (Math.random() - 0.5) * 0.0003;
                const adjustedLat = parseFloat(item.lat) + latOffset;
                const adjustedLng = parseFloat(item.lng) + lngOffset;

            // 마커 색상 결정
            let markerImageUrl = "";

            if (item.monthlyRent && item.deposit) {
              // 전세 + 월세
              markerImageUrl = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png";
            } else if (item.monthlyRent) {
              // 월세만
              markerImageUrl = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_yellow.png";
            } else if (item.deposit) {
              // 전세만
              markerImageUrl = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
            }

            const markerImage = new window.kakao.maps.MarkerImage(
              markerImageUrl,
              new window.kakao.maps.Size(24, 35)
            );

            const depositText = item.deposit ? item.deposit + "만원" : "-";
            const monthlyRentText = item.monthlyRent && item.monthlyRent !== "0"
              ? item.monthlyRent + "만원"
              : "-";

              let infoContent = `
                    <div style="
                      padding: 5px;
                      max-width: 150px;            
                      white-space: normal;        
                      word-break: break-word;     
                      overflow-wrap: break-word;   
                    ">
                      <b>${item.buildingName}</b><br/>
                      ${item.gu} ${item.dong}<br/>
                  `;

              if(item.deposit) infoContent += `전세: ${depositText}<br/>`;
              if(item.monthlyRent) infoContent += `월세: ${monthlyRentText}<br/>`;

              infoContent += `계약일: ${item.contractDay}</div>`;

           addMarker(
              map,
              adjustedLat,
              adjustedLng,
              infoContent,
              currentInfoWindow,
              markerImage
            );


            bounds.extend(new window.kakao.maps.LatLng(adjustedLat, adjustedLng));
            addedBuildings.add(buildingKey);
          });

      } catch (error) {
        console.error("지도 데이터 불러오기 실패:", error);
      }
    }

    if (typeof window !== "undefined" && !window.kakao) {
      const script = document.createElement("script");
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?appkey=ef5a7106c705f2615719a8d6aed1a2ad&libraries=services&autoload=false";
      script.async = true;
      document.head.appendChild(script);
      script.onload = () => window.kakao.maps.load(initMap);
    } else {
      window.kakao.maps.load(initMap);
    }
  }, []);

  return <div ref={mapContainer} style={{ width: "100%", height: "600px" }}></div>;
};

export default Map;
