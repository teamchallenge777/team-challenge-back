package team.challenge.MobileStore.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Address {
    private String country;
    private String region;
    private String city;
    private String street;
    private String houseNumber;
    private String postalCode;
}