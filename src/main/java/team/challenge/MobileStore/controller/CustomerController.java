package team.challenge.MobileStore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import team.challenge.MobileStore.dto.CustomerRequest;
import team.challenge.MobileStore.model.Customer;
import team.challenge.MobileStore.service.CustomerService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user/{u_id}/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<?> getAll(@PathVariable(name = "u_id") String userId){
        return customerService.getAll();
    }

    @GetMapping("/{c_id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or authentication.principal.id == #userId")
    public ResponseEntity<?> getOneCustomer(@PathVariable(name = "u_id") String userId,
                                            @PathVariable(name = "c_id") String customerId
    ){
        return ResponseEntity.ok(customerService.getOneById(customerId));
    }
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createCustomer(@PathVariable(name = "u_id") String userId,
                                            @RequestBody CustomerRequest customerRequest){
        Customer customer = customerService.create(customerRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(customer.getId())
                .toUri();
        return ResponseEntity.created(location).body(customer);
    }

    @PutMapping("/{c_id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or authentication.principal.id == #userId")
    public ResponseEntity<?> updateCustomer(@PathVariable(name = "u_id") String userId,
                                            @PathVariable(name = "c_id") String customerId,
                                            @RequestBody CustomerRequest customerRequest){
        return ResponseEntity.ok(customerService.update(customerId, customerRequest));
    }
    @DeleteMapping("/{c_id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteCustomer(@PathVariable(name = "u_id") String userId,
                                            @PathVariable(name = "c_id") String customerId
    ){
        customerService.delete(customerId);
        return ResponseEntity.noContent().build();
    }
}
