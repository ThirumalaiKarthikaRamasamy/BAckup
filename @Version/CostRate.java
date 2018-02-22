package com.ge.seawolf.planning.template.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "costrate")
public class CostRate {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "country")
    private String country;
    
    @Column(name = "region")
    private String region;

    @Column(name = "laborprovider")
    private String laborprovider;
    
    @Column(name = "resourcetype")
    private String resourcetype;

    @Column(name = "currency")
    private String currency;
    
    @Column(name = "year")
    private Integer year;
    
    @Column(name = "truecost_st")
    private BigDecimal truecostSt;
    
    @Column(name = "truecost_ot")
    private BigDecimal truecostOt;
    
    @Column(name = "truecost_dt")
    private BigDecimal truecostDt;
    
    @Column(name = "truecost_pd")
    private BigDecimal truecostPd;
    
    @Column(name = "transper_st")
    private BigDecimal  transferSt;
    
    @Column(name = "transper_ot")
    private BigDecimal transferOt;
    
    @Column(name = "transper_dt")
    private BigDecimal transferDt;
    
    @Column(name = "transper_pd")
    private BigDecimal transferPd;
    
    @Version
    @Column(name = "version")
    private Long version;
}
