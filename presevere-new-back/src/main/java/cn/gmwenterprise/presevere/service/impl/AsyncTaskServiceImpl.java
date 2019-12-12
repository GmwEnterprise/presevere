package cn.gmwenterprise.presevere.service.impl;

import cn.gmwenterprise.presevere.service.AsyncTaskService;
import cn.gmwenterprise.presevere.service.MailService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AsyncTaskServiceImpl implements AsyncTaskService {
    @Resource
    MailService mailService;

    @Override
    @Async
    public void pushNewPostEmails(Long url) {
        mailService.sendBatch("new_article", url);
    }
}
