package homework.soft.activity.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import homework.soft.activity.exception.HttpErrorException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


public class ExcelUtils {
    /**
     * 读取 Excel 文件
     *
     * @param file  Excel 文件
     * @param clazz 类
     * @param <T>   泛型
     * @return List<T> 数据
     */
    public static <T> List<T> readExcel(File file, Class<T> clazz) {
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<T> list;
        try {
            list = ExcelImportUtil.importExcel(file, clazz, params);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

    /**
     * 读取 Excel 文件
     *
     * @param file  Excel 文件
     * @param clazz 类
     * @param <T>   泛型
     * @return List<T> 数据
     */
    public static <T> List<T> readExcel(@RequestParam MultipartFile file, Class<T> clazz) {
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);

        return readExcel(file, clazz, params);
    }

    /**
     * 读取 Excel 文件
     *
     * @param file   Excel 文件
     * @param clazz  类
     * @param params 参数
     * @param <T>    泛型
     * @return List<T> 数据
     */
    public static <T> List<T> readExcel(@RequestParam MultipartFile file, Class<T> clazz, ImportParams params) {
        List<T> list;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), clazz, params);
        } catch (Exception e) {
            throw new HttpErrorException(INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return list;
    }

    /**
     * 导出 Excel 文件
     *
     * @param response HttpServletResponse
     * @param clazz    类
     * @param list     数据
     * @param fileName 文件名
     * @param <T>      泛型
     * @throws IOException IO 异常
     */
    public static <T> void downloadExcel(HttpServletResponse response, Class<T> clazz, List<T> list, String
            fileName) throws IOException {
        try (Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), clazz, list)) {
            downloadExcel(response, workbook, fileName);
        }
    }

    /**
     * 导出 Excel 文件
     *
     * @param response HttpServletResponse
     * @param workbook Workbook
     * @param fileName 文件名
     * @throws IOException IO 异常
     */
    public static void downloadExcel(HttpServletResponse response, Workbook workbook, String fileName) throws
            IOException {
        String yyyyMMdd = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8) + "." + yyyyMMdd + ".xlsx");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        workbook.write(response.getOutputStream());
    }

    public static <T> void exportExcel(FileOutputStream fileOutputStream, Class<T> clazz, List<T> list) throws
            IOException {
        try (Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), clazz, list)) {
            workbook.write(fileOutputStream);
        }

    }
}
