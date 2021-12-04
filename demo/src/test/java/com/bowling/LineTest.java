package com.bowling;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LineTest {

    @Test(expected = InvalidScoreException.class)
    public void testInvalidScore11() throws InvalidScoreException {
        Line.checkValidScore(11);
    }

    @Test(expected = InvalidScoreException.class)
    public void testInvalidScoreMinus1() throws InvalidScoreException {
        Line.checkValidScore(-1);
    }

    @Test
    public void testBonusTry1() throws TryOutOfLineException, EndOfLineException, InvalidScoreException {
        Line line = new Line();
        line.initLine();
        int[] scores = { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 };
        for (int score : scores) {
            line.startTry(score);
        }
        assertEquals(145, line.getScore());
        assertEquals(1, line.getRemainingBonus());

        try {
            line.startTry(7);
        } catch (EndOfLineException e) {
            assertEquals(152, line.getScore());
            assertEquals(0, line.getRemainingBonus());
        }
    }

    @Test
    public void testBonusTry2() throws TryOutOfLineException, EndOfLineException, InvalidScoreException {
        Line line = new Line();
        line.initLine();
        int[] scores = { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 10 };
        for (int score : scores) {
            line.startTry(score);
        }
        assertEquals(150, line.getScore());
        assertEquals(2, line.getRemainingBonus());

        try {
            line.startTry(7);
        } catch (EndOfLineException e) {
            assertEquals(157, line.getScore());
            assertEquals(1, line.getRemainingBonus());
        }
        try {
            line.startTry(10);
        } catch (Exception e) {
            assertEquals(167, line.getScore());
            assertEquals(0, line.getRemainingBonus());
        }
    }

    @Test(expected = TryOutOfLineException.class)
    public void testTooMuchBonusTries() throws TryOutOfLineException, EndOfLineException, InvalidScoreException {
        Line line = new Line();
        line.initLine();
        int[] scores = { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 10 };
        for (int score : scores) {
            line.startTry(score);
        }
        assertEquals(150, line.getScore());
        assertEquals(2, line.getRemainingBonus());

        try {
            line.startTry(7);
        } catch (EndOfLineException e) {
            assertEquals(157, line.getScore());
            assertEquals(1, line.getRemainingBonus());
        }
        try {
            line.startTry(10);
        } catch (Exception e) {
            assertEquals(167, line.getScore());
            assertEquals(0, line.getRemainingBonus());
        }

        line.startTry(5); // 3. bonus try
    }
}
