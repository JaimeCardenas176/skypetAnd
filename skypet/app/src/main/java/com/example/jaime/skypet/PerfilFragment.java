package com.example.jaime.skypet;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaime.skypet.API.ApiSkypet;
import com.example.jaime.skypet.API.ServiceGenerator;
import com.example.jaime.skypet.models.Pet;
import com.example.jaime.skypet.models.User;
import com.example.jaime.skypet.utils.Const;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilFragment extends android.support.v4.app.Fragment {
    TextView nombre, apellidos, email, direccion, telefono, numMascotas;
    String token , idUser;
    Integer longitudLista;
    Context ctx;

    private OnFragmentInteractionListener mListener;

    public PerfilFragment() {
        // Required empty public constructor
    }


    public static PerfilFragment newInstance(String token, String id) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putString(Const.USER_TOKEN, token);
        args.putString(Const.USER_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        Bundle extras = getArguments();
        if(extras!=null){
            token = extras.getString(Const.USER_TOKEN);
            idUser = extras.getString(Const.USER_ID);
        }
        ctx = getContext();
        nombre = view.findViewById(R.id.nombreUsuario);
        apellidos = view.findViewById(R.id.apellidosUsuario);
        email = view.findViewById(R.id.emailUsuario);
        direccion = view.findViewById(R.id.direccionUsuario);
        telefono = view.findViewById(R.id.telefonoUsuario);
        numMascotas = view.findViewById(R.id.numeroMascotas);

        String fullToken = "Bearer " + token;
        ApiSkypet service = ServiceGenerator.createService(ApiSkypet.class);
        Call<User> userDetail = service.details(fullToken, idUser);
        Call<List<Pet>> mascotas = service.mascotasUsuario(fullToken, idUser);
            mascotas.enqueue(new Callback<List<Pet>>() {
                @Override
                public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                    if(response.isSuccessful()){
                        longitudLista = response.body().size();
                    }
                }

                @Override
                public void onFailure(Call<List<Pet>> call, Throwable t) {

                }
            });
                userDetail.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User u = response.body();
                    nombre.setText(u.getName());
                    apellidos.setText(u.getSurname());
                    email.setText(u.getEmail());
                    direccion.setText(u.getAddress());
                    telefono.setText(u.getPhone());
                    numMascotas.setText(longitudLista.toString());
                }else{
                    Toast.makeText(ctx, "Error al cargar los datos de usuario", Toast.LENGTH_SHORT).show();
                }

                }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ctx, "Se ha producido un error de conexión", Toast.LENGTH_SHORT).show();

            }
        });

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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
