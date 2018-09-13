package com.example.luka.dogspictures;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.luka.dogspictures.MainActivity.KEY_IMAGES_SAVED;

public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.ViewHolder> {
    private Context context;
    private List<String> items;
    private boolean isSaved;

    public DogsAdapter(Context context, List<String> items, boolean isSaved) {
        this.context = context;
        this.items = items;
        this.isSaved = isSaved;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.dog_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Picasso.with(context)
                .load(items.get(position))
                .fit()
                .placeholder(R.drawable.dog_photo)
                .error(R.drawable.dog_photo2)
                .into(viewHolder.image);

        if (isSaved) {
            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteImageFromList(position);

                }
            });
        } else viewHolder.btnDelete.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void deleteImageFromList(int position) {
        List<String> newList = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (i != position) newList.add(items.get(i));
        }
        items = newList;
        notifyDataSetChanged();

        SharedPreferences pref = context.getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_IMAGES_SAVED, new Gson().toJson(items)).apply();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

}
