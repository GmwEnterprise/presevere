package me.presevere.spider.service.impl;

import me.presevere.spider.service.LocalFileService;
import org.springframework.stereotype.Service;

import java.io.*;

@Service(value = "localFileService")
public class LocalFileServiceImpl implements LocalFileService {
    private static final String FILE_PATH_PREFIX = "E:\\Desktop\\web-spider-files\\";

    @Override
    public InputStream getFileByName(String filename) {
        return getFileByPath(FILE_PATH_PREFIX + filename);
    }

    @Override
    public InputStream getFileByPath(String absolutePath) {
        File target = new File(absolutePath);
        if (target.isFile()) {
            try {
                return new FileInputStream(target);
            } catch (FileNotFoundException ignored) {
                // 不会发生
            }
        }
        return null;
    }

    @Override
    public void saveFile(InputStream fin, String targetPath) {
        try {
            File target = new File(targetPath);
            if (target.createNewFile()) {
                FileOutputStream fout = new FileOutputStream(target);
                int offset;
                byte[] buf = new byte[1024];
                while ((offset = fin.read(buf)) != -1) {
                    fout.write(buf, 0, offset);
                }
                fout.flush();
                fout.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
