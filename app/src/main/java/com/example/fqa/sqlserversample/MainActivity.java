package com.example.fqa.sqlserversample;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.fqa.sqlserversample.Models.Produto;
import com.example.fqa.sqlserversample.Connection.Querys;
import com.example.fqa.sqlserversample.Util.Log;
import com.example.fqa.sqlserversample.Util.NetworkPing;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Produto> produtos;
    private AsyncQuery asyncQuery;
    private Button btnAtualizar;
    private Log log;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            asyncQuery = new AsyncQuery();
            asyncQuery.execute();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log = new Log(this);
        log.logInfo("onCreate onProcesses");
        btnAtualizar = findViewById(R.id.btnAtualizar);
        btnAtualizar.setOnClickListener(onClickListener);
        log.logInfo("btnAtualizar ready");
        asyncQuery = new AsyncQuery();
        log.logInfo("Obj async created");
        log.logInfo("AsyncTask ready to go");
        asyncQuery.execute();
        log.logInfo("AsyncTask runnnig");
    }
    private void Layout(){
        TableLayout table = findViewById(R.id.TableLayout);
        table.removeAllViews();
        log.logInfo("Table cleared");
        if(produtos != null) {
            log.logInfo("produtos not empty");
            for (Produto produto : produtos) {
                TableRow row = new TableRow(this);
                log.logInfo(String.format("Row to %s created", produto.getProduto()));
                for (int j = 1; j < 4; j++) {
                    TextView label = new TextView(this);
                    log.logInfo(String.format(Locale.ENGLISH, "label %d to %s created", j, produto.getUtil(j)));
                    label.setText(produto.getUtil(j));
                    row.addView(label);
                    log.logInfo(String.format(Locale.ENGLISH, "label %d added to row", j));
                }
                table.addView(row);
                log.logInfo(String.format("Row to %s added", produto.getProduto()));
            }
        }else{
            log.logInfo("produtos is empty");
            for (int i = 1; i < 4; i++) {
                TableRow row = new TableRow(this);
                log.logInfo(String.format(Locale.ENGLISH, "Row %d created",i));
                for (int j = 1; j < 4; j++) {
                    TextView label = new TextView(this);
                    log.logInfo(String.format(Locale.ENGLISH, "label %d created",j));
                    label.setText(String.format(Locale.ENGLISH, "Label %d", j));
                    row.addView(label);
                    log.logInfo(String.format(Locale.ENGLISH, "label %d added", j));
                }
                table.addView(row);
                log.logInfo(String.format(Locale.ENGLISH, "row %d added", i));
            }
        }
    }
    class AsyncQuery extends AsyncTask<Void, Void, Void>{
        ProgressDialog dialog;
        @Override
        protected void onPreExecute(){
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Realizando o carregamento dos dados");
            dialog.setMessage("Aguarde o fim da requisição...");
            dialog.show();
            log.logInfo("ProgressDialog showed");
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Querys querys = new Querys();
                log.logInfo("Obj querys created");
                log.logInfo("Query ready to start");
                //produtos = querys.Pesquisar();
                log.logInfo("query completed");
                //NetworkPing np = new NetworkPing();
                //log.logInfo("Obj np created");
                //log.logInfo("np ready to start");
                //np.networkPing(log);
                //log.logInfo("np completed");
            }
            catch (Exception ex){
                produtos = null;
                log.logErro("doInBG erro, produtos is null", ex);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void voids){
            Layout();
            log.logInfo("Layout completed");
            dialog.dismiss();
            log.logInfo("Dialog dismiss");
            log.logInfo("Async Completed");
        }
    }
}
