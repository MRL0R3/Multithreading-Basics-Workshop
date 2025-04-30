public class Example01 {
    //TODO(1): Create a class that extends Thread class.
    //TODO(2): print greetings in the Thread class
    //TODO(4): repeat the greetings using a loop

    static class MyThread extends Thread{
        @Override
        public void run(){
            for(int i = 0 ; i < 20 ; i++) {
                System.out.println("Hi from " + Thread.currentThread());

            }
        }
    }

    public static void main(String[] args)
    {
        //TODO(3): Create an object of the class you created, and call start()
        //TODO(0): Write a greeting from the current thread
        Thread.currentThread().setName("MAIN");
        System.out.println();
        Thread thread = new MyThread();
        thread.start();
        for(int i = 0 ; i < 20 ; i++) {
            System.out.println("Hi from " + Thread.currentThread());

        }

    }
}
