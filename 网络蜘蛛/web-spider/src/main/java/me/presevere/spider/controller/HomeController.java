package me.presevere.spider.controller;

import me.presevere.spider.service.LocalFileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HomeController {
    @Resource
    LocalFileService localFileService;

    @RequestMapping(value = "/download/{path}", method = RequestMethod.GET)
    ResponseEntity<byte[]> downloadFile(@PathVariable String path) {

        return null;
    }
}
