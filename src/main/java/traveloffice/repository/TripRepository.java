package traveloffice.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import traveloffice.entity.Trip;

public interface TripRepository extends PagingAndSortingRepository<Trip, Long> {

}
