package com.bowling;

public class Frame {
    private int id;
    private int score;
    private int bonus;
    private int currentTry;
    private int bonusTry;
    private int[] tries = new int[2];

    public void setId(int id) {
        this.id = id + 1;
    }

    public int getScore() {
        return this.score;
    }

    public int[] getScoreOfTries() {
        return tries;
    }

    public int getBonusTry() {
        return this.bonusTry;
    }

    public int[] getTryScores() {
        return this.tries;
    }

    public int getCurrentTry() {
        return this.currentTry;
    }

    public void updateScore(int score) {
        this.score += score;
    }

    public void updateBonus(int score) {
        this.bonus += score;
        this.score += score;
        this.bonusTry -= 1;
    }

    public boolean startFrameTry(int score) throws InvalidScoreException {
        // check if score is valid
        if (score < 0 || score > 10) {
            throw new InvalidScoreException();
        } else if (currentTry == 1 && score + tries[0] > 10) {
            throw new InvalidScoreException();
        } else {
            if (currentTry >= 2) {
                return true;
            }
            tries[currentTry] = score;
            updateScore(score);
            currentTry += 1;
            if (currentTry == 1 && this.score == 10) { // Strike
                this.bonusTry = 2; // 2 bonus for strike
                System.out.printf("Strike! Frame %d is over. \n", this.id);
                return true;
            } else if (currentTry == 2 && this.score == 10) { // Spare
                this.bonusTry = 1; // 1 bonus for Spare
                System.out.printf("Spare! Frame %d is over \n", this.id);
                return true;
            } else if (currentTry == 2) { // Frame over with score < 10
                System.out.printf("Frame %d is over \n ", this.id);
                return true;
            } else if (currentTry == 1) { // First Try not Strike
                System.out.println("You still have one try in this Frame");
                return false;
            }
        }
        return true;
    }

}
