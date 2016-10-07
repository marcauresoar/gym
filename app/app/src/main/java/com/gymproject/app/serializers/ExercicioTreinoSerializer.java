package com.gymproject.app.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.gymproject.app.models.ExercicioTreino;

import java.lang.reflect.Type;

public class ExercicioTreinoSerializer implements JsonSerializer<ExercicioTreino> {
    @Override
    public JsonElement serialize(ExercicioTreino src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("nome", src.getNome());
        jsonObject.addProperty("grupo_muscular", src.getGrupo_muscular());
        jsonObject.add("treino", context.serialize(src.getTreino()));
        return jsonObject;
    }
}
