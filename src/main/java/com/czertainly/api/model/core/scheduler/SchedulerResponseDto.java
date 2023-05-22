package com.czertainly.api.model.core.scheduler;

import com.czertainly.api.model.scheduler.SchedulerJobDto;
import com.czertainly.api.model.scheduler.SchedulerStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SchedulerResponseDto {

    private SchedulerStatus schedulerStatus;

    private String schedulerName;

    private List<SchedulerJobDto> schedulerDetailList;

    public SchedulerResponseDto(SchedulerStatus schedulerStatus) {
        this.schedulerStatus = schedulerStatus;
    }

    public SchedulerResponseDto(final SchedulerStatus schedulerStatus, final String schedulerName) {
        this.schedulerStatus = schedulerStatus;
        this.schedulerName = schedulerName;
    }
}
