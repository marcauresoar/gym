package com.gymproject.app.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.gymproject.app.models.UpdateExercicioTreino;

import java.lang.reflect.Type;

public class UpdateExercicioTreinoSerializer implements JsonSerializer<UpdateExercicioTreino> {
    @Override
    public JsonElement serialize(UpdateExercicioTreino src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("acao", src.getAcao());
        jsonObject.addProperty("mid", src.getMid());
        jsonObject.add("exercicio_treino", context.serialize(src.getExercicio_treino()));
        return jsonObject;
    }
}
