package kz.thquiet.data_streaming_receiver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonsDTO {
    @JsonProperty(value = "id")
    private Integer id;
    @JsonProperty(value = "fullName")
    private String fullName;
    @JsonProperty(value = "email")
    private String email;
    @JsonProperty(value = "address")
    private String address;
}
