package com.example.rites.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rites.R;
import com.example.rites.activities.RideDetailsActivity;
import com.example.rites.activities.Rider_Activity;
import com.example.rites.models.Host;
import com.example.rites.models.LogedUser;
import com.example.rites.models.RideFilter;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class Adapter_rides extends RecyclerView.Adapter<Adapter_rides.ViewHolder>
{
    static private List<RideFilter> list;
    private  int layout;
    private int position;
    static Activity activity;
    static Realm realm;
    static RealmResults<LogedUser> userx;

    private OnItemClickListener listener;

    public Adapter_rides(List<RideFilter> rides, int layout, OnItemClickListener listener, Activity activity)
    {
        this.list=rides;
        this.layout=layout;
        this.listener=listener;
        this.activity=activity;

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

    public static  class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{
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
            v.setOnCreateContextMenuListener(this);
        }

        public void bind(final RideFilter name, final OnItemClickListener listener)
        {

            textView_destination.setText("Destino: "+name.getDestination());
            textView_starting_point.setText("Origen: "+name.getStarting_point());
            textView_hostname.setText("Conductor: "+ name.getHost().getFirst_name()+" "+ name.getHost().getLast_name());
            textView_cost.setText("Precio: "+name.getCost());
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


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Ver:");
            MenuInflater inflater=activity.getMenuInflater();
            inflater.inflate(R.menu.context_ride_detail_ride_activity, menu);
            for(int i=0; i<menu.size();i++)
            {
                menu.getItem(i).setOnMenuItemClickListener(this);  //Se anade el evento MenuItemClick
            }

        }

        @Override
        public  boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId())
            {
                case (R.id.view_details):
                    realm=Realm.getDefaultInstance();  //////////Inicializar DB interna
                    userx=realm.where(LogedUser.class).findAll();  //Recuperar el valor de usuario
                    final String ride_id = list.get(getAdapterPosition()).getId_ride();
                    final Host h= list.get(getAdapterPosition()).getHost();

                    Intent intent = new Intent(activity, RideDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ride_id",ride_id);
                    bundle.putInt("opc",1); //opc=0=no mostar detalles del vehiculo | 1=mostrar
                    bundle.putSerializable("host",h);
                    intent.putExtras(bundle);
                    activity.startActivity(intent);
                    return true;
                default:
                    return false;
            }
        }
    }




    public interface OnItemClickListener{
        void onItemClick(RideFilter name, int position);
    }





}
