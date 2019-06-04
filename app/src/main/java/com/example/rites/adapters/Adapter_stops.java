package com.example.rites.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.rites.R;

import com.example.rites.models.IntermediateStop;

import java.util.List;

public class Adapter_stops extends RecyclerView.Adapter<Adapter_stops.ViewHolder> {

    private List<IntermediateStop> list;
    private int layout;

    public Adapter_stops(List<IntermediateStop> stops, int layout)
    {
        this.list = stops;
        this.layout = layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(layout,viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.bind(list.get(position),position);

    }

    @Override
    public int getItemCount() {
        if (list!=null)
            return  list.size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView_stop;

        public ViewHolder(View v){
            super(v);
            textView_stop = itemView.findViewById(R.id.textView_stop);
        }

        public void bind(final IntermediateStop name, final int position)
        {
            textView_stop.setText(String.valueOf(position+1)+": "+name.getPlace());
        }
    }
}
