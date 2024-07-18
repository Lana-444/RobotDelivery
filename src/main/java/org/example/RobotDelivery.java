package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RobotDelivery {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        int numberOfThreads = 1000;

        Thread[] threads = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread();
            {
                String route = generateRoute("RLRFR", 100);
                int countR = countRotationCommands(route);
                updateFrequencyMap(countR);
                System.out.println("Количество команд поворота направо: " + countR);
            }
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        printFrequencyResults();
    }


    private static int countRotationCommands(String route) {
        int countR = 0;
        for (int i = 0; i < route.length(); i++) {
            if (route.charAt(i) == 'R') {
                countR++;
            }
        }
        return countR;
    }

    public static synchronized void updateFrequencyMap(int countR) {
        sizeToFreq.put(countR, sizeToFreq.getOrDefault(countR, 0) + 1);
    }

    private static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();

        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }

        return route.toString();
    }

    private static void printFrequencyResults() {
        int maxFrequency = 0;
        for (int freq : sizeToFreq.values()) {
            if (freq > maxFrequency) {
                maxFrequency = freq;
            }
        }
        System.out.println();
        System.out.println("Самое частое количество повторений: " + maxFrequency);
        System.out.println("Другие размеры:");
        sizeToFreq.entrySet().stream()
                .sorted((e1, e2) -> e2.getKey().compareTo(e1.getKey()))
                .forEach(entry -> System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз)"));
        System.out.println();
    }
}



