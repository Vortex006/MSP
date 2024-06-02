package com.zyj.msp.Controller;

import com.zyj.msp.Entity.Place;
import com.zyj.msp.Service.PlaceService;
import com.zyj.msp.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/place")
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

    @GetMapping("/{placeName}")
    public Result getPlaceByName(@PathVariable("placeName") String placeName) {
        return Result.SUCCEED();

    }


}
