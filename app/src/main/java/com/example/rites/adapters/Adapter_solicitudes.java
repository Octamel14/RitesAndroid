package com.example.rites.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rites.API.API;
import com.example.rites.API.APIservice.SubeleService;
import com.example.rites.R;
import com.example.rites.activities.RideDetailsActivity;
import com.example.rites.activities.SolicitudesActivity;
import com.example.rites.models.Ride;
import com.example.rites.models.RideFilter;
import com.example.rites.models.RideGuest;
import com.example.rites.models.Solicitud;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter_solicitudes extends RecyclerView.Adapter<Adapter_solicitudes.ViewHolder>
{
    private List<Solicitud> list;
    private  int layout;

    private OnItemClickListener listener;

    public static Context context;

    public Adapter_solicitudes(List<Solicitud> solicitudes, int layout, OnItemClickListener listener)
    {
        this.list=solicitudes;
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
        public TextView tvName;
        //public TextView tvOrigin;
        //public TextView tvDestination;
        public Button btAccept;
        public Button btDeny;
        public TextView tvStatus;



        public ViewHolder(View v)
        {
            super(v);
            tvName=itemView.findViewById(R.id.tv_guest_name);
            //tvOrigin=itemView.findViewById(R.id.tv_origin);
            //tvDestination=itemView.findViewById(R.id.tv_destination);
            btAccept=itemView.findViewById(R.id.bt_Accept);
            btDeny=itemView.findViewById(R.id.bt_Deny);
            tvStatus=itemView.findViewById(R.id.tv_Status);
        }

        public void bind(final Solicitud name, final OnItemClickListener listener)
        {
            tvName.setText("Nombre: " + name.getUser().getFirst_name()+" "+name.getUser().getLast_name());
            //tvOrigin.setText("Origen: "+name.getRide());
            //tvDestination.setText("Destination: " + name.getRide());

            if(name.getStatus() != 0)
            {
                btAccept.setVisibility(View.GONE);
                btDeny.setVisibility(View.GONE);
                tvStatus.setVisibility(View.GONE);
            }
            if(name.getStatus() == 1)
                tvStatus.setVisibility(View.VISIBLE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(name, getAdapterPosition());
                }
            });

            btAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RideGuest up_guest = new RideGuest(name.getGuest_id(), name.getRide(),
                            Integer.toString(name.getUser().getId_user()), "1", "false");

                    SubeleService service  = API.getApi().create(SubeleService.class);
                    Call<RideGuest> call = service.putGuest(name.getGuest_id(), up_guest);
                    call.enqueue(new Callback<RideGuest>() {
                        @Override
                        public void onResponse(Call<RideGuest> call, Response<RideGuest> response) {
                            Toast.makeText(context, "ACEPTADO", Toast.LENGTH_SHORT).show();
                            ((Activity)context).recreate();
                        }

                        @Override
                        public void onFailure(Call<RideGuest> call, Throwable t) {
                            Log.d("CLICK", "ERROR");
                        }
                    });
                }
            });

            btDeny.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RideGuest up_guest = new RideGuest(name.getGuest_id(), name.getRide(),
                            Integer.toString(name.getUser().getId_user()), "2", "false");

                    SubeleService service  = API.getApi().create(SubeleService.class);
                    Call<RideGuest> call = service.putGuest(name.getGuest_id(), up_guest);
                    call.enqueue(new Callback<RideGuest>() {
                        @Override
                        public void onResponse(Call<RideGuest> call, Response<RideGuest> response) {
                            Toast.makeText(context, "RECHAZADO", Toast.LENGTH_SHORT).show();
                            ((Activity)context).recreate();
                        }

                        @Override
                        public void onFailure(Call<RideGuest> call, Throwable t) {
                            Log.d("CLICK", "ERROR");
                        }
                    });
                }
            });
        }


    }


    public interface OnItemClickListener{
        void onItemClick(Solicitud name, int position);
    }

    public void setContext(Context curr_context)
    {
        context = curr_context;
    }

}
