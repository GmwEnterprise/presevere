package me.presevere.spider.service;

import java.io.InputStream;

/**
 * 本地文件服务
 */
public interface LocalFileService {

    InputStream getFileAsInputStream(String filename);

    byte[] getFileAsByteArray(String filename);
}
