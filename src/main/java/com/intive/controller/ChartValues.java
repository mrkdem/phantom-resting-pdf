package com.intive.controller;

import java.util.List;
import java.util.ArrayList;

/**
 * Product bean; note that it must be a public class!
 */
public class ChartValues {

    private List<String> vals = new ArrayList<String>();

    public List<String> getVals() {
        return vals;
    }

    public void setVals(List<String> vals) {
        this.vals = vals;
    }
}