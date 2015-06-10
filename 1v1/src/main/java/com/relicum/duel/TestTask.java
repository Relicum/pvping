package com.relicum.duel;

/**
 * Name: TestTask.java Created: 02 June 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class TestTask implements Runnable {

    int c = 0;

    @Override
    public void run() {

        while (c < 1000) {
            System.out.println("Number " + c);
            c++;
        }

    }
}
