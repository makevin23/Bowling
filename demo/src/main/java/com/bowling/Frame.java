package com.bowling;

public class Frame {
    int id;
    int score;
    int bonus;
    int currentTry;
    int[] tries = new int[2];

    public void setId(int id) {
        this.id = id + 1;
    }

    public int getScore() {
        return this.score;
    }

    public int[] getScoreOfTries() {
        return tries;
    }

    public boolean startTry(int score) throws InvalidScoreException {
        if (score < 0 || score > 10) {
            throw new InvalidScoreException();
        } else if (currentTry == 1 && score + tries[0] > 10) {
            throw new InvalidScoreException();
        } else {
            tries[currentTry] = score;
            updateScore(score);
            if (currentTry == 0 && this.score == 10) {
                System.out.printf("Strike! Frame %d is over. \n", this.id);
                return true;
            } else if (currentTry == 1 && this.score == 10) {
                System.out.printf("Spare! Frame %d is over \n", this.id);
                return true;
            } else if (currentTry == 1) {
                System.out.printf("Frame %d is over \n ", this.id);
                return true;
            } else {
                currentTry += 1;
                System.out.println("You still have one try in this Frame");
                return false;
            }
        }
    }

    public void updateScore(int score) {
        this.score += score;
    }

}
