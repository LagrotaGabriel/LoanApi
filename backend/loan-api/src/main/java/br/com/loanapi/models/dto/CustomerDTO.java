package br.com.loanapi.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/** Class that contains all attributes of the object of type CustomerDTO
 ** @author Gabriel Lagrota
 ** @version 1.0.0
 ** @since 30/06/2022
 ** @email gabriellagrota23@gmail.com
 ** @github https://github.com/LagrotaGabriel/Loan-Project/blob/master/backend/loan-api/src/main/java/br/com/loanapi/models/dto/CustomerDTO.java */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CustomerDTO {

    @JsonIgnore
    private Long id;

    private String name;
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String birthDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String signUpDate;

    private String rg;
    private String cpf;
    private String email;

    // CASCATA ADDRESS -> CUSTOMER [DETACH]
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private AddressDTO address;

    // CASCATA CUSTOMER -> SCORE
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ScoreDTO score;

    // CASCATA CUSTOMER -> PHONE
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private List<PhoneDTO> phones = new ArrayList<>();

    // CASCATA CUSTOMER -> LOAN
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<LoanDTO> loans = new ArrayList<>();

}
