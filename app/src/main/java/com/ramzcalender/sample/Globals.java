package com.ramzcalender.sample;

/**
 * Created by edison office on 7/24/2018.
 */
import android.app.Application;

public class Globals extends Application{

    private String name;
    private String email;
    private String passwords;
    private String login_mob;
    private String class_names;
    private String deal_nam_var;
    private String amout_var;
    private String hot_var;

    public String getName() {
        return name;
    }

    public void setName(String aName) {
        name = aName;
    }
    public String getPasswords() {
        return passwords;
    }

    public void setPasswords(String aName) {
        passwords = aName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String aEmail) {
        email = aEmail;
    }


    public String getLogin_mob() {
        return login_mob;
    }

    public void setLogin_mob(String aName) {
        login_mob = aName;
    }

    public String getClass_names() {
        return class_names;
    }

    public void setClass_names(String aName) {
        class_names = aName;
    }

    public String getDeal_nam_var() {
        return deal_nam_var;
    }

    public void setDeal_nam_var(String aName) {
        deal_nam_var = aName;
    }

    public String getAmout_var() {
        return amout_var;
    }

    public void setAmout_var(String aName) {
        amout_var = aName;
    }

    public String getHot_var() {
        return hot_var;
    }

    public void setHot_var(String aName) {
        hot_var = aName;
    }

}