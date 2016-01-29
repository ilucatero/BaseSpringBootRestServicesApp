package com.lucaterori.repositories;

import com.lucaterori.domains.Place;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends MongoRepository<Place, Long> {
    Place findByShortName(String shortName);

}