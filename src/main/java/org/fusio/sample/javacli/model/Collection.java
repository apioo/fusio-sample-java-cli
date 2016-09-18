package org.fusio.sample.javacli.model;

import java.util.List;

import com.google.api.client.util.Key;

public class Collection {
    @Key
    private int totalCount;

    @Key
    private List<Todo> entry;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Todo> getEntry() {
	return entry;
    }

    public void setEntry(List<Todo> entry) {
	this.entry = entry;
    }
}
