package com.cn.jm.core.utils.util;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("rawtypes")
public interface JMIControllerAction {

	public boolean isave(Model<?> model, boolean result);

	public boolean isaves(List<? extends Model<?>> modelList, int[] results);

	public boolean iupdate(Model<?> model, boolean result);

	public boolean iupdates(List<? extends Model<?>> modelList, int[] results);

	public boolean idelete(long id, boolean result);

	public boolean ideletes(List<Long> ids, boolean result);

}
