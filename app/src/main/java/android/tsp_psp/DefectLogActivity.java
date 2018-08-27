package android.tsp_psp;

import android.content.DialogInterface;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefectLogActivity extends AppCompatActivity {

    EditText txtDate,txtDefectDescripcion;
    Spinner spnType,spnPhaseInject,spnPhaseRemoved;
    Chronometer chronometer;
    boolean estado;
    private long cronometroDetenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defect_log);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtDate=findViewById(R.id.txtDate);
        txtDefectDescripcion=findViewById(R.id.txtDefectDescripcion);
        spnType=findViewById(R.id.spinnerType);
        spnPhaseInject=findViewById(R.id.spinnerInjected);
        spnPhaseRemoved=findViewById(R.id.spinnerRemoved);
        chronometer=findViewById(R.id.chronometro);
        cargarSpinners();

    }

    private AlertDialog abrirVentanaInfo() {
        AlertDialog.Builder ventana=new AlertDialog.Builder(DefectLogActivity.this);
        LayoutInflater layout=this.getLayoutInflater();
        View view=layout.inflate(R.layout.styled_defect_modal,null);

        ventana.setView(view);
        ventana.create();

        TextView lblDate=view.findViewById(R.id.lblMDate);
        TextView lblType=view.findViewById(R.id.lblDType);
        TextView lblInjected=view.findViewById(R.id.lblDInjected);
        TextView lblRemoved=view.findViewById(R.id.lblDRemoved);
        TextView lblTime=view.findViewById(R.id.lblDTime);
        TextView lblDescripcion=view.findViewById(R.id.lblDDescription);

        lblDate.setText(txtDate.getText().toString());
        lblType.setText(spnType.getSelectedItem().toString());
        lblInjected.setText(spnPhaseInject.getSelectedItem().toString());
        lblRemoved.setText(spnPhaseRemoved.getSelectedItem().toString());
        lblTime.setText(chronometer.getText().toString());
        lblDescripcion.setText(txtDefectDescripcion.getText().toString());

        ventana.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return ventana.show();
    }

    private void cargarSpinners() {
        String [] datosType={"Documentacion","Syntax","Build","Package","Assingment","Interface","Checking","Data","Function","System","Environment"};
        String [] datosInject={"PLAN","DLC","CODE","COMPILE","UP","PM"};
        String [] datosRemoved={"PLAN","DLC","CODE","COMPILE","UP","PM"};

        spnType.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,datosType));
        spnPhaseInject.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,datosInject));
        spnPhaseRemoved.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,datosRemoved));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onclick(View view) {
        switch (view.getId()){
            case R.id.btnDate:
                cargarFechaActual();
                break;

            case R.id.btnStart:
                iniciarCronometro();
                break;

            case R.id.btnStop:
                pausarCronometro();
                break;

            case R.id.btnRestart:
                chronometer.setBase(SystemClock.elapsedRealtime());
                cronometroDetenido=0;
                break;

            case R.id.btnDefectGuardar:
                abrirVentanaInfo();
                break;
        }
    }



    private void pausarCronometro() {
        if (estado){
            chronometer.stop();
            cronometroDetenido=SystemClock.elapsedRealtime()-chronometer.getBase();
            estado=false;
        }
    }

    private void iniciarCronometro() {
        if (!estado){
            chronometer.setBase(SystemClock.elapsedRealtime()- cronometroDetenido);
            chronometer.start();
            estado=true;
        }
    }

    private void cargarFechaActual() {
        SimpleDateFormat formatoFecha=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date=new Date();
        String fecha=formatoFecha.format(date);
        txtDate.setText(fecha);
    }
}
