import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Example06 {
    private List<String> plainPasswords = new ArrayList<>();
    private List<String> hashedPasswords = new ArrayList<>();
    private final Object lock = new Object();
    private volatile boolean loadingComplete = false;

    public void readFromFile(String filename) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                synchronized (lock) {
                    plainPasswords.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        loadingComplete = true;
    }

    public void hashPassword() {
        while (!loadingComplete || !plainPasswords.isEmpty()) {
            String password = null;
            synchronized (lock) {
                if (!plainPasswords.isEmpty()) {
                    password = plainPasswords.remove(0);
                }
            }

            if (password != null) {
                String hashed = SHA256(password);
                synchronized (lock) {
                    hashedPasswords.add(hashed);
                }
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    public void writeHashedPassword(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String hashed : hashedPasswords) {
                writer.write(hashed);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private String SHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    static class TaskRunnable implements Runnable {
        private final Example06 example;

        public TaskRunnable(Example06 example) {
            this.example = example;
        }

        public void performHashingTask() {
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(2000);
                    example.hashPassword();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        @Override
        public void run() {
            performHashingTask();
        }
    }

    static class LoadingRunnable implements Runnable {
        private final Example06 example;
        private final String inputFile;

        public LoadingRunnable(Example06 example, String inputFile) {
            this.example = example;
            this.inputFile = inputFile;
        }

        @Override
        public void run() {
            example.readFromFile(inputFile);
        }
    }

    static class LoadingAnimationRunnable implements Runnable {
        @Override
        public void run() {
            System.out.print("Loading");
            for (int i = 0; !Thread.currentThread().isInterrupted(); i++) {
                if (i % 6 == 0) System.out.print(".");
                if (i % 6 == 1) System.out.print(".");
                if (i % 6 == 2) System.out.print(".");
                if (i % 6 == 3) System.out.print("\b");
                if (i % 6 == 4) System.out.print("\b");
                if (i % 6 == 5) System.out.print("\b");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String inputFile = "plain-text-passwords.txt";  // File in resources folder
        String outputFile = "hashed-passwords.txt";    // Output file in project root

        Example06 example = new Example06();

        Thread loadingThread = new Thread(new LoadingRunnable(example, inputFile));
        Thread taskThread = new Thread(new TaskRunnable(example));
        Thread animationThread = new Thread(new LoadingAnimationRunnable());

        System.out.println("Started...");
        loadingThread.start();
        taskThread.start();
        animationThread.start();

        loadingThread.join();
        taskThread.join();
        animationThread.interrupt();
        animationThread.join();

        example.writeHashedPassword(outputFile);

        System.out.println("\nHashed passwords saved to: " + outputFile);
    }
}