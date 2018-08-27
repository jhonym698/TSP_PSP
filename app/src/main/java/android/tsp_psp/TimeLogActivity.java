package android.tsp_psp;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.tsp_psp.Utilidades.Conexion;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeLogActivity extends AppCompatActivity {

    EditText txtFechaStart,txtFechaStop,txtMinutos,txtTotal,txtDescripcion;
    Spinner spinner;

    View view;
    Conexion conexion=new Conexion(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_log);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtFechaStart= findViewById(R.id.txtFechaStart);
        txtFechaStop= findViewById(R.id.txtFechaStop);
        txtMinutos= findViewById(R.id.txtMinutos);
        txtTotal= findViewById(R.id.txtTotalDelta);
        txtDescripcion= findViewById(R.id.txtDescripcion);
        spinner=findViewById(R.id.spinnerPhase);

        String opciones[]={"PLAN","DLD","CODE","COMPILE","UT","PM"};
        spinner.setAdapter(new  ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, opciones));

        txtFechaStop.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                calcularTotal(view);
            }
        });
        txtFechaStart.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                calcularTotal(view);
            }
        });
        txtMinutos.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                calcularTotal(view);
            }
        });
    }

    private void limpiarFormulario() {
        txtFechaStart.setText("");
        txtFechaStop.setText("");
        txtMinutos.setText("");
        txtTotal.setText("");
        txtDescripcion.setText("");

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case  android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onclick(View view) {

        switch (view.getId()){
            case R.id.btnObtenerFecha1:
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date=new Date();
                String fecha=format.format(date);
                txtFechaStart.setText(fecha);
                break;

            case R.id.btnObtenerFecha2:
                SimpleDateFormat format2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date2=new Date();
                String fecha2=format2.format(date2);
                txtFechaStop.setText(fecha2);
                calcularTotal(getWindow().getDecorView().getRootView());
                break;

            case R.id.btnCalcular:
                calcularTotal(getWindow().getDecorView().getRootView());
                break;

            case  R.id.btnGuardar:
                Toast.makeText(this, "asdf", Toast.LENGTH_SHORT).show();
                abrirVentanaInformacion();
                break;
        }

    }


    private AlertDialog abrirVentanaInformacion() {
        AlertDialog.Builder ventana=new AlertDialog.Builder(TimeLogActivity.this);
        LayoutInflater inflater=this.getLayoutInflater();
        View view=inflater.inflate(R.layout.item_disenio,null);

        ventana.setView(view);
        ventana.create();

        TextView lblspinner=view.findViewById(R.id.lblSpinnerInfo);
        final TextView lblfechaIncio=view.findViewById(R.id.lblFechaInicioInfo);
        final TextView lblInterrupcion=view.findViewById(R.id.lblInterrupcionInfo);
        final TextView lblfechaFin=view.findViewById(R.id.lblFechaFinInfo);
        final TextView lblTotal=view.findViewById(R.id.lblTotalInfo);
        final TextView lblComentario=view.findViewById(R.id.lblComentarioInfo);

        lblspinner.setText(spinner.getSelectedItem().toString());
        lblfechaIncio.setText(txtFechaStart.getText().toString());
        lblfechaFin.setText(txtFechaStop.getText().toString());
        lblInterrupcion.setText(txtMinutos.getText().toString());
        lblTotal.setText(txtTotal.getText().toString());
        lblComentario.setText(txtDescripcion.getText().toString());

        ventana.setPositiveButton("guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String phase=spinner.getSelectedItem().toString();
                String start=lblfechaIncio.getText().toString();
                String interruption=lblInterrupcion.getText().toString();
                String stop=lblfechaFin.getText().toString();
                String delta=lblTotal.getText().toString();
                String comments=lblComentario.getText().toString();

                limpiarFormulario();

                try{
                    SQLiteDatabase db=conexion.getWritableDatabase();
                    String cadenaSQL="insert into timelog (pashe,start,interruption, stop, delta, comments)" +
                            " values ('"+phase+"','"+start+"','"+interruption+"','"+stop+"','"+delta+"','"+comments+"')";

                    db.execSQL(cadenaSQL);

                    Toast.makeText(TimeLogActivity.this, "Datos guardados", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(TimeLogActivity.this
                            , "Por favor asegurese de que los datos esten escritos correctamente",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton("Atras", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return ventana.show();

    }




    private void calcularTotal(View view) {

        try {
            SimpleDateFormat format2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date fechac1=format2.parse(txtFechaStart.getText().toString());
            Date fechac2=format2.parse(txtFechaStop.getText().toString());

            int diferencia= (int) (fechac2.getTime()-fechac1.getTime());

            if (txtMinutos.getText().toString().length()==0){
                int minutos=diferencia/1000/60;
                txtTotal.setText(minutos+" minutos");
            }else{
                int minutos=diferencia/1000/60;
                int interrupcion=Integer.parseInt(txtMinutos.getText().toString());
                int resultado=minutos-interrupcion;
                txtTotal.setText(resultado+" minutos");
            }

        } catch (ParseException e) {
            Snackbar.make(view,"Ingrese los datos correctamente", Snackbar.LENGTH_SHORT).show();
        }
    }
}
