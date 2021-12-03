package com.bowling;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LineTest {
    @Test
    public void testGetScoreOfFrames(){}

    @Test (expected = InvalidScoreException.class)
    public void testInvalidScore11() throws InvalidScoreException{
        Line.checkValidScore(11);
    }

    @Test (expected = InvalidScoreException.class)
    public void testInvalidScoreMinus1() throws InvalidScoreException{
        Line.checkValidScore(-1);
    }

    @Test
    public void testBonus(){}

    @Test
    public void testBonusTry1() throws TryOutOfLineException{
        Line line = new Line();
        line.initLine();
        int[] scores = {5,5, 5,5, 5,5, 5,5, 5,5, 5,5, 5,5, 5,5, 5,5, 5,5};
        for(int score: scores){
            line.startTry(score);
        }
        assertEquals(145, line.getScore());
        assertEquals(1, line.getRemainingBonus());

        line.startTry(7);
        assertEquals(152, line.getScore());
        assertEquals(0, line.getRemainingBonus());
    }

    @Test
    public void testBonusTry2() throws TryOutOfLineException{
        Line line = new Line();
        line.initLine();
        int[] scores = {5,5, 5,5, 5,5, 5,5, 5,5, 5,5, 5,5, 5,5, 5,5, 10};
        for(int score: scores){
            line.startTry(score);
        }
        assertEquals(150, line.getScore());
        assertEquals(2, line.getRemainingBonus());

        line.startTry(7);
        assertEquals(157, line.getScore());
        assertEquals(1, line.getRemainingBonus());

        line.startTry(10);
        assertEquals(167, line.getScore());
        assertEquals(0, line.getRemainingBonus());
    }

}
