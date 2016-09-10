import java.util.concurrent.*;
import java.util.*;

public class Frequency {
    public static ExecutorService threadPool = Executors.newCachedThreadPool();

    public static int parallelFreq(int x, int[] A, int numThreads) {
        // your implementation goes here.
        List<Future<Integer>> fs = new ArrayList<Future<Integer>>();
        int count = 0;
        int numElems = A.length / numThreads; //Integer division
        int currentSize = A.length;

        for (int i = 0; i < numThreads; i++){
            int splitSize = numElems;
            if (currentSize < splitSize){
                splitSize = currentSize;
            }
            currentSize -= splitSize;

            // Get the split
            int[] mSplit = new int[splitSize];
            for (int j = 0; j < splitSize; j++){
                mSplit[j] = A[i*numElems + j];
            }

            try {
                fs.add(threadPool.submit(new Freq(mSplit, x)));
            } catch (Exception e) { System.err.println (e); return 1;}
        }
        //Get the results
        for (int i = 0; i < numThreads; i++){
            try {
                count += fs.get(i).get();
            } catch (Exception e) { System.err.println (e); return 1;}
        }
        return count;
    }

    public static void main(String[] args){

    }
}

class Freq implements Callable<Integer> {
    //public static ExecutorService threadPool = Executors.newCachedThreadPool();
    int[] chunky;
    int targetNum;

    public Freq(int[] chunky, int targetNum){
        this.chunky = chunky;
        this.targetNum = targetNum;    
    }   

    public Integer call(){
        int count = 0;

        for (int i =0; i < chunky.length; i++) {
            if (chunky[i] == targetNum){
                count++;
            }
        }
        return count;
    }
}
