package com.yxb.cms.model.input;

import java.math.BigDecimal;

public class FeeAccountBalance extends FeeAccountBalanceKey {
    private BigDecimal rechargeAmount;

    private BigDecimal couponAmount;

    private BigDecimal backMoney;

    private BigDecimal balance;

    public BigDecimal getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(BigDecimal rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public BigDecimal getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(BigDecimal backMoney) {
        this.backMoney = backMoney;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}