package android.tsp_psp.Utilidades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexion extends SQLiteOpenHelper {
    public Conexion(Context context) {
        super(context, "app", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE proyecto(id INTEGER primary key autoincrement, nombre text)");
        db.execSQL("insert into proyecto(nombre) values('android studio')");

        //creacion de la tabla time log

        db.execSQL("create table timelog(id INTEGER PRIMARY KEY AUTOINCREMENT, pashe text, start text , interruption text, stop text, delta text, comments text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
