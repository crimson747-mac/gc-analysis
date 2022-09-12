package com.gc.analysis.dto;

import lombok.Data;

@Data
public class GcUtilDto {
    private long timestamp;
    private float s0;
    private float s1;
    private float e;
    private float o;
    private float m;
    private float ccs;
    private int ygc;
    private float ygct;
    private int fgc;
    private float fgct;
    private int cgc;
    private float cgct;
    private float gct;

    public GcUtilDto(String line) {
        String[] split = line.split(",");
        this.timestamp = (long) (Float.parseFloat(split[0]) * 1000);
        this.s0 = Float.parseFloat(split[1]);
        this.s1 = Float.parseFloat(split[2]);
        this.e = Float.parseFloat(split[3]);
        this.o = Float.parseFloat(split[4]);
        this.m = Float.parseFloat(split[5]);
        this.ccs = Float.parseFloat(split[6]);
        this.ygc = Integer.parseInt(split[7]);
        this.ygct = Float.parseFloat(split[8]);
        this.fgc = Integer.parseInt(split[9]);
        this.fgct = Float.parseFloat(split[10]);
        this.cgc = Integer.parseInt(split[11]);
        this.cgct = Float.parseFloat(split[12]);
        this.gct = Float.parseFloat(split[13]);
    }

    public float getAvgYgcTime() {
        if(this.ygct <= 0 || this.ygc <= 0) return 0;
        return this.ygct / this.ygc;
    }

    public float getAvgFgcTime() {
        if(fgct <= 0 || fgc <= 0) return 0;
        return this.fgct / this.fgc;
    }
}
