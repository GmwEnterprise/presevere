package cn.gmwenterprise.presevere.service.impl;

import cn.gmwenterprise.presevere.common.BusinessException;
import cn.gmwenterprise.presevere.config.SystemConfig;
import cn.gmwenterprise.presevere.dao.ArticleMetadataMapper;
import cn.gmwenterprise.presevere.dao.ArticleSubscriberMapper;
import cn.gmwenterprise.presevere.domain.ArticleMetadata;
import cn.gmwenterprise.presevere.domain.ArticleSubscriber;
import cn.gmwenterprise.presevere.service.MailService;
import cn.gmwenterprise.presevere.service.ThymeleafTemplateService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Objects;

@Service
public class MailServiceImpl implements MailService {
    final JavaMailSender javaMailSender;
    @Resource
    ArticleSubscriberMapper articleSubscriberMapper;
    @Resource
    ThymeleafTemplateService thymeleafTemplateService;
    @Resource
    ArticleMetadataMapper articleMetadataMapper;

    private String from;

    public MailServiceImpl(JavaMailSender javaMailSender) {
        // 构造方法注入javaMailSender，因为再sender的bean create函数中初始化了SystemConfig.params
        this.javaMailSender = javaMailSender;
        from = SystemConfig.params.get("username");
        Objects.requireNonNull(from);
    }

    @Override
    public void send(String to, String template) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("email", to);
        String html = thymeleafTemplateService.render(template, params);
        if (!mimeMail(from, "感谢订阅Presevere!", html, to)) {
            throw new BusinessException("验证邮箱失败!");
        }
    }

    private boolean mimeMail(String from, String subject, String html, String... toList) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        try {
            messageHelper.setFrom(from);
            for (String s : toList) {
                messageHelper.addTo(s);
            }
            messageHelper.setSubject(subject);
            messageHelper.setText(html, true);
            javaMailSender.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sendBatch(String template, Long articleUrl) {
        String[] toList = articleSubscriberMapper.selectAll(ArticleSubscriber.SUBSCRIBER_ON)
            .stream().map(ArticleSubscriber::getEmail).toArray(String[]::new);
        ArticleMetadata metadata = articleMetadataMapper.selectByUrlNumber(articleUrl);
        HashMap<String, Object> params = new HashMap<>();
        params.put("url", metadata.getUrlNumber());
        String html = thymeleafTemplateService.render(template, params);
        return mimeMail(from, "Presevere文章推送 - " + metadata.getTitle(), html, toList);
    }

    @Override
    public void subscribe(String email) {
        String[] split = email.split("@");
        if (split.length != 2) {
            throw new BusinessException("邮箱格式错误!");
        }
        ArticleSubscriber articleSubscriber = new ArticleSubscriber() {{
            setEmail(email);
            setPrefix(split[0]);
            setSuffix(split[1]);
            setSubscribe(ArticleSubscriber.SUBSCRIBER_ON);
        }};
        ArticleSubscriber subscriber = articleSubscriberMapper.selectByEmail(email);
        if (subscriber != null && ArticleSubscriber.SUBSCRIBER_ON.equals(subscriber.getSubscribe())) {
            throw new BusinessException("该邮箱已订阅.");
        }
        send(email, "welcome_subscriber");
        if (subscriber == null) {
            articleSubscriberMapper.insert(articleSubscriber);
        } else {
            articleSubscriberMapper.updateSubscriberByEmail(new ArticleSubscriber() {{
                setEmail(email);
                setSubscribe(ArticleSubscriber.SUBSCRIBER_ON);
            }});
        }
    }

    @Override
    public void unsubscribe(String email) {
        try {
            articleSubscriberMapper.updateSubscriberByEmail(new ArticleSubscriber() {{
                setEmail(email);
                setSubscribe(ArticleSubscriber.SUBSCRIBER_OFF);
            }});
        } catch (Exception ignored) {}
    }
}
