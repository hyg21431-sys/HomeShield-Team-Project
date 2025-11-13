package com.koreait.project.DAO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.koreait.project.DTO.MapDTO;



@Mapper
public interface MapDAO {
	
	// 좌표 조회
    @Select("select gu, dong, buildingName, lat, lng from building_coordinates where gu=#{gu} AND dong=#{dong} and buildingName=#{buildingName}")
    MapDTO selectCoordinates(@Param("gu") String gu,
                                @Param("dong") String dong,
                                @Param("buildingName") String buildingName);

    // 좌표 삽입
    @Insert("insert into building_coordinates (gu, dong, buildingName, lat, lng, create_at, update_at) " +
            "values (#{gu}, #{dong}, #{buildingName}, #{lat}, #{lng}, now(), now())")
    int insertCoordinates(MapDTO dto);

    // 좌표 업데이트
    @Update("update building_coordinates set lat=#{lat}, lng=#{lng}, update_at=now() " +
            "where gu=#{gu} and dong=#{dong} and buildingName=#{buildingName}")
    int updateCoordinates(MapDTO dto);

}
