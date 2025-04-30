public class Example03 {
    //TODO(1): create a class that implements the Runnable interface
    //TODO(2): print greetings in a loop
    //TODO(5): add a way of ending the thread when it is interrupted
    //TODO(6): add sleep in the loop
    //TODO(7): handle InterruptedException
    static class Myrunnable implements Runnable{
        @Override
        public void run() {
            for(int i = 0 ; i < 20 ; i++){
                System.out.println("Hi from "+ Thread.currentThread().getName() + " " + Thread.currentThread());
            }
            try{
                Thread.sleep(1000);
            }
            catch (InterruptedException e){
                System.out.println(">> Eror");
                Thread.currentThread().interrupt();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Hi from " + Thread.currentThread().getName());
        Thread thread = new Thread(new Myrunnable());
        System.out.println("State " + thread.getState());
        thread.start();
        System.out.println("State " + thread.getState());
        Thread.sleep(4000);

        thread.interrupt();
        System.out.println("State " + thread.getState());
        Thread.sleep(1000);
        System.out.println("State " + thread.getState());


        //TODO(0): print greetings in a loop
        //TODO(3): talk about thread.stop...
        //TODO(4): interrupt the other thread at some time
        //TODO(8): observe thread state

     }
}