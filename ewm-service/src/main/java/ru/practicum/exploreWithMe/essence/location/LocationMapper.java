package ru.practicum.exploreWithMe.essence.location;

public class LocationMapper {
    public static LocationDto toDto(Location location) {
        LocationDto dto = new LocationDto();
        dto.setLat(location.getLat());
        dto.setLon(location.getLon());
        return dto;
    }

    public static Location toLocation(LocationDto dto) {
        Location location = new Location();
        location.setLat(dto.getLat());
        location.setLon(dto.getLon());
        return location;
    }
}
