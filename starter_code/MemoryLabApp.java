import java.util.ArrayList;
import java.util.List;

/**
 * Lab: JVM Memory Analysis
 * 
 * TODO: Complete this application to demonstrate memory behavior
 * 
 * Run with: java -Xms128m -Xmx256m -Xlog:gc*:file=gc.log MemoryLabApp
 */
public class MemoryLabApp {

    private static final byte[] OOM_MSG = "OutOfMemoryError reached\n".getBytes();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== JVM Memory Lab ===");
        printMemoryStatus("Initial");

        List<byte[]> memoryBlocks = new ArrayList<>();
        int MB = 1024 * 1024;
        int allocationCount = 0;

        // Your code here:
        try {
            while (true) {
                // 1. Allocate memory
                memoryBlocks.add(new byte[MB]);
                allocationCount++;
                // 2. Print memory status after each allocation
                printMemoryStatus("After allocation #" + allocationCount);
                // 4. Small delay for observation
                Thread.sleep(250);
            }
        } catch (OutOfMemoryError e) {
            // 3. Handle OutOfMemoryError gracefully
            try {
                System.err.write(OOM_MSG);
                System.err.flush();
            } catch (Throwable ignored) {}
            printMemoryStatus("OOM Reached");
        }

        printMemoryStatus("Final");
    }

    /**
     * Helper method to print current memory status
     */
    private static void printMemoryStatus(String label) {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory() / (1024 * 1024);
        long totalMemory = runtime.totalMemory() / (1024 * 1024);
        long freeMemory = runtime.freeMemory() / (1024 * 1024);
        long usedMemory = totalMemory - freeMemory;

        System.out.printf("[%s] Max: %d MB, Total: %d MB, Used: %d MB, Free: %d MB%n",
                label, maxMemory, totalMemory, usedMemory, freeMemory);
    }
}
