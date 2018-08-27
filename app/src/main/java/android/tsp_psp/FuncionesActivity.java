package android.tsp_psp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import java.sql.Time;

public class FuncionesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funciones);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            case R.id.btn1:
                Intent intent=new Intent(this, TimeLogActivity.class);
                startActivity(intent);
                break;

            case R.id.btn2:
                Intent intent2=new Intent(this, DefectLogActivity.class);
                startActivity(intent2);
                break;

            case R.id.btn3:
                Intent intent3=new Intent(this, ProjectPlanSummaryActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
