package com.bowling;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class EndOfLineException extends Exception {
    public void endOfLineDialog(int score, UI ui) {
        JDialog dialog = new JDialog();
        dialog.setSize(300, 200);
        dialog.setLayout(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel message = new JLabel("Your Line is Over \n Final Score: " + score, SwingConstants.CENTER);
        dialog.add(message);
        message.setBounds(0, 50, 300, 20);

        JButton restartButton = new JButton("Restart");
        dialog.add(restartButton);
        restartButton.setBounds(50,100, 50, 20);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.restartLine();
                dialog.dispose();
            }
        });

        JButton endButton = new JButton("Exit");
        dialog.add(endButton);
        endButton.setBounds(150,100, 50, 20);
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        dialog.setModal(true);
        dialog.setVisible(true);
    }
}
