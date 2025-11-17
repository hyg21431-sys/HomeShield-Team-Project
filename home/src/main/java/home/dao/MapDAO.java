package home.dao;

import home.dto.MapDTO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MapDAO {

    @Select("SELECT * FROM lease WHERE house_type = 'APT'")
    List<MapDTO> selectAptList();

    @Select("SELECT * FROM lease WHERE house_type = 'OP'") 
    List<MapDTO> selectOpList();

    @Select("SELECT * FROM lease WHERE house_type = 'MLT'") 
    List<MapDTO> selectMltList();

    @Select("SELECT * FROM lease WHERE house_type = 'SNG'") 
    List<MapDTO> selectSngList();

    @Select("SELECT * FROM lease") 
    List<MapDTO> selectAll();

    // 위도/경도 없는 row만
    @Select("SELECT * FROM lease WHERE latitude IS NULL OR longitude IS NULL")
    List<MapDTO> selectUnGeocoded();

    @Update("UPDATE lease SET latitude = #{latitude}, longitude = #{longitude}, geocoded = true WHERE id = #{id}")
    void updateCoordinates(@Param("id") Long id,
                           @Param("latitude") double latitude,
                           @Param("longitude") double longitude);
}
