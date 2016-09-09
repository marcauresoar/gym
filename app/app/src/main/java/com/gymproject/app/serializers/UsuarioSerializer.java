package com.gymproject.app.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.gymproject.app.models.Ficha;
import com.gymproject.app.models.Usuario;

import java.lang.reflect.Type;

public class UsuarioSerializer implements JsonSerializer<Usuario> {
    @Override
    public JsonElement serialize(Usuario src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("nome", src.getNome());
        jsonObject.addProperty("email", src.getEmail());
        jsonObject.addProperty("senha", src.getSenha());
        return jsonObject;
    }
}
