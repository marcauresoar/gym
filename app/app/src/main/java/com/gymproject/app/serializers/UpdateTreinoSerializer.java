package com.gymproject.app.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.gymproject.app.models.UpdateTreino;

import java.lang.reflect.Type;

public class UpdateTreinoSerializer implements JsonSerializer<UpdateTreino> {
    @Override
    public JsonElement serialize(UpdateTreino src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("acao", src.getAcao());
        jsonObject.addProperty("mid", src.getMid());
        jsonObject.add("treino", context.serialize(src.getTreino()));
        return jsonObject;
    }
}
