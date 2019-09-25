package cn.gmwenterprise.website.web;

import cn.gmwenterprise.website.common.BaseController;
import cn.gmwenterprise.website.common.ResponseEntity;
import cn.gmwenterprise.website.config.mybatis.PageHelper;
import cn.gmwenterprise.website.service.${entityName}Service;
import cn.gmwenterprise.website.vo.${entityName}Vo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author gmw
 */
@RestController
@RequestMapping("/${entityAlias}")
public class ${entityName}Controller implements BaseController {
    private final ${entityName}Service ${entityAlias}Service;

    public ${entityName}Controller(${entityName}Service ${entityAlias}Service) {
        this.${entityAlias}Service = ${entityAlias}Service;
    }

    @GetMapping("/{${keyProperty}}")
    public ResponseEntity queryByPrimaryKey(@PathVariable ${keyPropertyType} ${keyProperty}) {
        return ok(${entityAlias}Service.selectByPrimaryKey(${keyProperty}));
    }

    @GetMapping
    public ResponseEntity queryPage(${entityName}Vo vo) {
        PageHelper.startPage(vo.getCurrentPage(), vo.getPageSize());
        return ok(PageHelper.page(${entityAlias}Service.selectPage(vo)));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity add(@RequestBody ${entityName}Vo vo) {
        ${entityAlias}Service.insert(vo);
        return ok();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping
    public ResponseEntity modify(@RequestBody ${entityName}Vo vo) {
        ${entityAlias}Service.updateByPrimaryKey(vo);
        return ok();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{${keyProperty}}")
    public ResponseEntity delete(@PathVariable ${keyPropertyType} ${keyProperty}) {
        ${entityAlias}Service.deleteByPrimaryKey(${keyProperty});
        return ok();
    }
}
