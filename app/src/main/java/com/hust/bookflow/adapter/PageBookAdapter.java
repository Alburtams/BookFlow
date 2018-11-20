package com.hust.bookflow.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hust.bookflow.R;
import com.hust.bookflow.adapter.base.BasePagerAdapter;
import com.hust.bookflow.model.bean.BooksBean;

import java.util.List;

/**
 * Created by ChinaLHR on 2016/12/24.
 * Email:13435500980@163.com
 */

public class PageBookAdapter extends BasePagerAdapter<BooksBean> {

    public PageBookAdapter(Context context, List<BooksBean> date) {
        super(context, date);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mFooterView != null && viewType == TYPE_FOOTER)
            return new MyViewHolder(mFooterView);

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_homelist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) return;

        ((PageBookAdapter.MyViewHolder)holder).item_base_tv_title.setText(mDate.get(position).getBook_name());

        Glide.with(mContext)
                .load(mDate.get(position).getPicture())
                .into(((PageBookAdapter.MyViewHolder)holder).item_base_iv);

        //设置点击事件
        if (mListener!=null){
            ((PageBookAdapter.MyViewHolder)holder).item_movie_cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    mListener.ItemClickListener(holder.itemView, mDate.get(position).getBook_id(),mDate.get(position).getPicture());
                }
            });

            ((PageBookAdapter.MyViewHolder)holder).item_movie_cardview.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = holder.getLayoutPosition();
                    mListener.ItemLongClickListener(holder.itemView, position);
                    return true;
                }
            });
        }

        }
    
    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView item_base_iv;
        TextView item_base_tv_title;
        CardView item_movie_cardview;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_base_iv = (ImageView) itemView.findViewById(R.id.item_base_iv);
            item_base_tv_title = (TextView) itemView.findViewById(R.id.item_base_tv_title);
            item_movie_cardview = (CardView) itemView.findViewById(R.id.item_homebook_cardview);
        }
    }
}