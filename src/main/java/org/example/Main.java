package org.example;

public class Main {
    public static void main(String[] args) {
        // Создаем блокирующую очередь с вместимостью 5 элементов
        BlockingQueue<Integer> queue = new BlockingQueue<>(5);

        // Создаем и запускаем производителей
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        queue.enqueue(j);
                        System.out.println("Produced: " + j);
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }

        // Создаем и запускаем потребителей
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    while (true) {
                        int item = queue.dequeue();
                        System.out.println("Consumed: " + item);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
}