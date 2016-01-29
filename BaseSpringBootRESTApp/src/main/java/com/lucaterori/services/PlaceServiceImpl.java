package com.lucaterori.services;

import com.lucaterori.domains.Place;
import com.lucaterori.repositories.PlaceRepository;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service(value = "PlaceService")
@EnableMongoRepositories("com.lucaterori.repositories")
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;


    @Override
    public Collection<Place> getAllPlaces() {
        return IteratorUtils.toList(this.placeRepository.findAll().iterator());
    }
    @Override
    public Place getPlaceById(Long id) {
        return this.placeRepository.findOne(id);
    }
    @Override
    public Place createPlace(Place place) {
        return this.placeRepository.save(place);
    }
    @Override
    public Place updatePlace(Place place) {
        return this.placeRepository.save(place);
    }
    @Override
    public void deletePlace(Long id) {
        this.placeRepository.delete(id);
    }
    @Override
    public Place getPlaceByShortName(String shortName) {
        return this.placeRepository.findByShortName(shortName);
    }


}
