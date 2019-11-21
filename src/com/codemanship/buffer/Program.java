package com.codemanship.buffer;

import org.junit.Test;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Program {

    private static Queue<Character> buffer;
    private static boolean finished;

    public static void main(String[] args) {
        buffer = new LinkedList<>();
        finished = false;

        Runnable inputter = () -> {
            String input = "This interface defines a protocol for bidirectional iteration over text. The iterator iterates over a bounded sequence of characters. Characters are indexed with values beginning with the value returned by getBeginIndex() and continuing through the value returned by getEndIndex()-1.\n" +
                    "Iterators maintain a current character index, whose valid range is from getBeginIndex() to getEndIndex(); the value getEndIndex() is included to allow handling of zero-length text ranges and for historical reasons. The current index can be retrieved by calling getIndex() and set directly by calling setIndex(), first(), and last().\n" +
                    "\n" +
                    "The methods previous() and next() are used for iteration. They return DONE if they would move outside the range from getBeginIndex() to getEndIndex() -1, signaling that the iterator has reached the end of the sequence. DONE is also returned by other methods to indicate that the current index is outside this range.";

            CharacterIterator iterator = new StringCharacterIterator(input);

            for (char c = iterator.first(); c != CharacterIterator.DONE; c = iterator.next()) {
                buffer.add(c);
            }

            finished = true;
        };

        Runnable outputter = () -> {
            while (!finished) {
                while (!buffer.isEmpty()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print(buffer.remove());
                }
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(inputter);
        executor.submit(outputter);
        executor.shutdown();

        try {
            executor.awaitTermination(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static double sqrt(double number) {
        if(number == 0) return 0;
        double t;

        double squareRoot = number / 2;

        do {
            t = squareRoot;
            squareRoot = (t + (number / t)) / 2;
        } while ((t - squareRoot) != 0);

        return squareRoot;
    }
}
