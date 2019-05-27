package com.example.rites.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rites.R;
import com.example.rites.models.Rides;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class Adapter_rides extends RecyclerView.Adapter<Adapter_rides.ViewHolder>
{
    private List<Rides> list;
    private  int layout;

    private OnItemClickListener listener;

    public Adapter_rides(List<Rides> rides, int layout, OnItemClickListener listener)
    {
        this.list=rides;
        this.layout=layout;
        this.listener=listener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.bind(list.get(position), this.listener);

    }

    @Override
    public int getItemCount() {

        if(list!=null)
            return list.size();
        return 0;
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView_starting_point;
        public TextView textView_destination;
        public TextView textView_hostname;
        public TextView textView_date;
        public TextView textView_hour;
        public TextView textView_cost;



        public ViewHolder(View v)
        {
            super(v);
            textView_starting_point=itemView.findViewById(R.id.textView_starting_point);
            textView_destination=itemView.findViewById(R.id.textView_destination);
            textView_hostname=itemView.findViewById(R.id.textView_host_name);
            textView_date=itemView.findViewById(R.id.textView_date);
            textView_hour=itemView.findViewById(R.id.textView_hour);
            textView_cost=itemView.findViewById(R.id.textViewCost);
        }

        public void bind(final Rides name, final OnItemClickListener listener)
        {

            textView_destination.setText("Destino: "+name.getDestination());
            textView_starting_point.setText("Origen: "+name.getStarting_point());
            textView_hostname.setText("Conductor: "+Integer.toString(name.getHost()));
            textView_cost.setText("Precio: "+Float.toString(name.getCost()));
            //DateFormat df=new SimpleDateFormat("dd/MM/yyyy");
           // String date=df.format(name.getDate());
            //textView_date.setText(date);
            textView_date.setText("Fecha: "+name.getDate());
            textView_hour.setText("Hora: "+name.getHour());



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(name, getAdapterPosition());

                }
            });
        }


    }


    public interface OnItemClickListener{
        void onItemClick(Rides name, int position);
    }



}
