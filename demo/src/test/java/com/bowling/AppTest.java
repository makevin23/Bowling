package com.bowling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class AppTest {

    Line line;

    @Before
    public void init(){
        line = new Line();
        line.initLine();
    }

    @Test
    public void testAllStrike() throws TryOutOfLineException, InvalidScoreException {
        
        int[] scores = { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };
        for (int score : scores) {
            try {
                line.startTry(score);
            } catch (EndOfLineException e) {
                assertTrue(false);
            }
        }
        assertEquals(10, line.getCurrentFrame());
        assertEquals(2, line.getRemainingBonus());
        assertEquals(270, line.getScore());

        // bonus 1
        try {
            line.startTry(10);
        } catch (EndOfLineException e) {
            assertTrue(false);
        }
        assertEquals(10, line.getCurrentFrame());
        assertEquals(1, line.getRemainingBonus());
        assertEquals(290, line.getScore());

        // bonus 2
        try {
            line.startTry(10);
        } catch (EndOfLineException e) {
            assertEquals(11, line.getCurrentFrame());
            assertEquals(0, line.getRemainingBonus());
        }
        assertEquals(300, line.getScore());
    }

    @Test
    public void testNineZero() throws TryOutOfLineException, InvalidScoreException {
        int[] scores = { 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0 };
        for (int score : scores) {
            try {
                line.startTry(score);
            } catch (EndOfLineException e) {
                assertEquals(11, line.getCurrentFrame());
                assertEquals(0, line.getRemainingBonus());
            }
        }
        assertEquals(90, line.getScore());
    }

    @Test
    public void testFiveSpare() throws TryOutOfLineException, InvalidScoreException {
        int[] scores = { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 };
        for (int score : scores) {
            try {
                line.startTry(score);
            } catch (EndOfLineException e) {
                assertTrue(false);
            }
        }

        assertEquals(145, line.getScore());
        assertEquals(10, line.getCurrentFrame());
        assertEquals(1, line.getRemainingBonus());

        // bonus
        try {
            line.startTry(5);
        } catch (EndOfLineException e) {
            assertEquals(11, line.getCurrentFrame());
            assertEquals(0, line.getRemainingBonus());
        }
        assertEquals(150, line.getScore());
    }

    @Test
    public void testAllZero() throws TryOutOfLineException, InvalidScoreException {
        int[] scores = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        for (int score : scores) {
            try {
                line.startTry(score);
            } catch (EndOfLineException e) {
                assertEquals(11, line.getCurrentFrame());
                assertEquals(0, line.getRemainingBonus());
            }
        }
        assertEquals(0, line.getScore());
    }

    @Test
    public void testNineOne() throws TryOutOfLineException, InvalidScoreException {
        int[] scores = { 9, 1, 9, 1, 9, 1, 9, 1, 9, 1, 9, 1, 9, 1, 9, 1, 9, 1, 9, 1 };
        for (int score : scores) {
            try {
                line.startTry(score);
            } catch (EndOfLineException e) {
                assertTrue(false);
            }
        }
        assertEquals(181, line.getScore());
        assertEquals(10, line.getCurrentFrame());
        assertEquals(1, line.getRemainingBonus());

        // bonus
        try {
            line.startTry(10);
        } catch (EndOfLineException e) {
            assertEquals(11, line.getCurrentFrame());
            assertEquals(0, line.getRemainingBonus());
        }
        assertEquals(191, line.getScore());
    }

    @Test 
    public void testZeroSpare() throws TryOutOfLineException, InvalidScoreException {
        int[] scores = { 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10 };
        for (int score : scores) {
            try {
                line.startTry(score);
            } catch (EndOfLineException e) {
                assertTrue(false);
            }
        }
        assertEquals(100, line.getScore());
        assertEquals(10, line.getCurrentFrame());
        assertEquals(1, line.getRemainingBonus());

        // bonus
        try {
            line.startTry(0);
        } catch (EndOfLineException e) {
            assertEquals(11, line.getCurrentFrame());
            assertEquals(0, line.getRemainingBonus());
        }
        assertEquals(100, line.getScore());
    }

    @Test
    public void testAllMiss() throws TryOutOfLineException, InvalidScoreException {
        int[] scores = { 3, 5, 3, 5, 3, 5, 3, 5, 3, 5, 3, 5, 3, 5, 3, 5, 3, 5, 3, 5 };
        for (int score : scores) {
            try {
                line.startTry(score);
            } catch (EndOfLineException e) {}
        }
        assertEquals(11, line.getCurrentFrame());
        assertEquals(0, line.getRemainingBonus());
        assertEquals(80, line.getScore());
    }

    @Test
    public void testStrikeSpare() throws TryOutOfLineException, InvalidScoreException {
        int[] scores = { 10, 9, 1, 10, 7,3, 10,2,8,10,5,5,10,1,9 };
        for (int score : scores) {
            try {
                line.startTry(score);
            } catch (EndOfLineException e) {
                assertTrue(false);
            }
        }
        assertEquals(190, line.getScore());
        assertEquals(10, line.getCurrentFrame());
        assertEquals(1, line.getRemainingBonus());

        // bonus
        try {
            line.startTry(10);
        } catch (EndOfLineException e) {
            assertEquals(11, line.getCurrentFrame());
            assertEquals(0, line.getRemainingBonus());
        }
        assertEquals(200, line.getScore());
    }


    @Test(expected = TryOutOfLineException.class)
    public void testTooMuchTries() throws TryOutOfLineException, EndOfLineException, InvalidScoreException {
        int[] scores = { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 };
        for (int score : scores) {
            try {
                line.startTry(score);
            } catch (EndOfLineException e) {
                assertTrue(false);
            }
        }
        assertEquals(145, line.getScore());
        assertEquals(10, line.getCurrentFrame());
        assertEquals(1, line.getRemainingBonus());

        // bonus
        try {
            line.startTry(5);
        } catch (EndOfLineException e) {
            assertEquals(11, line.getCurrentFrame());
            assertEquals(0, line.getRemainingBonus());
        }
        assertEquals(150, line.getScore());

        // a try out of line
        line.startTry(5);
    }

    @Test(expected = InvalidScoreException.class)
    public void testInvalidFrame() throws TryOutOfLineException, EndOfLineException, InvalidScoreException {
        int[] scores = { 5, 3, 5, 10 };
        for (int score : scores) {
            line.startTry(score);
        }
    }
}
