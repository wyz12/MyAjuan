package zxwl.com.myajuan.Log;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import zxwl.com.myajuan.R;

/**
 * Created by Administrator on 2017/12/6.
 */

public class Zadapter extends BaseAdapter{
    private ArrayList<String> list;
    private Context context;

    public Zadapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        HH hh;
        if(view==null){
            hh = new HH();
            view = LayoutInflater.from(context).inflate(R.layout.zbj, null);
            hh.img= view.findViewById(R.id.imagename);
            view.setTag(hh);
        }else {
            hh= (HH) view.getTag();
        }
        Bitmap bitmap = BitmapFactory.decodeFile(list.get(i));
        hh.img.setImageBitmap(bitmap);
        return view;
    }
    class  HH{
        ImageView img;
    }
}
