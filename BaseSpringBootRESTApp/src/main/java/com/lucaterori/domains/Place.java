package com.lucaterori.domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Place {

    @Id
    private Long id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String shortName;
    @JsonProperty
    private String coordinates;

    //Constructors, getters and setters are not shown here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!getClass().isInstance(obj)) {
            return false;
        }
        Place foo = (Place) obj;
        return this.getId().equals(((Place) obj).getId());
    }

    @Override
    public int hashCode() {
        return (getId() + getName()).hashCode();
    }

    @Override
    public String toString() {
        return "Place{ Id:"+ this.getId() +
                    ",Name:"+ this.getName() +
                    ",ShortName:"+ this.getShortName() +
                    ",Coordinates:"+ this.getCoordinates()+
                +'}';
    }

}
