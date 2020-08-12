package org.alio.robot.model.simulation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Point {
    private Integer x;
    private Integer y;
    @JsonIgnore
    private Portal z;

    public static Boolean eq(Point p1, Point p2) {
        return p1.x == p2.x && p1.y == p2.y;
    }

    public static Boolean isValidPoint(Point p, int dimension) {
        return p.x >= 0 && p.y >= 0 &&
               p.x <= dimension - 1 && p.y <= dimension - 1;
    }

    public Point clone() {
        return Point.builder()
                .x(x)
                .y(y)
                .build();
    }
}
