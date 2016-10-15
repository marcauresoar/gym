package com.gymproject.app.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.gymproject.app.R;
import com.gymproject.app.adapters.CustomSpinnerAdapter;
import com.gymproject.app.classes.ItemData;
import com.gymproject.app.dao.ExercicioDao;
import com.gymproject.app.dao.ExercicioTreinoDao;
import com.gymproject.app.dao.FichaDao;
import com.gymproject.app.dao.SerieDao;
import com.gymproject.app.dao.SerieTreinoDao;
import com.gymproject.app.dao.TreinoDao;
import com.gymproject.app.models.Exercicio;
import com.gymproject.app.models.ExercicioTreino;
import com.gymproject.app.models.Ficha;
import com.gymproject.app.models.Serie;
import com.gymproject.app.models.SerieTreino;
import com.gymproject.app.models.Treino;
import com.gymproject.app.sync.SyncService;
import com.gymproject.app.sync.event.SyncEvent;
import com.gymproject.app.sync.event.SyncStatus;
import com.gymproject.app.sync.event.SyncType;
import com.gymproject.app.utils.HashUtils;
import com.gymproject.app.utils.SessionUtils;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;

public class SalvarTreinoActivity extends AppCompatActivity {

    Snackbar snackbar;
    CoordinatorLayout coordinatorLayout;

    EditText edtData, edtHoraInicio, edtHoraFim;
    Spinner spnFicha;
    TextView textView3;

    DatePickerDialog dialogData;
    TimePickerDialog dialogHoraInicio;
    TimePickerDialog dialogHoraFim;
    SimpleDateFormat dateFormatter;

    Realm realm;

    String id = "";

    ArrayList<ItemData> mItens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salvar_treino);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        realm = Realm.getDefaultInstance();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        mItens = new ArrayList<>();
        List<Ficha> items = FichaDao.getAll(realm);
        for(Ficha item : items){
            mItens.add(new ItemData(item.getNome(), 0, item.getId()));
        }
        spnFicha =(Spinner)findViewById(R.id.spnFicha);
        CustomSpinnerAdapter adapter=new CustomSpinnerAdapter(this,
                R.layout.row_grupo_muscular, R.id.text, mItens);
        spnFicha.setAdapter(adapter);


        dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        edtData = (EditText) findViewById(R.id.edtData);
        edtData.setInputType(InputType.TYPE_NULL);
        edtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogData.show();
            }
        });
        edtData.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    dialogData.show();
                }
            }
        });

        edtHoraInicio = (EditText) findViewById(R.id.edtHoraInicio);
        edtHoraInicio.setInputType(InputType.TYPE_NULL);
        edtHoraInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogHoraInicio.show();
            }
        });
        edtHoraInicio.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    dialogHoraInicio.show();
                }
            }
        });

        edtHoraFim = (EditText) findViewById(R.id.edtHoraFim);
        edtHoraFim.setInputType(InputType.TYPE_NULL);
        edtHoraFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogHoraFim.show();
            }
        });
        edtHoraFim.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    dialogHoraFim.show();
                }
            }
        });

        Calendar newCalendar1 = Calendar.getInstance();
        dialogData = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                edtData.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar1.get(Calendar.YEAR), newCalendar1.get(Calendar.MONTH), newCalendar1.get(Calendar.DAY_OF_MONTH));

        Calendar newCalendar2 = Calendar.getInstance();
        dialogHoraInicio = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String hora = ("00" + String.valueOf(selectedHour)).substring(String.valueOf(selectedHour).length());
                String minuto = ("00" + String.valueOf(selectedMinute)).substring(String.valueOf(selectedMinute).length());
                edtHoraInicio.setText(hora + ":" + minuto + ":00");
            }
        }, newCalendar2.get(Calendar.HOUR), newCalendar2.get(Calendar.MINUTE), true);

        Calendar newCalendar3 = Calendar.getInstance();
        dialogHoraFim = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String hora = ("00" + String.valueOf(selectedHour)).substring(String.valueOf(selectedHour).length());
                String minuto = ("00" + String.valueOf(selectedMinute)).substring(String.valueOf(selectedMinute).length());
                edtHoraFim.setText(hora + ":" + minuto + ":00");
            }
        }, newCalendar3.get(Calendar.HOUR), newCalendar3.get(Calendar.MINUTE), true);

        textView3 = (TextView) findViewById(R.id.textView3);


        Bundle b = getIntent().getExtras();
        if(b!=null){
            id = b.getString("id");
        }

        if(!id.isEmpty()) {
            preencherForm();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_criar_treino, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_salvar:
                salvarRegistro();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void salvarRegistro(){
        String data = edtData.getText().toString();
        String horaInicio = edtHoraInicio.getText().toString();
        String horaFim = edtHoraFim.getText().toString();
        ItemData selectedItem = (ItemData) spnFicha.getSelectedItem();
        String ficha_id = selectedItem.getId();
        if(data.isEmpty()){
            snackbar = Snackbar
                    .make(coordinatorLayout, "Por favor, preencha o campo Data.", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if(horaInicio.isEmpty()){
            snackbar = Snackbar
                    .make(coordinatorLayout, "Por favor, preencha o campo Hora início.", Snackbar.LENGTH_LONG);
            snackbar.show();
        }  else if(ficha_id.isEmpty() && id.isEmpty()){
            snackbar = Snackbar
                    .make(coordinatorLayout, "Por favor, preencha o campo Ficha realizada.", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            // cria o registro e insere no realm
            Treino treino = new Treino();
            String acao = "";
            if(!id.isEmpty()) {
                treino.setId(id);
                acao = "update";
            }else{
                treino.setId(HashUtils.generateId());
                acao = "insert";
            }

            try {
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date date = formatter.parse(data);
                data = new SimpleDateFormat("yyyy-MM-dd").format(date);
                System.out.println(data);
            } catch (Exception e){
                e.printStackTrace();
            }

            treino.setData(data);
            treino.setHora_inicio(horaInicio);
            treino.setHora_fim(horaFim);
            treino.setUsuario(SessionUtils.getInstance(getApplicationContext()).getUsuario());
            treino.setFicha_id(ficha_id);
            TreinoDao.save(treino, acao);

            // dispara sync e fecha activity
            SyncEvent.send(SyncType.TREINOS, SyncStatus.COMPLETED);
            SyncService.request(SyncType.TREINOS);
            finish();
        }
    }

    public void preencherForm () {
        Treino treino = TreinoDao.getById(realm, id);

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dataFormatada = "";
        try {
            Date date = formatter.parse(treino.getData());
            dataFormatada = new SimpleDateFormat("dd/MM/yyyy").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        edtData.setText(dataFormatada);

        edtHoraInicio.setText(treino.getHora_inicio());
        edtHoraFim.setText(treino.getHora_fim());


        spnFicha.setVisibility(View.GONE);
        textView3.setVisibility(View.GONE);
    }

}
