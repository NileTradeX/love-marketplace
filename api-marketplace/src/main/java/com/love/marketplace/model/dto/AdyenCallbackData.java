package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdyenCallbackData {
    private String live;
    private List<NotificationItems> notificationItems;

    public String getLive() {
        return live;
    }

    public void setLive(String live) {
        this.live = live;
    }

    public List<NotificationItems> getNotificationItems() {
        return notificationItems;
    }

    public void setNotificationItems(List<NotificationItems> notificationItems) {
        this.notificationItems = notificationItems;
    }

    public static class NotificationItems {
        private NotificationItems.NotificationRequestItem NotificationRequestItem;

        public NotificationItems.NotificationRequestItem getNotificationRequestItem() {
            return NotificationRequestItem;
        }

        public void setNotificationRequestItem(NotificationItems.NotificationRequestItem NotificationRequestItem) {
            this.NotificationRequestItem = NotificationRequestItem;
        }

        public static class NotificationRequestItem {
            private AdditionalData additionalData;
            private Amount amount;
            private String eventCode;
            private String eventDate;
            private String merchantAccountCode;
            private String merchantReference;
            private String originalReference;
            private String paymentMethod;
            private String pspReference;
            private String reason;
            private String success;
            private List<String> operations;

            public AdditionalData getAdditionalData() {
                return additionalData;
            }

            public void setAdditionalData(AdditionalData additionalData) {
                this.additionalData = additionalData;
            }

            public Amount getAmount() {
                return amount;
            }

            public void setAmount(Amount amount) {
                this.amount = amount;
            }

            public String getEventCode() {
                return eventCode;
            }

            public void setEventCode(String eventCode) {
                this.eventCode = eventCode;
            }

            public String getEventDate() {
                return eventDate;
            }

            public void setEventDate(String eventDate) {
                this.eventDate = eventDate;
            }

            public String getMerchantAccountCode() {
                return merchantAccountCode;
            }

            public void setMerchantAccountCode(String merchantAccountCode) {
                this.merchantAccountCode = merchantAccountCode;
            }

            public String getMerchantReference() {
                return merchantReference;
            }

            public void setMerchantReference(String merchantReference) {
                this.merchantReference = merchantReference;
            }

            public String getOriginalReference() {
                return originalReference;
            }

            public void setOriginalReference(String originalReference) {
                this.originalReference = originalReference;
            }

            public String getPaymentMethod() {
                return paymentMethod;
            }

            public void setPaymentMethod(String paymentMethod) {
                this.paymentMethod = paymentMethod;
            }

            public String getPspReference() {
                return pspReference;
            }

            public void setPspReference(String pspReference) {
                this.pspReference = pspReference;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public String getSuccess() {
                return success;
            }

            public void setSuccess(String success) {
                this.success = success;
            }

            public List<String> getOperations() {
                return operations;
            }

            public void setOperations(List<String> operations) {
                this.operations = operations;
            }

            public static class AdditionalData {
                private String merchantOrderReference;
                private String authCode;
                private String avsResult;
                private String cardSummary;
                private String scaExemptionRequested;
                private String refusalReasonRaw;
                @SerializedName("checkout.cardAddedBrand")
                private String checkoutCardAddedBrand;
                private String cvcResult;
                private String expiryDate;
                private String recurringProcessingModel;
                private String avsResultRaw;
                private String cvcResultRaw;
                private String acquirerCode;
                private String acquirerReference;

                public String getMerchantOrderReference() {
                    return merchantOrderReference;
                }

                public void setMerchantOrderReference(String merchantOrderReference) {
                    this.merchantOrderReference = merchantOrderReference;
                }

                public String getAuthCode() {
                    return authCode;
                }

                public void setAuthCode(String authCode) {
                    this.authCode = authCode;
                }

                public String getAvsResult() {
                    return avsResult;
                }

                public void setAvsResult(String avsResult) {
                    this.avsResult = avsResult;
                }

                public String getCardSummary() {
                    return cardSummary;
                }

                public void setCardSummary(String cardSummary) {
                    this.cardSummary = cardSummary;
                }

                public String getScaExemptionRequested() {
                    return scaExemptionRequested;
                }

                public void setScaExemptionRequested(String scaExemptionRequested) {
                    this.scaExemptionRequested = scaExemptionRequested;
                }

                public String getRefusalReasonRaw() {
                    return refusalReasonRaw;
                }

                public void setRefusalReasonRaw(String refusalReasonRaw) {
                    this.refusalReasonRaw = refusalReasonRaw;
                }

                public String getCheckoutCardAddedBrand() {
                    return checkoutCardAddedBrand;
                }

                public void setCheckoutCardAddedBrand(String checkoutCardAddedBrand) {
                    this.checkoutCardAddedBrand = checkoutCardAddedBrand;
                }

                public String getCvcResult() {
                    return cvcResult;
                }

                public void setCvcResult(String cvcResult) {
                    this.cvcResult = cvcResult;
                }

                public String getExpiryDate() {
                    return expiryDate;
                }

                public void setExpiryDate(String expiryDate) {
                    this.expiryDate = expiryDate;
                }

                public String getRecurringProcessingModel() {
                    return recurringProcessingModel;
                }

                public void setRecurringProcessingModel(String recurringProcessingModel) {
                    this.recurringProcessingModel = recurringProcessingModel;
                }

                public String getAvsResultRaw() {
                    return avsResultRaw;
                }

                public void setAvsResultRaw(String avsResultRaw) {
                    this.avsResultRaw = avsResultRaw;
                }

                public String getCvcResultRaw() {
                    return cvcResultRaw;
                }

                public void setCvcResultRaw(String cvcResultRaw) {
                    this.cvcResultRaw = cvcResultRaw;
                }

                public String getAcquirerCode() {
                    return acquirerCode;
                }

                public void setAcquirerCode(String acquirerCode) {
                    this.acquirerCode = acquirerCode;
                }

                public String getAcquirerReference() {
                    return acquirerReference;
                }

                public void setAcquirerReference(String acquirerReference) {
                    this.acquirerReference = acquirerReference;
                }
            }

            public static class Amount {

                private String currency;
                private int value;

                public String getCurrency() {
                    return currency;
                }

                public void setCurrency(String currency) {
                    this.currency = currency;
                }

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }
            }
        }
    }
}
