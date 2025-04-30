public class Example05 {

    static class TaskRunnable implements  Runnable
    {
        @Override
        public void run() {
            for(int i = 0; i < 20; i++)
            {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    static class LoadingRunnable implements  Runnable
    {
        @Override
        public void run() {

            System.out.print("Loading");
            for(int i = 0; !Thread.currentThread().isInterrupted(); i++)
            {
                if(i % 6 == 0) System.out.print(".");
                if(i % 6 == 1) System.out.print(".");
                if(i % 6 == 2) System.out.print(".");
                if(i % 6 == 3) System.out.print("\b");
                if(i % 6 == 4) System.out.print("\b");
                if(i % 6 == 5) System.out.print("\b");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread taskThread = new Thread(new TaskRunnable());
        System.out.println("Started...");
        taskThread.start();


        Thread loadingThread = new Thread(new LoadingRunnable());
        loadingThread.start();

        taskThread.join();
        loadingThread.interrupt();
        System.out.println("\nEnded...");
    }
}







//TODO: Copy everything from Example05
//TODO: Add a method to hash texts.
//TODO: Read the text containing plain-text passwords, hash them, and save the hashed passwords in a new file.
//TODO: Make the hashing process concurrent with loading.