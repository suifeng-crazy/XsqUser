package com.example.xsq.My;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;
import com.example.xsq.util.BaseActivity;
import com.example.xsq.util.ConnectionAddress;
import com.example.xsq.util.NumberUtil;
import com.example.xsq.util.PopuWindow.SelectPicPopupWindow;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyInfoActivity extends BaseActivity {
    SelectPicPopupWindow menuWindow;
    RelativeLayout mReChangePhone,mReNickName,mReRealName,mReSex,mReHeaderImage;
    ImageView mImLeave,mImHeader;
    TextView mTvPhone,mTvNickeName,mTvRealName,mTvSex;
    Uri mUri;
    Boolean mBoChangeHeader = false;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_me_info);

        initUI();
        initData();
    }

    private void initData() {

        mTvPhone.setText(NumberUtil.user.getUserPhone());
        mTvNickeName.setText(NumberUtil.user.getUserNickName());
        mTvRealName.setText(NumberUtil.user.getUserRealName());
        mTvSex.setText(NumberUtil.user.getUserSex());
        if(NumberUtil.UserHeaderImageBitmap!=null){
            mImHeader.setImageBitmap(NumberUtil.UserHeaderImageBitmap);
        }
    }

    private void initUI() {
        mImHeader = findViewById(R.id.myInfo_headerI);
        mReHeaderImage = findViewById(R.id.myInfo_headIR);
        mReSex = findViewById(R.id.myInfo_sexR);
        mTvPhone= findViewById(R.id.MyInfo_phoneT);
        mTvNickeName= findViewById(R.id.myInfo_nickNameT);
        mTvRealName= findViewById(R.id.myInfo_realNameT);
        mTvSex= findViewById(R.id.myInfo_sexT);
        mImLeave = findViewById(R.id.MeInfo_topI);
        mReRealName = findViewById(R.id.myInfo_realNameR);
        mReNickName = findViewById(R.id.myInfo_nickNameR);
        mReChangePhone = findViewById(R.id.myInfo_phoneR);
        mReChangePhone.setOnClickListener(this);
        mReNickName.setOnClickListener(this);
        mReRealName.setOnClickListener(this);
        mImLeave.setOnClickListener(this);
        mReSex.setOnClickListener(this);
        mReHeaderImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.myInfo_headIR: // 点击头像，准备上传
               //实例化SelectPicPopupWindow
                menuWindow = new SelectPicPopupWindow(MyInfoActivity.this, itemsOnClick);
                //显示窗口
                //设置layout在PopupWindow中显示的位置
                menuWindow.showAtLocation(MyInfoActivity.this.findViewById(R.id.myInfo_headIR), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.myInfo_sexR:
                Intent intent3 = new Intent(this, MyChangeSexActivity.class);
                startActivityForResult(intent3, 3);
                break;
            case R.id.MeInfo_topI:
                if(mBoChangeHeader){
                    Intent data =new Intent();//只是回传数据就不用写跳转对象
                    data.putExtra("data",mBoChangeHeader);//数据放到data里面去
                    setResult(1,data);
                }
                finish();
                break;
            case R.id.myInfo_realNameR:
                strActivity(MyInfoActivity.this,MyRealNameActivity.class);
                break;
            case R.id.myInfo_phoneR:
                Intent intent2 = new Intent(this, MyChangePhoneActivity.class);
                startActivityForResult(intent2, 2);
                break;
            case R.id.myInfo_nickNameR:
                Intent intent = new Intent(this, MyChangeNiceNameActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }


    /*onActivityResult接收数据的方法
   *requestCode：请求的标志
   * resultCode：第二个页面返回的标志，哪个页面跳转的标识
   *data：第二个页面回传的数据，data是回传一个intent对象
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode==2)//通过请求码(去SActivity)和回传码（回传数据到第一个页面）判断回传的页面
        {
            data.getStringExtra("data");
            String content=data.getStringExtra("data");//字符串content得到data数据
            mTvNickeName.setText(content);
        }else if (requestCode==2&&resultCode==2) {
            data.getStringExtra("data");
            String content=data.getStringExtra("data");
            mTvPhone.setText(content);
        }else if(requestCode==3&&resultCode==3) {
            String content=data.getStringExtra("data");
            mTvSex.setText(content);
        }else{
            onActivityResult2(requestCode, resultCode, data);
        }
    }

    // 分割线， 为头像上传做的

    /* 头像文件 */
    private static  String IMAGE_FILE_NAME = "temp_head_image.jpg";

    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;

    // 裁剪后图片的宽(X)和高(Y),100 X 100的正方形。
    private static int output_X = 100;
    private static int output_Y = 100;
    //为底部弹出的窗口进行按钮的监听。
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                //通过拍照获取照片
                case R.id.btn_take_photo:
                    choseHeadImageFromCameraCapture();
                    break;
                //从相册获取照片
                case R.id.btn_pick_photo:
                    choseHeadImageFromGallery();
                    break;
                default:
                    break;
            }
        }
    };

    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_PICK);
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }

    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(
                    new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
            intentFromCapture.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        }

        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }

    protected void onActivityResult2(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
//            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {
            case CODE_GALLERY_REQUEST:
                cropRawPhoto(intent.getData());
                break;

            case CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File tempFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
                    //
                    cropRawPhoto(Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
                            .show();
                }

                break;

            case CODE_RESULT_REQUEST:
                if (intent != null) {
                    setImageToHeadView(intent);
                }

                break;
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);
        intent.putExtra("uri",uri);

        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            // photo 已经是裁剪后的圆形头像了， 要将他上传。
//            Uri uri = (Uri)intent.getExtras("uri");
//            Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            Uri uri = data.getData();
//            Cursor cursor = this.getContentResolver().query(photo, null, null, null, null);
//            cursor.moveToFirst();
//            String imgNo = cursor.getString(0); // 图片编号
//            String imgPath = cursor.getString(1); // 图片文件路径
//            String imgSize = cursor.getString(2); // 图片大小
//            String imgName = cursor.getString(3); // 图片文件名
//
////                    toastMessage(AutoActivity.this, imgPath);
//            System.out.println("imgPath:" + imgPath);
//            cursor.close();
////            intent = new Intent(AutoActivity.this, PhotoUpload.class);
////            uploadFile = imgPath;
//            IMAGE_FILE_NAME = imgPath;
            NumberUtil.UserHeaderImageBitmap = photo;
            new Thread(mRunnalbe).start();

            // photo 已经是裁剪后的圆形头像了， 要将他上传。
            mBoChangeHeader = true;
            mImHeader.setImageBitmap(photo);
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }

    private String newName = "temp_head_image.jpg";
     /* 上传文件至Server的方法 */
    Runnable mRunnalbe = new Runnable() {
        @Override
        public void run() {
//             HttpUtil.download("http://192.168.1.12:8088" + iconUrl, file);
//            String result = testtaskPost(fileUrl);

            String end = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            try {
                URL url = new URL(ConnectionAddress.BASE_Upload_UserHeaderImage+"?member="+NumberUtil.user.getUserID());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
          /* 允许Input、Output，不使用Cache */
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setUseCaches(false);
          /* 设置传送的method=POST */
                con.setRequestMethod("POST");
          /* setRequestProperty */
                con.setRequestProperty("Connection", "Keep-Alive");
                con.setRequestProperty("Charset", "UTF-8");
                con.setRequestProperty("Origin", ConnectionAddress.BASE_Upload_UserHeaderImage+"?member="+NumberUtil.user.getUserID());
                con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
          /* 设置DataOutputStream */
                DataOutputStream ds = new DataOutputStream(con.getOutputStream());
                ds.writeBytes(twoHyphens + boundary + end);
                ds.writeBytes("Content-Disposition: form-data; " +
                        "name=\"file\";filename=\"" +
                        newName + "\"" + end);
                ds.writeBytes(end);
          /* 取得文件的FileInputStream */
                System.out.println("IMAGE_FILE_NAME:"+IMAGE_FILE_NAME);
                FileInputStream fStream = new FileInputStream("/sdcard/"+IMAGE_FILE_NAME);
          /* 设置每次写入1024bytes */
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];
                int length = -1;
          /* 从文件读取数据至缓冲区 */
                while ((length = fStream.read(buffer)) != -1) {
            /* 将资料写入DataOutputStream中 */
                    ds.write(buffer, 0, length);
                }
                ds.writeBytes(end);
                ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
          /* close streams */
                fStream.close();
                ds.flush();
          /* 取得Response内容 */
                InputStream is = con.getInputStream();
                int ch;
                StringBuffer b = new StringBuffer();
                while ((ch = is.read()) != -1) {
                    b.append((char) ch);
                }
          /* 将Response显示于Dialog */
//                showDialog("上传成功"+b.toString().trim());
                System.out.println("上传成功" + b.toString().trim());
                String returnString = b.toString().trim();
                // 将返回的数据 拿去处理， 然后就可以显示出来了。
//                newImagePath = GetMessageToInfo.InfoToAutoNewImage(returnString);
//
//                mHandler.sendEmptyMessage(100001);

                // 这个是执行了的， 只要处理 他就行了。
          /* 关闭DataOutputStream */
                ds.close();
            } catch (Exception e) {
//                showDialog("上传失败"+e);
                System.out.println("上传失败" + e);
            }
        }
    };



    /////////////////////////////////////////////////////////////////////
    public static String testtaskPost(String filePath) throws Exception {
        String result = null;
        HttpClient httpclient = new DefaultHttpClient();
        try {
            // 新建一个httpclient Post 请求
            HttpPost httppost = new HttpPost("http://192.168.1.191/fileMg/up");
            // 由于只是测试使用 这里的路径对应本地文件的物理路径
            FileBody bin = new FileBody(new File(filePath));
            File myfile = new File(filePath);
            long size = myfile.length();
            // 向MultipartEntity添加必要的数据
            //StringBody member = new StringBody("123456",Charset.forName("UTF-8"));
            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("file", bin);// file为请求后台的Fileupload参数
            //reqEntity.addPart("member", member);// 请求后台Fileupload的参数
            httppost.setEntity(reqEntity);
            // 这里是后台接收文件的接口需要的参数，根据接口文档需要放在http请求的请求头
			/*String taskid = "919894d9-ea5a-4f6a-8edd-b14ef3b6f104";
			httppost.setHeader("task-id", taskid);
			String fileid = UUID.randomUUID().toString();
			httppost.setHeader("file-id", fileid);
			httppost.setHeader("file-name", "1.doc");
			httppost.setHeader("file-size", String.valueOf(size));
			httppost.setHeader("total", String.valueOf(1));
			httppost.setHeader("index", String.valueOf(1));*/

            HttpResponse response = httpclient.execute(httppost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                // httpclient自带的工具类读取返回数据
                result = EntityUtils.toString(resEntity);
//                EntityUtils.consume(resEntity);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.getConnectionManager().shutdown();
            } catch (Exception ignore) {
            }
        }
        return result;
    }
}
