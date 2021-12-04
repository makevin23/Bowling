package com.bowling;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class InvalidScoreException extends Exception {
    public void invalidScoreDialog() {
        JDialog dialog = new JDialog();

        dialog.setSize(300, 200);
        dialog.setLayout(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel message = new JLabel("Invalid Score! Try another one", SwingConstants.CENTER);
        dialog.add(message);
        message.setBounds(0, 50, 300, 20);

        JButton okButton = new JButton("OK");
        dialog.add(okButton);
        okButton.setBounds(125, 100, 50, 20);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setModal(true);
        dialog.setVisible(true);
    }
}
