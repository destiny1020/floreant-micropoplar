package com.micropoplar.pos.payment;

import org.slf4j.LoggerFactory;

import com.floreantpos.model.Ticket;
import com.tencent.WXPay;
import com.tencent.common.Log;
import com.tencent.common.Signature;
import com.tencent.common.Util;
import com.tencent.protocol.pay_query_protocol.ScanPayQueryReqData;
import com.tencent.protocol.pay_query_protocol.ScanPayQueryResData;

/**
 * 微信支付订单状态查询。
 * 
 * @author destiny1020
 *
 */
public class QueryBusiness {

	private static Log log = new Log(LoggerFactory.getLogger(PreorderBusiness.class));

	private static final int WAITING_TIME_BEFORE_QUERY_IN_MILLIS = 5000;
	private static final int QUERY_TIMES = 8;

	/**
	 * 对订单的支付状态进行查询
	 * 
	 * @param ticket
	 * @return
	 * @throws Exception
	 */
	public static PaymentResult queryQrCodePay(Ticket ticket) throws Exception {
		// 进行循环查询
		PaymentResult result = null;
		for (int i = 0; i < QUERY_TIMES; i++) {
			Thread.sleep(WAITING_TIME_BEFORE_QUERY_IN_MILLIS);// 等待一定时间再进行查询，避免状态还没来得及被更新
			result = queryQrCodePayOnce(ticket);
			if (result.isSuccessful()) {
				return result;
			}
		}
		return result;
	}

	/**
	 * 对订单的支付状态查询一次，在重新处理订单的时候使用
	 * 
	 * @param ticket
	 * @return
	 * @throws Exception
	 */
	public static PaymentResult queryQrCodePayOnce(Ticket ticket) throws Exception {
		ScanPayQueryReqData reqData = new ScanPayQueryReqData("", ticket.getUniqId());
		String responseString = WXPay.requestScanPayQueryService(reqData);
		log.i("支付订单查询API返回的数据如下：");
		log.i(responseString);

		// 将从API返回的XML数据映射到Java对象
		ScanPayQueryResData scanPayQueryResData = (ScanPayQueryResData) Util.getObjectFromXML(responseString,
				ScanPayQueryResData.class);
		if (scanPayQueryResData == null || scanPayQueryResData.getReturn_code() == null) {
			log.i("支付订单查询请求逻辑错误，请仔细检测传过去的每一个参数是否合法");
			return PaymentResult.FAIL_RESULT;
		}

		if (scanPayQueryResData.getReturn_code().equals("FAIL")) {
			// 注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
			log.i("支付订单查询API系统返回失败，失败信息为：" + scanPayQueryResData.getReturn_msg());
			return PaymentResult.FAIL_RESULT;
		} else {

			if (!Signature.checkIsSignValidFromResponseString(responseString)) {
				log.e("【支付失败】支付请求API返回的数据签名验证失败，有可能数据被篡改了");
				return PaymentResult.FAIL_RESULT;
			}

			if (scanPayQueryResData.getResult_code().equals("SUCCESS")) {// 业务层成功
				String transID = scanPayQueryResData.getTransaction_id();
				if (scanPayQueryResData.getTrade_state().equals("SUCCESS")) {
					// 表示查单结果为“支付成功”
					log.i("查询到订单支付成功，微信订单号为: " + transID);
					return new PaymentResult(true, transID, Integer.parseInt(scanPayQueryResData.getTotal_fee()));
				} else {
					// 支付不成功
					log.i("查询到订单支付不成功，微信订单号为: " + transID);
					return new PaymentResult(false, transID, 0);
				}
			} else {
				log.i("查询出错，错误码：" + scanPayQueryResData.getErr_code() + "     错误信息："
						+ scanPayQueryResData.getErr_code_des());
				return PaymentResult.FAIL_RESULT;
			}

		}
	}
}
