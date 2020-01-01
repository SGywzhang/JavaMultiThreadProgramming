package 高并发07;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MyContainer2<T> {


    volatile List<T> list  = new ArrayList();

    public void add(T o){
        list.add(o);
    }

    public int size(){
        return list.size();
    }

    public static void main(String[] args) {

        MyContainer2 myContainer2 = new MyContainer2();

        final Object lock = new Object();

        new Thread(()->{
            synchronized(lock) {
                System.out.println("T2 start");
                if (myContainer2.size() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("T2 end");
                lock.notifyAll();
            }
        },"T2").start();

        new Thread(()->{
            synchronized(lock){
                System.out.println("T1 start");
                for (int i = 0; i < 9 ; i++) {
                    myContainer2.add(new Object());
                    System.out.println("add" + i);

                    if(myContainer2.size() == 5){
                        lock.notifyAll();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"T1").start();
    }
}
