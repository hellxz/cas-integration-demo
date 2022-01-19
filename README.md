# cas-integration-demo
## 说明
此仓库为本人 Hellxz (hellxz001@foxmail.com) 在[本人CSDN](https://blog.csdn.net/u012586326)与[本人博客园](https://www.cnblogs.com/hellxz)同步更新，是 `CAS` 相关博客对应的代码仓库

本人CAS相关博客列表：
- [CAS学习笔记一：CAS 授权服务器简易搭建](https://blog.csdn.net/u012586326/article/details/122195507)
- [CAS学习笔记二：CAS单点登录流程](https://blog.csdn.net/u012586326/article/details/122320562)
- [CAS学习笔记三：SpringBoot自动/手动配置方式集成CAS单点登录](https://blog.csdn.net/u012586326/article/details/122331348)
- [CAS学习笔记四：CAS单点登出流程](https://blog.csdn.net/u012586326/article/details/122568510)
- [CAS学习笔记五：SpringBoot自动/手动配置方式集成CAS单点登出]()

## 快速开始
使用 IDE 以 `Maven` 方式引入最外层 `pom.xml`，修改 `application.properties`，按需启动模块即可。
其中：
- `demo-a-auto-config` 基于 `CAS` 官方提供 `SpringBoot` 依赖方式集成 `CAS` 客户端，占用端口号 `8081`
- `demo-b-manual-config` 基于 `Bean` 注册方式，手动配置各过滤器、监听器的方式集成 `CAS` 客户端，占用端口号 `8082`

测试前置条件是参考第一篇博客搭建 `CAS` 服务端，启动成功后访问对应端口号 `/index` 或 `/test` 验证与实验。

PS: 强烈建议读者自己实践一遍。