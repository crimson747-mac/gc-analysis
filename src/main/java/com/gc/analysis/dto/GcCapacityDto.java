package com.gc.analysis.dto;

import lombok.Data;

@Data
public class GcCapacityDto {
    private long timestamp;
    private float ngcmn;
    private float ngcmx;
    private float ngc;
    private float s0c;
    private float s1c;
    private float ec;
    private float ogcmn;
    private float ogcmx;
    private float ogc;
    private float oc;
    private float mcmn;
    private float mcmx;
    private float mc;
    private float ccsmn;
    private float ccsmx;
    private float ccsc;
    private int ygc;
    private int fgc;
    private int cgc;

    public GcCapacityDto(String line) {
        String[] split = line.split(",");
        this.timestamp = (long) (Float.parseFloat(split[0]) * 1000);
        this.ngcmn = Float.parseFloat(split[1]);
        this.ngcmx = Float.parseFloat(split[2]);
        this.ngc = Float.parseFloat(split[3]);
        this.s0c = Float.parseFloat(split[4]);
        this.s1c = Float.parseFloat(split[5]);
        this.ec = Float.parseFloat(split[6]);
        this.ogcmn = Float.parseFloat(split[7]);
        this.ogcmx = Float.parseFloat(split[8]);
        this.ogc = Float.parseFloat(split[9]);
        this.oc = Float.parseFloat(split[10]);
        this.mcmn = Float.parseFloat(split[11]);
        this.mcmx = Float.parseFloat(split[12]);
        this.mc = Float.parseFloat(split[13]);
        this.ccsmn = Float.parseFloat(split[14]);
        this.ccsmx = Float.parseFloat(split[15]);
        this.ccsc = Float.parseFloat(split[16]);
        this.ygc = Integer.parseInt(split[17]);
        this.fgc = Integer.parseInt(split[18]);
        this.cgc = Integer.parseInt(split[19]);
    }
}
