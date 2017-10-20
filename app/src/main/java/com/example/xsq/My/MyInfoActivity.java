package com.example.xsq.My;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;
import com.example.xsq.aifx.StringUtils;
import com.example.xsq.util.BaseActivity;
import com.example.xsq.util.NumberUtil;
import com.example.xsq.util.PopuWindow.SelectPicPopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;

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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Response;

public class MyInfoActivity extends BaseActivity {
    private final static String URL_HEADER_UPLOAD = "http://192.168.1.191:8081/fileMg/up";
    private final static String URL_MODIFY_HEADER_IMG = "http://192.168.1.191/app/mg/inx/ucc/sbm";

    SelectPicPopupWindow menuWindow;
    RelativeLayout mReChangePhone, mReNickName, mReRealName, mReSex, mReHeaderImage;
    ImageView mImLeave, mImHeader;
    TextView mTvPhone, mTvNickeName, mTvRealName, mTvSex;
    Uri mUri;
    Boolean mBoChangeHeader = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        if (NumberUtil.UserHeaderImageBitmap != null) {
            mImHeader.setImageBitmap(NumberUtil.UserHeaderImageBitmap);
        }
    }

    private void initUI() {
        mImHeader = findViewById(R.id.myInfo_headerI);
        mReHeaderImage = findViewById(R.id.myInfo_headIR);
        mReSex = findViewById(R.id.myInfo_sexR);
        mTvPhone = findViewById(R.id.MyInfo_phoneT);
        mTvNickeName = findViewById(R.id.myInfo_nickNameT);
        mTvRealName = findViewById(R.id.myInfo_realNameT);
        mTvSex = findViewById(R.id.myInfo_sexT);
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
        switch (v.getId()) {
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
                onBackPressed();
                break;
            case R.id.myInfo_realNameR:
                strActivity(MyInfoActivity.this, MyRealNameActivity.class);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (mBoChangeHeader) {
            Intent data = new Intent();//只是回传数据就不用写跳转对象
            data.putExtra("data", true);//数据放到data里面去
            setResult(RESULT_OK, data);
        }
        finish();
    }

    /*onActivityResult接收数据的方法
       *requestCode：请求的标志
       * resultCode：第二个页面返回的标志，哪个页面跳转的标识
       *data：第二个页面回传的数据，data是回传一个intent对象
        */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2)//通过请求码(去SActivity)和回传码（回传数据到第一个页面）判断回传的页面
        {
            data.getStringExtra("data");
            String content = data.getStringExtra("data");//字符串content得到data数据
            mTvNickeName.setText(content);
        } else if (requestCode == 2 && resultCode == 2) {
            data.getStringExtra("data");
            String content = data.getStringExtra("data");
            mTvPhone.setText(content);
        } else if (requestCode == 3 && resultCode == 3) {
            String content = data.getStringExtra("data");
            mTvSex.setText(content);
        } else {
            onActivityResult2(requestCode, resultCode, data);
        }
    }

    // 分割线， 为头像上传做的

    /* 头像文件 */
    private static String IMAGE_FILE_NAME = "temp_head_image.jpg";

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
        intent.putExtra("uri", uri);

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
            File file = saveBitmap(photo);
            if (null != file) {
                // photo 已经是裁剪后的圆形头像了， 要将他上传。
                mBoChangeHeader = true;
                NumberUtil.UserHeaderImageBitmap = photo;
                cachedThreadPool.execute(new UploadHeaderImgRunnable(MyInfoActivity.this, file));
            }
            mImHeader.setImageBitmap(photo);
        }
    }

    private File saveBitmap(Bitmap bitmap) {
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);
        if (null == bitmap || !sdCardExist) {
            return null;
        }
        File newFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        FileOutputStream mFileOutputStream = null;
        try {
            newFile.createNewFile();
            mFileOutputStream = new FileOutputStream(newFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    mFileOutputStream);
            return newFile;
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        } finally {
            if (null != mFileOutputStream) {
                try {
                    mFileOutputStream.flush();
                    mFileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
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
    private static class UploadHeaderImgRunnable implements Runnable {
        private File mHeaderFile;
        private WeakReference<BaseActivity> mBaseActivity;

        private UploadHeaderImgRunnable(@NonNull BaseActivity activity, @NonNull File file) {
            mBaseActivity = new WeakReference<>(activity);
            this.mHeaderFile = file;
        }

        private String getFileName(@NonNull File file) {
            String name = file.getName();
            if (!TextUtils.isEmpty(name) && -1 != name.lastIndexOf(".")) {
                return name;
            }
            return "temp.png";
        }

        @Override
        public void run() {
            final BaseActivity activity = mBaseActivity.get();
            if (null != activity) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.showDialog();
                    }
                });
            }
            boolean isResult = false;
            Response response = null;
            try {
                response = OkHttpUtils
                        .post()
                        .url(URL_HEADER_UPLOAD)
                        .addParams("member", StringUtils.nonNull(NumberUtil.userID))
                        .addFile("file", getFileName(mHeaderFile), mHeaderFile)
                        .build().execute();

                if (response.isSuccessful()) {
                    final String content = response.body().string();
                    Log.d("MyInfoAct", "return info = " + content);
                    JSONObject js = new JSONObject(content);
                    isResult = js.getBoolean("result");
                    if (isResult) {
                        isResult = modifyUserHeaderImg(js.getString("data"));
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if (null != response) {
                    response.close();
                }

                final BaseActivity baseActivity = mBaseActivity.get();
                if (null != baseActivity) {
                    final boolean temp = isResult;
                    baseActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            baseActivity.hideDialog();
                            Toast.makeText(baseActivity, temp ? R.string.user_info_modify_header_img_success :
                                    R.string.user_info_modify_header_img_fail, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }

        private boolean modifyUserHeaderImg(String data) {
            Response response = null;
            try {
                response = OkHttpUtils.post()
                        .url(URL_MODIFY_HEADER_IMG)
                        .addParams("_t", StringUtils.nonNull(NumberUtil.token))
                        .addParams("avatarPath", StringUtils.nonNull(data))
                        .build().execute();

                if (response.isSuccessful()) {
                    final String result = response.body().string();
                    JSONObject js = new JSONObject(result);
                    return js.getBoolean("result");
                }
            } catch (Exception e) {
                return false;
            } finally {
                if (null != response) {
                    response.close();
                }
            }
            return false;
        }
    }

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
