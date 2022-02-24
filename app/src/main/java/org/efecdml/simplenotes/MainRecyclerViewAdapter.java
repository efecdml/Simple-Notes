package org.efecdml.simplenotes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MainViewHolder> implements Filterable {

    private List<NotesModel> notesModelList;
    private List<NotesModel> notesModelListFull;
    private Context context;
    private Activity activity;

    public MainRecyclerViewAdapter(List<NotesModel> notesModelList, Context context, Activity activity) {
        this.notesModelList = notesModelList;
        notesModelListFull = new ArrayList<>(notesModelList);
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_main_activity_row, parent, false);
        MainViewHolder holder = new MainViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.tv_title.setText(notesModelList.get(position).getTitle());
        holder.tv_content.setText(notesModelList.get(position).getContent());
        holder.tv_date.setText(notesModelList.get(position).getCreatedOrModifiedDate());
        holder.mainRowLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateDeleteNoteActivity.class);
                intent.putExtra("id", Integer.valueOf(notesModelList.get(position).getId()));
                intent.putExtra("title", notesModelList.get(position).getTitle());
                intent.putExtra("content", notesModelList.get(position).getContent());
                intent.putExtra("date", notesModelList.get(position).getCreatedOrModifiedDate());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesModelList.size();
    }

    @Override
    public Filter getFilter() {
        return notesModelFilter;
    }

    private Filter notesModelFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<NotesModel> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(notesModelListFull);
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(NotesModel nm : notesModelListFull){
                    if(nm.getTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(nm);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            notesModelList.clear();
            notesModelList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class MainViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_content;
        TextView tv_date;
        LinearLayout mainRowLinearLayout;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_date = itemView.findViewById(R.id.tv_date);
            mainRowLinearLayout = itemView.findViewById(R.id.mainRowLinearLayout);
        }
    }
}
