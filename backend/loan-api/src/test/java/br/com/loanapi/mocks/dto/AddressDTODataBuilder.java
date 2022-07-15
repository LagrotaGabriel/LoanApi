package br.com.loanapi.mocks.dto;

import br.com.loanapi.mocks.entity.AddressEntityDataBuilder;
import br.com.loanapi.mocks.entity.CityEntityDataBuilder;
import br.com.loanapi.models.dto.AddressDTO;

public class AddressDTODataBuilder {

    AddressDTO address;
    AddressDTODataBuilder(){}

    public static AddressDTODataBuilder builder(){

        AddressDTODataBuilder builder = new AddressDTODataBuilder();
        builder.address = new AddressDTO();

        builder.address.setId(1L);
        builder.address.setStreet("Rua 9");
        builder.address.setNeighborhood("Lauzane Paulista");
        builder.address.setNumber(583);
        builder.address.setPostalCode("02442-090");
        builder.address.setCity(CityDTODataBuilder.builder().build());
        builder.address.setCustomers(null);

        return builder;

    }

    public AddressDTODataBuilder withCityWithAddressInList(){
        address.setCity(CityDTODataBuilder.builder().withAddress().build());
        return this;
    }


    public AddressDTODataBuilder withTooLongStreet(){
        address.setStreet("This is a street too long. Too long that the characters numbers is + 65");
        return this;
    }

    public AddressDTODataBuilder withTooLongNeighborhood(){
        address.setNeighborhood("This is a neighborhood too long. Too long that the characters numbers is + 65");
        return this;
    }

    public AddressDTODataBuilder withTooLongNumber(){
        address.setNumber(5830000);
        return this;
    }

    public AddressDTODataBuilder withAlphanumPostalCode(){
        address.setPostalCode("abcd1-abc");
        return this;
    }

    public AddressDTO build(){
        return address;
    }
}
