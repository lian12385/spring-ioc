package com.bulianglin.springioc.filewatch;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WatchDir {
    public static void watchDir(String dir) {
        Path path = Paths.get(dir);
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.OVERFLOW);
            while (true) {
                WatchKey key = watchService.take();
                for (WatchEvent<?> watchEvent : key.pollEvents()) {
                    if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                        System.out.println("create..." + System.currentTimeMillis());
                    } else if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                        System.out.println("modify..." + System.currentTimeMillis());
                    } else if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                        System.out.println("delete..." + System.currentTimeMillis());
                    } else if (watchEvent.kind() == StandardWatchEventKinds.OVERFLOW) {
                        System.out.println("overflow..." + System.currentTimeMillis());
                    }
                }
                if (!key.reset()) {
                    System.out.println("reset false");
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        // WatchService watchService = FileSystems.getDefault().newWatchService();

        String currentDir = System.getProperty("user.dir");
        Thread thread = new Thread(()->watchDir(currentDir + "/tmp/file_test"));
        thread.setDaemon(false);
        thread.start();

        Thread.sleep(500L);
        for (int i = 0; i < 3; i++) {
            String path = currentDir + "/tmp/file_test/test.txt";
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(i);
            fileWriter.close();
            getFileLastModifyTime(path);
            Thread.sleep(5);
        }

    }



    private static void getFileLastModifyTime(String filePath) {
        try {
            // 获取文件路径对应的Path对象
            Path path = Paths.get(filePath);
            // 获取文件的基本属性
            BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
            // 获取最后修改时间
            FileTime lastModifiedTime = attributes.lastModifiedTime();
            System.out.println("文件最后修改时间：" + lastModifiedTime.toMillis());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
