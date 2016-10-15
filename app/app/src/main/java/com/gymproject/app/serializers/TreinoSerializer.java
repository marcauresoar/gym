package com.gymproject.app.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.gymproject.app.models.Treino;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

public class TreinoSerializer implements JsonSerializer<Treino> {
    @Override
    public JsonElement serialize(Treino src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("data", src.getData());
        jsonObject.addProperty("hora_inicio", src.getHora_inicio());
        jsonObject.addProperty("hora_fim", src.getHora_inicio());
        jsonObject.add("usuario", context.serialize(src.getUsuario()));
        jsonObject.addProperty("ficha_id", src.getFicha_id());
        return jsonObject;
    }
}
