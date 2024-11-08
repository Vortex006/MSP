package com.vortex.msp.Mapper;

import com.vortex.msp.Entity.Place;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlaceMapper {

    /**
     * 获取所有地址信息
     *
     * @return 地址信息集合
     */
    List<Place> listPlaces();

    /**
     * 根据地址id获取地址信息
     *
     * @param placeId 地址id
     * @return 地址信息
     */
    Place getPlace(Integer placeId);

    /**
     * 根据地址名称获取地址信息
     *
     * @param name 地址名称
     * @return 地址信息
     */
    List<Place> getPlaceByName(String name);
}
