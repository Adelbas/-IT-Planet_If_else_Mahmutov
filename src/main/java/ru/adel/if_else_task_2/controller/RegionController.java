package ru.adel.if_else_task_2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.adel.api.RegionApi;
import ru.adel.if_else_task_2.public_interface.region.RegionMapper;
import ru.adel.if_else_task_2.public_interface.region.RegionService;
import ru.adel.if_else_task_2.public_interface.region.dto.GetRegionResponseDto;
import ru.adel.if_else_task_2.public_interface.region.dto.RegionResponseDto;
import ru.adel.model.RegionRequest;
import ru.adel.model.RegionResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RegionController implements RegionApi {

    private final RegionService regionService;

    private final RegionMapper regionMapper;

    @Override
    public ResponseEntity<RegionResponse> createRegion(RegionRequest regionRequest) {
        log.info("Handle create region request: {}", regionRequest);

        RegionResponseDto regionResponseDto = regionService.createRegion(regionMapper.toRegionRequestDto(regionRequest));

        RegionResponse regionResponse = regionMapper.toRegionResponse(regionResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(regionResponse);
    }

    @Override
    public ResponseEntity<RegionResponse> getRegion(Long regionId) {
        log.info("Handle get region with id {} request", regionId);

        GetRegionResponseDto getRegionResponseDto = regionService.getRegion(regionId);

        RegionResponse regionResponse = regionMapper.toRegionResponse(getRegionResponseDto);

        return ResponseEntity.status(HttpStatus.OK).body(regionResponse);
    }

    @Override
    public ResponseEntity<RegionResponse> updateRegion(Long regionId, RegionRequest regionRequest) {
        log.info("Handle update region with id {} request: {}", regionId, regionRequest);

        RegionResponseDto regionResponseDto = regionService.updateRegion(
                regionMapper.toUpdateRegionRequestDto(
                        regionId,
                        regionRequest
                )
        );

        RegionResponse regionResponse = regionMapper.toRegionResponse(regionResponseDto);

        return ResponseEntity.status(HttpStatus.OK).body(regionResponse);
    }

    @Override
    public ResponseEntity<RegionResponse> deleteRegion(Long regionId) {
        log.info("Handle delete region with id {} request", regionId);

        regionService.deleteRegion(regionId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
