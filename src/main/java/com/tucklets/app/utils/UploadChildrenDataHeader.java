package com.tucklets.app.utils;

import org.apache.commons.collections4.map.HashedMap;

import java.util.Collections;
import java.util.Map;

public enum UploadChildrenDataHeader {
    FIRST_NAME("First Name"),
    LAST_NAME("Last Name"),
    BIRTH_YEAR("Birth Year"),
    GRADE("Grade"),
    ASPIRATIONS("Aspirations"),
    IMAGE_URL("Image Name");

    public static final Map<String, UploadChildrenDataHeader> HEADER_TO_UPLOAD_CHILDREN_DATA_HEADER_MAP =
        Collections.unmodifiableMap(initalizeUploadChildrenDataHeaders());

    private String headerString;

    UploadChildrenDataHeader(String headerString) {
        this.headerString = headerString;
    }

    public String getHeaderString() {
        return headerString;
    }

    private static Map<String, UploadChildrenDataHeader> initalizeUploadChildrenDataHeaders() {
        Map<String, UploadChildrenDataHeader> headerToUploadChildrenDataHeaderMap = new HashedMap<>();
        for (UploadChildrenDataHeader header : UploadChildrenDataHeader.values()) {
            headerToUploadChildrenDataHeaderMap.put(header.getHeaderString(), header);
        }
        return headerToUploadChildrenDataHeaderMap;
    }
}
