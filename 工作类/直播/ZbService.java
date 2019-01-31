package com.cn.worldmodel.tencent;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.cn.jm.core.tool.security.JMToolMD5;
import com.cn.jm.core.utils.util.DateUtils;
import com.cn.worldmodel.dao.JMZbConfigDao;
import com.jfinal.kit.HttpKit;

/**
 * 创建人： 李智胜 创建时间：2017年11月16日下午4:58:51 类说明：腾讯直播服务端
 */

public class ZbService {
	public static final ZbService ZB_SERVICE = new ZbService();

	private static JMZbConfigDao configDao = JMZbConfigDao.jmd;

	private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	private static String getSafeUrl(String key, String streamId, long txTime) {
		String input = new StringBuilder().append(key).append(streamId).append(Long.toHexString(txTime).toUpperCase())
				.toString();

		String txSecret = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			txSecret = byteArrayToHexString(messageDigest.digest(input.getBytes("UTF-8")));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return txSecret == null ? ""
				: new StringBuilder().append("txSecret=").append(txSecret).append("&").append("txTime=")
						.append(Long.toHexString(txTime).toUpperCase()).toString();
	}

	private static String byteArrayToHexString(byte[] data) {
		char[] out = new char[data.length << 1];

		for (int i = 0, j = 0; i < data.length; i++) {
			out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS_LOWER[0x0F & data[i]];
		}
		return new String(out);
	}

	/**
	 * 
	 * 方法说明：生成推流地址 创建时间：2017年11月16日下午6:09:45 By 李智胜
	 * 
	 * @param zbRoomId
	 * @return
	 **
	 */
	public static String getPushUrl(String zbRoomId, boolean record) {
		String streamId = configDao.selectTencent().getTencentBizid() + "_" + zbRoomId;
		String pushUrl = "http://" + configDao.selectTencent().getTencentBizid() + ".livepush.myqcloud.com/live/"
				+ streamId + "?bizid=" + configDao.selectTencent().getTencentBizid() + "&"
				+ getSafeUrl(configDao.selectTencent().getTencentZhiboFangdaoKey(), streamId,
						DateUtils.offsetDate(new Date(), 1, Calendar.DATE).getTime() / 1000);
		if (record) {
			pushUrl = pushUrl + "&record=mp4&record_interval=5400";
		}
		return pushUrl;
	}

	/**
	 * 
	 * @Description:生成推流地址
	 * @param id
	 * @param record
	 * @return
	 */
	public static String getPushUrl(Long id, boolean record) {

		String streamId = configDao.selectTencent().getTencentBizid() + "_" + id;

		String pushUrl = "http://" + configDao.selectTencent().getTencentBizid() + ".livepush.myqcloud.com/live/"
				+ streamId + "?bizid=" + configDao.selectTencent().getTencentBizid() + "&"
				+ getSafeUrl(configDao.selectTencent().getTencentZhiboFangdaoKey(), streamId,
						DateUtils.offsetDate(new Date(), 1, Calendar.DATE).getTime() / 1000);
		if (record) {
			pushUrl = pushUrl + "&record=mp4&record_interval=5400";
		}
		return pushUrl;
	}

	/**
	 * 
	 * 方法说明：生成播放地址 创建时间：2017年11月17日上午9:27:51 By 李智胜
	 * 
	 * @param zbRoomId
	 * @return
	 **
	 */
	public static String getPlayUrl(String zbRoomId) {

		String streamId = configDao.selectTencent().getTencentBizid() + "_" + zbRoomId;

		String playUrl = "http://" + configDao.selectTencent().getTencentBizid() + ".liveplay.myqcloud.com/live/"
				+ streamId;
		return playUrl;
	}

	public static String getStreamId(Long id) {
		return configDao.selectTencent().getTencentBizid() + "_" + id;
	}

	/**
	 * 
	 * @Description:一对一没有房间
	 * @param id
	 * @return
	 */
	public static String getPlayUrl(Long id) {
		String streamId = configDao.selectTencent().getTencentBizid() + "_" + id +".m3u8";

		String playUrl = "http://" + configDao.selectTencent().getTencentBizid() + ".liveplay.myqcloud.com/live/"
				+ streamId;
		return playUrl;
	}

	/**
	 * 
	 * 方法说明：结束直播 创建时间：2017年11月17日下午2:54:12 By 李智胜
	 * 
	 * @param streamKey
	 * @return
	 **
	 */
	public static boolean stopZb(String streamKey) {
		try {
			long t = new Date().getTime() + 60000;
			String sign = JMToolMD5.MD5_32bit(configDao.selectTencent().getTencentZhiboApiKey() + t);
			String Paras = "?appid=" + configDao.selectTencent().getTencentAppid()
					+ "&interface=Live_Channel_SetStatus&Param.s.channel_id="
					+ configDao.selectTencent().getTencentBizid() + "_" + streamKey + "&Param.n.status=0&t=" + t
					+ "&sign=" + sign;
			String result = HttpKit.get(configDao.selectTencent().getTencentUrl() + Paras);
			JSONObject obj = JSONObject.parseObject(result);
			if (obj.getInteger("ret") == 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * method statement：API鉴权回调sign校验 createTime：2018年1月29日下午12:03:05 By JaysonLee
	 * 
	 * @param t
	 * @param sign
	 * @return
	 **
	 */
	public static boolean IsSign(String t, String sign) {
		String input = new StringBuffer().append(configDao.selectTencent().getTencentZhiboApiKey()).append(t)
				.toString();
		String mySign = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			mySign = byteArrayToHexString(messageDigest.digest(input.getBytes("UTF-8")));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (sign.equals(mySign)) {
			return true;
		}
		return false;
	}
}
