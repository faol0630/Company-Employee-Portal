package com.faol.security.config;

import com.faol.security.entity.Address;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class AddressSerializer extends JsonSerializer<Address> {

    @Override
    public void serialize(Address address, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("address_id", address.getAddress_id());
        gen.writeStringField("street", address.getStreet());
        gen.writeStringField("number", address.getNumber());
        gen.writeStringField("zipcode", address.getZipcode());
        gen.writeEndObject();
    }
}


