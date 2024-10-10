package com.koishop.models.certificate_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CertificateView {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;
    @NotBlank(message = "Link is required")
    private String link;

    @NotBlank(message = "koiFish is required")
    private String koiFish;
}
