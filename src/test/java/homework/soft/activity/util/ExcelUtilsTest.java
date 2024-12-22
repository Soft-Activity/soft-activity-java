package homework.soft.activity.util;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import homework.soft.activity.exception.HttpErrorException;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExcelUtilsTest {

    @Mock
    private HttpServletResponse response;

    // 测试用的实体类
    static class TestEntity {
        private String name;
        private Integer age;

        public TestEntity(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReadExcelFromFile() {
        // 创建测试用的Excel文件
        File tempFile = createTempExcelFile();
        
        // 测试读取Excel文件
        List<TestEntity> result = ExcelUtils.readExcel(tempFile, TestEntity.class);
        
        assertNotNull(result);
        // 根据实际数据验证结果
        
        // 清理测试文件
        tempFile.delete();
    }

    @Test
    void testReadExcelFromMultipartFile() {
        // 创建模拟的MultipartFile
        MockMultipartFile file = new MockMultipartFile(
            "test.xlsx",
            "test.xlsx",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "test data".getBytes()
        );

        // 测试可能抛出异常的情况
        assertThrows(HttpErrorException.class, () -> {
            ExcelUtils.readExcel(file, TestEntity.class);
        });
    }

    @Test
    void testReadExcelWithParams() {
        MockMultipartFile file = new MockMultipartFile(
            "test.xlsx",
            "test.xlsx",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "test data".getBytes()
        );

        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);

        assertThrows(HttpErrorException.class, () -> {
            ExcelUtils.readExcel(file, TestEntity.class, params);
        });
    }

    @Test
    void testDownloadExcel() throws IOException {
        List<TestEntity> testData = new ArrayList<>();
        testData.add(new TestEntity("Test Name", 25));

        // 配置mock response
        when(response.getOutputStream()).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            ExcelUtils.downloadExcel(response, TestEntity.class, testData, "test");
        });
    }

    @Test
    void testExportExcel() throws IOException {
        List<TestEntity> testData = new ArrayList<>();
        testData.add(new TestEntity("Test Name", 25));

        // 创建临时文件用于测试导出
        File tempFile = File.createTempFile("test", ".xlsx");
        FileOutputStream fos = new FileOutputStream(tempFile);

        ExcelUtils.exportExcel(fos, TestEntity.class, testData);

        assertTrue(tempFile.exists());
        assertTrue(tempFile.length() > 0);

        // 清理测试文件
        tempFile.delete();
    }

    private File createTempExcelFile() {
        try {
            File tempFile = File.createTempFile("test", ".xlsx");
            List<TestEntity> testData = new ArrayList<>();
            testData.add(new TestEntity("Test Name", 25));
            
            FileOutputStream fos = new FileOutputStream(tempFile);
            ExcelUtils.exportExcel(fos, TestEntity.class, testData);
            fos.close();
            
            return tempFile;
        } catch (IOException e) {
            throw new RuntimeException("Failed to create test excel file", e);
        }
    }
} 