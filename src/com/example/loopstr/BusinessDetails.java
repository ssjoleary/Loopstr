package com.example.loopstr;

import java.io.Serializable;

public class BusinessDetails implements Serializable {
    private final static long serialVersionUID = 1;
	 
    private String name;
    private String category;
    private String id;
    private String about;
    private String contact;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public BusinessDetails(String name, String category, String id) {
        super();
        this.name = name;
        this.category = category;
        this.id = id;
    }

    public BusinessDetails(String name, String category, String id, String about, String contact) {
        super();
        this.name = name;
        this.category = category;
        this.id = id;
        this.about = about;
        this.contact = contact;
    }

	public String getName() {
		return name;
	}

	public void setName(String title) {
		this.name = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String message) {
		this.category = message;
	}

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }
}
