package 高并发08;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock2 {
    Lock lock = new ReentrantLock(); // 创建锁

    void m1() {
        try {
            lock.lock(); //synchronized(this) 申请并锁定锁lock
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);

                System.out.println(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock(); // 解锁
        }
    }

    void m2() {
        lock.lock(); // 申请并锁定锁lock
        System.out.println("m2 ...");
        lock.unlock(); // 解锁
    }

    public static void main(String[] args) {
        ReentrantLock2 rl = new ReentrantLock2();
        new Thread(rl::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(rl::m2).start();
    }
}