package br.com.loanapi.services;

import br.com.loanapi.config.ModelMapperConfig;
import br.com.loanapi.exceptions.InvalidRequestException;
import br.com.loanapi.exceptions.ObjectNotFoundException;
import br.com.loanapi.mocks.dto.InstallmentDTODataBuilder;
import br.com.loanapi.mocks.entity.InstallmentEntityDataBuilder;
import br.com.loanapi.models.entities.InstallmentEntity;
import br.com.loanapi.repositories.InstallmentRepository;
import br.com.loanapi.validations.InstallmentValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@DisplayName("Service: Installment")
@ExtendWith(MockitoExtension.class)
class InstallmentServiceTest {

    @InjectMocks
    InstallmentService service;

    @Mock
    InstallmentValidation validation;

    @Mock
    InstallmentRepository repository;

    @Mock
    ModelMapperConfig modelMapper;

    @Test
    @DisplayName("Should test create method with success")
    void shouldTestCreateMethodWithSuccess() throws ParseException {

        Mockito.when(modelMapper.mapper()).thenReturn(new ModelMapper());
        Mockito.when(validation.validateRequest(Mockito.any())).thenReturn(true);
        Mockito.when(repository.save(Mockito.any())).thenReturn(InstallmentEntityDataBuilder.builder().build());

        Assertions.assertEquals("InstallmentDTO(id=1, maturityDate=Fri Nov 11 00:00:00 BRST 2011, " +
                        "paymentDate=Thu Nov 11 00:00:00 BRT 2021, expired=false, month=4, amortization=1000.0, " +
                        "interest=10.0, value=1100.0, loan=LoanDTO(id=1, startDate=Fri Nov 11 00:00:00 BRST 2011, " +
                        "originalValue=5000.0, debitBalance=2800.0, interestRate=10.0, numberOfInstallments=10, " +
                        "paymentDate=FIFTH_BUSINESS_DAY, amortization=SAC, customer=CustomerDTO(id=1, name=João, " +
                        "lastName=da Silva, birthDate=Fri Nov 11 00:00:00 BRST 2011, " +
                        "signUpDate=Thu Nov 11 00:00:00 BRT 2021, rg=55.626.926-4, cpf=391.534.277-44, " +
                        "email=joao@email.com, address=AddressDTO(id=1, street=Rua 9, neighborhood=Lauzane Paulista, " +
                        "number=583, postalCode=02442-090, city=CityDTO(id=1, city=São Paulo, state=SAO_PAULO, " +
                        "addresses=null), customers=null), score=ScoreDTO(id=1, pontuation=50.0, customer=null), " +
                        "phones=null, loans=null), installments=null))",
                service.create(InstallmentDTODataBuilder.builder().build()).toString());

    }

    @Test
    @DisplayName("Should test create method with exception")
    void shouldTestCreateMethodWithException(){

        Mockito.when(validation.validateRequest(Mockito.any())).thenReturn(false);

        try {
            service.create(InstallmentDTODataBuilder.builder().build());
            Assertions.fail();
        }
        catch(InvalidRequestException | ParseException exception) {
            Assertions.assertEquals("Installment validation failed", exception.getMessage());
        }

    }

    @Test
    @DisplayName("Should test findAll method with success")
    void shouldTestFindAllMethodWithSuccess() throws ParseException {

        List<InstallmentEntity> installments = new ArrayList<>();
        installments.add(InstallmentEntityDataBuilder.builder().build());

        Mockito.when(modelMapper.mapper()).thenReturn(new ModelMapper());
        Mockito.when(repository.findAll()).thenReturn(installments);

        Assertions.assertEquals("[InstallmentDTO(id=1, maturityDate=Fri Nov 11 00:00:00 BRST 2011, " +
                        "paymentDate=Thu Nov 11 00:00:00 BRT 2021, expired=false, month=4, amortization=1000.0, " +
                        "interest=10.0, value=1100.0, loan=LoanDTO(id=1, startDate=Fri Nov 11 00:00:00 BRST 2011, " +
                        "originalValue=5000.0, debitBalance=2800.0, interestRate=10.0, numberOfInstallments=10, " +
                        "paymentDate=FIFTH_BUSINESS_DAY, amortization=SAC, customer=CustomerDTO(id=1, name=João, " +
                        "lastName=da Silva, birthDate=Fri Nov 11 00:00:00 BRST 2011, " +
                        "signUpDate=Thu Nov 11 00:00:00 BRT 2021, rg=55.626.926-4, cpf=391.534.277-44, " +
                        "email=joao@email.com, address=AddressDTO(id=1, street=Rua 9, neighborhood=Lauzane Paulista, " +
                        "number=583, postalCode=02442-090, city=CityDTO(id=1, city=São Paulo, state=SAO_PAULO, " +
                        "addresses=null), customers=null), score=ScoreDTO(id=1, pontuation=50.0, customer=null), " +
                        "phones=null, loans=null), installments=null))]",
                service.findAll().toString());

    }


    @Test
    @DisplayName("Should test findAll method with exception")
    void shouldTestFindAllMethodWithException(){

        List<InstallmentEntity> cities = new ArrayList<>();
        Mockito.when(repository.findAll()).thenReturn(cities);

        try {
            service.findAll();
            Assertions.fail();
        }
        catch(ObjectNotFoundException exception) {
            Assertions.assertEquals("There is no installments saved in the database", exception.getMessage());
        }

    }

    @Test
    @DisplayName("Should test findById method with success")
    void shouldTestFindByIdMethodWithSuccess() throws ParseException {

        Mockito.when(modelMapper.mapper()).thenReturn(new ModelMapper());
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(InstallmentEntityDataBuilder.builder().build()));

        Assertions.assertEquals("InstallmentDTO(id=1, maturityDate=Fri Nov 11 00:00:00 BRST 2011, " +
                        "paymentDate=Thu Nov 11 00:00:00 BRT 2021, expired=false, month=4, amortization=1000.0, " +
                        "interest=10.0, value=1100.0, loan=LoanDTO(id=1, startDate=Fri Nov 11 00:00:00 BRST 2011, " +
                        "originalValue=5000.0, debitBalance=2800.0, interestRate=10.0, numberOfInstallments=10, " +
                        "paymentDate=FIFTH_BUSINESS_DAY, amortization=SAC, customer=CustomerDTO(id=1, name=João, " +
                        "lastName=da Silva, birthDate=Fri Nov 11 00:00:00 BRST 2011, " +
                        "signUpDate=Thu Nov 11 00:00:00 BRT 2021, rg=55.626.926-4, cpf=391.534.277-44, " +
                        "email=joao@email.com, address=AddressDTO(id=1, street=Rua 9, neighborhood=Lauzane Paulista, " +
                        "number=583, postalCode=02442-090, city=CityDTO(id=1, city=São Paulo, state=SAO_PAULO, " +
                        "addresses=null), customers=null), score=ScoreDTO(id=1, pontuation=50.0, customer=null), " +
                        "phones=null, loans=null), installments=null))",
                service.findById(1L).toString());

    }

    @Test
    @DisplayName("Should test findById method with exception")
    void shouldTestFindByIdMethodWithException() {

        Mockito.when(modelMapper.mapper()).thenReturn(new ModelMapper());
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());

        try {
            service.findById(1L);
        }
        catch (ObjectNotFoundException exception){
            Assertions.assertEquals("Installment not found", exception.getMessage());
        }

    }

    @Test
    @DisplayName("Should test update method with success")
    void shouldTestUpdateMethodWithSuccess() throws ParseException {

        Mockito.when(modelMapper.mapper()).thenReturn(new ModelMapper());
        Mockito.when(validation.validateRequest(Mockito.any())).thenReturn(true);
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(InstallmentEntityDataBuilder.builder().build()));
        Mockito.when(repository.save(Mockito.any())).thenReturn(InstallmentEntityDataBuilder.builder().build());

        Assertions.assertEquals("InstallmentDTO(id=1, maturityDate=Fri Nov 11 00:00:00 BRST 2011, " +
                        "paymentDate=Thu Nov 11 00:00:00 BRT 2021, expired=false, month=4, amortization=1000.0, " +
                        "interest=10.0, value=1100.0, loan=LoanDTO(id=1, startDate=Fri Nov 11 00:00:00 BRST 2011, " +
                        "originalValue=5000.0, debitBalance=2800.0, interestRate=10.0, numberOfInstallments=10, " +
                        "paymentDate=FIFTH_BUSINESS_DAY, amortization=SAC, customer=CustomerDTO(id=1, name=João, " +
                        "lastName=da Silva, birthDate=Fri Nov 11 00:00:00 BRST 2011, " +
                        "signUpDate=Thu Nov 11 00:00:00 BRT 2021, rg=55.626.926-4, cpf=391.534.277-44, " +
                        "email=joao@email.com, address=AddressDTO(id=1, street=Rua 9, neighborhood=Lauzane Paulista, " +
                        "number=583, postalCode=02442-090, city=CityDTO(id=1, city=São Paulo, state=SAO_PAULO, " +
                        "addresses=null), customers=null), score=ScoreDTO(id=1, pontuation=50.0, customer=null), " +
                        "phones=null, loans=null), installments=null))",
                service.update(1L, InstallmentDTODataBuilder.builder().build()).toString());

    }

    @Test
    @DisplayName("Should test update method with exception")
    void shouldTestUpdateMethodWithException() {

        Mockito.when(validation.validateRequest(Mockito.any())).thenReturn(false);

        try{
            service.update(1L, InstallmentDTODataBuilder.builder().build());
            Assertions.fail();
        }
        catch(InvalidRequestException | ParseException exception){
            Assertions.assertEquals("Installment validation failed", exception.getMessage());
        }
    }

    @Test
    @DisplayName("Should test delete method with success")
    void shouldTestDeleteMethodWithSuccess() throws ParseException {
        Mockito.when(modelMapper.mapper()).thenReturn(new ModelMapper());
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(InstallmentEntityDataBuilder.builder().build()));
        Assertions.assertTrue(service.delete(1L));
    }

    @Test
    @DisplayName("Should test delete method with exception")
    void shouldTestDeleteMethodWithException() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());

        try{
            service.delete(1L);
            Assertions.fail();
        }
        catch (ObjectNotFoundException exception) {
            Assertions.assertEquals("Installment not found", exception.getMessage());
        }

    }

}