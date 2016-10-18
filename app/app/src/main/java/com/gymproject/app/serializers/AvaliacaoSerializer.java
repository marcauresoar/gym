package com.gymproject.app.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.gymproject.app.models.Avaliacao;

import java.lang.reflect.Type;

public class AvaliacaoSerializer implements JsonSerializer<Avaliacao> {
    @Override
    public JsonElement serialize(Avaliacao src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());

        jsonObject.addProperty("data", src.getData());
        jsonObject.addProperty("altura", src.getAltura());
        jsonObject.addProperty("peso", src.getPeso());

        jsonObject.addProperty("peitoral", src.getPeitoral());
        jsonObject.addProperty("biceps", src.getBiceps());
        jsonObject.addProperty("triceps", src.getTriceps());
        jsonObject.addProperty("subescapular", src.getSubescapular());
        jsonObject.addProperty("supra_iliaca", src.getSupra_iliaca());
        jsonObject.addProperty("axiliar_media", src.getAxiliar_media());
        jsonObject.addProperty("abdominal", src.getAbdominal());
        jsonObject.addProperty("coxa", src.getCoxa());
        jsonObject.addProperty("panturrilha_media", src.getPanturrilha_media());

        jsonObject.addProperty("torax", src.getTorax());
        jsonObject.addProperty("abdomen", src.getAbdomen());
        jsonObject.addProperty("cintura", src.getCintura());
        jsonObject.addProperty("quadril", src.getQuadril());
        jsonObject.addProperty("braco_direito", src.getBraco_direito());
        jsonObject.addProperty("braco_esquerdo", src.getBraco_esquerdo());
        jsonObject.addProperty("antebraco_direito", src.getAntebraco_direito());
        jsonObject.addProperty("antebraco_esquerdo", src.getAntebraco_esquerdo());
        jsonObject.addProperty("coxa_direita", src.getCoxa_direita());
        jsonObject.addProperty("coxa_esquerda", src.getCoxa_esquerda());
        jsonObject.addProperty("perna_direita", src.getPerna_direita());
        jsonObject.addProperty("perna_esquerda", src.getPerna_esquerda());
        jsonObject.addProperty("ombro", src.getOmbro());
        jsonObject.addProperty("pescoco", src.getPescoco());
        jsonObject.addProperty("punho", src.getPunho());
        jsonObject.addProperty("joelho", src.getJoelho());
        jsonObject.addProperty("tornozelo", src.getTornozelo());

        jsonObject.add("usuario", context.serialize(src.getUsuario()));
        return jsonObject;
    }
}
