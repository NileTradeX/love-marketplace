package com.love.order.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyNoticeBO {
    private Order order;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Order {
        private List<LineItem> lineItems;
        private List<Transaction> transactions;
        private Customer customer;
        private Address shippingAddress;
        private Address billingAddress;
        private String totalTax;
        private String currency;
        private String phone;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class LineItem {
            private String title;
            private double price;
            private String grams;
            private int quantity;
            private List<TaxLine> taxLines;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class TaxLine {
                private double price;
                private double rate;
                private String title;
            }
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Transaction {
            private String kind;
            private String status;
            private double amount;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Customer {
            private boolean acceptsMarketing;
            private String acceptsMarketingUpdatedAt;
            private List<Address> addresses;
            private String currency;
            private String createdAt;
            private Address defaultAddress;
            private String email;
            private EmailMarketingConsent emailMarketingConsent;
            private String firstName;
            private Long id;
            private String lastName;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Address {
                private Long id;
                private Long customerId;
                private String firstName;
                private String lastName;
                private String company;
                private String address1;
                private String address2;
                private String city;
                private String province;
                private String country;
                private String zip;
                private String phone;
                private String provinceCode;
                private String countryCode;
                private String countryName;
            }

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class EmailMarketingConsent {
                private String state;
                private String optInLevel;
                private String consentUpdatedAt;
            }
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Address {
            private String address1;
            private String address2;
            private String city;
            private String company;
            private String country;
            private String firstName;
            private Long id;
            private String lastName;
            private String phone;
            private String province;
            private String zip;
            private String provinceCode;
            private String countryCode;
            private String countryName;
        }
    }
}