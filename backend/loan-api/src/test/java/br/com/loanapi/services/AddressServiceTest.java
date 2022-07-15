package br.com.loanapi.services;

import br.com.loanapi.config.ModelMapperConfig;
import br.com.loanapi.exceptions.InvalidRequestException;
import br.com.loanapi.exceptions.ObjectNotFoundException;
import br.com.loanapi.mocks.dto.AddressDTODataBuilder;
import br.com.loanapi.mocks.entity.AddressEntityDataBuilder;
import br.com.loanapi.mocks.entity.CityEntityDataBuilder;
import br.com.loanapi.models.entities.AddressEntity;
import br.com.loanapi.repositories.AddressRepository;
import br.com.loanapi.repositories.CityRepository;
import br.com.loanapi.validations.AddressValidation;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@DisplayName("Service: Address")
@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @InjectMocks
    AddressService service;

    @Mock
    AddressValidation addressValidation;

    @Mock
    CityRepository cityRepository;

    @Mock
    AddressRepository repository;

    @Mock
    ModelMapperConfig modelMapper;

    @Test
    @DisplayName("Should test create method with success")
    void shouldTestCreateMethodWithSuccess(){

        Mockito.when(modelMapper.mapper()).thenReturn(new ModelMapper());
        Mockito.when(addressValidation.validateRequest(Mockito.any())).thenReturn(true);
        Mockito.when(cityRepository.findByCityAndState(Mockito.any(), Mockito.any())).thenReturn(Optional.of(CityEntityDataBuilder.builder().build()));

        service.create(AddressDTODataBuilder.builder().build());

        Assertions.assertEquals("Rua 9", service.create(AddressDTODataBuilder.builder().build()).getStreet());

    }

    @Test
    @DisplayName("Should test create method with city with filled list")
    void shouldTestCreateMethodWithCityWithFilledList(){

        Mockito.when(modelMapper.mapper()).thenReturn(new ModelMapper());
        Mockito.when(addressValidation.validateRequest(Mockito.any())).thenReturn(true);
        Mockito.when(cityRepository.findByCityAndState(Mockito.any(), Mockito.any())).thenReturn(Optional.of(CityEntityDataBuilder.builder().withAddress().build()));

        service.create(AddressDTODataBuilder.builder().build());

        Assertions.assertEquals("Rua 9", service.create(AddressDTODataBuilder.builder().build()).getStreet());

    }

    @Test
    @DisplayName("Should test create method with not finded city")
    void shouldTestCreateMethodWithNotFindedCity(){

        Mockito.when(modelMapper.mapper()).thenReturn(new ModelMapper());
        Mockito.when(addressValidation.validateRequest(Mockito.any())).thenReturn(true);
        Mockito.when(cityRepository.findByCityAndState(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

        service.create(AddressDTODataBuilder.builder().build());

        Assertions.assertEquals("Rua 9", service.create(AddressDTODataBuilder.builder().build()).getStreet());

    }

    @Test
    @DisplayName("Should test create method with existing address")
    void shouldTestCreateMethodWithExistingAddress(){

        Mockito.when(addressValidation.validateRequest(Mockito.any())).thenReturn(true);
        Mockito.when(repository.findByStreetNumberAndPostalCode(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Optional.of(AddressEntityDataBuilder.builder().build()));

        try {
            service.create(AddressDTODataBuilder.builder().build());
            Assertions.fail();
        }
        catch (InvalidRequestException exception){
            Assertions.assertEquals("The address already exists in the database", exception.getMessage());
        }

    }

    @Test
    @DisplayName("Should test create method with exception")
    void shouldTestCreateMethodWithException(){

        Mockito.when(addressValidation.validateRequest(Mockito.any())).thenReturn(false);

        try {
            service.create(AddressDTODataBuilder.builder().build());
            Assertions.fail();
        }
        catch(InvalidRequestException exception) {
            Assertions.assertEquals("Address validation failed", exception.getMessage());
        }

    }

    @Test
    @DisplayName("Should test findAll method with success")
    void shouldTestFindAllMethodWithSuccess(){

        List<AddressEntity> addresses = new ArrayList<>();
        addresses.add(AddressEntityDataBuilder.builder().build());

        Mockito.when(modelMapper.mapper()).thenReturn(new ModelMapper());
        Mockito.when(repository.findAll()).thenReturn(addresses);

        Assertions.assertEquals("[AddressDTO(id=1, street=Rua 9, neighborhood=Lauzane Paulista, number=583, " +
                "postalCode=02442-090, city=CityDTO(id=1, city=São Paulo, state=SAO_PAULO, addresses=null), " +
                "customers=null)]", service.findAll().toString());

    }


    @Test
    @DisplayName("Should test findAll method with exception")
    void shouldTestFindAllMethodWithException(){

        List<AddressEntity> addresses = new ArrayList<>();
        Mockito.when(repository.findAll()).thenReturn(addresses);

        try {
            service.findAll();
            Assertions.fail();
        }
        catch(ObjectNotFoundException exception) {
            Assertions.assertEquals("There is no addresses saved in the database", exception.getMessage());
        }

    }

    @Test
    @DisplayName("Should test findById method with success")
    void shouldTestFindByIdMethodWithSuccess() {

        Mockito.when(modelMapper.mapper()).thenReturn(new ModelMapper());
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(AddressEntityDataBuilder.builder().build()));

        Assertions.assertEquals("AddressDTO(id=1, street=Rua 9, neighborhood=Lauzane Paulista, number=583, " +
                "postalCode=02442-090, city=CityDTO(id=1, city=São Paulo, state=SAO_PAULO, addresses=null), " +
                "customers=null)", service.findById(1L).toString());

    }

    @Test
    @DisplayName("Should test update method with address not finded")
    void shouldTestUpdateMethodWithAddressNotFinded() {

        Mockito.when(repository.findById(Mockito.any()))
                .thenReturn(Optional.empty());

        try {
            service.update(1L, AddressDTODataBuilder.builder().build());
            Assertions.fail();
        }
        catch (ObjectNotFoundException exception) {
            Assertions.assertEquals("Address not found", exception.getMessage());
        }

    }

    @Test
    @DisplayName("Should test update method with validation failed")
    void shouldTestUpdateMethodWithValidationFailed() {

        Mockito.when(repository.findById(Mockito.any()))
                .thenReturn(Optional.of(AddressEntityDataBuilder.builder().build()));
        Mockito.when(modelMapper.mapper()).thenReturn(new ModelMapper());
        Mockito.when(addressValidation.validateRequest(Mockito.any())).thenReturn(false);

        try {
            service.update(1L, AddressDTODataBuilder.builder().build());
            Assertions.fail();
        }
        catch (InvalidRequestException exception) {
            Assertions.assertEquals("Address validation failed", exception.getMessage());
        }

    }

    @Test
    @DisplayName("Should test delete method with success")
    void shouldTestDeleteMethodWithSuccess() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(AddressEntityDataBuilder.builder().build()));
        Mockito.when(cityRepository.findById(Mockito.any())).thenReturn(Optional.of(CityEntityDataBuilder.builder().withAddress().build()));
        Assertions.assertTrue(service.deleteById(1L));
    }

    @Test
    @DisplayName("Should test delete method with exception")
    void shouldTestDeleteMethodWithException() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());

        try{
            service.deleteById(1L);
            Assertions.fail();
        }
        catch (ObjectNotFoundException exception) {
            Assertions.assertEquals("Address not found", exception.getMessage());
        }

    }

    @Test
    @DisplayName("Should test delete method with city not found")
    void shouldTestDeleteMethodWithCityNotFound() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(AddressEntityDataBuilder.builder().build()));
        Mockito.when(cityRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        try{
            service.deleteById(1L);
            Assertions.fail();
        }
        catch (ObjectNotFoundException exception) {
            Assertions.assertEquals("City not found", exception.getMessage());
        }

    }


}
