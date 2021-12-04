package com.bowling;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Line line = new Line();
        line.initLine();

        // Interaction in terminal
        // System.out.print("please input your score for this try：");
        // Scanner scan = new Scanner(System.in);
        // while (scan.hasNextInt()) {

        // int score = scan.nextInt();
        // try{
        // line.startTry(score);
        // } catch (EndOfLineException e){
        // System.out.println("\n======Starting new Line======");
        // line.initLine();
        // } catch (TryOutOfLineException e){
        // System.out.println("[Error] This Line is already over!");
        // }
        // System.out.print("please input your score for this try：");
        // }
        // scan.close();

        UI ui = new UI();
        ui.startUI(line);
    }
}
