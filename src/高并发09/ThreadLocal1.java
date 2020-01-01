package 高并发09;

import java.util.concurrent.TimeUnit;

public class ThreadLocal1 {

    static ThreadLocal<Person> tl = new ThreadLocal();

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(tl.get());
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tl.set(new Person());
        }).start();
    }
}


class Person{
    String name = "tammy";
}

