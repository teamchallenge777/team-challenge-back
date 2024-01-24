package team.challenge.MobileStore.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.challenge.MobileStore.dto.CustomerRequest;
import team.challenge.MobileStore.exception.ModelAlreadyExistException;
import team.challenge.MobileStore.exception.ModelNotFoundException;
import team.challenge.MobileStore.model.Customer;
import team.challenge.MobileStore.repositories.CustomerRepository;
import team.challenge.MobileStore.service.CustomerService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getOneById(@NonNull String id) {
        return customerRepository.findById(id).orElseThrow(() -> new ModelNotFoundException(String.format("Customer with id: %s not found!", id)));
    }

    @Override
    public Customer getOneByPhoneNumber(@NonNull String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new ModelNotFoundException(String.format("Customer with phone number: %s not found!", phoneNumber)));
    }

    @Override
    public Customer getOneByEmail(@NonNull String email) {
        return customerRepository.findByEmail(email).orElseThrow(() -> new ModelNotFoundException(String.format("Customer with email: %s not found!", email)));
    }

    @Override
    public Customer create(@NonNull CustomerRequest customerRequest) {
        Optional<Customer> customerFromDb = customerRepository.findByEmail(customerRequest.email());
        if (customerFromDb.isPresent()){
            throw new ModelAlreadyExistException("Customer with present email is already exist!");
        }
        customerFromDb = customerRepository.findByPhoneNumber(customerRequest.phoneNumber());
        if (customerFromDb.isPresent()){
            throw new ModelAlreadyExistException("Customer with present email is already exist!");
        }
        Customer newCustomer = Customer.builder()
                .firstname(customerRequest.firstname())
                .lastname(customerRequest.lastname())
                .email(customerRequest.email())
                .phoneNumber(customerRequest.phoneNumber())
                .addresses(customerRequest.addresses())
                .build();
        return customerRepository.save(newCustomer);
    }

    @Override
    public Customer update(@NonNull String id, @NonNull CustomerRequest customerRequest) {
        Customer customerToUpdate = getOneById(id);
        Optional<Customer> customerToCheck = customerRepository.findByEmail(customerRequest.email());
        if (customerToCheck.isPresent()){
            if (!customerToUpdate.getId().equals(customerToCheck.get().getId())){
                throw new ModelAlreadyExistException("This email is already in use!");
            }
        }
        customerToCheck = customerRepository.findByPhoneNumber(customerRequest.phoneNumber());
        if (customerToCheck.isPresent()){
            if (!customerToUpdate.getId().equals(customerToCheck.get().getId())){
                throw new ModelAlreadyExistException("This phone number is already in use!");
            }
        }
        customerToUpdate = Customer.builder()
                .firstname(customerRequest.firstname())
                .lastname(customerRequest.lastname())
                .email(customerRequest.email())
                .phoneNumber(customerRequest.phoneNumber())
                .addresses(customerRequest.addresses())
                .build();
        return customerRepository.save(customerToUpdate);
    }

    @Override
    public void delete(@NonNull String id) {
        customerRepository.delete(getOneById(id));
    }
}
