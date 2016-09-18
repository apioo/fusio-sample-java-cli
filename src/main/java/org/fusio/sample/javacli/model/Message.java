package org.fusio.sample.javacli.model;

import com.google.api.client.util.Key;

public class Message {
    @Key
    private boolean success;

    @Key
    private String message;

    public boolean isSuccess() {
	return success;
    }

    public void setSuccess(boolean success) {
	this.success = success;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }
}
