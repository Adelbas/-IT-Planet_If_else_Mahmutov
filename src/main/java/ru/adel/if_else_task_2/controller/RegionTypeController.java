package ru.adel.if_else_task_2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.adel.api.RegionTypeApi;
import ru.adel.if_else_task_2.public_interface.region_type.RegionTypeMapper;
import ru.adel.if_else_task_2.public_interface.region_type.RegionTypeService;
import ru.adel.if_else_task_2.public_interface.region_type.dto.RegionTypeResponseDto;
import ru.adel.model.RegionTypeRequest;
import ru.adel.model.RegionTypeResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RegionTypeController implements RegionTypeApi {

    private final RegionTypeService regionTypeService;

    private final RegionTypeMapper regionTypeMapper;

    @Override
    public ResponseEntity<RegionTypeResponse> createRegionType(RegionTypeRequest regionTypeRequest) {
        log.info("Handle create region type request: {}", regionTypeRequest);

        RegionTypeResponseDto regionTypeResponseDto = regionTypeService.createRegionType(regionTypeMapper.toRegionTypeRequestDto(regionTypeRequest));

        RegionTypeResponse regionTypeResponse = regionTypeMapper.toRegionTypeResponse(regionTypeResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(regionTypeResponse);
    }

    @Override
    public ResponseEntity<RegionTypeResponse> getRegionType(Long typeId) {
        log.info("Handle get region type with id {} request", typeId);

        RegionTypeResponseDto regionTypeResponseDto = regionTypeService.getRegionType(typeId);

        RegionTypeResponse regionTypeResponse = regionTypeMapper.toRegionTypeResponse(regionTypeResponseDto);

        return ResponseEntity.status(HttpStatus.OK).body(regionTypeResponse);
    }

    @Override
    public ResponseEntity<RegionTypeResponse> updateRegionType(Long typeId, RegionTypeRequest regionTypeRequest) {
        log.info("Handle update region type with id {} request: {}", typeId, regionTypeRequest);

        RegionTypeResponseDto regionTypeResponseDto = regionTypeService.updateRegionType(
                regionTypeMapper.toUpdateRegionTypeRequestDto(
                        typeId,
                        regionTypeRequest
                )
        );

        RegionTypeResponse regionTypeResponse = regionTypeMapper.toRegionTypeResponse(regionTypeResponseDto);

        return ResponseEntity.status(HttpStatus.OK).body(regionTypeResponse);
    }


    @Override
    public ResponseEntity<RegionTypeResponse> deleteRegionType(Long typeId) {
        log.info("Handle delete region type with id {} request", typeId);

        regionTypeService.deleteRegionType(typeId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
