package com.fitibo.eyouapp.bean;

import lombok.Data;

import java.util.List;

/**
 * Created by qianhao.zhou on 7/29/16.
 */
@Data
public class Sku {

    private int id;
    private String uuid;
    private String name;
    private int cityId;
    private String city;
    private String cityEn;
    private int countryId;
    private String country;
    private String countryEn;
    private int categoryId;
    private String category;
    private int vendorId;
    private String vendor;
    private List<String> gatheringPlace;
    private boolean pickupService;
    private String description;
    private int durationId;
    private String duration;
    private List<SkuTicket> tickets;

    private String officialWebsite;
    private String confirmationTime;
    private String rescheduleCancelNotice;
    private String agendaInfo;
    private String activityTime;
    private String openingTime;
    private String ticketInfo;
    private String serviceInclude;
    private String serviceExclude;
    private String extraItem;
    private String attention;
    private String priceConstraint;
    private String otherInfo;
    private boolean autoGenerateReferenceNumber;
    private boolean available;
    private String checkAvailabilityWebsite;
    private boolean api;
    private int autoConfirm;
    private List<String> suggestRemark;
    private String suggestGatheringTime;
    private int type;
}
