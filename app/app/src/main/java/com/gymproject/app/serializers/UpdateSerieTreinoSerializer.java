package com.gymproject.app.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.gymproject.app.models.UpdateSerieTreino;

import java.lang.reflect.Type;

public class UpdateSerieTreinoSerializer implements JsonSerializer<UpdateSerieTreino> {
    @Override
    public JsonElement serialize(UpdateSerieTreino src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("acao", src.getAcao());
        jsonObject.addProperty("mid", src.getMid());
        jsonObject.add("serie_treino", context.serialize(src.getSerie_treino()));
        return jsonObject;
    }
}
