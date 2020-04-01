package com.tucklets.app.utils;

public enum UploadChildrenDataHeader {
    FIRST_NAME("First Name"),
    LAST_NAME("Last Name"),
    BIRTH_YEAR("Birth Year"),
    GRADE("Grade"),
    ASPIRATIONS("Aspirations"),
    IMAGE_URL("Image Name");

    private String headerString;

    UploadChildrenDataHeader(String headerString) {
        this.headerString = headerString;
    }

}
