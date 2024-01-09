package team.challenge.MobileStore.dto;

import team.challenge.MobileStore.model.SpecificationGroup;

import java.util.List;
import java.util.Map;

public record DeviceDtoFull(
        String id,
        String title,
        Integer price,
        Integer discount,
        List<SameDeviceDto> sameDevices,
        List<SameDeviceDto> sameMemories,
        List<String> photoUris,
        List<DevicePresentation> presentation,
        List<SpecificationGroup> specificationGroups,
        List<DeviceDtoShort> recommendations,
        Map<String, List<DeviceDtoShort>> accessories
) {
}
