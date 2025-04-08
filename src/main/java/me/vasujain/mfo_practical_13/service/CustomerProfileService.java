package me.vasujain.mfo_practical_13.service;

import me.vasujain.mfo_practical_13.model.CustomerProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerProfileService {
    CustomerProfile saveCustomer(CustomerProfile customerProfile);

    Optional<CustomerProfile> getCustomerById(UUID id);

    void deleteCustomer(UUID id);

    List<CustomerProfile> getCustomersByLocation(String location);

    List<CustomerProfile> searchCustomersByName(String keyword);

    List<CustomerProfile> getCustomersByEmailDomain(String domain);

    Page<CustomerProfile> getAllCustomersPaginated(Pageable pageable);
}