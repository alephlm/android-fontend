package br.gov.ce.sda.androidsda.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import br.gov.ce.sda.androidsda.R;
import br.gov.ce.sda.androidsda.model.Filme;

public class FilmeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme);

        TextView titulo = (TextView) findViewById(R.id.tvTitulo);

        Intent intent = getIntent();
        String target = intent.getStringExtra(ListaFilmesActivity.EXTRA_MESSAGE);
        Gson gs = new Gson();
        Filme filme = gs.fromJson(target, Filme.class);
        titulo.setText(filme.getTitulo());
    }
}
