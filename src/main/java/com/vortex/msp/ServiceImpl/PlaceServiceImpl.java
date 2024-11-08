package com.vortex.msp.ServiceImpl;

import com.vortex.msp.Entity.Place;
import com.vortex.msp.Mapper.PlaceMapper;
import com.vortex.msp.Service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {

    private final PlaceMapper placeMapper;

    @Autowired
    public PlaceServiceImpl(PlaceMapper placeMapper) {
        this.placeMapper = placeMapper;
    }

    @Override
    public List<Place> listPlaces() {
        List<Place> places = placeMapper.listPlaces();
        return places;
    }

    @Override
    public Place getPlace(Integer placeId) {
        Place place = placeMapper.getPlace(placeId);
        return place;
    }

    @Override
    public List<Place> getPlaceByName(String name) {
        List<Place> places = placeMapper.getPlaceByName(name);
        return places;
    }
}
