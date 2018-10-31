package com.hust.bookflow.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hust.bookflow.R;
import com.hust.bookflow.adapter.base.BaseCollectionAdapter;
import com.hust.bookflow.model.bean.BookListBeans;

import java.util.List;

/**
 * Created by 文辉 on 2018/10/31.
 */

public class BookBackAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<BookListBeans> mbooks;

    /**
     * 点击回调接口
     */
    public OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    public interface OnItemClickListener {
        void onItemClick(String id, String url);
        void onBtnClick(String id);
    }

    public BookBackAdapter(Context context, List<BookListBeans> list) {
        this.mContext = context;
        this.mbooks = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Holder holder = new Holder(LayoutInflater.from(mContext).inflate(R.layout.item_book_to_back, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Glide.with(mContext)
                .load(mbooks.get(position).getPicture())
                .into(((Holder)holder).item_back_iv);
        ((Holder)holder).item_back_title.setText(mbooks.get(position).getBook_name());
        ((Holder)holder).item_back_tv1.setText("作者：" + mbooks.get(position).getAuthor());
        ((Holder)holder).item_back_tv2.setText("出版社：" + mbooks.get(position).getPress());

        if(mListener != null) {
            ((Holder) holder).item_back_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(mbooks.get(position).getBook_id(), mbooks.get(position).getPicture());
                }
            });

            ((Holder) holder).item_back_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onBtnClick(mbooks.get(position).getBook_id());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mbooks.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public CardView item_back_card;
        public ImageView item_back_iv;
        public TextView item_back_title;
        public TextView item_back_tv1;
        public TextView item_back_tv2;
//        public TextView item_back_tv3;
        public Button item_back_btn;

        public Holder(View itemView) {
            super(itemView);
            item_back_card = (CardView) itemView.findViewById(R.id.item_back_card);
            item_back_iv = (ImageView) itemView.findViewById(R.id.item_back_iv);
            item_back_title = (TextView) itemView.findViewById(R.id.item_back_title);
            item_back_tv1 = (TextView) itemView.findViewById(R.id.item_back_tv1);
            item_back_tv2 = (TextView) itemView.findViewById(R.id.item_back_tv2);
//            item_back_tv3 = (TextView) itemView.findViewById(R.id.item_back_tv3);
            item_back_btn = (Button) itemView.findViewById(R.id.book_back_btn);
        }
    }
}
