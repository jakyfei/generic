package com.topsec.tsm.generic.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel读取工具类
 * Created by yue_tf on 2017/3/28
 */
public class ExcelImport {

    /**
     * 读取Excel的页码 默认为第一页（索引值为0）
     */
    private int sheetPage = 0;

    /**
     * 读取Excel表头行 默认为第一行（索引值为0）
     */
    private int headRow = 0;

    /**
     * 序列化bean数据起始行 默认为第二行(索引值为1)
     */
    private int startRow = 1;

    /**
     * Excel文件路径
     */
    private String excelPath = "";

    /**
     * 装载表头信息的map集合
     */
    private Map<Integer, String> header = null;

    /**
     * 默认的时间格式 yyyy-MM-dd
     */
    private String dateFormat = "yyyy-MM-dd";

    /**
     * 解析的时间格式
     */
    private SimpleDateFormat format;

    /**
     * 设定是否打印控制台信息
     */
    private boolean printMsg = false;

    /**
     * 无参构造器, 需要setExcelPath文件路径
     */
    public ExcelImport() {

    }

    /**
     * ExcelImport构造器
     *
     * @param excelPath Excel文件路径
     */
    public ExcelImport(String excelPath) {
        this.excelPath = excelPath;
    }

    /**
     * 读取Excel文件 并封装实体bean的集合
     *
     * @param header 装载表头与实体属性名的map< table_name,entity_name >集合
     * @param clazz  实体bean的类型
     * @return 实体bean的集合
     * @throws Exception
     */
    public <T> List<T> readExcel(Map<String, String> header, Class<T> clazz, InputStream inputStream) {
        try {
            return readExcel(this.excelPath, header, clazz, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据文件扩展名, 调用对应的读取方法
     *
     * @param excelPath 读取Excel文件路径
     * @param header    装载表头与实体属性名的map< table_name,entity_name >集合
     * @param clazz     实体bean的类型
     * @return 实体bean的集合
     */
    private <T> List<T> readExcel(String excelPath, Map<String, String> header, Class<T> clazz, InputStream inputStream) throws Exception {
        // 获取日期解析格式
        format = new SimpleDateFormat(this.dateFormat);
        // 获取扩展名
        String ext = excelPath.substring(excelPath.lastIndexOf(".") + 1);
        try {
            if ("xls".equals(ext)) {
                // 使用xls方式读取
                return readExcel_xls(excelPath, header, clazz, inputStream);
            } else if ("xlsx".equals(ext)) {
                // 使用xlsx方式读取
                return readExcel_xlsx(excelPath, header, clazz, inputStream);
            } else {
                // 依次尝试xls、xlsx方式读取
                try {
                    out("您要操作的文件没有扩展名，正在尝试以xls方式读取...");
                    return readExcel_xls(excelPath, header, clazz, inputStream);
                } catch (Exception e) {
                    try {
                        out("尝试以xls方式读取，结果失败！，正在尝试以xlsx方式读取...");
                        return readExcel_xlsx(excelPath, header, clazz, inputStream);
                    } catch (Exception ex) {
                        out("尝试以xlsx方式读取，结果失败！\n请您确保您的文件是Excel文件，并且无损，然后再试。");
                        throw ex;
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 读取Excel(97-2003版, xls格式)
     *
     * @param excelPath 读取Excel文件路径
     * @param header    装载表头与实体属性名的map< table_name,entity_name >集合
     * @param clazz     实体bean的类型
     * @return 实体bean的集合
     */
    private <T> List<T> readExcel_xls(String excelPath, Map<String, String> header, Class<T> clazz, InputStream inputStream) throws Exception {
        List<T> returnList = new ArrayList<T>();
        // 用于Workbook级的操作，创建、删除Excel
        HSSFWorkbook wb = null;
        try {
//			FileInputStream fis = new FileInputStream(file);
            // 读取Excel 97-03版，xls格式
            wb = new HSSFWorkbook(inputStream);
            returnList = readExcel(wb, header, clazz);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnList;
    }

    /**
     * 读取Excel(2007版, xlsx格式)
     *
     * @param excelPath 读取Excel文件路径
     * @param header    装载表头与实体属性名的map< table_name,entity_name >集合
     * @param clazz     实体bean的类型
     * @return 实体bean的集合
     */
    private <T> List<T> readExcel_xlsx(String excelPath, Map<String, String> header, Class<T> clazz, InputStream inputStream) throws Exception {
        List<T> returnList = new ArrayList<T>();
        // 用于Workbook级的操作, 创建、删除Excel
        XSSFWorkbook wb = null;
        try {
//			FileInputStream fis = new FileInputStream(file);
            // 读取Excel 2007版，xlsx格式
            wb = new XSSFWorkbook(inputStream);
            returnList = readExcel(wb, header, clazz);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnList;
    }

    /**
     * 通用读取Excel文件
     *
     * @param wb     工作簿对象
     * @param header 装载表头与实体属性名的map< table_name,entity_name >集合
     * @param clazz  实体bean的类型
     * @return 实体bean的集合
     */
    private <T> List<T> readExcel(Workbook wb, Map<String, String> header, Class<T> clazz) throws Exception {
        // 获取sheet页
        Sheet sheet = wb.getSheetAt(this.sheetPage);
        // 获取行数
        int rowNum = sheet.getLastRowNum();
        if (rowNum < 1) {
            return new ArrayList<T>();
        }
        // 加载标题栏数据
        this.loadHeader(sheet);
        List<T> result = new ArrayList<T>();
        for (int i = this.startRow; i <= rowNum; i++) {
            Row row = sheet.getRow(i);
            int cellNum = row.getLastCellNum();
            T instance = (T) clazz.newInstance();
            for (int columns = 0; columns < cellNum; columns++) {
                Cell cell = row.getCell(columns);
                // 判断单元格的数据类型
                String value = loadCellType(wb, cell);
                String key = this.header.get(columns);
//				if("ipType".equals(header.get(key))){
//					if("单独IP".equals(value)){
//						value = "0";
//					}else if("IP段".equals(value)){
//						value = "1";
//					}else{
//						value = "9";
//					}
//				}else 
                if ("role".equals(header.get(key))) {
                    if ("终端".equals(value)) {
                        value = "0";
                    } else if ("服务器".equals(value)) {
                        value = "1";
                    } else {
                        value = "9";
                    }
                }
                // 加载实际值
                this.loadValue(clazz, instance, header.get(key), value);
            }
            result.add(instance);
        }
        out("<<<<< 序列化完成, 集合内装载bean的个数 :" + result.size() + "个" + " >>>>>");
        return result;
    }

    /**
     * 获取标题栏数据
     *
     * @param sheet Excel表级对象
     * @return 设置表头信息map==>key:标题栏列索引 value:标题栏值
     */
    private void loadHeader(Sheet sheet) {
        this.header = new HashMap<Integer, String>();
        Row row = sheet.getRow(this.headRow);
        int columns = row.getLastCellNum();
        for (int i = 0; i < columns; i++) {
            Cell cell = row.getCell(i);
            if (cell == null)
                continue;
            String value = cell.getStringCellValue();
            this.header.put(i, value);
        }
        out("加载标题栏:" + this.header.toString());
    }

    /**
     * 将单元格数据转换成string类型
     *
     * @param wb   工作簿对象
     * @param cell 单元格对象
     * @return 单元格数据String类型
     */
    private String loadCellType(Workbook wb, Cell cell) {
        String value = null;
        if (cell == null)
            return null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    // 当前的cell数据为Date类型
                    value = this.formateDate(cell.getDateCellValue());
                } else {
                    // 将数字num以StringType类型读取
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    value = cell.getStringCellValue();
                    // value = String.valueOf((long) cell.getNumericCellValue());
                    // DecimalFormat df = new DecimalFormat("#.##");
                    // value = df.format(cellValue.getNumberValue());
                }
                break;
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                break;
            case Cell.CELL_TYPE_BLANK:
                break;
            case Cell.CELL_TYPE_ERROR:
                break;
            default:
                break;
        }

        return value;
    }

    /**
     * 通过反射, 为动态加载类注入属性值
     *
     * @param clazz    泛型
     * @param instance 动态加载类
     * @param pro      属性名
     * @param value    属性值
     */
    private <T> void loadValue(Class<T> clazz, T instance, String pro, String value) throws Exception {
        if (pro == null || value == null)
            return;
        String getMethod = this.initGetMethod(pro);
        Class<?> type = clazz.getDeclaredMethod(getMethod).getReturnType();
        Method method = clazz.getMethod(this.initSetMethod(pro), type);
        if (type == String.class) {
            method.invoke(instance, value);
        } else if (type == int.class || type == Integer.class) {
            if (!"".equals(value)) {
                method.invoke(instance, Integer.parseInt(value));
            }
        } else if (type == long.class || type == Long.class) {
            method.invoke(instance, Long.parseLong(value));
        } else if (type == float.class || type == Float.class) {
            method.invoke(instance, Float.parseFloat(value));
        } else if (type == double.class || type == Double.class) {
            method.invoke(instance, Double.parseDouble(value));
        } else if (type == Date.class) {
            method.invoke(instance, this.parseDate(value));
        }

    }

    private void out(String msg) {
        if (printMsg) {
            System.out.println(msg);
        }
    }

    private Date parseDate(String value) throws ParseException {
        return format.parse(value);
    }

    private String formateDate(Date date) {
        return format.format(date);
    }

    private String initSetMethod(String field) {
        return "set" + field.substring(0, 1).toUpperCase() + field.substring(1);
    }

    private String initGetMethod(String field) {
        return "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
    }

    public String getExcelPath() {
        return excelPath;
    }

    /**
     * 设置Excel文件路径
     */
    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }

    public String getDate_format() {
        return dateFormat;
    }

    /**
     * 设置时间格式 默认为 yyyy-MM-dd
     */
    public void setDate_format(String date_format) {
        this.dateFormat = date_format;
    }

    public int getSheetPage() {
        return sheetPage;
    }

    /**
     * 设置读取Excel的页码 默认为第一页（索引值为0）
     */
    public void setSheetPage(int sheetPage) {
        this.sheetPage = sheetPage;
    }

    public int getHeadRow() {
        return headRow;
    }

    /**
     * 设置Excel表头行 默认为第一行（索引值为0）
     */
    public void setHeadRow(int headRow) {
        this.headRow = headRow;
    }

    public int getStartRow() {
        return startRow;
    }

    /**
     * 设置序列化bean数据起始行 默认为第二行(索引值为1)
     */
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

}
