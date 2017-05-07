package br.gov.ce.sda.androidsda.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import br.gov.ce.sda.androidsda.R;
import br.gov.ce.sda.androidsda.adapter.FilmesAdapter;
import br.gov.ce.sda.androidsda.adapter.ItemClickListener;
import br.gov.ce.sda.androidsda.model.Filme;
import br.gov.ce.sda.androidsda.rest.ServiceAPI;
import br.gov.ce.sda.androidsda.rest.ServiceGenerator;
import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaFilmesActivity extends AppCompatActivity implements ItemClickListener{

    private static final String TAG = ListaFilmesActivity.class.getSimpleName();
    public static final String EXTRA_MESSAGE = "br.gov.ce.sda.FILME";

    List<Filme> filmes;
    FilmesAdapter fa;
    private RecyclerView recyclerView = null;
    private ItemClickListener icl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_filmes);

        icl = this;
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listFilmes();
    }

    @Override
    public void onClick(View view, int position) {
        Log.d(TAG, "ON CLICK MAIN: " + position);
        final Filme filme = filmes.get(position);
        Gson gS = new Gson();
        String target = gS.toJson(filme);
        Intent i = new Intent(this, FilmeActivity.class);
        i.putExtra(EXTRA_MESSAGE, target);
        startActivity(i);
    }

    private void listFilmes(){
        ServiceAPI serviceAPI = ServiceGenerator.createService(ServiceAPI.class);

        Call<List<Filme>> call = serviceAPI.listFilmes(getSharedPreferences("myPreferences", 0).getString("token", ""));

        call.enqueue(new Callback<List<Filme>>() {
            @Override
            public void onResponse(Call<List<Filme>> call, Response<List<Filme>> response) {

                filmes = response.body();
                fa = new FilmesAdapter(filmes, R.layout.list_item_filme);
                recyclerView.setAdapter(fa);
                fa.setClickListener(icl);
                Log.d(TAG, "Number of movies received: " + filmes.size());

                if(response.code() == 200){
                    Log.d(TAG, "onResponse: " + response.body().toString());
                } else {
                    Log.e(TAG, "onResponse: Usuário não autenticado.");
                }
            }

            @Override
            public void onFailure(Call<List<Filme>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
}
