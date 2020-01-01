package 高并发07;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MyContainer3<T> {


    List<T> list  = new ArrayList();

    public void add(T o){
        list.add(o);
    }

    public int size(){
        return list.size();
    }

    public static void main(String[] args) {

        MyContainer3 myContainer3 = new MyContainer3();

        CountDownLatch latch = new CountDownLatch(1);

        new Thread(()->{
            System.out.println("T2 start");

            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("T2 end");

        },"T2").start();


        new Thread(()->{
            System.out.println("T1 start");
            for (int i = 0; i < 9 ; i++) {
                myContainer3.add(new Object());
                System.out.println("add" + i);

                if(myContainer3.size() == 5){
                    latch.countDown();
                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"T1").start();
    }
}
