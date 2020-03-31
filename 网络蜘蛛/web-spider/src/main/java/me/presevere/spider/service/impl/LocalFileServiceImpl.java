package me.presevere.spider.service.impl;

import me.presevere.spider.service.LocalFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

@Service(value = "localFileService")
public class LocalFileServiceImpl implements LocalFileService {
    @Value("customProperties.filePathPrefix")
    private String filePathPrefix;

    private String path(String filename) {
        return filePathPrefix + File.separator + filename;
    }

    @Override
    public InputStream getFileAsInputStream(String filename) {
        File file = new File(path(filename));
        return null;
    }

    @Override
    public byte[] getFileAsByteArray(String filename) {
        return new byte[0];
    }
}
