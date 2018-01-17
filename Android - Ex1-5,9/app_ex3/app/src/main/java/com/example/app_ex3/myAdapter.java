package com.example.app_ex3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by ilay on 14/11/2017.
 */

public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {

    private List<String> mDataSet;
    private Context mContext;

    public myAdapter(Context context, List<String> dataSet) {
        this.mContext = context;
        this.mDataSet = dataSet;
    }
    @Override
    public myAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.images, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(myAdapter.ViewHolder viewHolder, int position) {
        int resourceId = mContext.getResources().getIdentifier(mDataSet.get(position), "drawable", mContext.getPackageName());
        String input = mDataSet.get(position);
        viewHolder.imageView.setImageResource(resourceId);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.personImage);
        }
    }

}

