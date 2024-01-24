package team.challenge.MobileStore.dto;

import team.challenge.MobileStore.model.Address;

import java.util.Set;

public record CustomerRequest(
        String firstname,
        String lastname,
        String email,
        String phoneNumber,
        Set<Address> addresses
) {
}
