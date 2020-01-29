package cn.gmwenterprise.website;

import cn.gmwenterprise.website.dao.PreArticleImageStoreDao;
import cn.gmwenterprise.website.domain.PreArticleImageStore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MyPersonalWebsiteApplicationTests {

    @Resource
    PreArticleImageStoreDao dao;

    @Test
    public void contextLoads() throws Exception {
        InputStream fileInputStream = new FileInputStream("E:\\Pictures\\Snipaste_2019-10-03_14-06-37.png");
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = fileInputStream.read(data)) != -1) {
            buffer.write(data, 0, nRead);
        }

        PreArticleImageStore imageStore = new PreArticleImageStore() {{
            setImageContent(buffer.toByteArray());
        }};
        int result = dao.insert(imageStore);
        if (result > 0) {
            List<PreArticleImageStore> all = dao.selectPage(null);
//            InputStream inputStream = new ByteArrayInputStream(all.get(0).getImageContent());
            OutputStream outputStream = new FileOutputStream("D:\\copy.png");
            outputStream.write(all.get(0).getImageContent());
            outputStream.flush();
            outputStream.close();
        }

        buffer.close();
        fileInputStream.close();
    }

}
