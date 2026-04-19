package org.willonwealth.model;

import lombok.Data;

@Data
public class Document {

    public String name;
    public String mime;
    public String contentBase64;
}
