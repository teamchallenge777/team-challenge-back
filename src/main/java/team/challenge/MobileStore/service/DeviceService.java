package team.challenge.MobileStore.service;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import team.challenge.MobileStore.dto.DeviceRequest;
import team.challenge.MobileStore.model.*;

import java.util.List;
import java.util.Map;

public interface DeviceService {
    Page<Device> getAll(Map<String, String> params);
    List<Device> getAllFromCatalogueAndBrand(@NonNull final Catalogue catalogue, @NonNull final Brand brand);
    List<Device> getAllWithSameColor(@NonNull final String deviceId, @NonNull final String color);
    List<Device> getAllWithSameMemory(@NonNull final String deviceId, @NonNull final String internalMemory);
    Device getOne(@NonNull final String deviceId);
    Device create(@NonNull final DeviceRequest deviceRequest);
    Device update(@NonNull final String deviceId, @NonNull final DeviceRequest deviceRequest);
    void delete(@NonNull final String deviceId);
    void addReviewToDevices(@NonNull final Review review, List<Device> devices);
    void deleteReviewFromDevices(Review review);

    void addQuestionToDevices(@NonNull Question question, List<Device> devices);

    void deleteQuestionFromDevices(Question question);
    Map<String , List<Device>> getGroupedDevices();
}
