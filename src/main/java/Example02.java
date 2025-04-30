public class Example02 {
    //TODO(1): Create a class that implements the Runnable interface

    static class Myrunnable implements Runnable{
       public void run(){
           for(int i = 0 ; i < 20 ; i++){
               System.out.println("Hi from "+ Thread.currentThread().getName());
           }
       }
    }
    public static void main(String[] args)
    {

        for(int i = 0 ; i < 20 ; i++){
            System.out.println("Hi from "+ Thread.currentThread().getName());
        }
        Runnable runnable = new Myrunnable();
        Thread thread = new Thread(runnable);
        thread.start();
        //TODO(2): start a thread that runs the Runnable
        //TODO(0): print 20 greetings
    }
}
