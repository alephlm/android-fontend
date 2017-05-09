package br.gov.ce.sda.androidsda.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.List;

import br.gov.ce.sda.androidsda.R;
import br.gov.ce.sda.androidsda.activity.FilmeActivity;
import br.gov.ce.sda.androidsda.adapter.FilmesAdapter;
import br.gov.ce.sda.androidsda.adapter.ItemClickListener;
import br.gov.ce.sda.androidsda.model.Filme;
import br.gov.ce.sda.androidsda.rest.ServiceAPI;
import br.gov.ce.sda.androidsda.rest.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FilmesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FilmesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilmesFragment extends Fragment implements ItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private static final String TAG = FilmesFragment.class.getSimpleName();
    public static final String EXTRA_MESSAGE = "br.gov.ce.sda.FILME";

    List<Filme> filmes;
    FilmesAdapter fa;
    private RecyclerView recyclerView = null;
    private ItemClickListener icl;

    public FilmesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FilmesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilmesFragment newInstance(String param1, String param2) {
        FilmesFragment fragment = new FilmesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_filmes, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        icl = this;
        listFilmes();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view, int position) {
        Log.d(TAG, "ON CLICK MAIN: " + position);
        final Filme filme = filmes.get(position);
        Gson gS = new Gson();
        String target = gS.toJson(filme);
        Intent i = new Intent(getActivity(), FilmeActivity.class);
        i.putExtra(EXTRA_MESSAGE, target);
        startActivity(i);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void listFilmes(){
        ServiceAPI serviceAPI = ServiceGenerator.createService(ServiceAPI.class);
        String token = this.getActivity().getSharedPreferences("myPreferences", 0).getString("token", "");

        Call<List<Filme>> call = serviceAPI.listFilmes(token);

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
