package org.willonwealth.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Document {

    private String name;
    private String mimeType;
    private String content;

    public Document() {
    }

    public Document(String name, String content, String mimeType) {
        this.name = name;
        this.content = content;
        this.mimeType = mimeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

}