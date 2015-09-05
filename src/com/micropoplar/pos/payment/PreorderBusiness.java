package com.micropoplar.pos.payment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;

import com.floreantpos.config.TerminalConfig;
import com.floreantpos.main.Application;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.util.DateUtil;
import com.micropoplar.pos.payment.config.WeChatConfig;
import com.tencent.WXPay;
import com.tencent.common.Util;
import com.tencent.protocol.pay_protocol.PreorderReqData;
import com.tencent.protocol.pay_protocol.PreorderResData;

import util.QRGen;

/**
 * 微信扫码支付的支持，通过统一下单生成二维码。
 * 
 * @author destiny1020
 *
 */
public class PreorderBusiness {

	public static String QRCODE_GEN_FAILED = "qrcode_gen_failed";

	/**
	 * 返回的是生成二维码图片的本地路径，若生成失败了，返回一个常量表示失败。
	 * 
	 * @param ticket
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public static String run(Ticket ticket, int amount) throws Exception {

		PreorderReqData reqData = new PreorderReqData();

		// 填充数据
		reqData.setDevice_info(String.valueOf(TerminalConfig.getTerminalId()));
		reqData.setBody(
				String.format("%s 订单: %s", Application.getInstance().getRestaurant().getName(), ticket.getUniqId()));
		reqData.setOut_trade_no(ticket.getUniqId());
		reqData.setTotal_fee(amount);
		reqData.setSpbill_create_ip(getLocalIpAddress());

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		reqData.setTime_start(DateUtil.getWeChatTimestamp(c.getTime()));

		// 一天内有效
		c.add(Calendar.DAY_OF_MONTH, 1);
		reqData.setTime_expire(DateUtil.getWeChatTimestamp(c.getTime()));

		reqData.setNotify_url(WeChatConfig.getWeChatNotifyUrl());
		reqData.setProduct_id(ticket.getUniqId());

		reqData.setSign();

		// 发送请求
		String response = WXPay.requestPreorderService(reqData);

		// 转换响应为对象
		PreorderResData preoderResData = (PreorderResData) Util.getObjectFromXML(response, PreorderResData.class);

		if (preoderResData.getReturn_code().equals("SUCCESS") && preoderResData.getReturn_msg().equals("OK")) {
			String fileDest = String.format("%s.png", "qrcode/" + ticket.getUniqId());
			QRGen.encodeQrcode(preoderResData.getCode_url(), fileDest);

			return fileDest;
		} else {
			// TODO
			return QRCODE_GEN_FAILED;
		}
	}

	private static String getLocalIpAddress() throws UnknownHostException {
		InetAddress[] inetAdds = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
		for (int i = 0; i < inetAdds.length; i++) {
			String ipAddress = inetAdds[i].getHostAddress();
			if (ipAddress.startsWith("192") || ipAddress.startsWith("172")) {
				return ipAddress;
			}
		}

		return "8.8.8.8";
	}

}
