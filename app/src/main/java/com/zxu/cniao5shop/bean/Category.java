package com.zxu.cniao5shop.bean;

/**
 * Created by Ivan on 15/9/24.
 */
public class Category extends BaseBean {


    public Category() {
    }

    public Category(String name) {

        this.name = name;
    }

    public Category(long id ,String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}
