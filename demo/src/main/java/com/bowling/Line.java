package com.bowling;

public class Line {
    public static final int NUMBER_OF_FRAMES = 10;

    // int lineNumber;
    private int score;
    private int currentFrame;
    private int remainingBonus;
    private int remainingBonusPins = 10;
    private Frame[] frames = new Frame[NUMBER_OF_FRAMES];

    public void initLine() {
        for (int i = 0; i < NUMBER_OF_FRAMES; i++) {
            frames[i] = new Frame();
            frames[i].setId(i);
        }
        this.score = 0;
        this.currentFrame = 0;
        this.remainingBonus = 0;

    }

    public int getScore() {
        return this.score;
    }

    // public void getScoreOfFrames() {
    // for (Frame f : frames) {
    // int score = f.getScore();
    // System.out.println(score);
    // }
    // }

    public int getCurrentFrame() {
        return this.currentFrame;
    }

    public Frame getFrame(int frameNumber) {
        return this.frames[frameNumber];
    }

    public int getRemainingBonus() {
        return this.remainingBonus;
    }

    /**
     * @param score
     * @throws InvalidScoreException
     */
    public static void checkValidScore(int score) throws InvalidScoreException {
        if (score < 0 || score > 10) {
            throw new InvalidScoreException();
        }
    }

    /**
     * detect if the current try is a normal try or a bonus try
     * pass the score to current frame
     * update bonus for previous frames
     * move to next frame if current frame is over after this try
     * if current frame is the last frame, check the point of the last frame and
     * update remainingBonus if Strike or Spare
     * 
     * @param score
     * @throws TryOutOfLineException
     * @throws EndOfLineException
     * @throws InvalidScoreException
     */
    public void startTry(int score) throws TryOutOfLineException, EndOfLineException, InvalidScoreException {
        if (currentFrame >= 11) { // Bonus tries are finished
            throw new TryOutOfLineException();
        }
        checkValidScore(score);

        if (currentFrame == 10 && this.remainingBonus > 0) { // Bonus try
            bonusTry(score);
        } else { // normal try
            normalTry(score);
        }
        // out of last frame and no remaining bonus try
        if (currentFrame == 10 && this.remainingBonus == 0) {
            endLine();
            throw new EndOfLineException();
        }
    }

    private void normalTry(int score) throws InvalidScoreException{
        System.out.printf("You are in Frame %d \n", currentFrame + 1);
        boolean frameOver = frames[currentFrame].startFrameTry(score);
        // check and update bonus for previous frame(s)
        updateBonus(score);
        this.score += score;
        if (frameOver) {
            int frameScore = frames[currentFrame].getScore();
            System.out.printf("Score of Frame %d: , Total Score: %d \n \n", frameScore, this.score);
            currentFrame += 1;
            if (currentFrame == 10) { // end of last frame
                int[] tryScores = frames[9].getScoreOfTries();  // get score of last frame
                if (tryScores[0] == 10) {
                    this.remainingBonus = 2; // 2 Bonus for Strike
                    System.out.println("You get 2 Bonus tries");
                } else if (tryScores[0] + tryScores[1] == 10) {
                    this.remainingBonus = 1; // 1 Bonus for Spare
                    System.out.println("You get 1 Bonus try");
                }
            }
        }
    }

    private void bonusTry(int score) throws InvalidScoreException{
        if(remainingBonus==1 && score>remainingBonusPins){
            throw new InvalidScoreException();
        }  
        remainingBonus -= 1;
        updateBonus(score);
        System.out.printf("You have %d bonus try \n", this.remainingBonus);
        if(remainingBonus==1 && score!=10){
            remainingBonusPins = 10-score;
        }
    }

    private void endLine(){
        System.out.println("This Line is over!");
        System.out.printf("Your Score: %d \n", this.score);
        printAllScore();
        currentFrame += 1;
    }

    /**
     * @param score
     */
    private void updateBonus(int score) {
        if (currentFrame == 0) { // first frame, no bonus to update
            return;
        } else if (currentFrame == 1) { // second frame, only look back to the first frame
            Frame lastFrame = frames[0];
            if (lastFrame.getBonusTry() > 0) {
                lastFrame.updateBonus(score);
                this.score += score;
            }
        } else { // other frames, look back two frames
            Frame lastOne = frames[currentFrame - 1];
            Frame lastTwo = frames[currentFrame - 2];
            if (lastOne.getBonusTry() > 0) {
                lastOne.updateBonus(score);
                this.score += score;
            }
            if (lastTwo.getBonusTry() > 0) {
                lastTwo.updateBonus(score);
                this.score += score;
            }
        }
    }

    public void printAllScore() {
        for (int i = 0; i < 10; i++) {
            Frame frame = this.frames[i];
            int[] tryScores = frame.getScoreOfTries();
            int try1 = tryScores[0];
            int try2 = tryScores[1];
            int frameScore = frame.getScore();
            if (try1 == 10) {
                System.out.printf("Frame %d:   X |  | %d \n", i + 1, frameScore);
            } else if (try1 + try2 == 10) {
                System.out.printf("Frame %d:   %d | / | %d \n", i + 1, try1, frameScore);
            } else {
                System.out.printf("Frame %d :   %d ??? %d | %d \n", i + 1, try1, try2, frameScore);
            }
        }
    }

    
}
