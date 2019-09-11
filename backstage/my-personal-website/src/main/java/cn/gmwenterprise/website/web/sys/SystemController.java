package cn.gmwenterprise.website.web.sys;

import cn.gmwenterprise.website.common.BaseController;
import cn.gmwenterprise.website.common.ResponseEntity;
import cn.gmwenterprise.website.common.SpringContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system")
public class SystemController implements BaseController {

    @GetMapping("/t")
    public String testApi() {
        return "WELCOME :-)";
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
