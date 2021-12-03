package com.bowling;



// import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Line l = new Line();
        l.initLine();
        int[] strike = {10,10,10,10,10,10,10,10,10,10,10,10,10,10};
        for(int n: strike){
            try{
                l.startTry(n);
            } catch (TryOutOfLineException e){
                System.out.println("\n======Starting new line======");
                l.initLine();
            }
        }
    }
}
