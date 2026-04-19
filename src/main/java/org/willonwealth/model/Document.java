package org.willonwealth.model;

import java.util.Arrays;
import java.util.Objects;

public class Document {

    private String name;
    private String mimeType;
    private String content;

    public Document() {
    }

    public Document(String name, String mimeType, String content) {
        setName(name);
        setMimeType(mimeType);
        setContent(content);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Document{" +
                "name='" + name + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getMimeType() {
        return mimeType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(name, document.name) && Objects.equals(mimeType, document.mimeType) && Objects.equals(content, document.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, mimeType, content);
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
