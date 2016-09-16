package com.gymproject.app.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.gymproject.app.models.UpdateExercicio;

import java.lang.reflect.Type;

public class UpdateExercicioSerializer implements JsonSerializer<UpdateExercicio> {
    @Override
    public JsonElement serialize(UpdateExercicio src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("acao", src.getAcao());
        jsonObject.addProperty("mid", src.getMid());
        jsonObject.add("exercicio", context.serialize(src.getExercicio()));
        return jsonObject;
    }
}
