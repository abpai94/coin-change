package coinchange;

final class MyThread {

    class AnotherThread {
        void AnotherThread() {
            System.out.println(MyThread.this.test);
        }
    }

    class AnotherDamnThread extends AnotherThread {
        @Override
        void AnotherThread() {
            loop:
            for (int i = 0; i < 10; i++) {
                break loop;
            }
        }
    }

    private String test = "test";

    public MyThread() {
        (new AnotherThread()).AnotherThread();
        boolean a = false;
        if (a = true) {
            System.out.println(MyThread.this.test);
        } else {
            System.out.println(MyThread.this.test + "sadfas");

        }
    }

    public static void main(String[] args) {
//            new MyThread();

        int[] a = {1};
        a[a.length - 1]++;
        System.out.println(a[a.length - 1]);
    }
}