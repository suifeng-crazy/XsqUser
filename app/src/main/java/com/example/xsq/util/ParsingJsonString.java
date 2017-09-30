package com.example.xsq.util;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 判断服务器返回状态 Created by YichenZ on 2014/6/16.
 */
public class ParsingJsonString {
	public final static String STR_LOGIN_OUTTIME="登陆已过期";

	/**
	 * 解析接口状态
	 * @param str
	 * @return
	 * @throws JSONException
	 */
	public static boolean parsing(JSONObject str) throws JSONException {

		int status = str.getInt("rt");
		if (status == 0) {
			return true;
		}
		NumberUtil.strErrorNum = status;
		setStrError(status);
		return false;
	}

	/**
	 * 解析接口状态(判断是否需要设置新密码使用的)
	 * @param str
	 * @return
	 * @throws JSONException
	 */
	public static int parsingNewPassword(JSONObject str) throws JSONException {

		int status = str.getInt("rt");
		if (status == 0) {
			return 0;
		}
		if(status == 10010){
			return 2;
		}
		setStrError(status);
		return 1;
	}


	public static void nowConnectBase() {
		setStrError(999999);
		NumberUtil.strErrorNum = 999999;
		return ;
	}

	/**
	 * 解析接口状态
	 * @param str
	 * @return
	 * @throws JSONException
	 */
	public static boolean parsing(int str) throws JSONException {

		int status = str;
		if (status == 0) {
			return true;
		}
		NumberUtil.strErrorNum = status;
//		if(status == 105 ||status ==109 ||status ==10007  ){
//			strActivity(context, MainActivity.class, true, true);
//		}
		setStrError(status);
		return false;
	}

	/**
	 * 判断错误状态并写入
	 * @param status
	 */
	private static void setStrError(int status) {
		switch (status){
			case 999999:NumberUtil.strError="当前网络环境不佳,请稍后再试。";break;
			case 101:NumberUtil.strError="Sql error"; break;
			case 105:NumberUtil.strError="未登录,请退出后重新登陆";break;
			case 109:NumberUtil.strError="该账号在别的设备上登陆，请重新登陆";break;
			case 110:NumberUtil.strError="商户不存在";break; // 这个应该是资源不存在， 然后在登陆页面的时候， 显示为 商户不存在。
			case 201:NumberUtil.strError="数据错误";break; // 应该是 缺少必要参数或存在错误参数
			case 501:NumberUtil.strError="该订单暂未被景区确认，请稍后查看";break;

			case 10000:NumberUtil.strError="密码错误";break;
			case 10001:NumberUtil.strError="密码输入错误次数过多";break;
			case 10002:NumberUtil.strError="新密码不能和旧密码相同";break;
			case 10007:NumberUtil.strError="该账号在别的设备上登陆，请重新登陆";break;
			case 10011:NumberUtil.strError="输入密码错误";break;
			case 10101:NumberUtil.strError="短信验证码发送过多";break;
			case 10102:NumberUtil.strError="短信验证码发送过于频繁";break;
			case 10103:NumberUtil.strError="短信验证码错误";break;
			case 10104:NumberUtil.strError="短信验证码错误次数过多";break;
			case 10110:NumberUtil.strError="该手机号已经注册商户";break;
			case 10111:NumberUtil.strError="商户申请正在处理";break;
			case 10112:NumberUtil.strError="商户申请过于频繁";break;
			case 10113:NumberUtil.strError="请先完善商户资料";break;
			case 10211:NumberUtil.strError="编辑内容超出长度限制";break;
			case 11001:NumberUtil.strError="商户不存在或者密钥错误";break;
			case 12003:NumberUtil.strError="指定时间范围内存在未上架的商品,请重新选择日期";break;
			case 12005:NumberUtil.strError="购买内容过多,数据无法保存";break;
			case 12010:NumberUtil.strError="余额不足";break;
			case 12011:NumberUtil.strError="订单未处于当前可操作状态";break;
			case 12012:NumberUtil.strError="退款数量跟实际数量不一致";break;
			case 12013:NumberUtil.strError="订单不能删除";break;
			case 12014:NumberUtil.strError="可退订单金额已不足以退款";break;
			case 12015:NumberUtil.strError="当前订单不支持的操作";break;
			case 12016:NumberUtil.strError="抢购未开始";break;
			case 12017:NumberUtil.strError="抢购已经结束";break;
			case 12018:NumberUtil.strError="商品有最少份的购买限制";break;
			case 12020:NumberUtil.strError="库存不足";break;
			case 12021:NumberUtil.strError="儿童不允许购买";break;
			case 12022:NumberUtil.strError="单房差不允许购买";break;

			case 12024:NumberUtil.strError="开放平台订单不可指定退款方式,只能按原路退款[开放平台订单只有单银行单次支付一种情况]";break;
			case 12026:NumberUtil.strError="当前状态的订单只能申请整单全退,不可指定退款量";break;
			case 12027:NumberUtil.strError="某些商品的订单只能申请整单全退(如跟团旅游)";break;
			case 12028:NumberUtil.strError="订单为多次付款订单,只能退至余额";break;
			case 12029:NumberUtil.strError="订单为开放平台订单,只能原路退款";break;

			case 12031:NumberUtil.strError="订单已过期(2小时)";break;
			case 12032:NumberUtil.strError="订单付款超额";break;
			case 12033:NumberUtil.strError="订单已足额支付";break;
			case 12034:NumberUtil.strError="每次分次支付的金额不可少于总金额的十分之一";break;
			case 12035:NumberUtil.strError="订单为开放平台订单,只能通过银行支付";break;
			case 12036:NumberUtil.strError="开放平台订单价格不一致时不可改期";break;
			case 12037:NumberUtil.strError="已有待处理的改期请求,无法发起新改期";break;
			case 12038:NumberUtil.strError="订单价格不一致且已有消费或退款不支持改期";break;
			case 12039:NumberUtil.strError="订单因供应方不支持改期";break;
			case 12040:NumberUtil.strError="要改期的日期与改之前一致，无需改期";break;
			case 12041:NumberUtil.strError="当前商品类型不支持改期(仅门票支持)";break;
			case 12071:NumberUtil.strError="淘宝订单只可通过淘宝操作";break;

			case 111:NumberUtil.strError="该银行卡已经添加了";break;
			default:NumberUtil.strError="错误码："+status+"";
		}
	}



}

