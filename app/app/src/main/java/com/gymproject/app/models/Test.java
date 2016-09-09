package com.gymproject.app.models;

import com.gymproject.app.db.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class Test extends BaseModel {

    @PrimaryKey
    @Column
    private String id;

    @Column
    private String nome;

    @Column
    @ForeignKey
    private Another another;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Another getAnother() {
        return another;
    }

    public void setAnother(Another another) {
        this.another = another;
    }
}
