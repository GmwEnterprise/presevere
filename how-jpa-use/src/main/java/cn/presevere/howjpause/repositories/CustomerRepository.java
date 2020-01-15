package cn.presevere.howjpause.repositories;

import cn.presevere.howjpause.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
