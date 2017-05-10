package br.gov.ce.sda.androidsda.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import br.gov.ce.sda.androidsda.R;
import br.gov.ce.sda.androidsda.fragment.FilmesFragment;
import br.gov.ce.sda.androidsda.model.Filme;
import br.gov.ce.sda.androidsda.rest.ServiceAPI;
import br.gov.ce.sda.androidsda.rest.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmeActivity extends AppCompatActivity {

    private static final String TAG = FilmeActivity.class.getSimpleName();
    private Filme filme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme);

        TextView titulo = (TextView) findViewById(R.id.tvTitulo);
        FloatingActionButton favoritar = (FloatingActionButton) findViewById(R.id.fabFavoritar);

        Intent intent = getIntent();
        String target = intent.getStringExtra(FilmesFragment.EXTRA_MESSAGE);
        Gson gs = new Gson();
        filme = gs.fromJson(target, Filme.class);
        titulo.setText(filme.getTitulo());

        favoritar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoritar();
            }
        });
    }

    private void favoritar() {
        ServiceAPI serviceAPI = ServiceGenerator.createService(ServiceAPI.class);

        Call<String> call = serviceAPI.favoritar(getSharedPreferences("myPreferences", 0).getString("token", ""), filme.getId());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, "onResponse: " + response.toString());
                Context context = getApplicationContext();
                CharSequence text = filme.getTitulo() + " adicionado aos favoritos";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
}
