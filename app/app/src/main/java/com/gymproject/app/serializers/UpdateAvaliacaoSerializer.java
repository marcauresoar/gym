package com.gymproject.app.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.gymproject.app.models.UpdateAvaliacao;

import java.lang.reflect.Type;

public class UpdateAvaliacaoSerializer implements JsonSerializer<UpdateAvaliacao> {
    @Override
    public JsonElement serialize(UpdateAvaliacao src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("acao", src.getAcao());
        jsonObject.addProperty("mid", src.getMid());
        jsonObject.add("avaliacao", context.serialize(src.getAvaliacao()));
        return jsonObject;
    }
}
