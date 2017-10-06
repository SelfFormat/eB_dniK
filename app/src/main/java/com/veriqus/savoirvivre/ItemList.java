package com.veriqus.savoirvivre;

/**
 * Created by krzysztofmarczewski on 06.09.2017.
 */

public class ItemList {


    String title;
    String content;

    public ItemList(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public ItemList(String content) {
        this.content = content;
    }
}
