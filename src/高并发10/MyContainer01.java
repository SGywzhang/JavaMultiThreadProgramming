package 高并发10;

import 高并发07.MyContainer1;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyContainer01<T> {

    final private LinkedList<T> lists = new LinkedList<>();
    final private int MAX = 10;
    private int count = 0;

    public synchronized void put(T t){

        while(lists.size() == MAX){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        lists.add(t);
        count++;
        this.notifyAll();
    }

    public synchronized T get(){
        T t = null;
        while(lists.size() == 0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t = lists.removeFirst();
        count--;
        this.notifyAll();
        return t;
    }

    public static void main(String[] args) {

        MyContainer01<String> c = new MyContainer01<>();

        for (int i = 0; i < 10 ; i++) {
                new Thread(()->{
                    for (int j = 0; j < 5 ; j++) {
                        System.out.println(c.get());
                    }
                }, "c"+i).start();
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for(int i=0; i<2; i++) {
                new Thread(()->{
                    for(int j=0; j<25; j++) c.put(Thread.currentThread().getName() + " " + j);
                }, "p" + i).start();
        }
    }
}
