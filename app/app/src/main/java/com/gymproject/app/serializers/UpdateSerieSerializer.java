package com.gymproject.app.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.gymproject.app.models.UpdateSerie;

import java.lang.reflect.Type;

public class UpdateSerieSerializer implements JsonSerializer<UpdateSerie> {
    @Override
    public JsonElement serialize(UpdateSerie src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("acao", src.getAcao());
        jsonObject.addProperty("mid", src.getMid());
        jsonObject.add("serie", context.serialize(src.getSerie()));
        return jsonObject;
    }
}
