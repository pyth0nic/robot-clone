package org.alio.robot.model.simulation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javatuples.Pair;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class World {
    public Map<Pair<Integer,Integer>,Point> points;
    private List<Robot> robots;
}
