package br.gov.ce.sda.androidsda.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity{

    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText user;
    private EditText password;
    private Button bEntrar;
    private Button bNovaConta;
    private LoginActivity la;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        la = this;

        user = (EditText) findViewById(R.id.etUsuario);
        password = (EditText) findViewById(R.id.etPassword);
        bEntrar = (Button) findViewById(R.id.bEntrar);
        bNovaConta = (Button) findViewById(R.id.bNovaConta);

        bEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        ServiceAPI serviceAPI = ServiceGenerator.createService(ServiceAPI.class);
        String authToken = Credentials.basic(user.getText().toString(), password.getText().toString());

        SharedPreferences gameSettings = getSharedPreferences("myPreferences", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = gameSettings.edit();
        prefEditor.putString("token", authToken);
        prefEditor.commit();

        Call<String> call = serviceAPI.login(getSharedPreferences("myPreferences", 0).getString("token", ""));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.code() == 200){
                    Log.d(TAG, "onResponse: " + response.body().toString());
                    Intent i = new Intent(la, MainActivity.class);
                    startActivity(i);
                } else {
                    Log.e(TAG, "onResponse: Usuário não autenticado.");
                    Context context = getApplicationContext();
                    CharSequence text = "Usuário ou senha inválidos";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
}
