package cn.presevere.next.rest;

import cn.presevere.next.domain.Post;
import cn.presevere.next.service.PostService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/post")
public class PostController {
    @Resource
    private PostService postService;

    @ApiOperation(
        value = "通过ID查找文章",
        httpMethod = "GET")
    @GetMapping("/findById/{id}")
    public Post findById(@PathVariable Long id) {
        return postService.findById(id);
    }
}
