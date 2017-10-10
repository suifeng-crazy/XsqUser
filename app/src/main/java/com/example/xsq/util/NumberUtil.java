package com.example.xsq.util;

import java.util.List;
import java.util.Map;

/**
 * 项目使用静态数据存储。
 * @author Administrator
 *
 */
public class NumberUtil {

    // 判断是否是登陆页， 在BaseActivity 中使用。来隐藏登陆页面。
    public static Boolean isMainActivity = true;

    // 这个是判断 首页轮播是否是第一次加载， 在 轮播类里面使用的。
    public  static Boolean isFirstPageT = true;
    public static Boolean isFirstPage = true;
    // 支付用于传递数据使用的(酒店使用)
    public static String zf_title;
    public static String zf_startTime;
    public static String zf_endTime;
    public static String zf_buyNum;
    public static String zf_orderId;
    public static String zf_userName;
    public static String zf_userPhone;
    public static String zf_money;
    public static String zf_orderType = "";
    public static final String zf_ticketOrder = "zf_ticketOrder";
    public static final String zf_hotelOrder = "zf_hotelOrder";
    public static final String zf_tourOrder = "zf_tourOrder";



    //屏幕高度和宽度
    public static int scree_with ;
    public static int scree_higeht;

    // 下面这个地方是当时想 用隐士开启 Activity 用的，暂时没用上
    public static String activity_intent = "";
    public static final String ticket_activity_intent = "com.example.fxs.TicketInfoActivity";
    public static final String hotel_activity_intent = "com.wz.hotel.HotelInfoActivity";
    public static final String tour_activity_intent = "com.wz.tour.TourInfoActivity";


    public  static final String app_name = "联合集散分销商";

    public  static final String WX_APP_ID = "wx6dc1993b2f1d3714";
    public  static final String QQ_APP_ID="1105446629";

    public static String strJXError = "数据解析错误";
    public static String strError = "暂无数据";
    public  static int strErrorNum = 0; // 这个错误码 是在具体的 activity中 使用的， 给用户更好的体验。

    public static String isTicketOrHotelTypeId;// 判断是 ticket 还是 hotle 类型，
    // 下面是 门票调价之前，保存的 typeId ，这个其实是可以不用保存到这的， 但是为了 方便，
    public static String ticketTypeId;
    public static String hotelTypeId;

    // 下面是 旅游详细信息保存用的 list 和Map数据
    public  static List tourList;
    public static Map tourMap;
//    public static List<TicketInfoSku> buyFiveSafeUser = new ArrayList(); // 购买5元保险的人的list

    // 下面是 选择的城市
    public static String nowProvice ="";
    public static String nowCity = "";

    public static int maxNum=1000000;  // 库存上限
    public static String address= "";// 在购买成功页面显示的地址。

    // 下面是 底部弹窗使用哦的数据
    public static Double sellerMoneyDouble = 0.00;  //原价
    public static Double discountMoneyDouble = 0.00; // 满减金额
    public static int Number = 0;               //购买份数

    public static int childNum = 0;         // 儿童购买份数
    public static int differNum = 0;        // 单房差购买份数
    public static Double childMoney = 0.00;
    public static Double differMoney = 0.00;

    // 公用layout 使用 修改UI 颜色等使用的数据
    public static  int FinanceFistUI = 1;
    public static int baseBottomSelectUI = 1;


    // ListView 下拉刷新机制
    public static final int WHAT_DID_LOAD_DATA = 0;
    public static final int WHAT_DID_REFRESH = 1;
    public static final int WHAT_DID_MORE = 2;

    public static final String sToken = "youyou&password!";
    public static final String sCorpID = "";
    public static final String sEncodingAESKey = "iu76trf63d5r43w0p9i74wsdrpo0a3etfdpoi9ugtfr";


    public static int page = 1;				//页码
    public static final int pageSize = 6; // 每页显示的条数
    public static int pageNumber = 0;		//能获取到的最大页码 。

    public static String token = "";
    public static String sellerId = "";
//    public static String TicketHotelTourType = "TicketHotelTourStaticType";
    public static String TicketHotelTourType = "TicketType";
    public static final String TicketType = "TicketType";
    public static  final String HotelType = "HotelType";
    public static final String TourType = "TourType";

    //下面三行是付款方法 标示
    public static String pay_method_remainderMoney = "money";
    public static String pay_method_WXPay = "wepay";
    public static String pay_method_ZFBPay = "alipay";

    public static String pay_mehtod = "app"; //支付接口固定值

//    public static List<BankList> bankList;
    public static Map bankMap;  // 已经在 login 方法中进行初始化了。可以直接在别的方法中 使用 NumberUtile.bankMap 而不会报错。

    public static Map CityMap ;
    public static List CityList ;
    public static Map proviceMap ;
    public static boolean showDialog = true;

    // 以下为新添加的
    public static Boolean checkUpdate = true;  // 检测是否需要自动检测更新。
}
