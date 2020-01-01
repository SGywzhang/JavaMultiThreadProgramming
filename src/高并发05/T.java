package 高并发05;

//volatile不能替代synchronized
//volatile并不能保证多个线程共同修改running变量时所带来的不一致问题，也就是说volatile不能替代synchronized
//
//运行下面的程序输出count的值会出现不到100000的结果，原因是因为虽然volatile可以保证每个线程读取count的值是同步的，但不能保证/要求线程写入count的值时一定是根据此时的count值+1的操作
//
//即读操作和写操作是复合操作，不构成原子性操作
//
//解决这个问题可以使用synchronized关键字修饰m方法即可以保证可见性和原子性同步

import java.util.ArrayList;
import java.util.List;

public class T {
    volatile int count = 0;

    void m() {
        for (int i = 0; i < 10000; i++) count++;
    }

    public static void main(String[] args) {
        T t = new T();

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(t::m, "thread-" + i));
        }

        threads.forEach((o) -> o.start());

        threads.forEach((o) -> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(t.count);
    }

}