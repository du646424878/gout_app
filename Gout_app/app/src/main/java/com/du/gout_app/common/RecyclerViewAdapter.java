package com.du.gout_app.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.du.gout_app.R;
import com.du.gout_app.activity.News_Activity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by QiWangming on 2015/6/13.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int PIC_ITEM = 1;
    private static final int NO_PIC_ITEM = 0;
    private List<News> newses;
    private Map<Integer,Bitmap> maps;
    private Context context;
    private LayoutInflater mLayoutInflater;
    public RecyclerViewAdapter(List<News> newses,Map<Integer,Bitmap> mp, Context context) {
        this.newses = newses;
        this.context= context;
        this.maps = mp;
        this.mLayoutInflater =LayoutInflater.from(context);
    }


    //自定义ViewHolder类
    public class News_nopicViewHolder extends RecyclerView.ViewHolder{

         CardView cardView;
         TextView news_title;
         TextView news_intro;
         TextView news_time;
         Button share;
         Button readMore;
         News_nopicViewHolder(final View itemView) {
            super(itemView);
            cardView= (CardView) itemView.findViewById(R.id.card_view);
            news_title= (TextView) itemView.findViewById(R.id.news_title);
            news_intro= (TextView) itemView.findViewById(R.id.news_intro);
            share= (Button) itemView.findViewById(R.id.btn_share);
            readMore= (Button) itemView.findViewById(R.id.btn_more);
            news_time = (TextView)itemView.findViewById(R.id.news_time);
        }


    }
    public class News_picViewHolder extends RecyclerView.ViewHolder{


        ImageView news_photo;
        CardView cardView;
        TextView news_title;
        TextView news_intro;
        TextView news_time;
        Button share;
        Button readMore;
        News_picViewHolder(final View itemView) {
            super(itemView);
            cardView= (CardView) itemView.findViewById(R.id.card_view);
            news_title= (TextView) itemView.findViewById(R.id.news_title);
            news_intro= (TextView) itemView.findViewById(R.id.news_intro);
            share= (Button) itemView.findViewById(R.id.btn_share);
            readMore= (Button) itemView.findViewById(R.id.btn_more);
            news_time = (TextView)itemView.findViewById(R.id.news_time);
            news_photo= (ImageView) itemView.findViewById(R.id.news_photo);
            //设置TextView背景为半透明
            news_title.setBackgroundColor(Color.argb(20, 0, 0, 0));

        }


    }

    //定义结束

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if(i==NO_PIC_ITEM)
            return new News_nopicViewHolder(mLayoutInflater.inflate(R.layout.news_item_nopic,viewGroup,false));
       else
            return new News_picViewHolder(mLayoutInflater.inflate(R.layout.news_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder personViewHolder, int i) {
  final int j=i;
        if(personViewHolder instanceof News_nopicViewHolder){
            News_nopicViewHolder vhnop=(News_nopicViewHolder)personViewHolder;
            vhnop.news_title.setText(newses.get(i).getTitle());
            if(newses.get(i).getIntro()!=null)
                vhnop.news_intro.setText(newses.get(i).getIntro());
            else
                vhnop.news_intro.setText("没有简介");
            long ttime= newses.get(i).getCreatedate().getTime();
            ttime +=8*3600*1000;
            vhnop.news_time.setText(new Date(ttime).toLocaleString());
            //为btn_share btn_readMore cardView设置点击事件
            vhnop.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,News_Activity.class);
                    intent.putExtra("itemid",newses.get(j).getId());
                    context.startActivity(intent);
                }
            });

            vhnop.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                    intent.putExtra(Intent.EXTRA_TEXT,"https://gout.suntao.science/app/news/render/" + newses.get(j).getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(Intent.createChooser(intent, newses.get(j).getTitle()));
                }
            });

            vhnop.readMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,News_Activity.class);
                    intent.putExtra("itemid",newses.get(j).getId());
                    context.startActivity(intent);
                }
            });
        }
        else if(personViewHolder instanceof News_picViewHolder){
            News_picViewHolder vhp=(News_picViewHolder)personViewHolder;
            vhp.news_photo.setImageBitmap(maps.get(j));
            vhp.news_title.setText(newses.get(i).getTitle());
            if(newses.get(i).getIntro()!=null)
                vhp.news_intro.setText(newses.get(i).getIntro());
            else
                vhp.news_intro.setText("没有简介");
            long ttime= newses.get(i).getCreatedate().getTime();
            ttime +=8*3600*1000;
            vhp.news_time.setText(new Date(ttime).toLocaleString());
            //为btn_share btn_readMore cardView设置点击事件
            vhp.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,News_Activity.class);
                    intent.putExtra("itemid",newses.get(j).getId());
                    context.startActivity(intent);
                }
            });

            vhp.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                    intent.putExtra(Intent.EXTRA_TEXT,"https://gout.suntao.science/app/news/render/" + newses.get(j).getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(Intent.createChooser(intent, newses.get(j).getTitle()));
                }
            });

            vhp.readMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,News_Activity.class);
                    intent.putExtra("itemid",newses.get(j).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
    //  添加数据
    public void addData(int position,News news) {
//      在list中添加数据，并通知条目加入一条
        newses.add(news);
        notifyItemInserted(position);
    }
    public void addBitmap(Integer i,Bitmap bm){
        maps.put(i,bm);
    }

    @Override
    public int getItemCount() {
        return newses.size();
    }
    @Override
    public int getItemViewType(int position) {
       if(newses.get(position).getImg()!=null)
        return PIC_ITEM;
        else
           return NO_PIC_ITEM;
    }
}
