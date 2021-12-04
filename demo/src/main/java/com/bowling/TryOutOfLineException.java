package com.bowling;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class TryOutOfLineException extends Exception {
    public void invalidLineNumberDialog() {
        JDialog dialog = new JDialog();
        JLabel label = new JLabel("[Error] This Line is finished!", SwingConstants.CENTER);
        dialog.add(label);
        dialog.setSize(300, 100);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.setVisible(true);
    }
}
