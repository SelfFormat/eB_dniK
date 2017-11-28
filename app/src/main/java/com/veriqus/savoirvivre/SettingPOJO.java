package com.veriqus.savoirvivre;

/**
 * Created by krzysztofmarczewski on 28.11.2017.
 */

public class SettingPOJO {
    private String name;
    private int drawable;

    public SettingPOJO(String name, int drawable) {
        this.name = name;
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public int getDrawable() {
        return drawable;
    }
}
