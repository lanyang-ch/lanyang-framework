# framework-pay 模块

## 模块简介

framework-pay 是一个基于 Spring Boot 的支付框架模块，提供了统一的支付接口和策略模式实现，支持支付宝和微信支付两种主流支付方式。

## 功能特性

- ✅ 统一的支付接口设计
- ✅ 支持支付宝和微信支付
- ✅ 策略模式实现，易于扩展
- ✅ 完整的支付流程支持（下单、查询、关闭、退款、查询退款）
- ✅ 支持微信企业付款到用户
- ✅ 支持支付宝信用支付
- ✅ 提供回调通知处理
- ✅ 配置化管理支付参数

## 目录结构

```
framework-pay/
├── src/main/java/com/lanyang/framework/pay/
│   ├── config/                  # 配置类
│   │   ├── ali/                 # 支付宝配置
│   │   ├── wechat/              # 微信支付配置
│   │   ├── HttpClientConfig.java      # HTTP 客户端配置
│   │   └── PayHttpClientProperties.java # HTTP 客户端属性
│   ├── domain/                  # 领域模型
│   │   ├── credit/              # 信用支付相关
│   │   ├── jsapi/               # JSAPI 支付相关
│   │   ├── BaseParam.java       # 基础参数
│   │   └── BaseResult.java      # 基础结果
│   ├── enums/                   # 枚举类
│   │   ├── ali/                 # 支付宝相关枚举
│   │   ├── wechat/              # 微信支付相关枚举
│   │   ├── CommonTradeStatus.java   # 通用交易状态
│   │   ├── CurrencyType.java        # 货币类型
│   │   └── PayType.java             # 支付类型
│   ├── exception/               # 异常类
│   │   └── PayException.java    # 支付异常
│   ├── handler/                 # 处理器
│   │   ├── AliNotifyHandler.java    # 支付宝通知处理
│   │   └── WechatNotifyHandler.java # 微信通知处理
│   ├── service/                 # 服务类
│   │   └── WechatTransferService.java # 微信转账服务
│   ├── strategy/                # 策略模式
│   │   ├── AliPayStrategy.java      # 支付宝策略
│   │   ├── PayStrategy.java         # 支付策略接口
│   │   ├── PayStrategyFactory.java  # 支付策略工厂
│   │   └── WechatPayStrategy.java   # 微信支付策略
│   └── util/                    # 工具类
│       ├── AliCreditUtils.java      # 支付宝信用支付工具
│       ├── AliPayUtils.java         # 支付宝工具
│       ├── BigDecimalConvertUtils.java # 金额转换工具
│       ├── WXPayUtility.java        # 微信支付工具
│       ├── WechatPayUtils.java      # 微信支付工具
│       └── WechatTransferTest.java  # 微信转账测试
├── src/main/resources/
│   ├── META-INF/spring/         # Spring 自动配置
│   └── application-pay-http-client.yml.example # HTTP 客户端配置示例
├── pom.xml                      # Maven 依赖配置
└── README.md                    # 模块说明文档
```

## 核心组件

### 1. 支付策略接口 (PayStrategy)

定义了统一的支付操作接口，包括：
- `createOrder()`: 创建支付订单
- `queryOrder()`: 查询订单状态
- `closeOrder()`: 关闭订单
- `refundOrder()`: 申请退款
- `queryRefundOrder()`: 查询退款状态

### 2. 支付策略工厂 (PayStrategyFactory)

根据支付类型动态选择对应的支付策略，提供统一的支付操作入口。

### 3. 具体支付策略实现

- **AliPayStrategy**: 支付宝支付策略实现
- **WechatPayStrategy**: 微信支付策略实现

### 4. 配置管理

- **支付宝配置**: `AliPayInitConfig`, `AliPayInitProperties`, `AliPayCreditDepositProperties`
- **微信支付配置**: `WechatPayConfig`, `WechatPayProperties`
- **HTTP 客户端配置**: `HttpClientConfig`, `PayHttpClientProperties`

### 5. 领域模型

- **JSAPI 支付相关**: 包含下单、查询、退款等参数和结果类
- **信用支付相关**: 包含支付宝信用支付相关参数和结果类

### 6. 通知处理器

- **AliNotifyHandler**: 处理支付宝异步通知
- **WechatNotifyHandler**: 处理微信支付异步通知

### 7. 服务类

- **WechatTransferService**: 微信企业付款到用户服务

## 支持的支付方式

### 1. 支付宝

- **JSAPI 支付**: 网页端支付
- **信用支付**: 支持信用预授权和扣款

### 2. 微信支付

- **JSAPI 支付**: 公众号支付
- **企业付款**: 支持企业付款到用户零钱

## 快速开始

### 1. 添加依赖

在项目的 pom.xml 文件中添加 framework-pay 模块依赖：

```xml
<dependency>
    <groupId>com.lanyang.framework</groupId>
    <artifactId>framework-pay</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2. 配置支付参数

#### 2.1 微信支付配置

在 application.yml 文件中添加微信支付配置：

```yaml
wechat:
  pay:
    appId: your_app_id
    mchId: your_mch_id
    apiKey: your_api_key
    notifyUrl: http://your-domain.com/pay/wechat/notify
    refundNotifyUrl: http://your-domain.com/pay/wechat/refund-notify
    certPath: /path/to/wechat/cert.p12
```

#### 2.2 支付宝配置

在 application.yml 文件中添加支付宝配置：

```yaml
ali:
  pay:
    appId: your_app_id
    privateKey: your_private_key
    publicKey: alipay_public_key
    notifyUrl: http://your-domain.com/pay/ali/notify
    returnUrl: http://your-domain.com/pay/ali/return
    appCertPath: /path/to/alipay/app_cert.crt
    alipayCertPath: /path/to/alipay/alipay_cert.crt
    alipayRootCertPath: /path/to/alipay/alipay_root_cert.crt
```

### 3. 使用支付服务

#### 3.1 创建支付订单

```java
import com.lanyang.framework.pay.domain.jsapi.ali.AliCreateOrderParam;
import com.lanyang.framework.pay.domain.jsapi.wechat.WechatCreateOrderParam;
import com.lanyang.framework.pay.enums.PayType;
import com.lanyang.framework.pay.strategy.PayStrategyFactory;
import java.math.BigDecimal;

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

// 创建支付宝支付订单
AliCreateOrderParam aliParam = new AliCreateOrderParam();
aliParam.setPayType(PayType.ALIPAY);
aliParam.setOutTradeNo("ORDER_" + System.currentTimeMillis());
aliParam.setTotalAmount(new BigDecimal("1.00"));
aliParam.setSubject("测试商品");
aliParam.setBody("测试商品详情");

PreOrderResult aliResult = payStrategyFactory.createOrder(aliParam);
// 使用 aliResult 中的参数调起支付宝支付
```

#### 3.2 查询订单

```java
import com.lanyang.framework.pay.domain.jsapi.ali.AliQueryOrderParam;
import com.lanyang.framework.pay.domain.jsapi.wechat.WechatQueryOrderParam;
import com.lanyang.framework.pay.enums.PayType;
import com.lanyang.framework.pay.strategy.PayStrategyFactory;

// 查询微信订单
WechatQueryOrderParam param = new WechatQueryOrderParam();
param.setPayType(PayType.WECHAT);
param.setOutTradeNo("ORDER_1234567890");

QueryOrderResult result = payStrategyFactory.queryOrder(param);
// 处理查询结果

// 查询支付宝订单
AliQueryOrderParam aliParam = new AliQueryOrderParam();
aliParam.setPayType(PayType.ALIPAY);
aliParam.setOutTradeNo("ORDER_1234567890");

QueryOrderResult aliResult = payStrategyFactory.queryOrder(aliParam);
// 处理查询结果
```

#### 3.3 关闭订单

```java
import com.lanyang.framework.pay.domain.jsapi.ali.AliCloseOrderParam;
import com.lanyang.framework.pay.domain.jsapi.wechat.WechatCloseOrderParam;
import com.lanyang.framework.pay.enums.PayType;
import com.lanyang.framework.pay.strategy.PayStrategyFactory;

// 关闭微信订单
WechatCloseOrderParam param = new WechatCloseOrderParam();
param.setPayType(PayType.WECHAT);
param.setOutTradeNo("ORDER_1234567890");

payStrategyFactory.closeOrder(param);

// 关闭支付宝订单
AliCloseOrderParam aliParam = new AliCloseOrderParam();
aliParam.setPayType(PayType.ALIPAY);
aliParam.setOutTradeNo("ORDER_1234567890");

payStrategyFactory.closeOrder(aliParam);
```

#### 3.4 申请退款

```java
import com.lanyang.framework.pay.domain.jsapi.ali.AliRefundOrderParam;
import com.lanyang.framework.pay.domain.jsapi.wechat.WechatRefundOrderParam;
import com.lanyang.framework.pay.enums.PayType;
import com.lanyang.framework.pay.strategy.PayStrategyFactory;
import java.math.BigDecimal;

// 微信订单退款
WechatRefundOrderParam param = new WechatRefundOrderParam();
param.setPayType(PayType.WECHAT);
param.setOutTradeNo("ORDER_1234567890");
param.setRefundAmount(new BigDecimal("0.50"));
param.setRefundReason("退款原因");

payStrategyFactory.refundOrder(param);

// 支付宝订单退款
AliRefundOrderParam aliParam = new AliRefundOrderParam();
aliParam.setPayType(PayType.ALIPAY);
aliParam.setOutTradeNo("ORDER_1234567890");
aliParam.setRefundAmount(new BigDecimal("0.50"));
aliParam.setRefundReason("退款原因");

payStrategyFactory.refundOrder(aliParam);
```

### 4. 处理支付通知

#### 4.1 微信支付通知

```java
@RestController
@RequestMapping("/pay/wechat")
public class WechatPayController {
    
    @Autowired
    private WechatNotifyHandler wechatNotifyHandler;
    
    @PostMapping("/notify")
    public String handleNotify(HttpServletRequest request) {
        return wechatNotifyHandler.handleNotify(request);
    }
}
```

#### 4.2 支付宝通知

```java
@RestController
@RequestMapping("/pay/ali")
public class AliPayController {
    
    @Autowired
    private AliNotifyHandler aliNotifyHandler;
    
    @PostMapping("/notify")
    public String handleNotify(HttpServletRequest request) {
        return aliNotifyHandler.handleNotify(request);
    }
}
```

### 5. 微信企业付款

```java
@Autowired
private WechatTransferService wechatTransferService;

TransferToUserParam param = new TransferToUserParam();
param.setPartnerTradeNo("TRANSFER_" + System.currentTimeMillis());
param.setOpenid("user_openid");
param.setAmount(new BigDecimal("100"));
param.setDesc("测试转账");

TransferToUserResult result = wechatTransferService.transferToUser(param);
// 处理转账结果
```

## 策略模式设计

### 核心设计理念

framework-pay 模块采用策略模式设计，主要包含以下组件：

1. **PayStrategy 接口**: 定义统一的支付操作方法
2. **具体策略实现**: AliPayStrategy 和 WechatPayStrategy
3. **PayStrategyFactory**: 根据支付类型选择对应的策略

### 优势

- **统一接口**: 不同支付方式使用相同的接口，简化调用
- **易于扩展**: 新增支付方式只需实现 PayStrategy 接口
- **配置化管理**: 支付参数通过配置文件管理，便于维护
- **代码清晰**: 各支付方式的实现逻辑分离，易于维护

## 支付流程

### 1. 下单流程

1. 构建支付参数（CreateOrderParam）
2. 调用 PayStrategyFactory.createOrder()
3. 根据支付类型选择对应的策略
4. 策略执行下单操作
5. 返回预下单结果（PreOrderResult）
6. 使用结果调起支付

### 2. 通知处理流程

1. 支付平台发送异步通知
2. 通知处理器接收并验证通知
3. 处理业务逻辑（更新订单状态等）
4. 返回成功响应给支付平台

## 配置说明

### 1. HTTP 客户端配置

```yaml
pay:
  http:
    client:
      connectTimeout: 5000
      readTimeout: 10000
      writeTimeout: 10000
      maxConnections: 100
      maxConnectionsPerRoute: 20
```

### 2. 支付宝配置

- **appId**: 应用ID
- **privateKey**: 应用私钥
- **publicKey**: 支付宝公钥
- **notifyUrl**: 异步通知地址
- **returnUrl**: 同步返回地址
- **appCertPath**: 应用证书路径
- **alipayCertPath**: 支付宝公钥证书路径
- **alipayRootCertPath**: 支付宝根证书路径

### 3. 微信支付配置

- **appId**: 公众号APPID
- **mchId**: 商户号
- **apiKey**: API密钥
- **notifyUrl**: 支付结果通知地址
- **refundNotifyUrl**: 退款结果通知地址
- **certPath**: 证书路径（用于退款等操作）

## 注意事项

1. **安全配置**：
   - 支付密钥和证书文件要妥善保管
   - 建议使用环境变量或加密配置管理敏感信息

2. **回调处理**：
   - 回调通知要进行签名验证
   - 要处理重复通知的情况
   - 通知处理要幂等

3. **异常处理**：
   - 捕获并处理 PayException 异常
   - 记录详细的错误日志

4. **参数验证**：
   - 对支付参数进行严格验证
   - 确保金额、订单号等关键参数正确

## 依赖说明

- **Spring Boot**: 提供自动配置和依赖注入
- **Apache HttpClient**: 用于发送 HTTP 请求
- **Fastjson**: 用于 JSON 序列化和反序列化
- **Lombok**: 简化代码开发
- **支付宝 SDK**: 支付宝支付接口调用
- **微信支付 SDK**: 微信支付接口调用

## 常见问题

### Q: 如何添加新的支付方式？

A: 实现 PayStrategy 接口，添加对应的配置类，然后在 Spring 容器中注册该策略。

### Q: 如何处理支付超时？

A: 可以通过定时任务查询订单状态，或者在支付流程中设置超时时间。

### Q: 如何测试支付功能？

A: 使用支付平台的沙箱环境进行测试，或者使用测试账号。

### Q: 如何处理退款失败的情况？

A: 记录失败原因，定期重试，或者人工处理。

### Q: 如何优化支付接口的性能？

A: 使用连接池，合理设置超时时间，异步处理非关键逻辑。

## 版本历史

- **1.0.0**: 初始版本，支持支付宝和微信支付的基本功能

## 许可证

本模块采用 MIT 许可证，详见项目根目录的 LICENSE 文件。