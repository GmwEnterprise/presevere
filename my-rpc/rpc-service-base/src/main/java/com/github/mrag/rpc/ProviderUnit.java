package com.github.mrag.rpc;

import java.lang.reflect.Method;
import java.util.Map;

public class ProviderUnit {
    private String name;
    private String group;
    private String version;

    private Object target;
    private Map<String, Method> publicApi;

    public Object callApiMethod(String methodName, Object... params) throws Exception {
        Method method = publicApi.get(methodName);
        if (method == null) {
            throw new RpcServiceAccessNotFoundException("未找到相应服务!");
        }
        return method.invoke(target, params);
    }

    public String getName() {
        return name;
    }

    public ProviderUnit setName(String name) {
        this.name = name;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public ProviderUnit setGroup(String group) {
        this.group = group;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public ProviderUnit setVersion(String version) {
        this.version = version;
        return this;
    }

    public Object getTarget() {
        return target;
    }

    public ProviderUnit setTarget(Object target) {
        this.target = target;
        return this;
    }

    public Map<String, Method> getPublicApi() {
        return publicApi;
    }

    public ProviderUnit setPublicApi(Map<String, Method> publicApi) {
        this.publicApi = publicApi;
        return this;
    }
}
