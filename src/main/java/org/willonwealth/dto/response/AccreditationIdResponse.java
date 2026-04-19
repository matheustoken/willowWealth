package org.willonwealth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class AccreditationIdResponse {

    @JsonProperty("accreditation_id")
    private String accreditation_id;

    public AccreditationIdResponse() {
    }

    public AccreditationIdResponse(String accreditation_id) {
        this.accreditation_id = accreditation_id;
    }

    public String getAccreditation_id() {
        return accreditation_id;
    }

    public void setAccreditation_id(String accreditation_id) {
        this.accreditation_id = accreditation_id;
    }

    @Override
    public String toString() {
        return "AccreditationIdResponse{" +
                "accreditation_id='" + accreditation_id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccreditationIdResponse)) return false;
        AccreditationIdResponse that = (AccreditationIdResponse) o;
        return Objects.equals(accreditation_id, that.accreditation_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accreditation_id);
    }
}

