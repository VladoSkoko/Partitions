/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package partitions;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Vlado
 */
public class Partitions {
    private String userPath;
    public long userTimestamp;
    private List<Traversal> traversals;
    private List<Thread> threads;
    //private ReentrantLock lock;
    private ConcurrentSkipListMap<Long, Path> paths;
    
    private Partitions(int n) {
        userPath = "C:\\Users\\Vlado\\Desktop\\partitions\\partition";
        userPath = userPath.replace("\\", "/");
        //userPath = "/mnt/";
        //lock = new ReentrantLock();
        paths = new ConcurrentSkipListMap<>();
        
        traversals = new ArrayList<>();
        threads = new ArrayList<>();
        
        for (int i = 1; i <= n; i++) {
            traversals.add(new Traversal(this, Paths.get(userPath + i)));
        }
        
        traversals.forEach((Traversal t) -> {
            threads.add(new Thread(t));
        });
    }
    
    public void insertPath(long timestamp, Path path) {
        /*lock.lock();
        try {
            paths.put(timestamp, path);
        }
        finally {
            lock.unlock();
        }*/
        
        paths.put(timestamp, path);
    }
    
    public static void main(String[] args) {
        Partitions part = new Partitions(12);
        
        try (Scanner in = new Scanner(System.in)) {
            System.out.print("Timestamp: ");
            part.userTimestamp = in.nextLong();
        }
        
        part.threads.forEach((Thread t) -> {
            t.start();
        });
        
        part.threads.forEach((t) -> {
            try {
                t.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Partitions.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        if (!part.paths.isEmpty())
            System.out.println("Result: " + part.paths.lastEntry().getValue());
        else
            System.out.println("Result: There is no entry with a lower or equal timestamp.");
    }
    
}
