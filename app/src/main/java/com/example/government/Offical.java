package com.example.government;

import java.util.HashMap;

public class Offical {

    private String name ;
    private String party;
    private String role;
    private String photoUrl;
    private String address;
    private String url;
    private String phone;
    private String emailz;

    private HashMap<String,String> social ;



    public Offical(String name, String party, String role, String photoUrl,
                   String address, String phone, String url, String emailz, HashMap<String,String> social){

        this.name = name;
        this.party = party;
        this.role = role;
        this.photoUrl = photoUrl;
        this.address =address;
        this.phone = phone;
        this.url = url;
        this.emailz=emailz;
        this.social = social;


    }


    public String getName() {
        return name;
    }

    public String getParty() {
        return party;
    }

    public String getRole() {
        return role;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getAddress() {
        return address;
    }

    public String getUrl() {
        return url;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmailz() {
        return emailz;
    }

    public HashMap<String, String> getSocial() {
        return social;
    }


    public String getFB(){


        return social.get("Facebook");
    }

    public String getTW(){


        return social.get("Twitter");
    }

    public String getYU(){


        return social.get("YouTube");
    }

    public String getGoo(){


        return social.get("GooglePlus");
    }


}
