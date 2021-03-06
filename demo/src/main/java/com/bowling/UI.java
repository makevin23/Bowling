package com.bowling;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class UI {

    Line line;
    JFrame frame;
    JPanel panel;
    JLabel[] tryScores = new JLabel[10];
    JLabel[] frameScores = new JLabel[10];

    
    /** 
     * @param line
     */
    public void startUI(Line line) {
        this.line = line;
        frame = new JFrame("Bowling");
        // Setting the width and height of frame
        frame.setSize(650, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        frame.add(panel);
        initElements(panel);
        initScoreBoard(panel);
        frame.setVisible(true);
    }

    
    /** 
     * @param panel
     */
    private void initScoreBoard(JPanel panel) {
        panel.setLayout(null);

        // board for score of each frame
        Border blackline = BorderFactory.createLineBorder(Color.black);

        for (int i = 0; i < 10; i++) {
            // first line: frame number
            JLabel frameNumber = new JLabel(Integer.toString(i + 1), SwingConstants.CENTER);
            int x = 20 + i * 60;
            frameNumber.setBounds(x, 10, 60, 40);
            frameNumber.setBorder(blackline);
            frameNumber.setForeground(Color.blue);
            panel.add(frameNumber);

            // second line: scores for each try
            tryScores[i] = new JLabel("|", SwingConstants.CENTER);
            tryScores[i].setBounds(x, 60, 60, 40);
            tryScores[i].setBorder(blackline);
            panel.add(tryScores[i]);

            // third line: scores for frames
            frameScores[i] = new JLabel("0", SwingConstants.CENTER);
            frameScores[i].setBounds(x, 110, 60, 40);
            frameScores[i].setBorder(blackline);
            panel.add(frameScores[i]);

        }
    }

    
    /** 
     * @param panel
     */
    private void initElements(JPanel panel) {
        // button to start new line
        JButton newLineButton = new JButton("RESTART");
        newLineButton.setBounds(20, 170, 150, 20);
        newLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartLine();
            }
        });
        panel.add(newLineButton);

        // input field for score of a try
        JTextField tryScore = new JTextField("10");
        tryScore.setBounds(500, 170, 40, 20);
        panel.add(tryScore);

        JLabel tryScoreText = new JLabel("Your Score for this Try: ");
        tryScoreText.setBounds(350, 170, 150, 20);
        panel.add(tryScoreText);

        // button to submit score of a try
        JButton submitButton = new JButton("submit");
        submitButton.setBounds(550, 170, 50, 20);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int score = Integer.valueOf(tryScore.getText());
                try {
                    line.startTry(score);
                    int currentFrame = line.getCurrentFrame();  // returns next frame if it is over
                    if (currentFrame < 10) {
                        updateTryScore(currentFrame);
                        updateFrameScore(currentFrame);
                    } else {                                    // bonus try
                        updateTryScore(10);
                        updateFrameScore(10);
                    }
                } catch (TryOutOfLineException e1) {
                    e1.invalidLineNumberDialog();
                } catch (EndOfLineException e1) {               // the line ends, update scores and show the final score
                    updateFrameScore(10);
                    updateTryScore(10);
                    e1.endOfLineDialog(line.getScore(), UI.this);
                } catch (InvalidScoreException e1) {
                    e1.invalidScoreDialog();
                }
            }
        });
        panel.add(submitButton);
    }

    
    /** 
     * @param currentFrame
     */
    private void updateTryScore(int currentFrame) {
        if (currentFrame > 9) {                         // last frame
            Frame frame = line.getFrame(9);
            int[] scores = frame.getScoreOfTries();
            if (scores[0] == 10) {                      // Strike
                tryScores[9].setText(" |X");
            } else if (scores[0] + scores[1] == 10) {   // Spare
                String first = Integer.toString(scores[0]);
                if (scores[0]==0){
                    first = "-";
                }
                tryScores[9].setText(first + "|/");
            } else {                                    // no Strike or Spare
                String first = Integer.toString(scores[0]);
                if(scores[0]==0){                       // if score is zero, show "-"
                    first = "-";
                }
                String second = Integer.toString(scores[1]);
                if(scores[1]==0){
                    second = "-";
                }
                tryScores[9].setText(first + "|" + second);
            }
            return;
        }

        Frame frame = line.getFrame(currentFrame);
        if (frame.getCurrentTry() == 1) {               // this frame is not over, update this frame
            int[] scores = frame.getScoreOfTries();
            String first = Integer.toString(scores[0]);
            if (scores[0]==0){
                first = "-";
            }
            tryScores[currentFrame].setText(first + "|");
        } else if (frame.getCurrentTry() == 0) {        // this is a new frame, update last frame
            frame = line.getFrame(currentFrame - 1);
            int[] scores = frame.getScoreOfTries();
            if (scores[0] == 10) {                      // Strike
                tryScores[currentFrame - 1].setText(" |X");
            } else if (frame.getScore() == 10) {        // Spare
                String first = Integer.toString(scores[0]);
                if(scores[0]==0){
                    first = "-";
                }
                tryScores[currentFrame - 1].setText(first + "|/");
            } else {                                    // no Strike or Spare
                String first = Integer.toString(scores[0]);
                if(scores[0]==0){
                    first = "-";
                }
                String second = Integer.toString(scores[1]);
                if(scores[1]==0){
                    second = "-";
                }
                tryScores[currentFrame - 1].setText(first + "|" + second);
            }
        }
    }

    
    /** 
     * @param currentFrame
     */
    private void updateFrameScore(int currentFrame) {
        if (currentFrame > 9) {                     // last frame and bonus try, update max. last 3 frame score
            frameScores[7].setText(Integer.toString(Integer.valueOf(frameScores[6].getText())+line.getFrame(7).getScore()));
            frameScores[8].setText(Integer.toString(Integer.valueOf(frameScores[7].getText())+line.getFrame(8).getScore()));
            frameScores[9].setText(Integer.toString(Integer.valueOf(frameScores[8].getText())+line.getFrame(9).getScore()));
            return;
        }
        Frame frame;
        if(currentFrame==0){
            frame = line.getFrame(currentFrame);
        } else{
            frame = line.getFrame(currentFrame-1);
        }
        
        if(frame.getCurrentTry()==1 && currentFrame!=0){
            frame = line.getFrame(currentFrame-1);
        }

        if (currentFrame==1 || currentFrame==0) {
            frameScores[0].setText(Integer.toString(frame.getScore()));
        } else if (currentFrame==2) {
            frameScores[0].setText(Integer.toString(line.getFrame(0).getScore()));
            frameScores[1].setText(Integer.toString(Integer.valueOf(frameScores[0].getText())+frame.getScore()));
        } else if (currentFrame==3) {
            frameScores[0].setText(Integer.toString(line.getFrame(0).getScore()));
            frameScores[1].setText(Integer.toString(Integer.valueOf(frameScores[0].getText())+line.getFrame(1).getScore()));
            frameScores[2].setText(Integer.toString(Integer.valueOf(frameScores[1].getText())+frame.getScore()));
        } else {
            frameScores[currentFrame-3].setText(Integer.toString(Integer.valueOf(frameScores[currentFrame-4].getText())+line.getFrame(currentFrame-3).getScore()));
            frameScores[currentFrame-2].setText(Integer.toString(Integer.valueOf(frameScores[currentFrame-3].getText())+line.getFrame(currentFrame-2).getScore()));
            frameScores[currentFrame-1].setText(Integer.toString(Integer.valueOf(frameScores[currentFrame-2].getText())+frame.getScore()));
        }

    }

    public void restartLine() {
        System.out.println("restart");
        line.initLine();
        frame.dispose();
        startUI(line);
    }

}