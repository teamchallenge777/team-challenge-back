package team.challenge.MobileStore.service;

import lombok.NonNull;
import team.challenge.MobileStore.dto.CustomerRequest;
import team.challenge.MobileStore.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAll();
    Customer getOneById(@NonNull final String id);
    Customer getOneByPhoneNumber(@NonNull final String phoneNumber);
    Customer getOneByEmail(@NonNull final String email);
    Customer create(@NonNull final CustomerRequest customerRequest);
    Customer update(@NonNull final String id, @NonNull final CustomerRequest customerRequest);
    void delete(@NonNull final String id);
}
