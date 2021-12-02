package com.bowling;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Line l = new Line();
        l.initLine();
        l.startTry(1);
        l.startTry(10);
        l.startTry(9);
        l.startTry(10);
        l.startTry(8);

    }
}
