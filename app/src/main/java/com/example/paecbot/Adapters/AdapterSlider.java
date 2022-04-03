package com.example.paecbot.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.example.paecbot.Objetos.ScrenItem;
import com.example.paecbot.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class AdapterSlider extends RecyclerView.Adapter<AdapterSlider.SliderViewHolder> {

    private List<ScrenItem> slideritem;
    private ViewPager2 viewPager2;
    Context contexto;

    public AdapterSlider(List<ScrenItem> slideritem, ViewPager2 viewPager2)
    {
        this.slideritem = slideritem;
        this.viewPager2 = viewPager2;
    }



    @NonNull
    @Override
    public AdapterSlider.SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item_container,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSlider.SliderViewHolder holder, int position) {

        holder.setImage(slideritem.get(position));

        if(position==slideritem.size()-2)
        {
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return slideritem.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder
    {
        private RoundedImageView imageView;

        public SliderViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageslide);
        }


        void setImage (ScrenItem sliderIterm)
        {
            imageView.setImageResource(sliderIterm.getFototutorial());
        }

    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            slideritem.addAll(slideritem);
            notifyDataSetChanged();
        }
    };
}
