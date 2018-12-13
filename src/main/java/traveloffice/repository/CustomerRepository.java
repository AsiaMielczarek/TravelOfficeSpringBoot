package traveloffice.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import traveloffice.entity.Customer;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

}
