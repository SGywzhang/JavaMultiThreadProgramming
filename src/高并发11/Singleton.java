package 高并发11;

import java.util.Arrays;

public class Singleton {

    private Singleton() {
        System.out.println("single");
    }

    private static class Inner {
        private static Singleton s = new Singleton();
    }

    public static Singleton getSingle() {
        return Inner.s;
    }

    public static void main(String[] args) {
        Thread[] ths = new Thread[200];
        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread(() -> {
                System.out.println(Singleton.getSingle());
            });
        }
        Arrays.asList(ths).forEach(o -> o.start());
    }
}

