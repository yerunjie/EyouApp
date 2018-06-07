package com.fitibo.eyouapp.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by qianhao.zhou on 8/7/16.
 */
@Data
public class Order {

    private int id;
    private int skuId;
    private String skuUuid;
    private String uuid;
    private String sku;
    private int agentId;
    private String remark;
    private int status;
    private BigDecimal price;
    private String firstName;
    private String lastName;
    private String primaryContactEmail;
    private String primaryContactPhone;
    private String primaryContactWechat;
    private String secondaryContact;
    private String secondaryContactEmail;
    private String secondaryContactPhone;
    private String secondaryContactWechat;
    private String gatheringInfo;
    private String referenceNumber;
    private String vendorPhone;
    private String agentName;

    private List<OrderTicket> orderTickets;
    private String ticketDate;
    private String ticketDateWeekDay;
    private String ticketDateWeekDayEn;
    private String agentOrderId;
    private BigDecimal modifiedPrice;
    private BigDecimal refund;
    //private PayStatus payStatus;
    private String currency;
    private String currencyEn;
    private int groupType;
    private String gatheringTime;
    private String gatheringPlace;
    private String ticketTime;
    private boolean fromVendor;
    private int countryId;

}
