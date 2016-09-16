package com.gymproject.app.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.gymproject.app.models.Exercicio;

import java.lang.reflect.Type;

public class ExercicioSerializer implements JsonSerializer<Exercicio> {
    @Override
    public JsonElement serialize(Exercicio src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("nome", src.getNome());
        jsonObject.addProperty("grupo_muscular", src.getGrupo_muscular());
        jsonObject.add("ficha", context.serialize(src.getFicha()));
        return jsonObject;
    }
}
