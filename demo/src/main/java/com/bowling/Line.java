package com.bowling;

public class Line {
    public static final int NUMBER_OF_FRAMES = 10;

    int lineNumber;
    int score;
    int currentFrame;
    Frame[] frames = new Frame[NUMBER_OF_FRAMES];

    public void initLine() {
        for (int i = 0; i < NUMBER_OF_FRAMES; i++) {
            frames[i] = new Frame();
            frames[i].setId(i);
        }
    }

    public int getScore() {
        return this.score;
    }

    public void getScoreOfFrames() {
        for (Frame f : frames) {
            int score = f.getScore();
            System.out.println(score);
        }
    }

    public void startTry(int score) {
        try {
            System.out.printf("You are in Frame %d \n", currentFrame + 1);
            boolean frameOver = this.frames[currentFrame].startTry(score);
            if (frameOver) {
                int frameScore = frames[currentFrame].getScore();
                this.score += frameScore;
                System.out.printf("Score of Frame %d: , Total Score: %d \n \n", frameScore, this.score);
                currentFrame += 1;
            }
            if (currentFrame >= 10) {
                System.out.println("This Line is over!");
                System.out.printf("Your Score: %d \n", this.score);
                printAllScore();
                System.exit(0);
            }

        } catch (InvalidScoreException e) {
            System.out.println("invalid score for a try!");
        }
    }

    public void printAllScore() {
        for (int i = 0; i < 10; i++) {
            Frame frame = this.frames[i];
            int[] scores = frame.getScoreOfTries();
            int try1 = scores[0];
            int try2 = scores[1];
            if (try1 == 10) {
                System.out.printf("Frame %d:   X |  | 10 \n", i + 1);
            } else if (try1 + try2 == 10) {
                System.out.printf("Frame %d:   %d | / | 10 \n", i + 1, try1);
            } else {
                System.out.printf("Frame %d :   %d ｜ %d | %d \n", i + 1, try1, try2, try1 + try2);
            }
        }
    }
}
