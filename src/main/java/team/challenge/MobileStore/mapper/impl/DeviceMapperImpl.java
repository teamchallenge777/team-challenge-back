package team.challenge.MobileStore.mapper.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import team.challenge.MobileStore.dto.DeviceDtoFull;
import team.challenge.MobileStore.dto.DeviceDtoShort;
import team.challenge.MobileStore.dto.DeviceGroupDto;
import team.challenge.MobileStore.dto.SameDeviceDto;
import team.challenge.MobileStore.mapper.DeviceMapper;
import team.challenge.MobileStore.mapper.ReviewMapper;
import team.challenge.MobileStore.model.Device;
import team.challenge.MobileStore.model.Review;
import team.challenge.MobileStore.model.Specification;
import team.challenge.MobileStore.model.SpecificationGroup;
import team.challenge.MobileStore.service.DeviceService;
import team.challenge.MobileStore.service.ReviewService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DeviceMapperImpl implements DeviceMapper {
    private final String COLOR = "Color";
    private final String MEMORY = "Internal memory";
    private final DeviceService deviceService;
    private final ReviewMapper reviewMapper;
    private final ReviewService reviewService;
    @Override
    public DeviceDtoFull mapToFullDto(@NonNull Device device) {
        String title = getTitle(device);
        List<Device> deviceServiceAllWithSameColor = deviceService.getAllWithSameColor(device.getId(), findInSpecification(device, COLOR));
        List<Device> devicesWithSameMemory = deviceService.getAllWithSameMemory(device.getId(), findInSpecification(device, MEMORY));
        return new DeviceDtoFull(device.getId(),
                title,
                device.getPrice(),
                device.getDiscount(),
                mapToSameDeviceMemoryDtoList(deviceServiceAllWithSameColor),
                mapToSameDeviceColorDtoList(devicesWithSameMemory),
                device.getUriPhotos(),
                device.getPresentations(),
                device.getSpecificationGroups(),
                null,
                null);
    }

    private String getTitle(Device device) {
        String color = findInSpecification(device, COLOR);
        String series = findInSpecification(device, "Series");
        String memory = findInSpecification(device, MEMORY);
        return String.format("%s %s %s %s (%s)", device.getBrand().getTitle(), series, memory, color, device.getSkuCode());
    }

    @Override
    public DeviceDtoShort mapToShortDto(@NonNull Device device) {

        // brand + series + internal memory + color + skuCode

        String title = getTitle(device);
        String color = findInSpecification(device, COLOR);
        String internalMemory = findInSpecification(device, MEMORY);
        List<Device> devicesAllWithSameMemory= deviceService.getAllWithSameMemory(device.getId(), internalMemory);
        List<Review> reviews = reviewService.getAllByDevice(device.getId());
        return new DeviceDtoShort(device.getId(), title, color, device.getUriMainPhoto(), reviewMapper.mapToReviewMarkDto(reviews), device.getPrice(), device.getDiscount(), mapToSameDeviceColorDtoList(devicesAllWithSameMemory));
    }

    @Override
    public List<DeviceDtoShort> mapToDeviceShortDtoList(List<Device> devices) {
        return devices.stream().map(this::mapToShortDto).toList();
    }

    @Override
    public List<SameDeviceDto> mapToSameDeviceColorDtoList(List<Device> devices) {
        return devices.stream().map(this::mapToSameDeviceColorDto).toList();
    }


    @Override
    public SameDeviceDto mapToSameDeviceColorDto(Device device) {
        return new SameDeviceDto(device.getId(), findInSpecification(device, COLOR));
    }

    @Override
    public List<SameDeviceDto> mapToSameDeviceMemoryDtoList(List<Device> devices) {
        return devices.stream().map(this::mapToSameDeviceMemoryDto).toList();
    }

    @Override
    public SameDeviceDto mapToSameDeviceMemoryDto(Device device) {
        return new SameDeviceDto(device.getId(), findInSpecification(device, MEMORY));
    }

    @Override
    public List<DeviceGroupDto> mapToDeviceList(Map<String, List<Device>> deviceMap) {
        List<DeviceGroupDto> resultList = new ArrayList<>();
        for (Map.Entry<String, List<Device>> entry:
                deviceMap.entrySet()){
            resultList.add(
                    new DeviceGroupDto(
                            entry.getKey(),
                            mapToDeviceShortDtoList(entry.getValue())
                    )
            );
        }
        return resultList;
    }

    private String findInSpecification(Device device, String specificationTitle){
        List<Specification> specifications = new ArrayList<>();
        for (SpecificationGroup group :
                device.getSpecificationGroups()) {
            specifications.addAll(group.getSpecifications());
        }
        return specifications.stream().filter(specification -> specification.getTitle().equals(specificationTitle)).map(Specification::getValue).collect(Collectors.joining());
    }

    @Override
    public Page<DeviceDtoShort> mapToDeviceDtoShortPage(Page<Device> devices) {
        return devices.map(this::mapToShortDto);
    }
}
