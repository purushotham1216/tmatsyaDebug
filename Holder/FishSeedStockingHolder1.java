package com.org.nic.ts.Holder;


import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.org.nic.ts.Interface.ItemClickListener;
import com.org.nic.ts.R;


public class FishSeedStockingHolder1 extends RecyclerView.ViewHolder {

    public TextView spices_name_txt,
    fingerling_per_kg_txt,
            total_no_of_kgs_txt,
    total_no_of_fingerlings_txt,
            entered_spices_count_txt,
    vehicle_txt;

    public CheckBox checkBox;

    private ItemClickListener itemClickListener;

    public FishSeedStockingHolder1(View itemView) {
        super(itemView);

        this.spices_name_txt = itemView.findViewById(R.id.spices_name_txt);
        this.fingerling_per_kg_txt = itemView.findViewById(R.id.fingerling_per_kg_txt);
        this.total_no_of_kgs_txt = itemView.findViewById(R.id.total_no_of_kgs_txt);
        this.total_no_of_fingerlings_txt = itemView.findViewById(R.id.total_no_of_fingerlings_txt);
        this.entered_spices_count_txt = itemView.findViewById(R.id.entered_spices_count_txt);
        this.vehicle_txt = itemView.findViewById(R.id.vehicle_txt);
//        this.amt_txt = itemView.findViewById(R.id.amt_txt);
//        checkBox = itemView.findViewById(R.id.checkbox_sub_component_chq_dist);


        // itemView.setOnClickListener(this);
    }


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
