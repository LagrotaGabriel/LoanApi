package br.com.loanapi.models.dto;

import br.com.loanapi.models.enums.PhoneTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


/** Class that contains all attributes of the object of type PhoneDTO
 ** @author Gabriel Lagrota
 ** @version 1.0.0
 ** @since 30/06/2022
 ** @email gabriellagrota23@gmail.com
 ** @github https://github.com/LagrotaGabriel/Loan-Project/blob/master/backend/loan-api/src/main/java/br/com/loanapi/models/dto/PhoneDTO.java */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PhoneDTO {

    @JsonIgnore
    private Long id;

    private Integer prefix;
    private String number;
    private PhoneTypeEnum phoneType;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private CustomerDTO customer;
}
