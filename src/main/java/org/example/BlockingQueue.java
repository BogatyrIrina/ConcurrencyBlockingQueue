package org.example;

public class BlockingQueue<T> {
    private final Object lock = new Object(); // Объект блокировки
    private final T[] queue;
    private int head = 0;
    private int tail = 0;
    private int count = 0;

    @SuppressWarnings("unchecked")
    public BlockingQueue(int capacity) {
        queue = (T[]) new Object[capacity];
    }

    public void enqueue(T item) throws InterruptedException {
        synchronized (lock) {
            while (count == queue.length) {
                lock.wait(); // Если очередь полная, ждем уведомления
            }
            queue[tail] = item;
            tail = (tail + 1) % queue.length;
            count++;
            lock.notify(); // Уведомляем ожидающих потребителей
        }
    }

    public T dequeue() throws InterruptedException {
        synchronized (lock) {
            while (count == 0) {
                lock.wait(); // Если очередь пустая, ждем уведомления
            }
            T item = queue[head];
            head = (head + 1) % queue.length;
            count--;
            lock.notify(); // Уведомляем ожидающих производителей
            return item;
        }
    }

    public int size() {
        synchronized (lock) {
            return count;
        }
    }
}
