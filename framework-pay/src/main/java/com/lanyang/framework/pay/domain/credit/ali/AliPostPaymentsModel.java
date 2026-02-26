package com.lanyang.framework.pay.domain.credit.ali;

/**
 * 付费项目模型
 * 创建芝麻免押订单时必需的入参
 * 1、详细内容可参考接口文档：
 *
 * @see <a href="https://opendocs.alipay.com/open/e2e70da4_alipay.fund.auth.order.app.freeze?pathHash=1002681c&ref=api&scene=3392871eb88945fa977f613f214ea1c6/">
 * alipay.fund.auth.order.app.freeze(线上资金授权冻结接口)</a>
 */
public class AliPostPaymentsModel {

    /**
     * 特殊可选：免押模式为 POSTPAY 或 POSTPAY_UNCERTAIN 需要必填
     * 如：租金
     */
    private String productModeName;

    /**
     * 特殊可选：免押模式为: POSTPAY_UNCERTAIN  需要必填
     * 如：2元/小时，99元封顶
     */
    private String productModeDescription;

    /**
     * 特殊可选：免押模式为: POSTPAY  需要必填 ；为小数点后仅2位的金额数字
     * 如：0.15
     */
    private String productModeAmount;

    public String getProductModeName() {
        return productModeName;
    }

    public void setProductModeName(String productModeName) {
        this.productModeName = productModeName;
    }

    public String getProductModeDescription() {
        return productModeDescription;
    }

    public void setProductModeDescription(String productModeDescription) {
        this.productModeDescription = productModeDescription;
    }

    public String getProductModeAmount() {
        return productModeAmount;
    }

    public void setProductModeAmount(String productModeAmount) {
        this.productModeAmount = productModeAmount;
    }
}
