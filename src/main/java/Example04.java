public class Example04 {

    public static class MyRunnable implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++)
            {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {

                }
                System.out.println("Hello from " + Thread.currentThread().getName());
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new MyRunnable());
        thread.start();



        thread.join();
        System.out.println("Done");
        //TODO: create a thread and start it
        //TODO: print a message after the thread is done
    }
}
