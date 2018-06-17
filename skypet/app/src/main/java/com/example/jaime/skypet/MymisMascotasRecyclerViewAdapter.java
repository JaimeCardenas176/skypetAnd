package com.example.jaime.skypet;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jaime.skypet.misMascotasFragment.OnListFragmentInteractionListener;
import com.example.jaime.skypet.dummy.DummyContent.DummyItem;
import com.example.jaime.skypet.models.Pet;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MymisMascotasRecyclerViewAdapter extends RecyclerView.Adapter<MymisMascotasRecyclerViewAdapter.ViewHolder> {

    private final List<Pet> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MymisMascotasRecyclerViewAdapter(List<Pet> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_mismascotas, parent, false);
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
            return super.toString() + " '" + mNombre.getText() + "'";
        }
    }
}
