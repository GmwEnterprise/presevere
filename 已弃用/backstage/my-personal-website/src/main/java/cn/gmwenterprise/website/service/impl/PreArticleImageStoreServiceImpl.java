package cn.gmwenterprise.website.service.impl;

import cn.gmwenterprise.website.dao.PreArticleImageStoreDao;
import cn.gmwenterprise.website.domain.PreArticleImageStore;
import cn.gmwenterprise.website.service.PreArticleImageStoreService;
import cn.gmwenterprise.website.vo.ImageStoreVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PreArticleImageStoreServiceImpl implements PreArticleImageStoreService {
    private final PreArticleImageStoreDao preArticleImageStoreDao;

    public PreArticleImageStoreServiceImpl(PreArticleImageStoreDao preArticleImageStoreDao) {
        this.preArticleImageStoreDao = preArticleImageStoreDao;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return preArticleImageStoreDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ImageStoreVo vo) {
        return preArticleImageStoreDao.insert(domain(vo));
    }

    @Override
    public ImageStoreVo selectByPrimaryKey(Integer id) {
        return vo(preArticleImageStoreDao.selectByPrimaryKey(id));
    }

    @Override
    public List<ImageStoreVo> selectPage(ImageStoreVo vo) {
        return preArticleImageStoreDao.selectPage(domain(vo))
            .stream()
            .map(this::vo)
            .collect(Collectors.toList());
    }

    @Override
    public int updateByPrimaryKey(ImageStoreVo vo) {
        return preArticleImageStoreDao.updateByPrimaryKey(domain(vo));
    }

    @Override
    public String uploadImage(byte[] imageBytes, String imageName) {
        int lastPoint = imageName.lastIndexOf(".");
        String name = imageName.substring(0, lastPoint);
        String type = imageName.substring(lastPoint + 1);
        String contentType = getContentTypeByImageType(type);
        PreArticleImageStore domain = new PreArticleImageStore() {{
            setImageContent(imageBytes);
            setImageTitle(name);
            setImageType(type);
            setContentType(contentType);
        }};
        preArticleImageStoreDao.insert(domain);
        // 通过返回的主键来拼接访问地址
        return "preArticleImageStore/imageOutput/" + domain.getId();
    }

    private String getContentTypeByImageType(String imageType) {
        switch (imageType) {
            case "tif":
            case "tiff":
                return "image/tiff";
            case "fax":
                return "image/fax";
            case "gif":
                return "image/gif";
            case "ico":
                return "image/x-icon";
            case "jfif":
            case "jpe":
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            default:
                return null;
        }
    }

    private ImageStoreVo vo(PreArticleImageStore domain) {
        if (domain == null) {
            return null;
        }
        ImageStoreVo vo = new ImageStoreVo();
        BeanUtils.copyProperties(domain, vo);
        return vo;
    }

    private PreArticleImageStore domain(ImageStoreVo vo) {
        if (vo == null) {
            return null;
        }
        PreArticleImageStore domain = new PreArticleImageStore();
        BeanUtils.copyProperties(vo, domain);
        return domain;
    }
}
