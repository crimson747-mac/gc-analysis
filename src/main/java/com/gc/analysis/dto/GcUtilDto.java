package com.gc.analysis.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class GcUtilDto {
    private long timestamp;
    private BigDecimal s0;
    private BigDecimal s1;
    private BigDecimal e;
    private BigDecimal o;
    private BigDecimal m;
    private BigDecimal ccs;
    private int ygc;
    private BigDecimal ygct;
    private int fgc;
    private BigDecimal fgct;
    private int cgc;
    private BigDecimal cgct;
    private BigDecimal gct;

    public GcUtilDto(String line) {
        String[] split = line.split(",");
        this.timestamp = (long) (Float.parseFloat(split[0]) * 1000);
        this.s0 = new BigDecimal(split[1]);
        this.s1 = new BigDecimal(split[2]);
        this.e = new BigDecimal(split[3]);
        this.o = new BigDecimal(split[4]);
        this.m = new BigDecimal(split[5]);
        this.ccs = new BigDecimal(split[6]);
        this.ygc = Integer.parseInt(split[7]);
        this.ygct = new BigDecimal(split[8]);
        this.fgc = Integer.parseInt(split[9]);
        this.fgct = new BigDecimal(split[10]);
        this.cgc = Integer.parseInt(split[11]);
        this.cgct = new BigDecimal(split[12]);
        this.gct = new BigDecimal(split[13]);
    }

    public BigDecimal getAvgYgcTime() {
        if(this.ygct.floatValue() <= 0 || this.ygc <= 0) return BigDecimal.ZERO;

        return new BigDecimal(this.ygct.floatValue() / this.ygc)
                .setScale(5, RoundingMode.HALF_UP);
    }

    public BigDecimal getAvgFgcTime() {
        if(fgct.floatValue() <= 0 || fgc <= 0) return BigDecimal.ZERO;

        return new BigDecimal(this.fgct.floatValue() / this.fgc)
                .setScale(5, RoundingMode.CEILING);
    }
}
