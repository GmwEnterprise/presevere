package cn.gmwenterprise.presevere.config;

import cn.gmwenterprise.presevere.dao.SysUserMapper;
import cn.gmwenterprise.presevere.dao.SysUserRoleMapper;
import cn.gmwenterprise.presevere.domain.SysUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Configuration
public class SystemConfig {
    @Resource
    SysUserMapper sysUserMapper;
    @Resource
    SysUserRoleMapper sysUserRoleMapper;

    public static Map<String, String> params = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() throws SQLException {
        // 删除多余的用户角色关系
        deleteUnlessUserRoleRelation();
    }

    @Resource
    DataSource dataSource;

    private void deleteUnlessUserRoleRelation() {
        List<Integer> userIds = sysUserMapper
            .selectByCondition(null)
            .stream()
            .map(SysUser::getId)
            .collect(Collectors.toList());
        sysUserRoleMapper.deleteByUserIds(userIds);
    }

    @Bean(name = "javaMailSender")
    public JavaMailSender getJavaMailSender() throws SQLException {
        PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement("" +
            "select " +
            "   a.mail_sender_username as username," +
            "   a.mail_sender_password as password," +
            "   a.mail_sender_host as host," +
            "   a.mail_sender_port as port " +
            "from system_param a limit 1");
        ResultSet resultSet = preparedStatement.executeQuery();
        JavaMailSenderImpl sender = null;
        while (resultSet.next()) {
            sender = new JavaMailSenderImpl();
            String host = resultSet.getString("host");
            sender.setHost(host);
            String port = resultSet.getString("port");
            sender.setPort(Integer.parseInt(port));
            String username = resultSet.getString("username");
            sender.setUsername(username);
            String password = resultSet.getString("password");
            sender.setPassword(password);
            sender.setDefaultEncoding("UTF-8");
            sender.setProtocol("smtp");

            params.put("host", host);
            params.put("port", port);
            params.put("username", username);
            params.put("password", password);
        }
        Objects.requireNonNull(sender);
        Properties properties = sender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");
        return sender;
    }
}
