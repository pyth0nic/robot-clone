package org.alio.robot.model;

import lombok.Data;

import java.util.List;

@Data
public class Agent {
    private Integer x;

    private Integer y;

    private List<String> moves;
}
