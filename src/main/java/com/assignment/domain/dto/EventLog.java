package com.assignment.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventLog {

    private String id;

    private String state;

    private String type;

    private String host;

    private long timestamp;
}