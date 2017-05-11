package com.piedra.platease.entity;

import com.piedra.platease.model.system.Function;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限分配对象
 * @author webinglin
 * @since 2017-05-11
 */
public class FuncLegend {
    // legend描述
    private String legend;

    private List<Function> funcs = new ArrayList<>();

    public String getLegend() {
        return legend;
    }

    public void setLegend(String legend) {
        this.legend = legend;
    }

    public List<Function> getFuncs() {
        return funcs;
    }

    public void setFuncs(List<Function> funcs) {
        this.funcs = funcs;
    }
}
