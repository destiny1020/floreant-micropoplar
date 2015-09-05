package com.micropoplar.pos.payment.config;

import java.io.File;

import org.apache.commons.configuration.PropertiesConfiguration;

import com.tencent.WXPay;

/**
 * 读取微信支付的配置项
 * 
 * @author destiny1020
 *
 */
public class WeChatConfig {

	public static final String WECHAT_ENABLE = "wechat_enable";
	public static final String WECHAT_SIGN_KEY = "wechat_sign_key";
	public static final String WECHAT_APP_ID = "wechat_app_id";
	public static final String WECHAT_MCH_ID = "wechat_mch_id";
	public static final String WECHAT_SUB_MCH_ID = "wechat_sub_mch_id";
	public static final String WECHAT_CREDENTIAL_PATH = "wechat_credential_path";
	public static final String WECHAT_CREDENTIAL_PASS = "wechat_credential_pass";

	private static PropertiesConfiguration config;

	static {
		try {
			File configFile = new File("config/wechat.properties");
			if (!configFile.exists()) {
				configFile.createNewFile();
			}
			
			// 初始化微信支付环境
			WXPay.initSDKConfiguration(
					WeChatConfig.getWeChatSignKey(), 
					WeChatConfig.getWeChatAppId(), 
					WeChatConfig.getWeChatMchId(), 
					WeChatConfig.getWeChatSubMchId(), 
					WeChatConfig.getWeChatCredentialPath(), 
					WeChatConfig.getWeChatCredentialPass()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isWeChatSupported() {
		return config.getBoolean(WECHAT_ENABLE);
	}

	public static PropertiesConfiguration getConfig() {
		return config;
	}

	public static String getString(String key) {
		return config.getString(key, null);
	}

	public static String getString(String key, String defaultValue) {
		return config.getString(key, defaultValue);
	}

	public static String getWeChatSignKey() {
		return getString(WECHAT_SIGN_KEY);
	}

	public static String getWeChatAppId() {
		return getString(WECHAT_APP_ID);
	}

	public static String getWeChatMchId() {
		return getString(WECHAT_MCH_ID);
	}

	public static String getWeChatSubMchId() {
		return getString(WECHAT_SUB_MCH_ID);
	}

	public static String getWeChatCredentialPath() {
		return getString(WECHAT_CREDENTIAL_PATH);
	}

	public static String getWeChatCredentialPass() {
		return getString(WECHAT_CREDENTIAL_PASS);
	}

}
