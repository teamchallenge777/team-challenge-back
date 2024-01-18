package team.challenge.MobileStore.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.challenge.MobileStore.dto.DeviceDtoFull;
import team.challenge.MobileStore.dto.DeviceDtoShort;
import team.challenge.MobileStore.dto.DeviceGroupDto;
import team.challenge.MobileStore.exception.ApiError;
import team.challenge.MobileStore.mapper.DeviceMapper;
import team.challenge.MobileStore.service.DeviceService;

import java.util.List;
import java.util.Map;

/**
 * Main controller to get all devices, create, update, delete
 */
@Tag(name = "Device endpoints", description = "HTTP device methods")
@RestController
@RequestMapping("/api/v1/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;
    private final DeviceMapper deviceMapper;


    /**
     * @param id - unique id of device
     * @return - json file with device by his id.
     */

    @GetMapping("/{id}")
    @Operation(summary = "Get one Device by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Found one device by ID",
            content = {
                    @Content(mediaType = "application/json",
                    schema = @Schema(implementation = DeviceDtoFull.class))
            }),
            @ApiResponse(
                    responseCode = "404",
            description = "Device with present ID not found!",
            content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))
            })
    })
    public ResponseEntity<DeviceDtoFull> getDeviceById(@PathVariable String id) {
        return ResponseEntity.ok(deviceMapper.mapToFullDto(deviceService.getOne(id)));
    }


    @GetMapping()
    public Page<DeviceDtoShort> getAll(
            @RequestParam
                    @Parameter(name = "params", examples = {@ExampleObject(name = "Map desc", value = """
                            {
                                'key': 'value'
                            }
                            """, description = "Map structure."),
                    @ExampleObject(name = "Params example" , value = """
                            {
                                'catalogue': 'catalogueId'
                                'brand': 'brandId'
                                'series': 'series value'
                            }""",
                    description = "Example with parameters.")},
                    description = "Parameters that are necessary for the operation of the catalog and filters for obtaining the desired list of devices. " +
                            "If you do not specify the parameters, you will receive all the devices that are in the database.",
                    required = false
                    )
            Map<String, String> param)
            {
                return deviceMapper.mapToDeviceDtoShortPage(deviceService.getAll(param));

    }

    /**
     * @param id - unique id of phone
     * @return status of action
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete one Device by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Delete one device by ID"
                    ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Device with present ID not found!",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))
                    })
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteDeviceById(@PathVariable String id) {
        deviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Get device list for main page.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Get grouped list of devices",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DeviceGroupDto.class))
                    })
    })
    @GetMapping("/main-page")
    public List<DeviceGroupDto> geDevicesForMainPage(){
        return deviceMapper.mapToDeviceList(deviceService.getGroupedDevices());
    }

//    /**
//     * @param deviceRequest - information about phone that was put by user
//     * @return json file with all information about created phone
//     */
//    @PostMapping
//    public ResponseEntity<DeviceDtoFull> createDevice(@RequestBody DeviceRequest deviceRequest) {
//        return ResponseEntity.ok(this.deviceService.create(deviceRequest));
//    }
//
//    /**
//     * @param id - unique id of phone
//     * @param deviceRequest - updated device
//     * @return json file with all information about updated phone
//     */
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateDeviceById(@PathVariable String id, @RequestBody DeviceRequest deviceRequest) {
//        return ResponseEntity.ok(this.deviceService.update(id, deviceRequest));
//    }
}