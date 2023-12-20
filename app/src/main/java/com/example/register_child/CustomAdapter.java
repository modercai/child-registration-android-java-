package com.example.register_child;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList child_id;
    private ArrayList first_name;
    private ArrayList last_name;
    private ArrayList child_age;
    private ArrayList child_gender;
    private ArrayList child_immunization;
    CustomAdapter(Context context, ArrayList child_id,ArrayList first_name,ArrayList last_name,ArrayList child_age,ArrayList child_gender,ArrayList child_immunization){
        this.context = context;
        this.child_id = child_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.child_age = child_age;
        this.child_gender = child_gender;
        this.child_immunization = child_immunization;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view  = layoutInflater.inflate(R.layout.list_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.firstName.setText(String.valueOf(first_name.get(position)));
        holder.lastName.setText(String.valueOf(last_name.get(position)));
        holder.child_id.setText(String.valueOf(child_id.get(position)));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChildProfile.class);
                intent.putExtra("CHILD_ID", String.valueOf(child_id.get(position)));
                intent.putExtra("FIRST_NAME", String.valueOf(first_name.get(position)));
                intent.putExtra("LAST_NAME", String.valueOf(last_name.get(position)));
                intent.putExtra("AGE", String.valueOf(child_age.get(position)));
                intent.putExtra("GENDER", String.valueOf(child_gender.get(position)));
                intent.putExtra("IMMUNIZATION", String.valueOf(child_immunization.get(position)));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return first_name.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView child_id, firstName, lastName;
        LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.child_fname);
            lastName = itemView.findViewById(R.id.child_lname);
            child_id = itemView.findViewById(R.id.child_id);
            linearLayout = itemView.findViewById(R.id.linearlayout);
        }
    }
}
