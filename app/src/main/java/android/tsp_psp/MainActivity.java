package android.tsp_psp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.tsp_psp.Utilidades.Conexion;
import android.tsp_psp.Utilidades.Utilidades;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText txtNombre;
    Button btnAdicionar;
    ListView listaProyectos;
    List<String> datos;
    Conexion conexion;

    String datosLista[][]=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombre= findViewById(R.id.txtNombre);
        btnAdicionar= findViewById(R.id.btnAdicionar);
        listaProyectos=findViewById(R.id.listView);
        conexion=new Conexion(this);


        cargarLista();

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarProyecto(view);
            }
        });

        listaProyectos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Utilidades.idProyecto=datosLista[i][0];
                Intent intent=new Intent(MainActivity.this,FuncionesActivity.class);
                startActivity(intent);
            }
        });

    }



    private void adicionarProyecto(View view) {
        if (txtNombre.getText().toString().length()>0){
            SQLiteDatabase db=conexion.getWritableDatabase();
            String cadenaSQL="insert into proyecto(nombre) values('"+txtNombre.getText().toString()+"')";
            db.execSQL(cadenaSQL);
            txtNombre.setText("");
            Snackbar.make(view,"Adicionado correctamente",Snackbar.LENGTH_SHORT).show();
            cargarLista();

        }else{
            Toast.makeText(this, "escriba nombre porfavor", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarLista() {
        datos=new ArrayList<>();

        SQLiteDatabase db=conexion.getReadableDatabase();
        String cadenaSQL="select * from proyecto";
        Cursor cursor=db.rawQuery(cadenaSQL,null);
        int contador=0;

        datosLista=new String[cursor.getCount()][2];
        if (cursor!=null){
            while (cursor.moveToNext()){
                datos.add(cursor.getString(1));
                datosLista[contador][0]=cursor.getString(0).toString();
                datosLista[contador][1]=cursor.getString(1).toString();
                contador++;

            }

            listaProyectos.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datos));
        }
    }
}
