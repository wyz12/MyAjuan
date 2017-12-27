package zxwl.com.myajuan;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import zxwl.com.myajuan.Log.ImgFileListActivity;

public class MainActivity extends AppCompatActivity  {
    int alpha=0;
    float ratio=0.1f;
    ListView listView;
    ListView listView2;
    ArrayList<String> listfile=new ArrayList<String>();
    ArrayList<String> llist=new ArrayList<String>();

    private MyZoomImageView imagename;
    private String s;
    private String s1;
    private Bitmap bm;
    private Button buxuanz;
    private Button bupiany;
    private Button bucaijian;
    private Button busuof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView) findViewById(R.id.listView1);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }



        imagename = (MyZoomImageView) findViewById(R.id.imagename);
        bupiany = (Button) findViewById(R.id.bupiany);
        buxuanz = (Button) findViewById(R.id.buxuanz);
        bucaijian = (Button) findViewById(R.id.bucaijian);
        busuof = (Button) findViewById(R.id.busuof);


        Bundle bundle= getIntent().getExtras();


        if (bundle!=null) {
            if (bundle.getStringArrayList("files")!=null) {
                listfile= bundle.getStringArrayList("files");

//                ArrayAdapter<String> arryAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listfile);
                final ArrayList<String> listfile = this.listfile;
                Adapter adapter = new Adapter(listfile, this);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                        final Bitmap bitmap = BitmapFactory.decodeFile(listfile.get(i));

                        imagename.setImageBitmap(bitmap);

                        buxuanz.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (alpha < 345) {
                                    alpha += 15;
                                } else {
                                    alpha = 0;
                                }
                                Bitmap bitmap1 = rotateBitmap(bitmap,alpha);
                                imagename.setImageBitmap(bitmap1);
                            }
                        });
                        bupiany.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bitmap skewBM = skewBitmap(bitmap);

                                imagename.setImageBitmap(skewBM);
                            }
                        });
                        busuof.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (ratio < 3) {
                                    ratio += 0.2f;
                                } else {
                                    ratio = 1.0f;
                                }

                                Bitmap bitmap1 = scaleBitmap(bitmap,ratio);
                                imagename.setImageBitmap(bitmap1);
                            }
                        });
                        bucaijian.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Bitmap cropBitmap = cropBitmap(bitmap);


                                imagename.setImageBitmap(cropBitmap);
                            }
                        });
                    }
                });



            }
        }

    }




    public void chise(View v){
        Intent intent = new Intent();
        intent.setClass(this,ImgFileListActivity.class);
        startActivity(intent);
    }



    private Bitmap scaleBitmap(Bitmap origin, int newWidth, int newHeight) {
        if (origin == null) {
            return null;
        }
        int height = origin.getHeight();
        int width = origin.getWidth();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);// 使用后乘
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (!origin.isRecycled()) {
            origin=null;
        }
        return newBM;
    }

    /**
     * 按比例缩放图片
     *
     * @param origin 原图
     * @param ratio  比例
     * @return 新的bitmap
     */
    private Bitmap scaleBitmap(Bitmap origin, float ratio) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin=null;
        return newBM;
    }

    /**
     * 裁剪
     *
     * @param bitmap 原图
     * @return 裁剪后的图像
     */
    private Bitmap cropBitmap(Bitmap bitmap) {
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();
        int cropWidth = w >= h ? h : w;// 裁切后所取的正方形区域边长
        cropWidth /= 2;
        int cropHeight = (int) (cropWidth / 1.2);
        return Bitmap.createBitmap(bitmap, w / 3, 0, cropWidth, cropHeight, null, false);
    }

    /**
     * 选择变换
     *
     * @param origin 原图
     * @param alpha  旋转角度，可正可负
     * @return 旋转后的图片
     */
    private Bitmap rotateBitmap(Bitmap origin, float alpha) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(alpha);
        // 围绕原地进行旋转
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin=null;
        return newBM;
    }

    /**
     * 偏移效果
     * @param origin 原图
     * @return 偏移后的bitmap
     */
    private Bitmap skewBitmap(Bitmap origin) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.postSkew(-0.6f, -0.3f);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin=null;
        return newBM;
    }
}