# lanyang-framework

## 项目简介

lanyang-framework 是一个基于 Spring Boot 的企业级开发框架，提供了丰富的功能模块和工具类，旨在简化企业应用的开发过程，提高开发效率和代码质量。

## 核心特性

- ✅ 模块化设计，按需引入
- ✅ 统一的认证授权体系
- ✅ 数据权限控制
- ✅ 支付功能集成（支付宝、微信支付）
- ✅ Netty 网络通信框架
- ✅ Redis 缓存集成
- ✅ MyBatis-Plus 增强
- ✅ 日志管理
- ✅ Swagger 文档生成
- ✅ 丰富的工具类库

## 模块结构

```
lanyang-framework/
├── framework-common/          # 通用工具模块
│   ├── src/main/java/com/lanyang/framework/common/  # 通用代码
│   └── pom.xml                # 模块依赖
├── framework-dependencies/    # 依赖管理模块
│   └── pom.xml                # 统一依赖版本管理
├── framework-log/             # 日志模块
│   ├── src/main/java/com/lanyang/framework/log/    # 日志相关代码
│   └── pom.xml                # 模块依赖
├── framework-mybatis-plus/    # MyBatis-Plus 增强模块
│   ├── src/main/java/com/lanyang/framework/mp/     # MyBatis-Plus 增强代码
│   └── pom.xml                # 模块依赖
├── framework-netty/           # Netty 网络通信模块
│   ├── src/main/java/com/lanyang/framework/netty/  # Netty 相关代码
│   ├── README.md              # 模块说明
│   └── pom.xml                # 模块依赖
├── framework-pay/             # 支付模块
│   ├── src/main/java/com/lanyang/framework/pay/    # 支付相关代码
│   ├── README.md              # 模块说明
│   └── pom.xml                # 模块依赖
├── framework-redis/           # Redis 缓存模块
│   ├── src/main/java/com/lanyang/framework/redis/  # Redis 相关代码
│   └── pom.xml                # 模块依赖
├── framework-swagger/         # Swagger 文档模块
│   ├── src/main/java/com/lanyang/framework/swagger/ # Swagger 相关代码
│   └── pom.xml                # 模块依赖
├── framework-web/             # Web 开发模块
│   ├── src/main/java/com/lanyang/framework/web/    # Web 相关代码
│   ├── README.md              # 模块说明
│   └── pom.xml                # 模块依赖
├── .gitignore                 # Git 忽略文件
├── LICENSE                    # 许可证文件
├── pom.xml                    # 项目父 POM
└── README.md                  # 项目说明文档
```

## 模块说明

### 1. framework-common

通用工具模块，提供了各种工具类和通用功能：

- **常量定义**：网络、安全、Token 等常量
- **上下文管理**：TransmittableThreadLocalContextHolder
- **领域模型**：BaseQueryEntity、R、TreeVO 等
- **枚举类**：AppType、CommonStatusEnum 等
- **工具类**：BeanConvertUtils、JsonUtils、JwtUtils 等

### 2. framework-dependencies

依赖管理模块，统一管理项目的依赖版本，确保各模块使用一致的依赖版本。

### 3. framework-log

日志模块，提供了统一的日志注解和日志处理：

- **@Log 注解**：用于标记需要记录日志的方法
- **日志枚举**：BusinessStatus、BusinessType、OperatorType 等

### 4. framework-mybatis-plus

MyBatis-Plus 增强模块，提供了租户隔离、数据权限等功能：

- **租户隔离**：基于 MyBatis-Plus 的租户插件
- **数据权限**：基于注解的数据权限控制
- **基础实体**：BaseEntity、BaseTenantEntity
- **元对象处理器**：自动填充创建时间、修改时间等

### 5. framework-netty

Netty 网络通信模块，提供了 TCP 服务器功能：

- **自动配置**：基于 Spring Boot 的自动配置
- **JSON 解码**：支持 JSON 格式数据传输
- **可配置**：线程池、心跳机制等可配置
- **自定义处理器**：支持自定义通道处理器

### 6. framework-pay

支付模块，集成了支付宝和微信支付：

- **统一接口**：基于策略模式的统一支付接口
- **支付宝**：支持 JSAPI 支付和信用支付
- **微信支付**：支持 JSAPI 支付和企业付款
- **通知处理**：统一的支付通知处理

### 7. framework-redis

Redis 缓存模块，提供了 Redis 操作的封装：

- **RedisService**：Redis 操作服务
- **RedisUtils**：Redis 操作工具类
- **分布式锁**：基于 Redis 的分布式锁
- **消息发布订阅**：基于 Redis 的消息发布订阅

### 8. framework-swagger

Swagger 文档模块，提供了 API 文档生成功能：

- **自动配置**：基于 Spring Boot 的自动配置
- **分组配置**：支持 API 分组
- **全局参数**：支持全局参数配置

### 9. framework-web

Web 开发模块，提供了 Web 应用开发的核心功能：

- **认证授权**：基于注解的认证授权
- **数据权限**：细粒度的数据权限控制
- **异常处理**：全局异常处理
- **响应封装**：统一的响应格式
- **分页工具**：内置分页功能
- **Feign 拦截器**：全局 Feign 请求拦截器
- **工具类**：各种 Web 开发工具类

## 快速开始

### 1. 环境要求

- JDK 1.8+
- Maven 3.6+
- Spring Boot 2.7+

### 2. 项目结构

推荐的项目结构：

```
your-project/
├── your-project-api/         # API 模块
├── your-project-service/     # 服务模块
├── your-project-dao/         # 数据访问模块
├── your-project-common/      # 项目通用模块
├── pom.xml                   # 项目 POM
└── README.md                 # 项目说明
```

### 3. 添加依赖

在项目的 pom.xml 文件中添加 lanyang-framework 模块依赖：

```xml
<dependency>
    <groupId>com.lanyang.framework</groupId>
    <artifactId>framework-web</artifactId>
    <version>1.0.0</version>
</dependency>

<!-- 根据需要添加其他模块 -->
<dependency>
    <groupId>com.lanyang.framework</groupId>
    <artifactId>framework-pay</artifactId>
    <version>1.0.0</version>
</dependency>

<dependency>
    <groupId>com.lanyang.framework</groupId>
    <artifactId>framework-netty</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 4. 启用配置

在 Spring Boot 应用的主配置类上添加相应的启用注解：

```java
@SpringBootApplication
@EnableWebCommonConfig           // 启用 Web 通用配置
@EnableSecurityInterceptor       // 启用安全拦截器
@EnableDataScopeInterceptor      // 启用数据权限拦截器
@EnableGlobalFeignInterceptor    // 启用全局 Feign 拦截器
@EnableCommonSwagger             // 启用 Swagger 文档
@EnableNettyConfig               // 启用 Netty 配置
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

## 核心功能使用

### 1. 认证授权

#### 1.1 登录认证

```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @PostMapping("/login")
    public R login(@RequestBody LoginRequest request) {
        // 验证用户名密码
        // 生成 token
        // 构建 LoginUser 对象
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(1L);
        loginUser.setUsername("admin");
        loginUser.setRoles(Arrays.asList("admin"));
        loginUser.setPermissions(Arrays.asList("user:list", "user:add"));
        
        // 存储到安全上下文
        SecurityContextHolder.setLoginUser(loginUser);
        
        return R.ok().put("token", "generated-token");
    }
}
```

#### 1.2 权限控制

```java
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @RequiresPermissions("user:list")
    @GetMapping("/list")
    public R list() {
        // 查询用户列表
        return R.ok().put("users", new ArrayList<>());
    }
    
    @RequiresRoles("admin")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id) {
        // 删除用户
        return R.ok();
    }
}
```

### 2. 数据权限

```java
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @DataScope(deptAlias = "d", userAlias = "u")
    @Override
    public List<User> selectUserList(UserQuery query) {
        return userMapper.selectUserList(query);
    }
}
```

### 3. 支付功能

```java
@Autowired
private PayStrategyFactory payStrategyFactory;

// 创建微信支付订单
WechatCreateOrderParam param = new WechatCreateOrderParam();
param.setPayType(PayType.WECHAT);
param.setOutTradeNo("ORDER_" + System.currentTimeMillis());
param.setTotalFee(new BigDecimal("1.00"));
param.setBody("测试商品");
param.setOpenid("user_openid");

PreOrderResult result = payStrategyFactory.createOrder(param);
// 使用 result 中的参数调起微信支付
```

### 4. Netty 服务器

```java
// 只需在配置类上添加 @EnableNettyConfig 注解
// Netty 服务器会在应用启动时自动启动
```

### 5. Redis 缓存

```java
@Autowired
private RedisService redisService;

// 设置缓存
redisService.set("key", "value", 3600);

// 获取缓存
String value = redisService.get("key");

// 删除缓存
redisService.delete("key");
```

### 6. 日志记录

```java
@Log(title = "用户管理", businessType = BusinessType.INSERT)
@PostMapping("/add")
public R add(@RequestBody User user) {
    // 添加用户
    return R.ok();
}
```

## 配置说明

### 1. 通用配置

```yaml
# 服务器配置
server:
  port: 8080
  servlet:
    context-path: /api

# Spring 配置
spring:
  # 数据源配置
  datasource:
    url: jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false
    username: root
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  # Redis 配置
  redis:
    host: localhost
    port: 6379
    password:
    database: 0
```

### 2. 安全配置

```yaml
security:
  # 白名单路径
  whitelist:
    - /api/auth/login
    - /api/auth/logout
    - /api/public/**
  # token 过期时间（秒）
  token:
    expireTime: 3600
  # 加密密钥
  encrypt:
    key: your-secret-key
```

### 3. 支付配置

```yaml
# 微信支付配置
wechat:
  pay:
    appId: your_app_id
    mchId: your_mch_id
    apiKey: your_api_key
    notifyUrl: http://your-domain.com/pay/wechat/notify

# 支付宝配置
ali:
  pay:
    appId: your_app_id
    privateKey: your_private_key
    publicKey: alipay_public_key
    notifyUrl: http://your-domain.com/pay/ali/notify
```

### 4. Netty 配置

```yaml
netty:
  port: 18050                    # 服务器端口
  bossCount: 1                    # Boss 线程数
  workerCount: 50                 # Worker 线程数
  keepalive: true                 # 是否保持连接
  backlog: 100                    # 连接队列大小
  heartbeat: 2                    # 心跳周期（秒）
```

## 最佳实践

### 1. 项目结构

- **分层架构**：API 层、服务层、数据访问层清晰分离
- **模块化设计**：按功能模块划分代码，提高代码复用性
- **配置集中管理**：使用配置文件管理环境相关配置

### 2. 代码规范

- **命名规范**：类名使用大驼峰，方法名和变量名使用小驼峰
- **注释规范**：关键代码添加注释，使用 JavaDoc 格式
- **异常处理**：统一的异常处理机制，避免直接抛出异常
- **日志记录**：关键操作添加日志记录，便于问题排查

### 3. 性能优化

- **缓存使用**：合理使用 Redis 缓存，减少数据库访问
- **连接池**：使用数据库连接池和 HTTP 连接池
- **异步处理**：耗时操作使用异步处理
- **批量操作**：批量处理数据，减少网络开销

### 4. 安全措施

- **密码加密**：使用 PasswordUtils 进行密码加密
- **权限控制**：使用 @RequiresPermissions 和 @RequiresRoles 进行权限控制
- **数据验证**：对输入参数进行严格验证
- **SQL 注入防护**：使用参数化查询，避免 SQL 注入

## 常见问题

### Q: 如何添加新的模块？

A: 在 framework- 目录下创建新的模块目录，添加 pom.xml 文件，然后在父 POM 中添加模块引用。

### Q: 如何自定义认证逻辑？

A: 实现 LoginUserHandler 接口，重写 getLoginUser 方法，返回自定义的登录用户信息。

### Q: 如何扩展数据权限？

A: 可以在 DataScopeAspect 中添加自定义的数据权限逻辑，或者继承 DataScopeAspect 重写相关方法。

### Q: 如何添加新的支付方式？

A: 实现 PayStrategy 接口，添加对应的配置类，然后在 Spring 容器中注册该策略。

### Q: 如何配置 Swagger 文档？

A: 在 application.yml 文件中配置 swagger 相关属性，或者通过 SwaggerProperties 类进行配置。

## 版本历史

- **1.0.0**：初始版本，提供基本的框架功能

## 许可证

本项目采用 MIT 许可证，详见 LICENSE 文件。

## 联系方式

- **作者**：lanyang


---

**lanyang-framework** - 让开发更简单，让代码更优雅！