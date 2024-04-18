package com.love.payment.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "adyen")
public class AdyenConfigProperties {

    @NestedConfigurationProperty
    private LegalEntityApi legalEntityApi;

    public LegalEntityApi getLegalEntityApi() {
        return legalEntityApi;
    }

    public void setLegalEntityApi(LegalEntityApi legalEntityApi) {
        this.legalEntityApi = legalEntityApi;
    }

    @NestedConfigurationProperty
    private AccountHolderApi accountHolderApi;

    public AccountHolderApi getAccountHolderApi() {
        return accountHolderApi;
    }

    public void setAccountHolderApi(AccountHolderApi accountHolderApi) {
        this.accountHolderApi = accountHolderApi;
    }

    @NestedConfigurationProperty
    private BalanceAccountApi balanceAccountApi;

    public void setBalanceAccountApi(BalanceAccountApi balanceAccountApi) {
        this.balanceAccountApi = balanceAccountApi;
    }

    public BalanceAccountApi getBalanceAccountApi() {
        return balanceAccountApi;
    }

    private String loveBalanceAccount;
    private String liveUrlPrefix;

    public String getLoveBalanceAccount() {
        return loveBalanceAccount;
    }

    public void setLoveBalanceAccount(String loveBalanceAccount) {
        this.loveBalanceAccount = loveBalanceAccount;
    }

    public void setLiveUrlPrefix(String liveUrlPrefix) {
        this.liveUrlPrefix = liveUrlPrefix;
    }

    public String getLiveUrlPrefix() {
        return liveUrlPrefix;
    }

    @Data
    public static class LegalEntityApi {
        private String username;
        private String password;
    }

    @Data
    public static class AccountHolderApi {
        private String username;
        private String password;
    }

    @Data
    public static class BalanceAccountApi {
        private String username;
        private String password;
    }
}
