package com.cn.jm.core.utils.util;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.i18n.I18n;
import com.jfinal.i18n.Res;

public class I18NUtils {

	public static Res getRes(String language) {
		Res resEn = null;
		// 默认中文 得到对应的Res对象
		if (StringUtils.isEmpty(language))
			resEn = I18n.use("zh_CN");
		else
			resEn = I18n.use(language);

		return resEn;

	}

}
