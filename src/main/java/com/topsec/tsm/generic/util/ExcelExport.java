package com.topsec.tsm.generic.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel写入工具类
 * Created by yue_tf on 2017/3/28
 */
public class ExcelExport {

    /**
     * 写入Excel(97-2003版, xls格式)
     */
    private static final String XLS_TYPE = ".xls";

    /**
     * 写入Excel(2007版, xlsx格式)
     */
    private static final String XLSX_TYPE = ".xlsx";

    /**
     * 设定写入Excel格式 默认为.xls格式
     */
    private String excelType = XLS_TYPE;

    /**
     * 写入Excel文件名
     */
    private String fileName;

    /**
     * 设定写入行数 默认为10万行
     */
    private static final int ROW_COUNT = 100000;

    /**
     * 工作簿对象
     */
    private Workbook wb;

    /**
     * 无参构造器, 需要setFileName文件名
     */
    public ExcelExport() {

    }

    /**
     * ExcelExport构造器
     *
     * @param fileName Excel文件名
     */
    public ExcelExport(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 通用Excel写入方法
     *
     * @param list     实体bean的集合
     * @param header   装载实体属性名与表头的map< entity_name,table_name >集合
     * @param response response输出流
     */
    public <T> void writeExcel(List<T> list, Map<String, String> header, HttpServletResponse response) throws Exception {
        if (excelType.equals(XLS_TYPE)) {
            // xls格式, 写入Excel(97-2003版, xls格式)
            writeExcel_xls(list, header, response);
        } else if (excelType.equals(XLSX_TYPE)) {
            // xlsx格式, 写入Excel(2007版, xlsx格式)
            writeExcel_xlsx(list, header, response);
        } else {
            try {
                // 两种格式分别尝试写入
                writeExcel_xls(list, header, response);
            } catch (Exception e) {
                writeExcel_xlsx(list, header, response);
            }
        }
    }

    /**
     * 写入Excel(97-2003版, xls格式)
     *
     * @param list     实体bean的集合
     * @param header   装载实体属性名与表头的map< entity_name,table_name >集合
     * @param response response输出流
     */
    private <T> void writeExcel_xls(List<T> list, Map<String, String> header, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> datas = this.genDatas4WB(list);
        wb = this.preparedDatas2WB_xls(datas, header);
        this.writeDates2Response(wb, fileName + excelType, response);
    }

    /**
     * 写入Excel(2007版, xlsx格式)
     *
     * @param list     实体bean的集合
     * @param header   装载实体属性名与表头的map< entity_name,table_name >集合
     * @param response response输出流
     */
    private <T> void writeExcel_xlsx(List<T> list, Map<String, String> header, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> datas = this.genDatas4WB(list);
        wb = this.preparedDatas2WB_xlsx(datas, header);
        this.writeDates2Response(wb, fileName + excelType, response);
    }

    /**
     * 实体参数名与值的map键值对
     *
     * @param list 实体bean的集合
     * @return 实体map键值对的List集合
     */
    private <T> List<Map<String, Object>> genDatas4WB(List<T> list) {
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        try {
            if (null != list && !list.isEmpty()) {
                for (T em : list) {
                    Map<String, Object> emValues = this.getPropertyValues(em);
                    datas.add(emValues);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datas;
    }

    /**
     * 写入Excel工作簿(97-2003版, xls格式)
     *
     * @param datas   实体map键值对的List集合
     * @param header 装载实体属性名与表头的map< entity_name,table_name >集合
     */
    private Workbook preparedDatas2WB_xls(List<Map<String, Object>> datas, Map<String, String> header) throws Exception {

        if ((datas == null || datas.isEmpty()) && header != null) {
            datas = new ArrayList<>(8);
            Map<String, Object> headerTmp = new HashMap<>();
            headerTmp.putAll(header);
            datas.add(headerTmp);
        }

        if (null != datas && !datas.isEmpty() && null != header && !header.isEmpty()) {
            int dataRowCount = datas.size();
            int over = (dataRowCount % ROW_COUNT != 0) ? 1 : 0;
            int sheetCount = (dataRowCount / ROW_COUNT) + over;

            // System.out.println("dataRowCount=" + dataRowCount +
            // ",sheetCount=" + sheetCount);

            // 创建HSSFWorkbook对象
            this.wb = new HSSFWorkbook();

            // 创建HSSFSheet对象
            for (int i = 0; i < sheetCount; i++) {
                int fromIndex = i * ROW_COUNT;
                int toIndex = (i + 1) * ROW_COUNT;
                if (toIndex > dataRowCount) {
                    toIndex = dataRowCount;
                }

                String sheetName = "sheet_" + i;
                // System.out.println("sheetName=" + sheetName+" ,fromIndex=" +
                // fromIndex + ",toIndex=" + toIndex);
                List<Map<String, Object>> subDatas = datas.subList(fromIndex, toIndex);

                HSSFSheet sheet = (HSSFSheet) wb.createSheet(sheetName);

                // 写列头
                HSSFRow head = sheet.createRow(0);
                Set<Map.Entry<String, String>> headEntrySet = header.entrySet();
                int headIdx = 0;
                for (Iterator<Map.Entry<String, String>> headIte = headEntrySet.iterator(); headIte.hasNext(); ) {
                    Map.Entry<String, String> entry = headIte.next();

                    HSSFCell cell = head.createCell(headIdx, Cell.CELL_TYPE_STRING);
                    // cell.setCellStyle(style);

                    String value = entry.getValue() == null ? "Col_" + headIdx : entry.getValue().toString();
                    cell.setCellValue(value);
                    headIdx++;
                }

                // 创建HSSFRow对象
                int subSize = subDatas.size();
                for (int j = 0; j < subSize; j++) {
                    HSSFRow row = sheet.createRow(j + 1);

                    Map<String, Object> keyValues = subDatas.get(j);

                    int colIndex = 0;
                    Set<String> colSets = header.keySet();
                    for (Iterator<String> colIte = colSets.iterator(); colIte.hasNext(); ) {
                        String key = colIte.next();
                        String value = "";
//						if ("ipType".equalsIgnoreCase(key)) {
//							value = null == keyValues.get(key) ? "" : keyValues.get(key).toString();
//							if (Integer.parseInt(value) == 0) {
//								value = "单独IP";
//							} else if (Integer.parseInt(value) == 1) {
//								value = "IP段";
//							} else {
//								value = "未知";
//							}
//						} else 
                        if ("role".equalsIgnoreCase(key)) {
                            value = null == keyValues.get(key) ? "" : keyValues.get(key).toString();

                            try {
                                if (Integer.parseInt(value) == 0) {
                                    value = "终端";
                                } else if (Integer.parseInt(value) == 1) {
                                    value = "服务器";
                                } else {
                                    value = "未知";
                                }
                            } catch (Exception e) {
                                value = "未知";
                                e.printStackTrace();
                            }
                        } else {
                            value = null == keyValues.get(key) ? "" : keyValues.get(key).toString();
                        }
                        HSSFCell cell = row.createCell(colIndex, Cell.CELL_TYPE_STRING);
                        cell.setCellValue(value);
                        colIndex++;
                    }

                }
            }
        }

        return wb;
    }

    /**
     * 写入Excel工作簿(2007版, xlsx格式)
     *
     * @param datas   实体map键值对的List集合
     * @param header 装载实体属性名与表头的map< entity_name,table_name >集合
     */
    private Workbook preparedDatas2WB_xlsx(List<Map<String, Object>> datas, Map<String, String> header) throws Exception {

        if ((datas == null || datas.isEmpty()) && header != null) {
            datas = new ArrayList<>(16);
            Map<String, Object> headerTmp = new HashMap<>();
            headerTmp.putAll(header);
            datas.add(headerTmp);
        }

        if (null != datas && !datas.isEmpty() && null != header && !header.isEmpty()) {
            int dataRowCount = datas.size();
            int over = (dataRowCount % ROW_COUNT != 0) ? 1 : 0;
            int sheetCount = (dataRowCount / ROW_COUNT) + over;

            // System.out.println("dataRowCount=" + dataRowCount +
            // ",sheetCount=" + sheetCount);

            // 创建XSSFWorkbook对象
            this.wb = new XSSFWorkbook();

            // 创建XSSFSheet对象
            for (int i = 0; i < sheetCount; i++) {
                int fromIndex = i * ROW_COUNT;
                int toIndex = (i + 1) * ROW_COUNT;
                if (toIndex > dataRowCount) {
                    toIndex = dataRowCount;
                }

                String sheetName = "sheet_" + i;
                // System.out.println("sheetName=" + sheetName+" ,fromIndex=" +
                // fromIndex + ",toIndex=" + toIndex);
                List<Map<String, Object>> subDatas = datas.subList(fromIndex, toIndex);

                XSSFSheet sheet = (XSSFSheet) wb.createSheet(sheetName);

                // 写列头
                XSSFRow head = sheet.createRow(0);
                Set<Map.Entry<String, String>> headEntrySet = header.entrySet();
                int headIdx = 0;
                for (Iterator<Map.Entry<String, String>> headIte = headEntrySet.iterator(); headIte.hasNext(); ) {
                    Map.Entry<String, String> entry = headIte.next();

                    XSSFCell cell = head.createCell(headIdx, Cell.CELL_TYPE_STRING);
                    // cell.setCellStyle(style);

                    String value = entry.getValue() == null ? "Col_" + headIdx : entry.getValue().toString();
                    cell.setCellValue(value);
                    headIdx++;
                }

                // 创建XSSFRow对象
                int subSize = subDatas.size();
                for (int j = 0; j < subSize; j++) {
                    XSSFRow row = sheet.createRow(j + 1);

                    Map<String, Object> keyValues = subDatas.get(j);

                    int colIndex = 0;
                    Set<String> colSets = header.keySet();
                    for (Iterator<String> colIte = colSets.iterator(); colIte.hasNext(); ) {
                        String key = colIte.next();
                        String value = null == keyValues.get(key) ? "" : keyValues.get(key).toString();
                        XSSFCell cell = row.createCell(colIndex, Cell.CELL_TYPE_STRING);
                        cell.setCellValue(value);
                        colIndex++;
                    }

                }
            }
        }

        return wb;
    }

    /**
     * 通过输出流 生成Excel文件
     *
     * @param wb       工作簿对象
     * @param fileName Excel文件名
     * @param response response输出流
     */
    private void writeDates2Response(Workbook wb, String fileName, HttpServletResponse response) throws Exception {
        OutputStream os = null;
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        // response.setContentType("application/octet-stream;charset=UTF-8");
        response.setContentType("application/msexcel");
        try {
            os = response.getOutputStream();
            wb.write(os);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 获取实体参数名与值的map键值对
     *
     * @param obj 动态加载类
     * @return 实体参数名与值的map键值对
     * @throws Exception
     */
    private Map<String, Object> getPropertyValues(Object obj) throws Exception {
        Map<String, Object> properties = new HashMap<String, Object>();

        if (null != obj) {

            Class<? extends Object> cls = obj.getClass();

            Method[] methods = cls.getMethods();
            for (Method method : methods) {
                String methodName = method.getName();
                String methodReTypeName = method.getReturnType().getSimpleName();

                if (methodName.contains("get") && !methodReTypeName.contains("Class")) {

                    String fieldName = methodName.replace("get", "").toLowerCase();

                    String value = "";
                    if (methodReTypeName.toLowerCase().contains("date")) {

                        Object vo = method.invoke(obj);
                        if (null != vo) {
                            Date datev = (Date) vo;
                            value = formatDate(datev, "yyyy-MM-dd HH:mm:ss");
                        }

                    } else {
                        value = null == method.invoke(obj) ? "" : method.invoke(obj).toString();
                    }

                    // System.out.println("methodName="+methodName);

                    // System.out.println("fieldName="+fieldName+",methodReTypeName="+methodReTypeName);
                    properties.put(fieldName, value);
                }
            }
        }

        return properties;
    }

    /**
     * 日期格式转化
     *
     * @param date 日期Date
     * @param type 日期格式
     * @return 时间格式String
     */
    private String formatDate(Date date, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        String result = null;
        try {
            if (null != date) {
                result = sdf.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 设置写入Excel(97-2003版, xls格式) 默认为.xls格式
     */
    public void setExcelType_xls() {
        this.excelType = XLS_TYPE;
    }

    /**
     * 设置写入Excel(2007版, xlsx格式) 默认为.xls格式
     */
    public void setExcelType_xlsx() {
        this.excelType = XLSX_TYPE;
    }

    public String getFileName() {
        return fileName;
    }

    /**
     * 设置Excel文件名
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
