package 高并发07;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MyContainer1<T> {


    volatile List<T> list  = new ArrayList();

    public void add(T o){
        list.add(o);
    }

    public int size(){
        return list.size();
    }

    public static void main(String[] args) {

        MyContainer1 myContainer1 = new MyContainer1();

        new Thread(()->{
            System.out.println("T1 start");
            for (int i = 0; i < 9 ; i++) {
                myContainer1.add(new Object());
                System.out.println("add" + i);

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        },"T1").start();

        new Thread(()->{
            System.out.println("T2 start");
            while(true){

                if(myContainer1.size() == 5){
                    break;
                }
            }
            System.out.println("T2 end");

        },"T2").start();

    }
}
