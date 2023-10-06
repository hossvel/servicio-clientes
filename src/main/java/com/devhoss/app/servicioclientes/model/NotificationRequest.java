package com.devhoss.app.servicioclientes.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest{

    @JsonProperty("to_customer_id")
    Integer toCustomerId;
    @JsonProperty("to_customer_name")
    String toCustomerName;
    String message;
    public Integer getToCustomerId() {
        return toCustomerId;
    }
    public void setToCustomerId(Integer toCustomerId) {
        this.toCustomerId = toCustomerId;
    }
    public String getToCustomerName() {
        return toCustomerName;
    }
    public void setToCustomerName(String toCustomerName) {
        this.toCustomerName = toCustomerName;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }


}


