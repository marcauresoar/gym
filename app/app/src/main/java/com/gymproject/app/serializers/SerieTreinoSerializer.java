package com.gymproject.app.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.gymproject.app.models.SerieTreino;

import java.lang.reflect.Type;

public class SerieTreinoSerializer implements JsonSerializer<SerieTreino> {
    @Override
    public JsonElement serialize(SerieTreino src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("tipo", src.getTipo());
        jsonObject.addProperty("tempo", src.getTempo());
        jsonObject.addProperty("repeticoes", src.getRepeticoes());
        jsonObject.addProperty("peso", src.getPeso());
        jsonObject.add("exercicio_treino", context.serialize(src.getExercicio_treino()));
        return jsonObject;
    }
}
