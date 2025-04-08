package me.vasujain.mfo_practical_13.service.impl;

import me.vasujain.mfo_practical_13.model.CustomerProfile;
import me.vasujain.mfo_practical_13.repository.CustomerProfileRepository;
import me.vasujain.mfo_practical_13.service.CustomerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerProfileServiceImpl implements CustomerProfileService {
    private final CustomerProfileRepository customerProfileRepository;

    @Autowired
    public CustomerProfileServiceImpl(CustomerProfileRepository customerProfileRepository) {
        this.customerProfileRepository = customerProfileRepository;
    }

    @Override
    public CustomerProfile saveCustomer(CustomerProfile customerProfile) {
        return customerProfileRepository.save(customerProfile);
    }

    @Override
    public Optional<CustomerProfile> getCustomerById(UUID id) {
        return customerProfileRepository.findById(id);
    }

    @Override
    public void deleteCustomer(UUID id) {
        customerProfileRepository.deleteById(id);
    }

    @Override
    public List<CustomerProfile> getCustomersByLocation(String location) {
        return customerProfileRepository.findByLocation(location);
    }

    @Override
    public List<CustomerProfile> searchCustomersByName(String keyword) {
        return customerProfileRepository.findByFullNameContaining(keyword);
    }

    @Override
    public List<CustomerProfile> getCustomersByEmailDomain(String domain) {
        return customerProfileRepository.findByEmailDomain(domain);
    }

    @Override
    public Page<CustomerProfile> getAllCustomersPaginated(Pageable pageable) {
        return customerProfileRepository.findAll(pageable);
    }
}