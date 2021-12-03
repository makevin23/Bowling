package com.bowling;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AppTest 
{

    @Test
    public void testAllStrike() throws TryOutOfLineException, EndOfLineException
    {
        Line line = new Line();
        line.initLine();
        int[] scores = {10,10,10,10,10,10,10,10,10,10,10,10};
        for(int score: scores){
            line.startTry(score);
        }
        int finalScore = line.getScore();
        assertEquals(finalScore, 300);
    }

    @Test
    public void testNineZero() throws TryOutOfLineException, EndOfLineException
    {
        Line line = new Line();
        line.initLine();
        int[] scores = {9,0, 9,0, 9,0, 9,0, 9,0, 9,0, 9,0, 9,0, 9,0, 9,0};
        for(int score: scores){
            line.startTry(score);
        }
        int finalScore = line.getScore();
        assertEquals(90, finalScore);
    }

    @Test
    public void testFiveSpare() throws TryOutOfLineException, EndOfLineException
    {
        Line line = new Line();
        line.initLine();
        int[] scores = {5,5, 5,5, 5,5, 5,5, 5,5, 5,5, 5,5, 5,5, 5,5, 5,5, 5};
        for(int score: scores){
            line.startTry(score);
        }
        int finalScore = line.getScore();
        assertEquals(150, finalScore);
    }

    @Test (expected = TryOutOfLineException.class)
    public void testTooMuchTries() throws TryOutOfLineException, EndOfLineException
    {
        Line line = new Line();
        line.initLine();
        int[] scores = {5,5, 5,5, 5,5, 5,5, 5,5, 5,5, 5,5, 5,5, 5,5, 5,5, 5,5, 5,5};
        for(int score: scores){
            line.startTry(score);
        }
    }

    @Test
    public void testInvalidFrame() throws TryOutOfLineException, EndOfLineException
    {
        Line line = new Line();
        line.initLine();
        int[] scores = {5,3, 5,10};
        for(int score: scores){
            line.startTry(score);
        }
        assertEquals(13, line.getScore());
        Frame frame1 = line.frames[0];
        Frame frame2 = line.frames[1];
        assertEquals(8, frame1.getScore());
        assertEquals(5, frame2.getScore());
        int[] tryScores = frame2.getScoreOfTries();
        assertEquals(5, tryScores[0]);
        assertEquals(0, tryScores[1]);
    }
}
