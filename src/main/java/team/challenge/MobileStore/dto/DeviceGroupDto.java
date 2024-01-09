package team.challenge.MobileStore.dto;

import java.util.List;

public record DeviceGroupDto(
        String title,
        List<DeviceDtoShort> devices
) {
}
