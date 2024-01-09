package team.challenge.MobileStore.mapper;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import team.challenge.MobileStore.dto.DeviceDtoFull;
import team.challenge.MobileStore.dto.DeviceDtoShort;
import team.challenge.MobileStore.dto.DeviceGroupDto;
import team.challenge.MobileStore.dto.SameDeviceDto;
import team.challenge.MobileStore.model.Device;

import java.util.List;
import java.util.Map;

public interface DeviceMapper {
    DeviceDtoFull mapToFullDto(@NonNull Device device);
    DeviceDtoShort mapToShortDto(@NonNull Device device);
    List<DeviceDtoShort> mapToDeviceShortDtoList(List<Device> devices);
    Page<DeviceDtoShort> mapToDeviceDtoShortPage(Page<Device> devices);
    List<SameDeviceDto> mapToSameDeviceColorDtoList(List<Device> devices);
    List<SameDeviceDto> mapToSameDeviceMemoryDtoList(List<Device> devices);
    SameDeviceDto mapToSameDeviceColorDto(Device device);
    SameDeviceDto mapToSameDeviceMemoryDto(Device device);
    List<DeviceGroupDto> mapToDeviceList(Map<String, List<Device>> deviceMap);

}
