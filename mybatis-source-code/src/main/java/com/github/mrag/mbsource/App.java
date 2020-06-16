package com.github.mrag.mbsource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    public static void main(String[] args) throws IOException, SQLException {
        InputStream resource = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resource);

        SqlSession session = sessionFactory.openSession();
        System.out.println(session);

        Connection connection = session.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from user");
        while (rs.next()) {
            System.out.println(rs.getMetaData());
        }

        rs.close();
        statement.close();
        session.close();
    }
}
