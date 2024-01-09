package team.challenge.MobileStore.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import team.challenge.MobileStore.dto.DevicePresentation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Model for device with all information about device.
 */
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class
Device {


    /**
     * id - unique device's identifier.
     */
    @Id
    private String id;
    /**
     * uriMainPhoto - main photo of device.
     */
    private String uriMainPhoto;
    /**
     * uriPhotos - other photos of device.
     */
    private List<String> uriPhotos;
    /**
     * price - price of device.
     */
    private Integer price;
    /**
     * discount - discount for device from 0-100. Where 0 - means there isn't any discount for device.
     */
    private Integer discount;
    /**
     * barCode - unique device's barCode, it is often written at the end of the name of device. This barcode is craeted by the company of the device.
     */
    private String skuCode;
    /**
     *
     */
    private List<DevicePresentation> presentations;
    /**
     * specificationGroups - set of specifications of the phone.
     */
    private List<SpecificationGroup> specificationGroups;
    /**
     * quantity - number of phones in stock.
     */
    private Integer quantity;

    /**
     * brand - Device's brand in the real world.
     */
    @DocumentReference
    private Brand brand;
    /**
     * catalog - Device's place in the main catalog where it stores.
     */
    @DocumentReference
    private Catalogue catalogue;
    /**
     * reviews - it is attached to the device.
     */
    @DocumentReference
    private List<Review> reviews;

    /**
     * questions -
     */
    @DocumentReference
    private List<Question> questions;

    private LocalDateTime creatingDate;
    private Boolean isLeader;

    public String getSpecificationValue(String specificationTitle){
        List<Specification> specifications = new ArrayList<>();
        for (SpecificationGroup group: this.specificationGroups){
            specifications.addAll(group.getSpecifications());
        }
        return specifications.stream().filter(specification -> specification.getTitle().equals(specificationTitle)).map(Specification::getValue).collect(Collectors.joining());
    }
}