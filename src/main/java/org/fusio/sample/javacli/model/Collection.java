package org.fusio.sample.javacli.model;

import java.util.List;

import com.google.api.client.util.Key;

public class Collection {
    @Key
    private int totalResults;

    @Key
    private List<Todo> entry;

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Todo> getEntry() {
	return entry;
    }

    public void setEntry(List<Todo> entry) {
	this.entry = entry;
    }
}
