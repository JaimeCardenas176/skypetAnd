package com.example.jaime.skypet;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jaime.skypet.API.ApiSkypet;
import com.example.jaime.skypet.API.ServiceGenerator;
import com.example.jaime.skypet.dummy.DummyContent;
import com.example.jaime.skypet.dummy.DummyContent.DummyItem;
import com.example.jaime.skypet.models.Pet;
import com.example.jaime.skypet.utils.Const;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class EncontradosFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private misMascotasFragment.OnListFragmentInteractionListener mListener;
    Context ctx;
    private String tokenUser;
    private String idUser;
    RecyclerView recyclerView;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EncontradosFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EncontradosFragment newInstance(int columnCount, String token) {
        EncontradosFragment fragment = new EncontradosFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(Const.USER_TOKEN, token);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mismascotas_list, container, false);
        ctx=getContext();
        Bundle extras = getArguments();
        if(extras!=null){
            tokenUser = extras.getString(Const.USER_TOKEN);

        }
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            ApiSkypet service = ServiceGenerator.createService(ApiSkypet.class);
            Call<List<Pet>> response = service.mascotasEncontradas("Bearer "+tokenUser);
            response.enqueue(new Callback<List<Pet>>() {
                @Override
                public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                    if (response.isSuccessful()){
                        List<Pet> mascotas = response.body();
                        if(mascotas.size()!=0){
                            recyclerView.setAdapter(new MymisMascotasRecyclerViewAdapter(mascotas, mListener));
                        }else{
                            Toast.makeText(ctx, "No hay mascotas encontradas", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Pet>> call, Throwable t) {
                    Toast.makeText(ctx, "Error inesperado", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof misMascotasFragment.OnListFragmentInteractionListener) {
            mListener = (misMascotasFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name

    }
}
