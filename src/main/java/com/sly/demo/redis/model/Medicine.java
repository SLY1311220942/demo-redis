package com.sly.demo.redis.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author SLY
 * @description
 * @date 2020/7/20
 */
public class Medicine implements Serializable {
    /**
     * varchar(450) NULL
     */
    private String manufacturer;
    /**
     * varchar(150) NULL
     */
    private String drugName;
    /**
     * decimal(17,0) NULL
     */
    private BigDecimal suggestPrice;
    /**
     * varchar(450) NULL
     */
    private String specification;
    /**
     * varchar(96) NULL
     */
    private String packingUnit;
    /**
     * int(11) NULL
     */
    private Integer id;
    /**
     * varchar(33) NULL
     */
    private String type;
    /**
     * int(11) NULL
     */
    private Integer clinicId;
    /**
     * decimal(17,0) NULL
     */
    private BigDecimal customizePrice;
    /**
     * int(11) NULL
     */
    private Integer priceType;
    /**
     * int(11) NULL
     */
    private Integer mpId;

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public BigDecimal getSuggestPrice() {
        return suggestPrice;
    }

    public void setSuggestPrice(BigDecimal suggestPrice) {
        this.suggestPrice = suggestPrice;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getPackingUnit() {
        return packingUnit;
    }

    public void setPackingUnit(String packingUnit) {
        this.packingUnit = packingUnit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }

    public BigDecimal getCustomizePrice() {
        return customizePrice;
    }

    public void setCustomizePrice(BigDecimal customizePrice) {
        this.customizePrice = customizePrice;
    }

    public Integer getPriceType() {
        return priceType;
    }

    public void setPriceType(Integer priceType) {
        this.priceType = priceType;
    }

    public Integer getMpId() {
        return mpId;
    }

    public void setMpId(Integer mpId) {
        this.mpId = mpId;
    }
}
