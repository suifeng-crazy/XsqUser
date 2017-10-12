package com.example.xsq.util;

/**
 * Created by Administrator on 2016/7/15.
 */

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;

import org.apache.http.params.CoreConnectionPNames;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import aes.WXBizMsgCrypt;

/**
 * app更新类 使用方式: 1.在string.xml 中添加获取版本号连接URL 使用的是http连接更新 TODO
 * 获取网络数据是否需要更新,可使用http and https 方式更新
 *
 * @author YichenZ
 */
public class UpdateManager {

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 显示当前进度信息
     */
    private TextView dataSizeTv;

    /**
     * 提示语
     */
    private String updateMsg = "有最新出的更新包哦，小伙伴们赶快下载吧~";

    /**
     * 错误信息
     */
    private String strError = "";

    /**
     * 安装包的URL  测试服 正式服也是没有t。 的
     */
    private String apkURL = "http://down.lianhejisan.com/app/android/lianhejisanFXS_2.0.1.apk";

    /**
     * 获取版本号URL
     */
    private String checkVersionURL = "";

    /**
     * 提示dialog
     */
    private Dialog noticeDialog;

    /**
     * 下载dialog
     */
    private Dialog downLoadDialog;

    /**
     * 本地sd卡路径
     */
    private static final String savePath = "/sdcard/";

    /**
     * 应用apk名称
     */
    private static final String saveFileName = savePath + "yanzheng.apk";

    /**
     * 进度条
     */
    private ProgressBar mProgressBar;

    /**
     * 进度
     */
    private int progress;

    /**
     * 显示当期/总共下载进度
     */
    private String apkFileSize; // apk大小
    private String tmpFileSize; // 临时文件大小

    /**
     * app当前版本名称
     */
    private String currVersionName;
    /**
     * app当前版本号
     */
    private int currVersionCode;

    /**
     * 更新中
     */
    private static final int DOWN_UPDATE = 0x01;
    /**
     * 更新完毕
     */
    private static final int DOWN_OVER = 0x02;
    /**
     * 获取最新版本号完毕
     */
    private static final int MSG_GETLASTVERSION = 0x03;

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    private ProgressDialog progressDialog;

    private Update mUpdate = null;

    private Thread downLoadThread;

    private boolean interceptFlag = false;

    private boolean activityFlag = false;

    public UpdateManager(Context context, String checkVersionURL)
            throws NameNotFoundException {
        mContext = context;
        PackageInfo packageInfo = getAppVersion();
        // 获取当前版本号和名称
        currVersionName = packageInfo.versionName;
        currVersionCode = packageInfo.versionCode;
        mUpdate = new Update();
        this.checkVersionURL=checkVersionURL;
    }

    /**
     * 软件升级
     * @param context
     * @param flag
     * @param checkVersionURL
     * @throws NameNotFoundException
     */
    public UpdateManager(Context context, boolean flag, String checkVersionURL)
            throws NameNotFoundException {
        this.activityFlag = flag;
        mContext = context;
        PackageInfo packageInfo = getAppVersion();
        // 获取当前版本号和名称
        currVersionName = packageInfo.versionName;
        currVersionCode = packageInfo.versionCode;
        System.out.println("currVersionName:"+currVersionName+"  currVersionCode"+currVersionCode);
        mUpdate = new Update();
        this.checkVersionURL=checkVersionURL;
    }

    /**
     * 显示更新信息
     */
    public void checkUpdateInfo() {
        // 判断是否需要更新（获取网络数据）
        progressDialog.setMessage("获取更新信息...");
        if(progressDialog.isShowing()) {
            progressDialog.show();
        }
        checkVersion();
    }

    /**
     * 检测版本更新
     */
    private void checkVersion() {
        new Thread() {
            @Override
            public void run() {
                try {
                    mUpdate = getLastVersion(checkVersionURL);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(MSG_GETLASTVERSION);
            }

        }.start();
    }

    /**
     * 显示更新询问对话框
     */
    private void showNoticeDialog() {
        Builder builder = new Builder(mContext);
        builder.setTitle("有新的版本需要更新");
        builder.setMessage(updateMsg);
        builder.setPositiveButton("更新", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDownloadDialog();
            }
        });

        builder.setNegativeButton("下次再说", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 增加 这段时间不再 提示更新。
                NumberUtil.checkUpdate = false;
                dialog.dismiss();
            }
        });

        noticeDialog = builder.create();
        noticeDialog.show();
    }

    /**
     * 显示下载进度对话框
     */
    private void showDownloadDialog() {
        Builder builder = new Builder(mContext);
        builder.setTitle("下载中...");

        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.base_updata_progress, null);

        dataSizeTv = (TextView) v.findViewById(R.id.dataSizeTv);
        mProgressBar = (ProgressBar) v.findViewById(R.id.progress);

        builder.setView(v);
        builder.setNegativeButton("取消", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                interceptFlag = true;
            }
        });

        downLoadDialog = builder.create();
        downLoadDialog.show();

        downloadApk();
    }

    /**
     * 更新进度线程
     */
    private Runnable mdownApkRunnable = new Runnable() {

        @Override
        public void run() {
            try {
                URL url = new URL(apkURL);
                System.out.println("apk下载地址urlapk下载地址urlapk下载地址urlapk下载地址urlapk下载地址url");
                System.out.println("apk下载地址url"+"http://192.168.1.21/group1/M00/00/00/wKgBFVnd28iAMt9FAH8NabyOdxA955.apk");
                System.out.println("apk下载地址url"+"http://192.168.1.21/group1/M00/00/00/wKgBFVnd28iAMt9FAH8NabyOdxA955.apk");
                System.out.println("apk下载地址url"+apkURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(35000);
                conn.setReadTimeout(35000);
//                conn.setDoOutput(true);
//                conn.setRequestMethod("POST");
                conn.connect();

                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdir();
                }

                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                FileOutputStream fos = new FileOutputStream(ApkFile);

                int count = 0;
                byte buf[] = new byte[1024];
                DecimalFormat df = new DecimalFormat("0.00");
                apkFileSize = df.format((float) length / 1024 / 1024) + "MB";
                do {
                    int numread = is.read(buf);
                    count += numread;
                    progress = (int) (((float) count / length) * 100);
                    tmpFileSize = df.format((float) count / 1024 / 1024) + "MB";
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if (numread <= 0) {
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    fos.write(buf, 0, numread);
                } while (!interceptFlag);

                fos.close();
                is.close();
            } catch (MalformedURLException e) {
//                getProgressDialog();
//                Toast.makeText(mContext, "下载失败，请稍后再试", Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessage(999);
                e.printStackTrace();
            } catch (IOException e) {
//                Toast.makeText(mContext, "下载失败，请稍后再试", Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessage(999);
                e.printStackTrace();
            }
        }
    };

    /**
     * 下载状态handler
     */
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            switch (msg.what) {
                case 999:
                    Toast.makeText(mContext, "下载失败，请稍后再试", Toast.LENGTH_SHORT).show();
                    NumberUtil.checkUpdate = false;
                    downLoadDialog.dismiss();
                    break;
                case MSG_GETLASTVERSION: // 获取最新版本信息
                    if (mUpdate != null) {
                        System.out.println("+_+_+_+_+_+_+" + currVersionCode
                                + "=-=-=-=-=" + mUpdate.getVersionCode());
                        if (currVersionCode < mUpdate.getVersionCode()) {

                            apkURL = mUpdate.getPatchUrl();
                            // update_log = mUpdate.getVersionRemark();
                            // if (mNoticeDialog == null) {
                            showNoticeDialog();
                            // } else if (!mNoticeDialog.isShowing()) {
                            // showNoticeDialog();
                            // }
                        } else {
                            if (!activityFlag) {
                                Toast.makeText(mContext, "这已经是最新版本了", Toast.LENGTH_SHORT).show();
                            }
                            // if (showToast) {

                            // }
                        }
                    }
                    break;
                case DOWN_UPDATE:
                    dataSizeTv.setText("当前下载进度:" + tmpFileSize + "/" + apkFileSize);
                    mProgressBar.setProgress(progress);
                    break;
                case DOWN_OVER:
                    installApk();
                    break;
                default:
                    break;
            }
        }

    };

    /**
     * 下载apk
     */
    private void downloadApk() {
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    /**
     * 安装程序
     */
    private void installApk() {
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }

        Intent i = new Intent(Intent.ACTION_VIEW);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 打开新的应用
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()),"application/vnd.android.package-archive");

        mContext.startActivity(i);
    }

    /**
     * 获取当前软件版本号
     *
     * @return PackageInfo 包含版本号和版本名称
     *
     * @throws NameNotFoundException
     */
    public PackageInfo getAppVersion() throws NameNotFoundException {
        PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);

        System.out.println(info.versionCode);
        return info;
    }

    /**
     * app升级 需要配置的位置
     *
     * @param url
     *
     * @return
     */
    public Update getLastVersion(String url)  throws Exception {
        Update update = new Update();
        WXBizMsgCrypt wxcpt ;
        String request = null;
        JSONObject jsonObject;
        try {
//            wxcpt = new WXBizMsgCrypt(NumberUtil.sToken, NumberUtil.sEncodingAESKey, NumberUtil.sCorpID);
            System.out.println("请求地址："+checkVersionURL);
            request = HttpGetOrPost.getJsonHttpGetLin(checkVersionURL, null);
            request = HttpGetOrPost.getJsonHttpGetLin(checkVersionURL, null);
//            JSONArray array=new JSONArray(request);
//            String sMsg = wxcpt.DecryptMsg(array.getString(0), array.getString(1), array.getString(2), array.getString(3));

            jsonObject = new JSONObject(request);
            System.out.println("版本检测返回："+request);
//            if (!ParsingJsonString.parsing(jsonObject)) {
//                return null;
//            }

            if(request == null ||request.equals("")){
                ParsingJsonString.nowConnectBase();
                return null;
            }
            if(jsonObject.optBoolean("success")){
                update.setPatchUrl(ConnectionAddress.BASE_FileAdress+jsonObject.optJSONObject("data").optString("FILE_URL"));
                update.setApkURL(ConnectionAddress.BASE_FileAdress+jsonObject.optJSONObject("data").optString("FILE_URL"));
                System.out.println("下载地址："+ConnectionAddress.BASE_FileAdress+jsonObject.optJSONObject("data").optString("FILE_URL"));
                update.setVersionName(jsonObject.optJSONObject("data").optString("VERSION_TITLE"));
                update.setVersionCode(jsonObject.optJSONObject("data").optDouble("MAX_VERSION"));
            }else{
                NumberUtil.strError = jsonObject.optString("message");
                return null;
            }

//            update.setStatus(jsonObject.optString("status"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return update;
    }

    /**
     * 内部实体类
     *
     * @author YichenZ
     */
    class Update {
        private double versionCode = 0.0; // 版本号
        private String versionName = ""; // 版本名称
        private String versionRemark = ""; // 升级日志
        private String patchUrl = ""; // 下载url
        private String status = "";// 状态值

        private String apkURL = ""; // 下载的apk 的 URL


        public String getApkURL() {
            return apkURL;
        }

        public void setApkURL(String apkURL) {
            this.apkURL = apkURL;
        }
        public double getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(double versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getVersionRemark() {
            return versionRemark;
        }

        public void setVersionRemark(String versionRemark) {
            this.versionRemark = versionRemark;
        }

        public String getPatchUrl() {
            return patchUrl;
        }

        public void setPatchUrl(String patchUrl) {
            this.patchUrl = patchUrl;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "Update [versionCode=" + versionCode + ", versionName="
                    + versionName + ", versionRemark=" + versionRemark
                    + ", patchUrl=" + patchUrl + "]";
        }
    }
}

