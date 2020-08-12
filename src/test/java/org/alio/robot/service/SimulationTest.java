package org.alio.robot.service;

import org.alio.robot.model.simulation.Robot;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import org.alio.robot.model.simulation.*;


class SimulationTest {

    @Test
    void makeRobot_moves_to_opposite_side_for_x_L() {
        var robot =
                Robot.builder().point(
                        Point.builder()
                        .x(0).y(4).build()
                ).build();
        var newPoint = Simulation.makeMove(robot, MOVE.L, 10);
        Assert.assertEquals(Point.builder().x(9).y(4).build(), newPoint);
    }

    @Test
    void makeRobot_moves_to_opposite_side_for_x_R() {
        var robot =
                Robot.builder().point(
                        Point.builder()
                                .x(9).y(0).build()
                ).build();
        var newPoint = Simulation.makeMove(robot, MOVE.R, 10);
        Assert.assertEquals(Point.builder().x(0).y(0).build(), newPoint);
    }

    @Test
    void makeRobot_moves_to_opposite_side_for_x_U() {
        var robot =
                Robot.builder().point(
                        Point.builder()
                                .x(0).y(0).build()
                ).build();
        var newPoint = Simulation.makeMove(robot, MOVE.U, 10);
        Assert.assertEquals(Point.builder().x(0).y(9).build(), newPoint);
    }

    @Test
    void makeRobot_moves_to_opposite_side_for_y_D() {
        var robot =
                Robot.builder().point(
                        Point.builder()
                                .x(0).y(9).build()
                ).build();
        var newPoint = Simulation.makeMove(robot, MOVE.D, 10);
        Assert.assertEquals(Point.builder().x(0).y(0).build(), newPoint);
    }

    @Test
    void makeRobot_moves_to_normaly_for_valid_points() {
        var robot =
                Robot.builder().point(
                        Point.builder()
                                .x(1).y(1).build()
                ).build();
        var newPointU = Simulation.makeMove(robot, MOVE.U, 10);
        var newPointD = Simulation.makeMove(robot, MOVE.D, 10);
        var newPointL = Simulation.makeMove(robot, MOVE.L, 10);
        var newPointR = Simulation.makeMove(robot, MOVE.R, 10);

        Assert.assertEquals(Point.builder().x(1).y(2).build(), newPointD);
        Assert.assertEquals(Point.builder().x(1).y(0).build(), newPointU);
        Assert.assertEquals(Point.builder().x(0).y(1).build(), newPointL);
        Assert.assertEquals(Point.builder().x(2).y(1).build(), newPointR);
    }
}