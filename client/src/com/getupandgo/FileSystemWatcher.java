package com.getupandgo;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 * Created by getupandgo on 7/19/15.
 */
public class FileSystemWatcher {

    void registerWatcher(String path) throws IOException {
        watcher = FileSystems.getDefault().newWatchService();
        Path dir = Paths.get(path);
        dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

        System.out.println("Watch Service registered for dir: " + dir.getFileName());


    }

    void startWatching(){
        while (true) {
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException ex) {
                return;
            }

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                @SuppressWarnings("unchecked")
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path fileName = ev.context();

                System.out.println(kind.name() + ": " + fileName);

                if (kind == ENTRY_MODIFY &&
                        fileName.toString().equals("DirectoryWatchDemo.java")) {
                    System.out.println("My source file has changed!!!");
                }
            }

            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
    }

    private WatchService watcher;
}
