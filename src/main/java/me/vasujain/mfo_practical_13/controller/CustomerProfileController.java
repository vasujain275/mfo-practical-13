package me.vasujain.mfo_practical_13.controller;

import me.vasujain.mfo_practical_13.model.CustomerProfile;
import me.vasujain.mfo_practical_13.response.ApiResponse;
import me.vasujain.mfo_practical_13.service.CustomerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customer")
public class CustomerProfileController {
    private final CustomerProfileService customerProfileService;

    @Autowired
    public CustomerProfileController(CustomerProfileService customerProfileService) {
        this.customerProfileService = customerProfileService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerProfile>> createCustomer(@RequestBody CustomerProfile customerProfile) {
        CustomerProfile savedCustomer = customerProfileService.saveCustomer(customerProfile);

        ApiResponse<CustomerProfile> response = ApiResponse.<CustomerProfile>builder()
                .status(HttpStatus.CREATED)
                .message("Customer profile created successfully")
                .data(savedCustomer)
                .timestamp(LocalDate.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerProfile>> getCustomerById(@PathVariable UUID id) {
        return customerProfileService.getCustomerById(id)
                .map(customer -> {
                    ApiResponse<CustomerProfile> response = ApiResponse.<CustomerProfile>builder()
                            .status(HttpStatus.OK)
                            .message("Customer profile retrieved successfully")
                            .data(customer)
                            .timestamp(LocalDate.now())
                            .build();
                    return new ResponseEntity<>(response, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(
                        ApiResponse.<CustomerProfile>builder()
                                .status(HttpStatus.NOT_FOUND)
                                .message("Customer profile not found")
                                .timestamp(LocalDate.now())
                                .build(),
                        HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(@PathVariable UUID id) {
        if (customerProfileService.getCustomerById(id).isPresent()) {
            customerProfileService.deleteCustomer(id);

            ApiResponse<Void> response = ApiResponse.<Void>builder()
                    .status(HttpStatus.OK)
                    .message("Customer profile deleted successfully")
                    .timestamp(LocalDate.now())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<Void> response = ApiResponse.<Void>builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("Customer profile not found")
                    .timestamp(LocalDate.now())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<ApiResponse<List<CustomerProfile>>> getCustomersByLocation(@PathVariable String location) {
        List<CustomerProfile> customers = customerProfileService.getCustomersByLocation(location);

        ApiResponse<List<CustomerProfile>> response = ApiResponse.<List<CustomerProfile>>builder()
                .status(HttpStatus.OK)
                .message("Customer profiles retrieved by location")
                .data(customers)
                .timestamp(LocalDate.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CustomerProfile>>> searchCustomersByName(@RequestParam String name) {
        List<CustomerProfile> customers = customerProfileService.searchCustomersByName(name);

        ApiResponse<List<CustomerProfile>> response = ApiResponse.<List<CustomerProfile>>builder()
                .status(HttpStatus.OK)
                .message("Customer profiles retrieved by name search")
                .data(customers)
                .timestamp(LocalDate.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/domain")
    public ResponseEntity<ApiResponse<List<CustomerProfile>>> getCustomersByEmailDomain(@RequestParam String domain) {
        List<CustomerProfile> customers = customerProfileService.getCustomersByEmailDomain(domain);

        ApiResponse<List<CustomerProfile>> response = ApiResponse.<List<CustomerProfile>>builder()
                .status(HttpStatus.OK)
                .message("Customer profiles retrieved by email domain")
                .data(customers)
                .timestamp(LocalDate.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CustomerProfile>>> getPaginatedCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "fullName") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<CustomerProfile> customerPage = customerProfileService.getAllCustomersPaginated(pageable);

        ApiResponse<Page<CustomerProfile>> response = ApiResponse.<Page<CustomerProfile>>builder()
                .status(HttpStatus.OK)
                .message("Paginated customer profiles retrieved successfully")
                .data(customerPage)
                .timestamp(LocalDate.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}