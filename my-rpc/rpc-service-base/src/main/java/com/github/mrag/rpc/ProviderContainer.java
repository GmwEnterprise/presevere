package com.github.mrag.rpc;

import java.util.HashMap;
import java.util.Map;

/**
 * 装配本地所有提供方服务，来自外部IP的远程调用全部由该类拦截并调用
 */

public final class ProviderContainer {
    private static final Map<String, ProviderUnit> providerMap = new HashMap<>();

    public static void addProvider(ProviderUnit unit) {
        providerMap.put(unit.getName(), unit);
    }

    public static Object call(String serviceName, String methodName, Object... params) throws Exception {
        return providerMap.get(serviceName).callApiMethod(methodName, params);
    }
}
