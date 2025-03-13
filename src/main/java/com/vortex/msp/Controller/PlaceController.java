package com.vortex.msp.Controller;

import com.vortex.msp.Entity.Place;
import com.vortex.msp.Service.PlaceService;
import com.vortex.msp.Utils.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/place")
@Tag(name = "地址模块")
public class PlaceController {

    private final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/list")
    public Result getPlace() {
        List<Place> places = placeService.listPlaces();
        return Result.SUCCEED(places);
    }

    @GetMapping("/name/{placeName}")
    public Result getPlaceByName(@PathVariable("placeName") String placeName) {
        List<Place> places = placeService.getPlaceByName(placeName);
        return Result.SUCCEED(places);
    }

    @GetMapping("/{placeId}")
    public Result getPlace(@PathVariable("placeId") Integer placeId) {
        Place place = placeService.getPlace(placeId);
        return Result.SUCCEED(place);
    }


}
