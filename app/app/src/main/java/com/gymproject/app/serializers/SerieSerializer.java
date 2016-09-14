package com.gymproject.app.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.gymproject.app.models.Exercicio;
import com.gymproject.app.models.Serie;

import java.lang.reflect.Type;

public class SerieSerializer implements JsonSerializer<Serie> {
    @Override
    public JsonElement serialize(Serie src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("tipo", src.getTipo());
        jsonObject.addProperty("repeticoes", src.getRepeticoes());
        jsonObject.addProperty("tempo", src.getTempo());
        jsonObject.add("exercicio", context.serialize(src.getExercicio()));
        return jsonObject;
    }
}
