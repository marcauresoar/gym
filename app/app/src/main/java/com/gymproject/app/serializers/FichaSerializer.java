package com.gymproject.app.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.gymproject.app.models.Ficha;
import java.lang.reflect.Type;

public class FichaSerializer implements JsonSerializer<Ficha> {
    @Override
    public JsonElement serialize(Ficha src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("nome", src.getNome());
        jsonObject.addProperty("dias_semana", src.getDias_semana());
        jsonObject.add("usuario", context.serialize(src.getUsuario()));
        return jsonObject;
    }
}
