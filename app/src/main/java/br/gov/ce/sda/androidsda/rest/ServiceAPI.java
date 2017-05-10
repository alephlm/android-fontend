package br.gov.ce.sda.androidsda.rest;

import java.util.List;

import br.gov.ce.sda.androidsda.model.Filme;
import br.gov.ce.sda.androidsda.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * This interface contains signatures of methods for retrofit communication with server.
 * Esta interface contem assinatura de metodos usados pelo retrofit na comunicação com o servidor
 */

public interface ServiceAPI {
    @GET("user/user")
    Call<User> getExampleMethod(@Header("Authorization") String token);

    @POST("user/registration")
    Call<String> createUser(@Body User user);

    @GET("user/login")
    Call<String> login(@Header("Authorization") String token);

    @GET("filme/list")
    Call<List<Filme>> listFilmes(@Header("Authorization") String token);

    @GET("user/favoritar/{id}")
    Call<String> favoritar(@Header("Authorization") String token, @Path("id") Long var);

    @GET("filme/favoritos")
    Call<List<Filme>> favoritos(@Header("Authorization") String token);

    @GET("filme/procura")
    Call<List<Filme>> procuraFilme(@Header("Authorization") String token, @Query("titulo") String titulo);
}
