package cn.presevere.next.repo;

import cn.presevere.next.domain.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Override
    Optional<Customer> findById(Long id);

    List<Customer> findByLastName(String lastName);
}
