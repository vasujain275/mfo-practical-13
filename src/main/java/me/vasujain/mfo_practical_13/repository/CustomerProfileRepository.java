package me.vasujain.mfo_practical_13.repository;

import me.vasujain.mfo_practical_13.model.CustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, UUID> {
    List<CustomerProfile> findByLocation(String location);

    List<CustomerProfile> findByFullNameContaining(String keyword);

    @Query("SELECT c FROM CustomerProfile c WHERE c.email LIKE %:domain%")
    List<CustomerProfile> findByEmailDomain(@Param("domain") String domain);
}
