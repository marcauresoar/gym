package com.gymproject.app.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.gymproject.app.models.UpdateFicha;

import java.lang.reflect.Type;

public class UpdateFichaSerializer implements JsonSerializer<UpdateFicha> {
    @Override
    public JsonElement serialize(UpdateFicha src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("acao", src.getAcao());
        jsonObject.add("ficha", context.serialize(src.getFicha()));
        return jsonObject;
    }
}
