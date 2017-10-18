package com.example.xsq.util;

import java.io.File;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class AppUploadFile {
//    public static void main(String[] args) throws Exception {
//        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\webapps\\xsq_service\\WEB-INF\\classes\\spring-mvc.xml");
//        AnylineService service = context.getBean(AnylineService.class);
//        //initAndUploadBrand(service);
//        service.query("SELECT series_name,vehicle_level_name FROM MDM_VEHICLE_SERIES GROUP BY vehicle_level_code");
//    }
//
//    private static void initAndUploadBrand(AnylineService service) throws Exception {
//        DataSet brands = service.query("kz_bvs.mdm_vehicle_brand");
//        for (int i = 0; i < brands.size(); i++) {
//            DataRow row = brands.getRow(i);
//            DataRow temp = new DataRow("MM_VEHICLE_BRAND");
//            temp.put("CODE", row.getString("brand_code"));
//            temp.put("NAME", row.getString("brand_name"));
//            temp.put("INITIAL", row.getString("initial"));
//            temp.put("IS_HOT", row.getString("is_hot"));
//
//            String iconUrl = row.getString("logo");
//
//            if(BasicUtil.isNotEmpty(iconUrl)){
//                String fileUrl = "D://brandIcon//" + row.getString("brand_code")
//                        + "12.png";
//                File file = new File(fileUrl);
//                HttpUtil.download("http://192.168.1.12:8088" + iconUrl, file);
//                String result = testtaskPost(fileUrl);
//                JSONObject json = JSONObject.fromObject(result);
//                String url = json.getString("data");
//                temp.put("ICON_URL",url);
//            }
//            service.save(temp);
//        }
//    }
//
//    public static String testtaskPost(String filePath) throws Exception {
//        String result = null;
//        HttpClient httpclient = new DefaultHttpClient();
//        try {
//            // 新建一个httpclient Post 请求
//            HttpPost httppost = new HttpPost("http://192.168.1.191/fileMg/up");
//            // 由于只是测试使用 这里的路径对应本地文件的物理路径
//            FileBody bin = new FileBody(new File(filePath));
//            File myfile = new File(filePath);
//            long size = myfile.length();
//            // 向MultipartEntity添加必要的数据
//            //StringBody member = new StringBody("123456",Charset.forName("UTF-8"));
//            MultipartEntity reqEntity = new MultipartEntity();
//            reqEntity.addPart("file", bin);// file为请求后台的Fileupload参数
//            //reqEntity.addPart("member", member);// 请求后台Fileupload的参数
//            httppost.setEntity(reqEntity);
//            // 这里是后台接收文件的接口需要的参数，根据接口文档需要放在http请求的请求头
//			/*String taskid = "919894d9-ea5a-4f6a-8edd-b14ef3b6f104";
//			httppost.setHeader("task-id", taskid);
//			String fileid = UUID.randomUUID().toString();
//			httppost.setHeader("file-id", fileid);
//			httppost.setHeader("file-name", "1.doc");
//			httppost.setHeader("file-size", String.valueOf(size));
//			httppost.setHeader("total", String.valueOf(1));
//			httppost.setHeader("index", String.valueOf(1));*/
//
//            HttpResponse response = httpclient.execute(httppost);
//            int statusCode = response.getStatusLine().getStatusCode();
//            if (statusCode == HttpStatus.SC_OK) {
//                HttpEntity resEntity = response.getEntity();
//                // httpclient自带的工具类读取返回数据
//                result = EntityUtils.toString(resEntity);
//                EntityUtils.consume(resEntity);
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                httpclient.getConnectionManager().shutdown();
//            } catch (Exception ignore) {
//            }
//        }
//        return result;
//    }
}
