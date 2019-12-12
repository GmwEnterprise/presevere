package cn.gmwenterprise.presevere.service;

public interface MailService {
    /**
     * 发送感谢订阅邮件
     *
     * @param to       收件人
     * @param template 模板
     */
    void send(String to, String template);

    /**
     * 批量发送新文章推送邮件
     *
     * @param template   模板
     * @param articleUrl 文章链接
     * @return boolean是否成功发送
     */
    boolean sendBatch(String template, Long articleUrl);

    /**
     * 订阅
     *
     * @param email 邮箱
     */
    void subscribe(String email);

    /**
     * 退订
     * @param email 邮箱
     */
    void unsubscribe(String email);
}
