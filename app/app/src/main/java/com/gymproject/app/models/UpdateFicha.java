package com.gymproject.app.models;

import com.gymproject.app.models.Ficha;
import com.raizlabs.android.dbflow.sql.language.Update;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.UpdateFichaRealmProxy;
import io.realm.annotations.PrimaryKey;

@Parcel(implementations = { UpdateFichaRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { UpdateFicha.class })
public class UpdateFicha extends RealmObject {
    @PrimaryKey
    private String id;
    private String acao;
    private Ficha ficha;

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    public UpdateFicha createCopy(){
        UpdateFicha updateFicha = new UpdateFicha();
        updateFicha.setId(this.id);
        updateFicha.setAcao(this.acao);
        updateFicha.setFicha(this.ficha.createCopy());
        return updateFicha;
    }
}
