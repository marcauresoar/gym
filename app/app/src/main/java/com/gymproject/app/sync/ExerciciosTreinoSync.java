package com.gymproject.app.sync;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.gymproject.app.classes.Status;
import com.gymproject.app.dao.ExercicioTreinoDao;
import com.gymproject.app.dao.SerieTreinoDao;
import com.gymproject.app.dao.UpdateExercicioTreinoDao;
import com.gymproject.app.dao.UpdateSerieTreinoDao;
import com.gymproject.app.models.ExercicioTreino;
import com.gymproject.app.models.SerieTreino;
import com.gymproject.app.models.UpdateExercicioTreino;
import com.gymproject.app.models.UpdateSerieTreino;
import com.gymproject.app.restful.RestfulAPI;
import com.gymproject.app.services.ExercicioTreinoService;
import com.gymproject.app.services.SerieTreinoService;
import com.gymproject.app.sync.event.SyncEvent;
import com.gymproject.app.sync.event.SyncStatus;
import com.gymproject.app.sync.event.SyncType;
import com.gymproject.app.utils.SessionUtils;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ExerciciosTreinoSync extends AbsSync {

    Context context;

    ExerciciosTreinoSync(@NonNull Context context) {
        super(context);
    }

    @Override
    protected SyncType getSyncType() {
        return SyncType.EXERCICIOS_TREINOS;
    }

    @Override
    protected void post() {

        SyncEvent.send(getSyncType(), SyncStatus.IN_PROGRESS);

        Log.i("ExerciciosTreinoSync", "execute post method");

        List<UpdateExercicioTreino> updates = UpdateExercicioTreinoDao.getAll(Realm.getDefaultInstance());

        ExercicioTreinoService apiService =
                RestfulAPI.getClient().create(ExercicioTreinoService.class);

        Call<Status> call = apiService.update(updates);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status>call, Response<Status> response) {
                Status status = response.body();
                if(status != null) {
                    Log.e("updateExercicios", status.getMensagem());
                    UpdateExercicioTreinoDao.removeAll();

                    List<UpdateSerieTreino> updates = UpdateSerieTreinoDao.getAll(Realm.getDefaultInstance());

                    SerieTreinoService apiService =
                            RestfulAPI.getClient().create(SerieTreinoService.class);

                    Call<Status> call2 = apiService.update(updates);
                    call2.enqueue(new Callback<Status>() {
                        @Override
                        public void onResponse(Call<Status>call, Response<Status> response) {
                            Status status = response.body();
                            if(status != null) {
                                Log.e("updateSeriesTreino", status.getMensagem());
                            } else {
                                onFailure(call, new Throwable("Erro com os dados retornados do servidor!"));
                            }
                            UpdateSerieTreinoDao.removeAll();
                            get();
                        }

                        @Override
                        public void onFailure(Call<Status> call, Throwable t) {
                            Log.e("updateSeriesTreino", t.toString());
                            SyncEvent.send(getSyncType(), SyncStatus.COMPLETED);
                        }
                    });

                } else {
                    onFailure(call, new Throwable("Erro com os dados retornados do servidor!"));
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Log.e("updateExerciciosTreino", t.toString());
                SyncEvent.send(getSyncType(), SyncStatus.COMPLETED);
            }
        });

    }

    @Override
    protected void get() {
        Log.i("ExerciciosTreinoSync", "execute get method");

        ExercicioTreinoService apiService =
                RestfulAPI.getClient().create(ExercicioTreinoService.class);

        Call<Status<List<ExercicioTreino>>> call = apiService.listar(SessionUtils.getInstance(context).getIdUsuario());
        call.enqueue(new Callback<Status<List<ExercicioTreino>>>() {
            @Override
            public void onResponse(Call<Status<List<ExercicioTreino>>>call, Response<Status<List<ExercicioTreino>>> response) {
                Status status = response.body();
                if(status != null) {
                    if(status.getCodigo() == 1) {
                        List<ExercicioTreino> itens = (List<ExercicioTreino>) status.getDados();
                        ExercicioTreinoDao.removeAll();
                        ExercicioTreinoDao.save(itens);

                        SerieTreinoService apiService =
                                RestfulAPI.getClient().create(SerieTreinoService.class);

                        Call<Status<List<SerieTreino>>> call2 = apiService.listar(SessionUtils.getInstance(context).getIdUsuario());
                        call2.enqueue(new Callback<Status<List<SerieTreino>>>() {
                            @Override
                            public void onResponse(Call<Status<List<SerieTreino>>>call, Response<Status<List<SerieTreino>>> response) {
                                Status status = response.body();
                                if(status != null) {
                                    if(status.getCodigo() == 1) {
                                        List<SerieTreino> itens = (List<SerieTreino>) status.getDados();
                                        SerieTreinoDao.removeAll();
                                        SerieTreinoDao.save(itens);

                                        SyncEvent.send(getSyncType(), SyncStatus.COMPLETED);
                                    } else {
                                        onFailure(call, new Throwable("Erro ao listar series!"));
                                    }
                                }else {
                                    onFailure(call, new Throwable("Erro com os dados retornados do servidor!"));
                                }
                            }

                            @Override
                            public void onFailure(Call<Status<List<SerieTreino>>> call, Throwable t) {
                                Log.e("listarSeriesTreino", t.toString());
                                SyncEvent.send(getSyncType(), SyncStatus.COMPLETED);
                            }
                        });
                    } else {
                        onFailure(call, new Throwable("Erro ao listar exercicios!"));
                    }
                }else {
                    onFailure(call, new Throwable("Erro com os dados retornados do servidor!"));
                }
            }

            @Override
            public void onFailure(Call<Status<List<ExercicioTreino>>> call, Throwable t) {
                Log.e("listarExerciciosTreino", t.toString());
                SyncEvent.send(getSyncType(), SyncStatus.COMPLETED);
            }
        });
    }

}
