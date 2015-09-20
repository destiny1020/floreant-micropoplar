package com.micropoplar.pos.payment.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.floreantpos.model.PaymentType;
import com.micropoplar.pos.dao.TakeoutPlatformDao;
import com.micropoplar.pos.model.TakeoutPlatform;

/**
 * 保存外卖平台的各种设置信息
 * 
 * @author destiny1020
 *
 */
@SuppressWarnings("unchecked")
public class TakeoutPlatformConfig {

	private static Map<String, TakeoutPlatform> mappings;

	static {
		List<TakeoutPlatform> allPlatforms = TakeoutPlatformDao.getInstance().findAll();

		if (allPlatforms.size() > 0) {
			mappings = new HashMap<>(allPlatforms.size());
			for (TakeoutPlatform platform : allPlatforms) {
				mappings.put(platform.getName(), platform);
			}
		} else {
			throw new RuntimeException("无法读取外卖平台的配置信息，请联系管理员");
		}
	}

	public static boolean isPaymentEnabled(PaymentType paymentType) {
		TakeoutPlatform takeoutPlatform = mappings.get(paymentType.name().toLowerCase());
		if (takeoutPlatform == null) {
			return false;
		}

		return takeoutPlatform.getEnabled();
	}

	public static double getPaymentDiscount(PaymentType paymentType) {
		TakeoutPlatform takeoutPlatform = mappings.get(paymentType.name().toLowerCase());
		if (takeoutPlatform == null) {
			return 1.0;
		}

		return takeoutPlatform.getDiscount();
	}

}
