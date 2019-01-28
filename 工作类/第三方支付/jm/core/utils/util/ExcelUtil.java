package com.cn.jm.core.utils.util;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Excel工具类 使用前请注意是否引入poi、poi-ooxml、cglib依赖
 *
 * @author liuzhou create at 2017-03-16 21:08
 */
public class ExcelUtil {

	/**
	 * 此方法用于向指定工作薄(workbook)写入一个工作页(sheet)
	 *
	 * @param workBook
	 *            工作簿，数据会写入该工作簿，之后可用它来写入文件或输出流（HSSFWorkbook是xls，XSSFWorkbook是xlsx）
	 * @param list
	 *            存放源数据
	 * @param info
	 *            存放表头、字段名、处理器
	 * @param sheetName
	 *            工作页名
	 * @param sheetNum
	 *            工作页号，从0开始
	 * @param <T>
	 *            目前支持的类型：框架无关【javabean，Map】；beetlsql【Tail】；jfinal【Model，Record】
	 */
	public static <T> void writeExcel(Workbook workBook, List<T> list, Info<T> info, String sheetName, int sheetNum) {
		CellStyle cellStyle = workBook.createCellStyle();
		// 设置单元格格式为“文本”
		DataFormat format = workBook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("@"));
		Sheet sheet = workBook.createSheet();
		workBook.setSheetName(sheetNum, sheetName);
		Objects.requireNonNull(info, "导出excel，info不得为空");
		List<SingleInfo<T>> infos = info.list;

		// 创建表头信息
		Row titleRow = sheet.createRow(0);
		for (int i = 0; i < infos.size(); i++) {
			Cell cell = titleRow.createCell(i);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(infos.get(i).title);
		}

		// 填充数据，先遍历行，再遍历列
		for (int i = 0; i < list.size(); i++) {
			Row row = sheet.createRow(i + 1);
			T bean = list.get(i);
			for (int j = 0; j < infos.size(); j++) {
				Cell cell = row.createCell(j);
				SingleInfo<T> singleInfo = infos.get(j);
				CellStyle columnCellStyle = singleInfo.getStyle(workBook);
				if (columnCellStyle == null) {
					cell.setCellStyle(cellStyle);
				} else {
					cell.setCellStyle(columnCellStyle);
				}
				CellHandler<T> handler = singleInfo.realHandler;
				Object value = null;
				if (Operation.INDEX == singleInfo.operation) {
					value = i + 1;
				}
				if (handler == null) {
					throw new UnsupportedOperationException("handler不可能为空");
				}
				handler.process(workBook, cell, bean, value);
			}
		}
		// 设置列宽，先遍历列，再遍历行
		for (int j = 0; j < infos.size(); j++) {
			// sheet.autoSizeColumn((short) j); // 自动调整列宽度
			int max = 0;
			// 从0到list.size()，因为还有表头行
			for (int i = 0; i <= list.size(); i++) {
				Cell cell = sheet.getRow(i).getCell(j);
				@SuppressWarnings("deprecation")
				CellType type = cell.getCellTypeEnum();
				switch (type) {
				case STRING: {
					String value = sheet.getRow(i).getCell(j).getStringCellValue();
					int length = (value.getBytes().length + value.length()) / 2 + 2;
					max = (length > max) ? length : max;
					break;
				}
				case FORMULA: {
					// 目前只有超链接这一种函数
					String value = sheet.getRow(i).getCell(j).getCellFormula();
					int length = (value.length()) / 2 - 10;
					max = (length > max) ? length : max;
					break;
				}
				default: {
				}
				}
			}
			sheet.setColumnWidth(j, max * 256);
		}
	}

	/**
	 * 私有化Info的构造器，由此方法提供Info的实例
	 *
	 * @param <T>
	 *            数据的类型
	 * @return Info实例
	 */
	public static <T> Info<T> buildInfo() {
		return new Info<>();
	}

	/**
	 * 提供一个处理器，若value为null，则单元格设为0，否则进行默认处理
	 *
	 * @param <T>
	 *            数据的类型
	 * @return Handler实例
	 */
	public static <T> CellHandler<T> nullToZeroHandler() {
		return (book, cell, bean, value) -> {
			if (value == null) {
				cell.setCellValue(0);
			} else {
				setCellValue(cell, value);
			}
		};
	}

	private static void setCellValue(Cell cell, Object value) {
		if (value == null) {
			cell.setCellValue("");
			return;
		}
		if (value instanceof Number) {
			cell.setCellValue(((Number) value).doubleValue());
			return;
		}
		if (value instanceof Date) {
			cell.setCellValue((Date) value);
			return;
		}
		if (value instanceof Calendar) {
			cell.setCellValue((Calendar) value);
			return;
		}
		if (value instanceof TemporalAccessor) {
			DateTimeFormatter formatter = TimeUtil.getDateTimeFormatter(value);
			cell.setCellValue(formatter.format((TemporalAccessor) value));
			return;
		}
		if (value instanceof RichTextString) {
			cell.setCellValue((RichTextString) value);
			return;
		}
		if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
			return;
		}
		cell.setCellValue(value.toString());
	}

	/**
	 * 特殊的取值操作，目前只有INDEX，表示取出行号
	 */
	public enum Operation {
		/**
		 * 取出行号
		 */
		INDEX
	}

	/**
	 * 存放每列的信息
	 *
	 * @param <T>
	 *            数据的类型
	 */
	public static class Info<T> {
		private List<SingleInfo<T>> list = new ArrayList<>();

		private Info() {
		}

		/**
		 * 添加一列，该列使用supplier得到value，不进行加工
		 *
		 * @param title
		 *            表头
		 * @param supplier
		 *            提供值
		 * @return Info实例
		 */
		public Info<T> fastAddCol(String title, Supplier<?> supplier) {
			Objects.requireNonNull(supplier, "导出excel，supplier不得为空");
			CellHandler<T> handler = (book, cell, bean, value) -> setCellValue(cell, supplier.get());
			list.add(new SingleInfo<>(title, null, null, handler));
			return this;
		}

		/**
		 * 添加一列，该列使用supplier得到value，handler再将value进行加工
		 *
		 * @param title
		 *            表头
		 * @param supplier
		 *            提供值
		 * @param handler
		 *            处理器，用于该列数据的特殊处理，不得为空
		 * @return Info实例
		 */
		public Info<T> fastAddCol(String title, Supplier<?> supplier, CellHandler<T> handler) {
			Objects.requireNonNull(supplier, "导出excel，supplier不得为空");
			Objects.requireNonNull(handler, "导出excel，handler不得为空");
			CellHandler<T> newHandler = (book, cell, bean, value) -> handler.process(book, cell, bean, supplier.get());
			list.add(new SingleInfo<>(title, handler, null, newHandler));
			return this;
		}

		/**
		 * 添加一列，该列使用function得到value，不进行加工
		 *
		 * @param title
		 *            表头
		 * @param function
		 *            提供值
		 * @return Info实例
		 */
		public Info<T> fastAddCol(String title, Function<T, ?> function) {
			Objects.requireNonNull(function, "导出excel，function不得为空");
			CellHandler<T> handler = (book, cell, bean, value) -> setCellValue(cell, function.apply(bean));
			list.add(new SingleInfo<>(title, null, null, handler));
			return this;
		}

		/**
		 * 添加一列，该列使用function得到value，handler再将value进行加工
		 *
		 * @param title
		 *            表头
		 * @param function
		 *            提供值
		 * @param handler
		 *            处理器，用于该列数据的特殊处理，不得为空
		 * @return Info实例
		 */
		public Info<T> fastAddCol(String title, Function<T, ?> function, CellHandler<T> handler) {
			Objects.requireNonNull(function, "导出excel，function不得为空");
			Objects.requireNonNull(handler, "导出excel，handler不得为空");
			CellHandler<T> newHandler = (book, cell, bean, value) -> handler.process(book, cell, bean,
					function.apply(bean));
			list.add(new SingleInfo<>(title, handler, null, newHandler));
			return this;
		}

		/**
		 * 添加一列，该列使用key得到value，不进行加工 推荐使用fastAddCol(String, Function)方法效率更高
		 *
		 * @param title
		 *            表头
		 * @param key
		 *            字段名，不得为空
		 * @return Info实例
		 * @see Info#fastAddCol(String, Function)
		 */
		public Info<T> addColumn(String title, String key) {
			Objects.requireNonNull(key, "导出excel，key不得为空");
			CellHandler<T> handler = (book, cell, bean, value) -> setCellValue(cell, BeanUtil.get(bean, key));
			list.add(new SingleInfo<>(title, null, null, handler));
			return this;
		}

		/**
		 * 添加一列，handler直接进行加工
		 *
		 * @param title
		 *            表头
		 * @param handler
		 *            处理器，用于该列数据的特殊处理，不得为空
		 * @return Info实例
		 */
		public Info<T> addColumn(String title, CellHandler<T> handler) {
			Objects.requireNonNull(handler, "导出excel，handler不得为空");
			list.add(new SingleInfo<>(title, null, null, handler));
			return this;
		}

		/**
		 * 添加一列，该列使用key得到value，handler再将value进行加工 推荐使用fastAddCol(String, Function,
		 * CellHandler)方法效率更高
		 *
		 * @param title
		 *            表头
		 * @param key
		 *            字段名，不得为空
		 * @param handler
		 *            处理器，用于该列数据的特殊处理，不得为空
		 * @return Info实例
		 * @see Info#fastAddCol(String, Function, CellHandler)
		 */
		public Info<T> addColumn(String title, String key, CellHandler<T> handler) {
			Objects.requireNonNull(key, "导出excel，key不得为空");
			Objects.requireNonNull(handler, "导出excel，handler不得为空");
			CellHandler<T> newHandler = (book, cell, bean, value) -> handler.process(book, cell, bean,
					BeanUtil.get(bean, key));
			list.add(new SingleInfo<>(title, handler, null, newHandler));
			return this;
		}

		/**
		 * 添加一列，该列根据operation得到value，handler再将value进行加工
		 *
		 * @param title
		 *            表头
		 * @param operation
		 *            特殊的取值操作，不得为空
		 * @param handler
		 *            处理器，用于该列数据的特殊处理，不得为空
		 * @return Info实例
		 */
		public Info<T> addColumn(String title, Operation operation, CellHandler<T> handler) {
			Objects.requireNonNull(operation, "导出excel，operation不得为空");
			Objects.requireNonNull(handler, "导出excel，handler不得为空");
			list.add(new SingleInfo<>(title, handler, operation, handler));
			return this;
		}

		/**
		 * 添加一列，该列根据operation得到value，不进行加工
		 *
		 * @param title
		 *            表头
		 * @param operation
		 *            特殊的取值操作，不得为空
		 * @return Info实例
		 */
		public Info<T> addColumn(String title, Operation operation) {
			Objects.requireNonNull(operation, "导出excel，operation不得为空");
			CellHandler<T> handler = (book, cell, bean, value) -> setCellValue(cell, value);
			list.add(new SingleInfo<>(title, null, operation, handler));
			return this;
		}

		/**
		 * 替换指定位置的信息的key，根据原来有否设置处理器来决定是否进行加工
		 *
		 * @param index
		 *            索引，注意不要越界
		 * @param key
		 *            字段名，不得为空
		 */
		public Info<T> changeColumn(int index, String key) {
			Objects.requireNonNull(key, "导出excel，key不得为空");
			SingleInfo<T> singleInfo = list.get(index);
			if (singleInfo.handler == null) {
				singleInfo.realHandler = (book, cell, bean, value) -> {
					setCellValue(cell, BeanUtil.get(bean, key));
				};
			} else {
				singleInfo.realHandler = (book, cell, bean, value) -> singleInfo.handler.process(book, cell, bean,
						BeanUtil.get(bean, key));
			}
			return this;
		}

		/**
		 * 替换指定位置的信息的function，根据原来有否设置处理器来决定是否进行加工
		 *
		 * @param index
		 *            索引，注意不要越界
		 * @param function
		 *            提供值
		 */
		public Info<T> changeColumn(int index, Function<T, ?> function) {
			Objects.requireNonNull(function, "导出excel，function不得为空");
			SingleInfo<T> singleInfo = list.get(index);
			if (singleInfo.handler == null) {
				singleInfo.realHandler = (book, cell, bean, value) -> {
					setCellValue(cell, function.apply(bean));
				};
			} else {
				singleInfo.realHandler = (book, cell, bean, value) -> singleInfo.handler.process(book, cell, bean,
						function.apply(bean));
			}
			return this;
		}

		/**
		 * 替换指定位置的信息的supplier，根据原来有否设置处理器来决定是否进行加工
		 *
		 * @param index
		 *            索引，注意不要越界
		 * @param supplier
		 *            提供值
		 */
		public Info<T> changeColumn(int index, Supplier<?> supplier) {
			Objects.requireNonNull(supplier, "导出excel，supplier不得为空");
			SingleInfo<T> singleInfo = list.get(index);
			if (singleInfo.handler == null) {
				singleInfo.realHandler = (book, cell, bean, value) -> {
					setCellValue(cell, supplier.get());
				};
			} else {
				singleInfo.realHandler = (book, cell, bean, value) -> singleInfo.handler.process(book, cell, bean,
						supplier.get());
			}
			return this;
		}

		/**
		 * 移除指定位置的信息
		 *
		 * @param index
		 *            索引，注意不要越界
		 */
		public Info<T> removeColumn(int index) {
			return changeColumnIndex(index, -1);
		}

		/**
		 * 移动末尾位置的信息，将其插入到新的位置
		 *
		 * @param index
		 *            索引，注意不要越界
		 */
		public Info<T> changeLastColumn(int index) {
			return changeColumnIndex(-1, index);
		}

		/**
		 * 移动指定位置的信息，将其插入到新的位置
		 *
		 * @param original
		 *            当前索引，注意不要越界，但可为-1表示末尾位置
		 * @param target
		 *            目标索引，注意不要越界，但可为-1表示移除
		 */
		public Info<T> changeColumnIndex(int original, int target) {
			if (original == -1) {
				original = list.size() - 1;
			}
			SingleInfo<T> singleInfo = list.remove(original);
			if (target != -1) {
				list.add(target, singleInfo);
			}
			return this;
		}

		/**
		 * 为末尾位置的信息提供样式，样式只应用于数据，不会应用在title
		 *
		 * @param styleSupplier
		 *            样式提供器
		 */
		public Info<T> applyStyle(Function<Workbook, CellStyle> styleSupplier) {
			return applyStyle(-1, styleSupplier);
		}

		/**
		 * 为指定位置的信息提供样式，样式只应用于数据，不会应用在title
		 *
		 * @param index
		 *            索引，注意不要越界，但可为-1表示末尾位置
		 * @param styleSupplier
		 *            样式提供器
		 */
		public Info<T> applyStyle(int index, Function<Workbook, CellStyle> styleSupplier) {
			if (index == -1) {
				index = list.size() - 1;
			}
			SingleInfo<T> singleInfo = list.get(index);
			singleInfo.style = null;
			singleInfo.styleSupplier = styleSupplier;
			return this;
		}

		/**
		 * 注意：调用本方法后，请确保所有的column都没有使用到T的信息 如果有column使用了T的信息，请使用changeColumn系列方法替换之
		 *
		 * @param <U>
		 *            目标数据类型
		 * @return Info实例
		 */
		@SuppressWarnings("unchecked")
		public <U> Info<U> convertType() {
			return (Info<U>) this;
		}
	}

	private static class SingleInfo<T> {
		String title;
		CellHandler<T> handler;
		Operation operation;
		CellHandler<T> realHandler;
		CellStyle style;
		Function<Workbook, CellStyle> styleSupplier;

		private SingleInfo(String title, CellHandler<T> handler, Operation operation, CellHandler<T> realHandler) {
			this.title = title;
			this.handler = handler;
			this.operation = operation;
			this.realHandler = realHandler;
		}

		CellStyle getStyle(Workbook workbook) {
			if (style == null && styleSupplier != null) {
				style = styleSupplier.apply(workbook);
			}
			return style;
		}
	}

	public static void applyLinkStyle(Workbook workbook, Cell cell) {
		CellStyle linkStyle = workbook.createCellStyle();
		Font cellFont = workbook.createFont();
		cellFont.setUnderline((byte) 1);
		cellFont.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
		linkStyle.setFont(cellFont);
		cell.setCellStyle(linkStyle);
	}

}
