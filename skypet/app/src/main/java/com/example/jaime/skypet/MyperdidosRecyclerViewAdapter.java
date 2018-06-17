package com.example.jaime.skypet;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jaime.skypet.models.Pet;
import com.example.jaime.skypet.perdidosFragment.OnListFragmentInteractionListener;

import java.util.List;


public class MyperdidosRecyclerViewAdapter extends RecyclerView.Adapter<MyperdidosRecyclerViewAdapter.ViewHolder> {

    private final List<Pet> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyperdidosRecyclerViewAdapter(List<Pet> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_perdidos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNombre.setText(mValues.get(position).getName());
        holder.mSexo.setText(mValues.get(position).getGender());
        holder.mLoc.setText(mValues.get(position).getLocation());
        holder.mEstado.setText(mValues.get(position).getState());

        /*holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNombre;
        public final TextView mSexo;
        public final TextView mLoc;
        public final TextView mEstado;
        public Pet mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNombre = (TextView) view.findViewById(R.id.nombrePet);
            mSexo = (TextView) view.findViewById(R.id.genderPet);
            mLoc = (TextView) view.findViewById(R.id.localizacionPet);
            mEstado = (TextView) view.findViewById(R.id.estadoPet);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItem.getName() + " '";
        }
    }
}
