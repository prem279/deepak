/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on 27 Oct 2016
 */

package com.lmig.ci.lmbc.empr.common.util;

/**
 * @author N0287705
 *
 */
public class Pair<L,R> {
    private L l;
    private R r;
    public Pair(L l, R r){
        this.l = l;
        this.r = r;
    }
    public L getL(){ return l; }
    public R getR(){ return r; }
    public void setL(L l){ this.l = l; }
    public void setR(R r){ this.r = r; }
}
