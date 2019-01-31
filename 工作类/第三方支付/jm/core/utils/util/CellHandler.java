package com.cn.jm.core.utils.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 提供excel单元格的操作
 *
 * @author liuzhou create at 2017-10-16 15:38
 */
@FunctionalInterface
public interface CellHandler<T> {
	/**
	 * 对单元格进行操作
	 *
	 * @param book
	 *            Workbook实例
	 * @param cell
	 *            单元格
	 * @param bean
	 *            数据实例
	 * @param value
	 *            要设置的值
	 */
	void process(Workbook book, Cell cell, T bean, Object value);
}
