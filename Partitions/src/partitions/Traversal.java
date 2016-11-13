/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package partitions;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vlado
 */
public class Traversal extends SimpleFileVisitor<Path> implements Runnable {
    private final Partitions partition;
    private final Path path;
    
    public Traversal(Partitions partition, Path path) {
        this.partition = partition;
        this.path = path;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        String fileName = file.getFileName().toString();
        String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
        Long timestamp = Long.parseLong(fileNameWithoutExtension);
        if (timestamp <= partition.userTimestamp) {
            partition.insertPath(timestamp, file);
        }
        return FileVisitResult.CONTINUE;
    }
    
    
    
    @Override
    public void run() {
        try {
            if (Files.exists(path)) {
                Files.walkFileTree(path, this);
            }
        } 
        catch (IOException ex) {
            Logger.getLogger(Traversal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
