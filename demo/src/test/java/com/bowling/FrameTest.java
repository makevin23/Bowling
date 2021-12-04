package com.bowling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FrameTest {

    @Test(expected = InvalidScoreException.class)
    public void testInvalidScore11() throws InvalidScoreException {
        Frame f = new Frame();
        f.startFrameTry(11);
    }

    @Test(expected = InvalidScoreException.class)
    public void testInvalidScoreMinus1() throws InvalidScoreException {
        Frame f = new Frame();
        f.startFrameTry(-1);
    }

    @Test(expected = InvalidScoreException.class)
    public void testInvalidFrameScore() throws InvalidScoreException {
        Frame f = new Frame();
        f.startFrameTry(5);
        f.startFrameTry(20);
    }

    @Test
    public void testOneTry() throws InvalidScoreException {
        Frame f = new Frame();
        assertFalse(f.startFrameTry(5));
    }

    @Test
    public void testStrike() throws InvalidScoreException {
        Frame f = new Frame();
        boolean frameOver = f.startFrameTry(10);
        assertTrue(frameOver);
        assertEquals(2, f.getBonusTry());

        f.updateBonus(3);
        assertEquals(1, f.getBonusTry());
        assertEquals(13, f.getScore());

        f.updateBonus(6);
        assertEquals(0, f.getBonusTry());
        assertEquals(19, f.getScore());
    }

    @Test
    public void testSpare() throws InvalidScoreException {
        Frame f = new Frame();
        boolean frameOver1 = f.startFrameTry(3);
        boolean frameOver2 = f.startFrameTry(7);
        assertFalse(frameOver1);
        assertTrue(frameOver2);
        assertEquals(1, f.getBonusTry());
        assertEquals(10, f.getScore());

        f.updateBonus(3);
        assertEquals(0, f.getBonusTry());
        assertEquals(13, f.getScore());
    }

    @Test
    public void testThreeTries() throws InvalidScoreException {
        Frame f = new Frame();
        boolean frameOver1 = f.startFrameTry(3);
        boolean frameOver2 = f.startFrameTry(6);
        boolean frameOver3 = f.startFrameTry(5);
        assertFalse(frameOver1);
        assertTrue(frameOver2);
        assertTrue(frameOver3);
        assertEquals(0, f.getBonusTry());
        assertEquals(9, f.getScore());
    }

}