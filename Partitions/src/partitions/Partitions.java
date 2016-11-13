/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package partitions;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Vlado
 */
public class Partitions {
    private final String userPath;
    public static long userTimestamp;
    private List<Traversal> traversals;
    public static List<Thread> threads;
    private ReentrantLock lock;
    private TreeMap<Long, String> paths;
    
    private Partitions(int n) {
        userPath = "/mnt/";
        traversals = new ArrayList<>();
        threads = new ArrayList<>();
        
        for (int i = 0; i < n; i++) {
            StringBuilder sb = new StringBuilder(userPath);
            sb.append("partition").append(i);
            traversals.add(new Traversal(this, sb.toString()));
        }
        
        traversals.forEach((Traversal t) -> {
            threads.add(new Thread(t));
        });
    }
    
    public void insertPath(long timestamp, String path) {
        lock.lock();
        try {
            paths.put(timestamp, path);
        }
        finally {
            lock.unlock();
        }
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        userTimestamp = in.nextLong();
        threads.forEach((Thread t) -> {
            t.start();
        });
    }
    
}
