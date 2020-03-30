package me.presevere.spider.service;

import java.io.InputStream;

/**
 * 本地文件服务
 */
public interface LocalFileService {

    InputStream getFileByName(String filename);

    InputStream getFileByPath(String absolutePath);

    void saveFile(InputStream fin, String targetPath);
}
