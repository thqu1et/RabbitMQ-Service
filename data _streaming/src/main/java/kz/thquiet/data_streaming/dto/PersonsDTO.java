package kz.thquiet.data_streaming.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class PersonsDTO {
    private Integer id;
    private String fullName;
    private String email;
    private String address;
}
