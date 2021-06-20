package com.vaneks.restapi.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.vaneks.restapi.model.Account;

import java.lang.reflect.Type;

public class AutoAdapter implements JsonSerializer<Account> {

    @Override
    public JsonElement serialize(Account account, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", account.getId());
        jsonObject.addProperty("firstName", account.getFirstName());
        jsonObject.addProperty("lastName", account.getLastName());
        jsonObject.addProperty("age", account.getAge());
        return jsonObject;
    }
}
