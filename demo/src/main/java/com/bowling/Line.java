package com.bowling;

public class Line {
    public static final int NUMBER_OF_FRAMES = 10;

    // int lineNumber;
    private int score;
    private int currentFrame;
    private int remainingBonus;
    Frame[] frames = new Frame[NUMBER_OF_FRAMES];

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
    //     for (Frame f : frames) {
    //         int score = f.getScore();
    //         System.out.println(score);
    //     }
    // }

    public int getRemainingBonus(){
        return this.remainingBonus;
    }

    public static void checkValidScore(int score) throws InvalidScoreException {
        if (score < 0 || score > 10) {
            throw new InvalidScoreException();
        }
    }

    public void startTry(int score) throws TryOutOfLineException,EndOfLineException{
        try {
            if (currentFrame == 11){    // Bonus tries are finished
                throw new TryOutOfLineException();
            }
            checkValidScore(score);
            if (currentFrame == 10 && this.remainingBonus > 0) {  // Bonus try
                this.remainingBonus -= 1;
                updateBonus(score);
                System.out.printf("You have %d bonus try \n", this.remainingBonus);
            } else {   // normal try
                System.out.printf("You are in Frame %d \n", currentFrame + 1);
                boolean frameOver = this.frames[currentFrame].startFrameTry(score);
                // check and update bonus for previous frame(s)
                updateBonus(score);
                this.score += score;
                if (frameOver) {
                    int frameScore = frames[currentFrame].getScore();
                    System.out.printf("Score of Frame %d: , Total Score: %d \n \n", frameScore, this.score);
                    currentFrame += 1;
                    if (currentFrame == 10) {
                        int[] tryScores = frames[9].getScoreOfTries();
                        if (tryScores[0] == 10) {
                            this.remainingBonus = 2;    // 2 Bonus for Strike
                            System.out.println("You get 2 Bonus tries");
                        } else if (tryScores[0] + tryScores[1] == 10) {
                            this.remainingBonus = 1;        // 1 Bonus for Spare
                            System.out.println("You get 1 Bonus try");
                        }
                    }
                }
            }

            if (currentFrame == 10 && this.remainingBonus == 0) {
                System.out.println("This Line is over!");
                System.out.printf("Your Score: %d \n", this.score);
                printAllScore();
                currentFrame += 1;
                throw new EndOfLineException();
            }

        } catch (InvalidScoreException e) {
            System.out.println("invalid score for a try!");
        }
    }

    private void updateBonus(int score) {
        if (currentFrame == 0) { // first frame, no bonus to update
            return;
        } else if (currentFrame == 1) { // second frame, only look back to the first frame
            Frame lastFrame = frames[0];
            if (lastFrame.getBonusTry() > 0) {
                lastFrame.updateBonus(score);
                this.score += score;
            }
        } else  { // other frames, look back two frames
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
                System.out.printf("Frame %d :   %d ｜ %d | %d \n", i + 1, try1, try2, frameScore);
            }
        }
    }
}
