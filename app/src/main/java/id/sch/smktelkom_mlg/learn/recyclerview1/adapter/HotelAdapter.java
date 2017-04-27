package id.sch.smktelkom_mlg.learn.recyclerview1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import id.sch.smktelkom_mlg.learn.recyclerview1.R;
import id.sch.smktelkom_mlg.learn.recyclerview1.databerita;


public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.MyHolder> {

    List<databerita> data = Collections.emptyList();
    databerita current;
    int currentPos = 0;
    private Context context;
    private LayoutInflater inflater;


    public HotelAdapter(Context context, List<databerita> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        MyHolder myHolder = holder;
        databerita current = data.get(position);
        myHolder.textTitle.setText(current.title);
        myHolder.textAuthor.setText(current.author);
        myHolder.textContent.setText(current.content);

        Glide.with(context).load("http://dev.republika.co.id/android/latest/smktelkom/" + current.thumbnail)
                .into(myHolder.imgpic1);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView textTitle;
        TextView textAuthor;
        TextView textContent;
        ImageView imgpic1;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.textTitle);
            imgpic1 = (ImageView) itemView.findViewById(R.id.imgpic1);
            textAuthor = (TextView) itemView.findViewById(R.id.textAuthor);
            textContent = (TextView) itemView.findViewById(R.id.textContent);
        }

    }


}
