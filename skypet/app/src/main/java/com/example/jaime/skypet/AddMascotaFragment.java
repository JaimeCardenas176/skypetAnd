package com.example.jaime.skypet;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jaime.skypet.API.ApiSkypet;
import com.example.jaime.skypet.API.ServiceGenerator;
import com.example.jaime.skypet.models.Pet;
import com.example.jaime.skypet.utils.Const;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddMascotaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddMascotaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMascotaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    EditText editNombre, editSex, editLoc;
    String nombre , sexo, localizacion, tokenUser, IdUser;
    Button add;
    Context ctx;
    private OnFragmentInteractionListener mListener;

    public AddMascotaFragment() {
        // Required empty public constructor
    }

    public static AddMascotaFragment newInstance(String token, String id) {
        AddMascotaFragment fragment = new AddMascotaFragment();
        Bundle args = new Bundle();
        args.putString(Const.USER_TOKEN, token);
        args.putString(Const.USER_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tokenUser = getArguments().getString(Const.USER_TOKEN);
            IdUser = getArguments().getString(Const.USER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_mascota, container, false);
        Bundle extras = getArguments();
        if(extras!=null){
            tokenUser = extras.getString(Const.USER_TOKEN);
            IdUser = extras.getString(Const.USER_ID);
        }

        ctx= getContext();
        editNombre = view.findViewById(R.id.nombreMascota);
        editSex = view.findViewById(R.id.sexoMascota);
        editLoc = view.findViewById(R.id.localizacionMascota);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre = editNombre.getText().toString();
                sexo = editSex.getText().toString();
                localizacion = editLoc.getText().toString();

                if("".equals(nombre) || "".equals(sexo) || "".equals(localizacion)){
                    Toast.makeText(ctx, "Introduzca los datos necesarios", Toast.LENGTH_SHORT).show();
                }else{
                    ApiSkypet servicio = ServiceGenerator.createService(ApiSkypet.class);
                    Pet pet = new Pet(nombre, sexo, IdUser, 0, localizacion, new Date(),null);
                    Call<Pet> response = servicio.addPet(pet);

                    response.enqueue(new Callback<Pet>() {
                        @Override
                        public void onResponse(Call<Pet> call, Response<Pet> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(ctx, "mascota añadida con éxito", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Pet> call, Throwable t) {
                            Toast.makeText(ctx, "Se ha producido un error inesperado", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
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
