package com.example.xsq.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 一些基础的公共方法，
 * 主要是 将服务器返回的Json 数据进行格式转换。
 */
public class JsonStringMapUtil {
	/**金额为分的格式 */
	public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";

	public static String getNowTime(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		Date curDate = new Date(System.currentTimeMillis());
		//获取当前时间
		 String str = formatter.format(curDate);
		return str;
	}

	/**
	 * 将数据 拼接成jsonObject的形式。
	 */
	public static JSONObject MapToJSONObject(String username, String password){
		JSONObject jsonParam  = new JSONObject() ;
		try {
			jsonParam = new JSONObject();
			jsonParam.put("_a", username);
			jsonParam.put("_p", password);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonParam;
	}

	/**
	 * 将数据 拼接成jsonObject的形式。
	 */
	public static JSONObject MapToJSONObject(String username, String password, String newPassword){
		JSONObject jsonParam  = new JSONObject() ;
		try {
			jsonParam = new JSONObject();
			jsonParam.put("user", username);
			jsonParam.put("password", password);
			jsonParam.put("new_password", newPassword);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonParam;
	}

	/**
	 * 将服务器返回的图片地址转换为能看懂的地址信息
	 */

	public static String ImagePathInfo(String imagePath){
		String imageURL ;
		String[] imageSum = imagePath.split("\\|");
		String[] imageSum2 = imageSum[0].split("\\.");
//		imageURL = ConnectionAddress.Image_BASE_URL+imageSum2[0]+ConnectionAddress.Image_Second_URL+imageSum2[1]+ConnectionAddress.Image_SUFFIX;

		return "";
	}

	/**
	 * 得到现在的时间戳
	 * @param time
	 * @return
     */

	public static long getTimestamp(String time){
		long unixTimestamp;
		try {
			time = time + " 00:00:00";
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
			unixTimestamp = date.getTime();
			System.out.println(unixTimestamp);
			return  unixTimestamp;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (Long) null;
	}

	/**
	 *  只到 天的时间 转换为 时间戳
	 * @param time
	 * @return
	 */
	public static long TimestampNoHHMMSS(String time){
		long unixTimestamp;
		try {
			time = time + " 00:00:00";
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
			unixTimestamp = date.getTime()/1000;
			System.out.println(unixTimestamp);
			return  unixTimestamp;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (Long) null;
	}
	/**
	 * 将时间戳转换为时间
	 */
	public static String TimeStampToTimeYMD(String dat){
		long da = Long.parseLong(dat);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = sdf.format(new Date(da*1000));
		String ymd = date.substring(0,10);
		return ymd;
	}

	/**
	 * 将时间戳转换为时间
	 */
	public static String TimeStampToTimeString(String dat){
		long da = Long.parseLong(dat);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = sdf.format(new Date(da*1000));
		System.out.println(date);
		return date;
	}


	/**
	 * 将 yyyy-MM-dd HH:mm:ss 格式时间字符串转换为时间戳
	 * @param time
	 * @return
	 */
	public static String TimeStringToTimeStampUseInCalend(String time){
		long unixTimestamp;
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
			unixTimestamp = date.getTime();
			String dat = String.valueOf(unixTimestamp);
			return dat;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 将 yyyy-MM-dd HH:mm:ss 格式时间字符串转换为时间戳
	 * @param time
	 * @return
	 */
	public static String TimeStringToTimeStamp(String time){
		long unixTimestamp;
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
			unixTimestamp = date.getTime()/1000;
			String dat = String.valueOf(unixTimestamp);
			return dat;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

//	0.订单生成 1.部分付款 2.全额付款(待确认) 3.已确认 8.已消费[正常完成,可含部分退款]  |  9.已删除 7.已取消 6.已退款 5.退款中[部分/全额] 4.等待退款受理[部分/全额]
	/**
	 * 将 状态int 数据转换为 String 类型来展示。
	 * @param status
	 * @return
	 */
	public static String orderStatusIntToString(int status){
		String orderStatusString = "";
		switch (status) {
			case 0:
				orderStatusString = "订单生成";
				break;
			case 1:
				orderStatusString = "部分付款";
				break;
			case 2:
				orderStatusString = "全额付款(待确认)";
				break;
			case 3:
				orderStatusString = "已确认";
				break;
			case 4:
//				orderStatusString = "等待退款受理[部分/全额]";  // 等待 退款 部分或者部分退款中的时候，都不能申请下一个 退款。
				orderStatusString = "等待退款处理";
				break;
			case 5:
				orderStatusString = "退款中[部分/全额]";
				break;
			case 6:
				orderStatusString = "已退款";
				break;
			case 7:
				orderStatusString = "已取消";
				break;
			case 8:
				orderStatusString = "已消费[正常完成,可含部分退款]";
				break;
			case 9:
				orderStatusString = "已删除";
				break;
			default:
				orderStatusString = "状态码错误";
				break;
		}
		return orderStatusString;
	}

	/**
	 * 订单类型 从编号变为 用户可看懂的
	 * @param orderType
	 * @return
	 */
//	public static String OrderTypeIntToString(String orderType){
//		String orderTypeString = "";
//		switch (orderType) {
//		case "01":
//
//			break;
//
//		default:
//			break;
//		}
//		return orderTypeString;
//	}

	/**
	 * 支付类型 从 编码 到汉字
	 * @param payMehtod
	 * @return
	 */
	public static String payMethodIntToString(String payMehtod){
		String payM = "";
		switch (payMehtod) {
			case "":
				payM = "未付款";
				break;
			case "money":
				payM = "余额";
				break;
			case "alipay":
				payM = "余额宝[包含各种银行卡支付]";
				break;
			case "wepay":
				payM = "微信支付";
				break;
			case "multi":
				payM = "多次支付";
				break;
			case "offline":
				payM = "线下支付";
				break;
			default:
				break;
		}
		return  payM;
	}

	/**
	 * 商品类型 编号 更改
	 * @param goodsType
	 * @return
	 */
	public static String OrderTypeChange(String goodsType){
		String goods = "";
		switch (goodsType) {
			case "01":
				goods = "门票";
				break;
			case "02":
				goods = "酒店";
				break;
			case "03":
				goods = "旅游";
				break;
			default:
				goods="编号没有";
				break;
		}
		return  goods;
	}

	public static void main(String[] args) {

		String x = JsonStringMapUtil.payMethodIntToString("");
		System.out.println(x);
	}

//	public boolean isNumeric(String str){
//		Pattern pattern = Pattern.compile("[0-9]*");
//		Matcher isNum = pattern.matcher(str);
//		if( !isNum.matches() ){
//			return false;
//		}
//		return true;
//	}

	/**
	 * 金额 从分转换为 元为单位的。
	 */
	public static String moneyFenToYuan(String amount){
		try {
			if("".equals(amount)){
				return "";
			}

			if(!amount.toString().matches(CURRENCY_FEN_REGEX)) {
				throw new Exception("金额格式有误");
			}

			int flag = 0;
			String amString = amount.toString();
			if(amString.charAt(0)=='-'){
				flag = 1;
				amString = amString.substring(1);
			}
			StringBuffer result = new StringBuffer();
			if(amString.length()==1){
				result.append("0.0").append(amString);
			}else if(amString.length() == 2){
				result.append("0.").append(amString);
			}else{
				String intString = amString.substring(0,amString.length()-2);
				for(int i=1; i<=intString.length();i++){
					if( (i-1)%3 == 0 && i !=1){
						result.append(",");
					}
					result.append(intString.substring(intString.length()-i,intString.length()-i+1));
				}
				result.reverse().append(".").append(amString.substring(amString.length()-2));
			}
			if(flag == 1){
				return "-"+result.toString();
			}else{
				return result.toString();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 金额 从分转换为 元为单位的。
	 */
	public static String moneyFenToYuanZheng(String amount){
		try {
			if("".equals(amount)){
				return "";
			}

			if(!amount.toString().matches(CURRENCY_FEN_REGEX)) {
				throw new Exception("金额格式有误");
			}

			int flag = 0;
			String amString = amount.toString();
			if(amString.charAt(0)=='-'){
				flag = 1;
				amString = amString.substring(1);
			}
			StringBuffer result = new StringBuffer();
			if(amString.length()==1){
				result.append("0.0");
				result.append(amString);
			}else if(amString.length() == 2){
				result.append("0.");
				result.append(amString);
			}else{
				String intString = amString.substring(0,amString.length()-2);
				for(int i=1; i<=intString.length();i++){
					if( (i-1)%3 == 0 && i !=1){
						result.append(",");
					}
					result.append(intString.substring(intString.length()-i,intString.length()-i+1));
				}
				result.reverse().append(".").append(amString.substring(amString.length()-2));
			}
			if(flag == 1){
				return "-"+result.toString();
			}else{
				return result.toString();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 订单状态  转汉字
	 * @param orderStatus
	 * @return
	 */
	public static String orderStatusString(String orderStatus){
		String orderSta = "";

		switch(Integer.parseInt(orderStatus)){
			case 0:
				orderSta = "等待付款";
				break;
			case 1:
				orderSta = "已部分付款";
				break;
			case 2:
				orderSta = "等待确认订单";
				break;
			case 3:
//				if($more === true){
//					if($order['parent_type'] === '03'){
//						return '等待消费';
//					}else{
//						return '等待消费 - 总计 '.($order['total_num'] - $order['refund_num']).' 份，已用 '.$order['use_num'].' 份';
//					}
//				}else{
				orderSta = "等待消费";
//				}
				break;
			case 4:
				orderSta = "等待退款处理";
				break;
			case 5:
				orderSta = "退款中";
				break;
			case 6:
				orderSta = "已退款";
				break;
			case 7:
				orderSta = "已取消";
				break;
			case 8:
//				if($order['total_num'] == $order['use_num']){
//					return '交易完成';
//				}else{
//					if($more === true){
//						return '部分完成 - 已用 '.$order['use_num'].' 份，已退 ' + $order['refund_num'].' 份';
//					}else{
//						return '部分完成';
//					}
//				}
				orderSta = "已完成";
				break;
			case 9:
				orderSta = "已删除";
				break;
		}
		return orderSta;
	}
	/**
	 * 判定输入汉字
	 * @param c
	 * @return
	 */

	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 检测String是否全是中文
	 *
	 * @param name
	 * @return
	 */
	public static boolean checkNameChese(String name) {
		boolean res = true;
		char[] cTemp = name.toCharArray();
		for (int i = 0; i < name.length(); i++) {
			if (!isChinese(cTemp[i])) {
				res = false;
				break;
			}
		}
		return res;
	}


	/**
	 * 检查输入的用户名是否符合规则
	 */

	public static Boolean checkUserName(String userName){
		Pattern p = Pattern.compile("[0-9]*");
		Matcher m = p.matcher(userName);
		System.out.println("输入的是汉字0");
		if(m.matches() ){
			System.out.println("输入的是汉字1");
			return false;
		}
		p= Pattern.compile("[a-zA-Z]");
		m=p.matcher(userName);
		if(m.matches()){
			System.out.println("输入的是汉字2");
			if(userName.length() >=2 && userName.length() <=20)
			return true;
		}
		p= Pattern.compile("[\u4e00-\u9fa5]");
		m=p.matcher(userName);
		if(m.matches()){
			System.out.println("输入的是汉字3");
			if(userName.length() >= 2 && userName.length()<= 5)
			return true;
		}
		return false;
	}

	/**
	 * 验证昵称 并且控制汉字和英文的长度。
	 * (这个只允许汉字 )
	 * @param nickname
	 * @param longSize
	 * @param smallSize
     * @return
     */
	public static boolean verifyNickname(String nickname, int longSize, int smallSize, Context context) {
//		String nickname = edt_username.getText().toString();
		boolean isChina = false;
		boolean isEnglish = false;
		if (nickname == null || nickname.length() == 0) {
			System.out.println("不能为空");
			return false;
		}
		int len = 0;
		char[] nickchar = nickname.toCharArray();
		for (int i = 0; i < nickchar.length; i++) {
			if (isChinese(nickchar[i])) {
				isChina = true;
				len += 2;
			} else {
				isEnglish = true;
				len += 1;
			}
		}
//		Toast.makeText(context,"开户名只能是汉字 len:"+len,Toast.LENGTH_SHORT).show();
		if(isChina && isEnglish){
//			Toast.makeText(context,"111 len:"+len,Toast.LENGTH_SHORT).show();
			return false;
		}

		if(isChina){
			if (len >= smallSize && len <= longSize) {
				return true;
			}
		}else{
			if(len >=2 && len <=20){
//				Toast.makeText(context,"2222 len:"+len,Toast.LENGTH_SHORT).show();
				return false;
			}
		}
//		Toast.makeText(context,"3333 len:"+len,Toast.LENGTH_SHORT).show();
		return false;
	}


	// 验证昵称
	public static boolean verifyNickname(String nickname) {
//		String nickname = edt_username.getText().toString();   (^\d{3,4}-\d{7,8}$)|(^1\d{10}$)
//..
//		(^\d{3,4}-\d{7,8}$)|(^1\d{10}$)
		java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("(^[\\u4e00-\\u9fa5]{2,5}$)|(^[a-zA-Z ]{2,20}$)");
		java.util.regex.Matcher match=pattern.matcher(nickname);
//		if(match.matches()==false)
		return match.matches();

//		Pattern p = Pattern.compile("[0-9]*");
//		Matcher m = p.matcher(nickname);
//		if(m.matches() ){
////			Toast.makeText(Main.this,"输入的是数字", Toast.LENGTH_SHORT).show();
//			..
//			return false;
//		}
//
//		boolean isChina = false;
//		boolean isEnglish = false;
//		if (nickname == null || nickname.length() == 0) {
//			System.out.println("不能为空");
//			return false;
//		}
//		int len = 0;
//		char[] nickchar = nickname.toCharArray();
//		for (int i = 0; i < nickchar.length; i++) {
//			if (isChinese(nickchar[i])) {
//				isChina = true;
//				len += 2;
//			} else {
//				isEnglish = true;
//				len += 1;
//			}
//		}
//		if(isChina && isEnglish){
//			return false;
//		}
//		if(isChina){
//			if (len >= 4 && len <= 10) {
//				return true;
//			}
//		}else{
//			if(len >=2 && len <=20){
//				return true;
//			}
//		}
//
//		return false;
	}

//	private boolean isChinese(char c) {
//		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
//		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
//				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
//				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
//				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
//				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
//				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
//			return true;
//		}
//		return false;
//	}


	/**
	 * 检查 用户买票用的 电话， 首字母为 1 的11 位数字.
	 *
	 */
	public static Boolean checkUserPhone(String userPhone){
		if(userPhone== null || "".equals(userPhone)){
			return false;
		}
		Pattern p = Pattern.compile("[0-9]*");
		Matcher m = p.matcher(userPhone);
		if(m.matches() ){
//			Toast.makeText(Main.this,"输入的是数字", Toast.LENGTH_SHORT).show();
			if(userPhone.length() ==11 && userPhone.substring(0,1).equals("1"))
			return true;
		}

		return false;
	}

	/**
	 * 检查输入的是 数字、字母、汉字
	 * @param text
	 * @return
     */
	public static String checkString(String text){

		Pattern p = Pattern.compile("[0-9]*");
		Matcher m = p.matcher(text);
		if(m.matches() ){
//			Toast.makeText(Main.this,"输入的是数字", Toast.LENGTH_SHORT).show();
			return "isNum";
		}
		p= Pattern.compile("[a-zA-Z]");
		m=p.matcher(text);
		if(m.matches()){
//			Toast.makeText(Main.this,"输入的是字母", Toast.LENGTH_SHORT).show();
			return "isChar";
		}
		p= Pattern.compile("[\u4e00-\u9fa5]");
		m=p.matcher(text);
		if(m.matches()){
//			Toast.makeText(Main.this,"输入的是汉字", Toast.LENGTH_SHORT).show();
			return "isHZ";
		}
		return "";
	}

	/**
	 * 将服务器返回的景区级别转换
	 * @param rank
	 * @return
     */
	public static String ticketRankToChange(String rank){
		String ticketRank = "" ;
		switch (rank){
			case "1":
				ticketRank = "AAAAA级";
				break;
			case "2":
				ticketRank = "AAAA级";
				break;
			case "3":
				ticketRank = "AAA级";
				break;
			case "0":
				ticketRank = "其他";
				break;
		}
		return ticketRank;
	}

	/**
	 * 订单处理信息
	 * @param status
	 * @return
     */
	public static String AppleyGetMoneyStatus(String status){
		String applyStatus = "";
		switch (status){
			case "0":
				applyStatus="等待处理";
				break;
			case "1":
				applyStatus="结算完成";
				break;
			case "2":
				applyStatus="结算失败";
				break;
		}
		return applyStatus;
	}


	public static String hotelGrade(String grade){
		String applyStatus = "";
		switch (grade){
			case "0":
				applyStatus="其他";
				break;
			case "1":
				applyStatus="青年旅社";
				break;
			case "2":
				applyStatus="二星级/经济型";
				break;
			case "3":
				applyStatus="三星级/舒适型";
				break;
			case "4":
				applyStatus="四星级/高档型";
				break;
			case "5":
				applyStatus="五星级/豪华型";
				break;
			case "6":
				applyStatus="家庭旅馆";
				break;
			case "7":
				applyStatus="度假公寓";
				break;
		}
		return applyStatus;
	}

	/**
	 * 旅游成团方式，目的地成团或者出发地成团
	 * @param status
	 * @return
     */
	public static String tourCreateTeam(String status){
		String applyStatus = "";
		switch (status){
			case "1":
				applyStatus="出发地成团";
				break;
			case "2":
				applyStatus="目的地成团";
				break;
		}
		return applyStatus;
	}
	/////////////////////////////////////////////
	/**
	 * 功能：身份证的有效验证
	 *
	 * @param IDStr
	 *            身份证号
	 * @return 有效：返回"" 无效：返回String信息
	 * @throws ParseException
	 */
	public static boolean IDCardValidate(String IDStr) {
		String errorInfo = "";// 记录错误信息
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",
				"3", "2","X" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
				"9", "10", "5", "8", "4", "2" };
		String Ai = "";
		// ================ 号码的长度 15位或18位 ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			errorInfo = "身份证号码长度应该为15位或18位。";
			return false;
		}
		// =======================(end)========================
		// ================ 数字 除最后以为都为数字 ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (isNumeric(Ai) == false) {
			errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
			return false;
		}
		// =======================(end)========================

		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 月份
		if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
			errorInfo = "身份证生日无效。";
			return false;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
					|| (gc.getTime().getTime() - s.parse(
					strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
				errorInfo = "身份证生日不在有效范围。";
				return false;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			errorInfo = "身份证月份无效";
			return false;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			errorInfo = "身份证日期无效";
			return false;
		}
		// =====================(end)=====================


		// ================ 判断最后一位的值 ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi
					+ Integer.parseInt(String.valueOf(Ai.charAt(i)))
					* Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai = Ai + strVerifyCode;

		if (IDStr.length() == 18) {
			if (Ai.equals(IDStr) == false) {
				errorInfo = "身份证无效，不是合法的身份证号码";
				return false;
			}
		} else {
			return true;
		}
		// =====================(end)=====================
		return true;
	}
	/**
	 * 功能：判断字符串是否为数字
	 *
	 * @param str
	 * @return
	 */
	public  static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证日期字符串是否是YYYY-MM-DD格式
	 *
	 * @param str
	 * @return
	 */
	private static boolean isDataFormat(String str) {
		boolean flag = false;
		// String
		// regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
		String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		Pattern pattern1 = Pattern.compile(regxStr);
		Matcher isNo = pattern1.matcher(str);
		if (isNo.matches()) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 判断是否为金额
	 */
	public static boolean isMoney(String str)
	{
		java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
		java.util.regex.Matcher match=pattern.matcher(str);
		if(match.matches()==false)
		{
			return false;
		} else {
			return true;
		}
	}

	/**
	 *  王晓航给的 服务器端判断 支付宝账号信息。
	 */
	public static boolean isAlipayAccount(String str)
	{
		java.util.regex.Pattern pattern2=java.util.regex.Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
		java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("(^1\\d{10}$)|(^\\w+([\\.\\-_]\\w+)*@\\w+([\\-.]\\w+)*\\.\\w+$)"); // 判断小数点后2位的数字的正则表达式
		java.util.regex.Matcher match=pattern.matcher(str);
		if(match.matches()==false)
		{
			return false;
		} else {
			return true;
		}
	}
	/**
	 *  王晓航给的 服务器端判断 支付宝账号信息。
	 */
	public static boolean isBankpayAccount(String str)
	{
		java.util.regex.Pattern pattern2=java.util.regex.Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
		java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("^\\d{15,20}$"); // 判断小数点后2位的数字的正则表达式
		java.util.regex.Matcher match=pattern.matcher(str);
		if(match.matches()==false)
		{
			return false;
		} else {
			return true;
		}
	}

	/**
	 *  王晓航给的 服务器端判断 电话号码
	 */
	public static boolean isAutoPhoneNumber(String str)
	{
		java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("(^\\d{3,4}-\\d{7,8}$)|(^1\\d{10}$)"); // 判断小数点后2位的数字的正则表达式
	//	java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("^\\d{15,20}$"); // 判断小数点后2位的数字的正则表达式
		java.util.regex.Matcher match=pattern.matcher(str);
		if(match.matches()==false)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	/**
	 *  王晓航给的 服务器端判断 分销商名称。
	 */
	public static boolean isAutoShopName(String str)
	{
		//		String nickname = edt_username.getText().toString();
		boolean isChina = false;
		boolean isEnglish = false;
		if (str == null || str.length() == 0) {
			System.out.println("不能为空");
			return false;
		}
		int len = 0;
		char[] nickchar = str.toCharArray();
		for (int i = 0; i < nickchar.length; i++) {
			if (isChinese(nickchar[i])) {
				isChina = true;
				len += 2;
			} else {
				isEnglish = true;
				len += 1;
			}
		}
		if(isChina && isEnglish){
			return false;
		}
		if(isChina){
			if (len >= 2 && len <= 20) {
				return true;
			}
		}else{
			if(len >=2 && len <=50){
				return true;
			}
		}

		return false;
	}


	/**
	 * 存放实体类以及任意类型
	 * @param context 上下文对象
	 * @param key
	 * @param obj
	 */
	public static void putBean(Context context, String key, Object obj) {
		if (obj instanceof Serializable) {// obj必须实现Serializable接口，否则会出问题
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(obj);
				String string64 = new String(Base64.encode(baos.toByteArray(),
						0));
				SharedPreferences.Editor editor = context.getSharedPreferences("data",context.MODE_PRIVATE).edit();
				editor.putString(key, string64).commit();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new IllegalArgumentException(
					"the obj must implement Serializble");
		}

	}

	public static Object getBean(Context context, String key) {
		Object obj = null;
		try {
			String base64 = context.getSharedPreferences("data",context.MODE_PRIVATE).getString(key, "");
			if (base64.equals("")) {
				return null;
			}
			byte[] base64Bytes = Base64.decode(base64.getBytes(), 1);
			ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			obj = ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}
