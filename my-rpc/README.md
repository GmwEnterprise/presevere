# 自己实现的基于Netty的RPC调用框架

## 基本设计

- 注册中心模块
- 服务模块
- 测试模块

## 模块功能说明

服务在启动时要向注册中心请求建立连接，并注册自己提供的所有可调用服务。

注册中心与服务之间用心跳来维护连接，心跳检测失败则断开连接且注册中心移除服务。

消费方调用提供方的服务时：

1. 向注册中心发送将要调用的服务信息以及具体调用的方法名称以及方法参数；
2. 等待注册中心返回调用结果，其中包含一个状态值用来表明服务端是否成功运行相应方法，如果成功则提供返回值，失败则提供异常信息
3. 消费方可以设定调用等待延时，超过时间则抛出请求服务超时异常
4. 