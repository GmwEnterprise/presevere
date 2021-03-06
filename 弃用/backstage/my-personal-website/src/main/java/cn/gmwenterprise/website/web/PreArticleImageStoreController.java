package cn.gmwenterprise.website.web;

import cn.gmwenterprise.website.common.AjaxResult;
import cn.gmwenterprise.website.common.BaseController;
import cn.gmwenterprise.website.service.ImageService;
import cn.gmwenterprise.website.service.PreArticleImageStoreService;
import cn.gmwenterprise.website.vo.ImageStoreVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author gmw
 */
@RestController
@RequestMapping("/preArticleImageStore")
public class PreArticleImageStoreController implements BaseController {
    private final PreArticleImageStoreService preArticleImageStoreService;
    private final ImageService imageService;

    public PreArticleImageStoreController(PreArticleImageStoreService preArticleImageStoreService, ImageService imageService) {
        this.preArticleImageStoreService = preArticleImageStoreService;
        this.imageService = imageService;
    }

    @GetMapping("/{id}")
    public AjaxResult queryByPrimaryKey(@PathVariable Integer id) {
        return ok(preArticleImageStoreService.selectByPrimaryKey(id));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public AjaxResult add(@RequestBody ImageStoreVo vo) {
        preArticleImageStoreService.insert(vo);
        return ok();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable Integer id) {
        preArticleImageStoreService.deleteByPrimaryKey(id);
        return ok();
    }

    /**
     * 上传图片。
     * markdown编辑文本时，选择上传图片时即调用此接口，将图片存入数据库，并返回图片访问直链。
     * 上传链接需要权限，返回的访问链接不需要权限
     *
     * @param request req
     * @return 图片访问链接
     * @throws IOException 文件解析异常
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/upload")
    public AjaxResult upload(HttpServletRequest request) throws IOException {
        StandardMultipartHttpServletRequest multipartHttpServletRequest = (StandardMultipartHttpServletRequest) request;
        MultipartFile image = multipartHttpServletRequest.getFile("image");
        String imageName = image.getOriginalFilename();
        String addr = preArticleImageStoreService.uploadImage(image.getBytes(), imageName);
        return ok(addr);
    }

    /**
     * 图片访问链接，不需要权限
     * TODO 缓存
     *
     * @param response resp
     * @param id       图片id
     * @param render   图片渲染方式
     * @throws IOException exp
     */
    @GetMapping(value = "/imageOutput/{id}")
    public void imageOutputById(
        HttpServletResponse response,
        @PathVariable Integer id,
        @RequestParam(
            required = false,
            defaultValue = ImageStoreVo.SIMPLE_RENDERING
        ) String render
    ) throws IOException {
        ImageStoreVo before = preArticleImageStoreService.selectByPrimaryKey(id);
        ImageStoreVo afterRendering = imageService.imageRender(before, render);
        response.setContentType(afterRendering.getContentType());
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(afterRendering.getImageContent());
        outputStream.flush();
        outputStream.close();
    }
}
