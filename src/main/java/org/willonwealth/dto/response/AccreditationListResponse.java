package org.willonwealth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccreditationListResponse {

    private String user_id;
    private Map<String, Map<String,String>> accreditation_statuses = new LinkedHashMap<>();
}
