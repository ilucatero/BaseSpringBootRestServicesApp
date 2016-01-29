package com.lucaterori.controllers;

import com.lucaterori.domains.Place;
import com.lucaterori.services.PlaceService;
import com.lucaterori.services.PlaceServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;

@RestController @RequestMapping(value = "/places")
public class PlaceController {

    @Resource
    private PlaceService placeService;

    @RequestMapping(method = RequestMethod.POST)
    public Place createPlace(@RequestBody Place place) {

        return this.placeService.createPlace(place);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Place> getAllPlaces() {

        return this.placeService.getAllPlaces();
    }

    @RequestMapping(value = "/{shortName}", method = RequestMethod.GET)
    public Place getPlaceForShortName(@PathVariable(value = "shortName") String shortName) {

        //find place by shortname
        return this.placeService.getPlaceByShortName(shortName);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE) public void
    deletePlace(@PathVariable(value = "id") Long id) {
        this.placeService.deletePlace(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Place updatePlace(@PathVariable(value = "id") Long id, @RequestBody Place place) {
        place.setId(id);
        return this.placeService.updatePlace(place);
    }

    public PlaceService getPlaceService() {
        return placeService;
    }

    public void setPlaceService(PlaceServiceImpl placeService) {
        this.placeService = placeService;
    }

}
