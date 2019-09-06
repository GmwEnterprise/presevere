package cn.gmwenterprise.website.web.sys;

import cn.gmwenterprise.website.common.BaseController;
import cn.gmwenterprise.website.common.ResponseEntity;
import cn.gmwenterprise.website.common.SpringContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system")
public class SystemController implements BaseController {

    @GetMapping("/moduleColumns/{moduleName}")
    public ResponseEntity moduleColumns(@PathVariable String moduleName) throws ClassNotFoundException {
        if (Character.isLowerCase(moduleName.charAt(0))) {
            String className = Character.toUpperCase(moduleName.charAt(0)) + moduleName.substring(1);
            return ok(Arrays.stream(Class.forName("cn.gmwenterprise.website.vo." + className + "Vo").getDeclaredFields())
                .filter(field -> !"currentPage".equals(field.getName()) && !"pageSize".equals(field.getName()))
                .map(Field::getName)
                .collect(Collectors.toList()));
        }
        return fail("moduleName不符合驼峰命名规则");
    }

    @GetMapping("/allRouters")
    public ResponseEntity getAllRouter() {
        RequestMappingHandlerMapping bean = SpringContext.getBean(RequestMappingHandlerMapping.class);
        List<Object> allRouters =
            bean.getHandlerMethods().keySet().stream()
                .map(item -> {
                    Set<RequestMethod> methods = item.getMethodsCondition().getMethods();
                    Set<String> patterns = item.getPatternsCondition().getPatterns();
                    Map<String, Object> map = new HashMap<>();
                    map.put("method", methods.stream().findFirst().orElse(RequestMethod.TRACE).toString());
                    map.put("pattern", patterns.stream().findFirst().orElse("NULL"));
                    return map;
                })
                .collect(Collectors.toList());
        return ok(allRouters);
    }
}
