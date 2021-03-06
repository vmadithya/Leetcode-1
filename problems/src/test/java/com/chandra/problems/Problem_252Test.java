package com.chandra.problems;

import com.chandra.common.Interval;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Problem_252Test {

    Problem_252.Solution1 solution1;
    Interval[] input = new Interval[3];

    @Before
    public void setUp() throws Exception {
        solution1 = new Problem_252.Solution1();
        input[0] = new Interval(0, 30);
        input[1] = new Interval(5, 10);
        input[2] = new Interval(15, 20);
    }

    @Test
    public void solution1Test() {
        assertFalse(solution1.canAttendMeetings(input));
    }
}