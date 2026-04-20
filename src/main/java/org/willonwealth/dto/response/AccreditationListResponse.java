package org.willonwealth.dto.response;

import java.util.LinkedHashMap;
import java.util.Map;

public class AccreditationListResponse {

    private String user_id;
    private Map<String, Map<String,String>> accreditation_statuses = new LinkedHashMap<>();

    public Map<String, Map<String, String>> getAccreditation_statuses() {
        return accreditation_statuses;
    }

    public void setAccreditation_statuses(Map<String, Map<String, String>> accreditation_statuses) {
        this.accreditation_statuses = accreditation_statuses;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public AccreditationListResponse(String user_id, Map<String, Map<String, String>> accreditation_statuses) {
        this.user_id = user_id;
        this.accreditation_statuses = accreditation_statuses;
    }

    public AccreditationListResponse() {
    }
}
