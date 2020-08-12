package org.alio.robot.model.simulation;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Queue;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class Robot extends Agent {
    public Queue<MOVE> moveQueue;
}
