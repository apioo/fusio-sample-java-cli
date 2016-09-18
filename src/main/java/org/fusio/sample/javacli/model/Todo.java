package org.fusio.sample.javacli.model;

import com.google.api.client.util.Key;

public class Todo {
    @Key
    private int id;

    @Key
    private int status;

    @Key
    private String title;

    @Key
    private String insertDate;

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public int getStatus() {
	return status;
    }

    public void setStatus(int status) {
	this.status = status;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getInsertDate() {
	return insertDate;
    }

    public void setInsertDate(String insertDate) {
	this.insertDate = insertDate;
    }
}
