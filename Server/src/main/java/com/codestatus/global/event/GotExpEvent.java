package com.codestatus.global.event;

import com.codestatus.domain.status.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GotExpEvent {
    private Status status;
    private Integer exp;
}
