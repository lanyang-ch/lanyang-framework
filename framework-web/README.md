# framework-web 模块

## 模块简介

framework-web 是一个基于 Spring Boot 的 Web 开发框架模块，提供了丰富的 Web 开发工具和功能，包括认证授权、数据权限、异常处理、响应封装等核心功能，旨在简化 Web 应用的开发过程。

## 功能特性

- ✅ 统一的认证授权体系
- ✅ 细粒度的数据权限控制
- ✅ 全局异常处理
- ✅ 统一的响应格式
- ✅ 分页工具支持
- ✅ Feign 请求拦截器
- ✅ JSON 序列化配置
- ✅ 登录用户上下文管理
- ✅ 各种 Web 开发工具类

## 目录结构

```
framework-web/
├── src/main/java/com/lanyang/framework/web/
│   ├── annotation/              # 注解定义
│   │   ├── group/               # 验证分组注解
│   │   │   ├── AddGroup.java    # 添加操作分组
│   │   │   ├── DefaultGroup.java # 默认分组
│   │   │   ├── Group.java       # 分组基类
│   │   │   └── UpdateGroup.java # 更新操作分组
│   │   ├── DataScope.java       # 数据权限注解
│   │   ├── EnableDataScopeInterceptor.java # 启用数据权限拦截器
│   │   ├── EnableGlobalFeignInterceptor.java # 启用全局Feign拦截器
│   │   ├── EnableSecurityInterceptor.java # 启用安全拦截器
│   │   ├── EnableWebCommonConfig.java # 启用Web通用配置
│   │   ├── InnerAuth.java       # 内部认证注解
│   │   ├── Logic.java           # 逻辑删除注解
│   │   ├── RequiresLogin.java   # 登录认证注解
│   │   ├── RequiresPermissions.java # 权限认证注解
│   │   └── RequiresRoles.java   # 角色认证注解
│   ├── aspect/                  # 切面类
│   │   ├── DataScopeAspect.java # 数据权限切面
│   │   ├── InnerAuthAspect.java # 内部认证切面
│   │   └── UserAuthAspect.java  # 用户认证切面
│   ├── config/                  # 配置类
│   │   ├── FeignRequestConfig.java # Feign请求配置
│   │   ├── JsonSerializeConfig.java # JSON序列化配置
│   │   ├── SecurityAutoConfig.java # 安全自动配置
│   │   └── WebConfig.java       # Web配置
│   ├── constant/                # 常量定义
│   │   └── LoginCacheConstants.java # 登录缓存常量
│   ├── context/                 # 上下文
│   │   └── SecurityContextHolder.java # 安全上下文
│   ├── domain/                  # 领域模型
│   │   ├── BaseQueryEntity.java # 基础查询实体
│   │   ├── DataAuth.java        # 数据权限
│   │   ├── LoginUser.java       # 登录用户
│   │   └── R.java               # 响应结果
│   ├── enums/                   # 枚举类
│   │   └── JdbcTypeEnum.java    # JDBC类型枚举
│   ├── exception/               # 异常类
│   │   ├── AuthException.java   # 认证异常
│   │   ├── BizException.java    # 业务异常
│   │   ├── ExceptionResolvable.java # 可解析异常接口
│   │   ├── InnerAuthException.java # 内部认证异常
│   │   ├── NotPermissionException.java # 无权限异常
│   │   ├── NotRoleException.java # 无角色异常
│   │   ├── RestControllerExceptionHandle.java # 全局异常处理器
│   │   └── ServiceException.java # 服务异常
│   ├── handler/                 # 处理器
│   │   └── LoginUserHandler.java # 登录用户处理器
│   ├── interceptor/             # 拦截器
│   │   ├── GlobalFeignRequestInterceptor.java # 全局Feign请求拦截器
│   │   └── SecurityInterceptor.java # 安全拦截器
│   └── util/                    # 工具类
│       ├── page/                # 分页相关工具
│       │   ├── BasePageQuery.java # 基础分页查询
│       │   ├── PageData.java    # 分页数据
│       │   ├── PageDomain.java  # 分页领域
│       │   ├── PageUtils.java   # 分页工具
│       │   ├── TableDataInfo.java # 表格数据信息
│       │   └── TableSupport.java # 表格支持
│       ├── AuthUtils.java       # 认证工具
│       ├── IpUtils.java         # IP工具
│       ├── LoginUserUtils.java  # 登录用户工具
│       ├── PasswordUtils.java   # 密码工具
│       ├── SecurityUtils.java   # 安全工具
│       ├── ServletUtils.java    # Servlet工具
│       ├── SpringUtils.java     # Spring工具
│       └── SqlUtil.java         # SQL工具
├── src/main/resources/
│   └── META-INF/spring/         # Spring 自动配置
├── pom.xml                      # Maven 依赖配置
└── README.md                    # 模块说明文档
```

## 核心组件

### 1. 认证授权体系

#### 1.1 注解
- **@RequiresLogin**: 要求用户登录
- **@RequiresPermissions**: 要求用户拥有指定权限
- **@RequiresRoles**: 要求用户拥有指定角色
- **@InnerAuth**: 内部接口认证，防止外部调用

#### 1.2 实现
- **SecurityInterceptor**: 安全拦截器，处理认证授权逻辑
- **UserAuthAspect**: 用户认证切面，处理方法级别的认证
- **SecurityContextHolder**: 安全上下文，存储用户信息

### 2. 数据权限控制

#### 2.1 注解
- **@DataScope**: 数据权限注解，控制数据访问范围

#### 2.2 实现
- **DataScopeAspect**: 数据权限切面，根据用户角色生成数据过滤条件
- **DataAuth**: 数据权限实体，存储数据权限信息

### 3. 异常处理

- **RestControllerExceptionHandle**: 全局异常处理器，统一处理异常
- **各种异常类**: 定义了各种业务异常和认证异常

### 4. 响应封装

- **R**: 统一响应结果类，封装API响应

### 5. 分页工具

- **PageUtils**: 分页工具类，处理分页逻辑
- **TableDataInfo**: 表格数据信息，封装分页查询结果
- **BasePageQuery**: 基础分页查询参数

### 6. 工具类

- **AuthUtils**: 认证相关工具
- **LoginUserUtils**: 登录用户相关工具
- **PasswordUtils**: 密码加密和验证工具
- **ServletUtils**: Servlet相关工具
- **SpringUtils**: Spring相关工具
- **IpUtils**: IP地址相关工具
- **SqlUtil**: SQL相关工具

## 快速开始

### 1. 添加依赖

在项目的 pom.xml 文件中添加 framework-web 模块依赖：

```xml
<dependency>
    <groupId>com.lanyang.framework</groupId>
    <artifactId>framework-web</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2. 启用 Web 配置

在 Spring Boot 应用的主配置类上添加 `@EnableWebCommonConfig` 注解：

```java
@SpringBootApplication
@EnableWebCommonConfig
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### 3. 启用安全拦截器

在 Spring Boot 应用的主配置类上添加 `@EnableSecurityInterceptor` 注解：

```java
@SpringBootApplication
@EnableWebCommonConfig
@EnableSecurityInterceptor
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### 4. 启用数据权限

在 Spring Boot 应用的主配置类上添加 `@EnableDataScopeInterceptor` 注解：

```java
@SpringBootApplication
@EnableWebCommonConfig
@EnableSecurityInterceptor
@EnableDataScopeInterceptor
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### 5. 启用全局 Feign 拦截器

在 Spring Boot 应用的主配置类上添加 `@EnableGlobalFeignInterceptor` 注解：

```java
@SpringBootApplication
@EnableWebCommonConfig
@EnableGlobalFeignInterceptor
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

## 使用示例

### 1. 认证授权

#### 1.1 登录认证

```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @PostMapping("/login")
    public R login(@RequestBody LoginRequest request) {
        // 验证用户名密码
        // 生成token
        // 构建LoginUser对象
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(1L);
        loginUser.setUsername("admin");
        loginUser.setRoles(Arrays.asList("admin"));
        loginUser.setPermissions(Arrays.asList("user:list", "user:add"));
        
        // 存储到安全上下文
        SecurityContextHolder.setLoginUser(loginUser);
        
        return R.ok().put("token", "generated-token");
    }
    
    @RequiresLogin
    @GetMapping("/info")
    public R getInfo() {
        LoginUser loginUser = SecurityContextHolder.getLoginUser();
        return R.ok().put("user", loginUser);
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
    
    @RequiresPermissions("user:add")
    @PostMapping("/add")
    public R add(@RequestBody User user) {
        // 添加用户
        return R.ok();
    }
    
    @RequiresRoles("admin")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id) {
        // 删除用户
        return R.ok();
    }
}
```

#### 1.3 内部接口

```java
@RestController
@RequestMapping("/api/inner")
public class InnerController {
    
    @InnerAuth
    @PostMapping("/sync")
    public R syncData(@RequestBody SyncRequest request) {
        // 内部接口逻辑
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

### 3. 响应封装

```java
@RestController
@RequestMapping("/api/test")
public class TestController {
    
    @GetMapping("/success")
    public R success() {
        return R.ok().put("data", "success");
    }
    
    @GetMapping("/error")
    public R error() {
        return R.error("操作失败");
    }
    
    @GetMapping("/exception")
    public R exception() {
        throw new BizException("业务异常");
    }
}
```

### 4. 分页查询

```java
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/list")
    public TableDataInfo list(UserQuery query) {
        startPage(); // 开始分页
        List<User> list = userService.selectUserList(query);
        return getDataTable(list); // 封装分页结果
    }
    
    // 分页工具方法
    protected void startPage() {
        PageUtils.startPage();
    }
    
    protected TableDataInfo getDataTable(List<?> list) {
        return PageUtils.getDataTable(list);
    }
}
```

### 5. 工具类使用

#### 5.1 登录用户工具

```java
// 获取当前登录用户
LoginUser loginUser = LoginUserUtils.getLoginUser();

// 获取用户ID
Long userId = LoginUserUtils.getUserId();

// 获取用户名
String username = LoginUserUtils.getUsername();
```

#### 5.2 密码工具

```java
// 加密密码
String encryptedPassword = PasswordUtils.encrypt("123456");

// 验证密码
boolean matches = PasswordUtils.matches("123456", encryptedPassword);
```

#### 5.3 Servlet工具

```java
// 获取请求参数
String param = ServletUtils.getParameter(request, "param");

// 获取IP地址
String ip = ServletUtils.getClientIP(request);

// 获取请求URI
String uri = ServletUtils.getRequestURI(request);
```

## 配置说明

### 1. 安全配置

```yaml
security:
  # 白名单路径
  whitelist:
    - /api/auth/login
    - /api/auth/logout
    - /api/public/**
  # token过期时间（秒）
  token:
    expireTime: 3600
  # 加密密钥
  encrypt:
    key: your-secret-key
```

### 2. 分页配置

```yaml
page:
  # 默认页码
  defaultPageNum: 1
  # 默认每页大小
  defaultPageSize: 10
  # 最大每页大小
  maxPageSize: 100
```

## 工作原理

### 1. 认证流程

1. 用户登录，生成token
2. 将用户信息存储到 SecurityContextHolder
3. 后续请求携带token
4. SecurityInterceptor 拦截请求，验证token
5. 根据注解 @RequiresLogin、@RequiresPermissions、@RequiresRoles 进行权限验证
6. 验证通过，继续处理请求；验证失败，抛出相应异常

### 2. 数据权限流程

1. 方法添加 @DataScope 注解
2. DataScopeAspect 切面拦截方法
3. 获取当前登录用户的角色和权限
4. 根据角色生成数据过滤条件
5. 将过滤条件添加到查询参数中
6. 执行查询，返回过滤后的数据

### 3. 异常处理流程

1. 业务代码抛出异常
2. RestControllerExceptionHandle 捕获异常
3. 根据异常类型生成相应的响应
4. 返回统一格式的错误响应

## 注意事项

1. **认证配置**：
   - 需要实现 LoginUserHandler 接口，提供获取登录用户信息的方法
   - 需要配置 token 生成和验证逻辑

2. **数据权限**：
   - @DataScope 注解需要指定表别名
   - 需要在 SQL 查询中使用这些别名

3. **异常处理**：
   - 业务异常建议继承 BizException
   - 认证异常建议继承 AuthException

4. **分页查询**：
   - 使用 PageUtils.startPage() 开始分页
   - 使用 PageUtils.getDataTable() 封装结果

5. **工具类**：
   - 工具类都是静态方法，可以直接调用
   - 部分工具类需要在 Web 环境中使用

## 依赖说明

- **Spring Boot**：提供基础框架支持
- **Spring Security**：提供安全认证支持
- **MyBatis-Plus**：提供 ORM 支持（可选）
- **Feign**：提供服务间调用支持（可选）
- **Fastjson**：提供 JSON 序列化支持
- **Lombok**：简化代码开发

## 常见问题

### Q: 如何自定义认证逻辑？

A: 实现 LoginUserHandler 接口，重写 getLoginUser 方法，返回自定义的登录用户信息。

### Q: 如何扩展数据权限？

A: 可以在 DataScopeAspect 中添加自定义的数据权限逻辑，或者继承 DataScopeAspect 重写相关方法。

### Q: 如何自定义异常处理？

A: 可以继承 RestControllerExceptionHandle 类，重写相应的异常处理方法。

### Q: 如何修改响应格式？

A: 可以修改 R 类的实现，或者继承 R 类添加自定义方法。

### Q: 如何配置白名单？

A: 在 application.yml 文件中配置 security.whitelist 属性，添加不需要认证的路径。

## 版本历史

- **1.0.0**：初始版本，提供基本的 Web 开发功能

## 许可证

本模块采用 MIT 许可证，详见项目根目录的 LICENSE 文件。