package homework.soft.activity.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import homework.soft.activity.util.HttpRequestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AIHelperUtilsTest {

    private HttpResponse mockResponse;
    private HttpEntity mockEntity;

    @BeforeEach
    void setUp() throws IOException {
        // 设置模拟对象
        mockResponse = mock(HttpResponse.class);
        mockEntity = mock(HttpEntity.class);
        when(mockResponse.getEntity()).thenReturn(mockEntity);
    }

    @Test
    void address_normalResponse_returnsAIReply() throws IOException {
        // Prepare test data
        String mockJsonResponse = "{\"result\": \"This is a test reply\"}";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
            mockJsonResponse.getBytes(StandardCharsets.UTF_8)
        );
        when(mockEntity.getContent()).thenReturn(inputStream);

        try (MockedStatic<HttpRequestUtils> mockedStatic = Mockito.mockStatic(HttpRequestUtils.class)) {
            mockedStatic.when(() -> HttpRequestUtils.request(any(), any()))
                    .thenReturn(mockResponse);

            // Execute test
            String result = homework.web.util.AIHelperUtils.address("test question");

            // Verify result
            assertEquals("This is a test reply", result);
        }
    }

    @Test
    void address_requestException_returnsErrorMessage() throws IOException {
        // Mock exception scenario
        when(mockEntity.getContent()).thenThrow(new IOException("Network error"));

        try (MockedStatic<HttpRequestUtils> mockedStatic = Mockito.mockStatic(HttpRequestUtils.class)) {
            mockedStatic.when(() -> HttpRequestUtils.request(any(), any()))
                    .thenReturn(mockResponse);

            // Execute test
            String result = homework.web.util.AIHelperUtils.address("test question");

            // Verify result
            assertEquals("AI Analysis Failed", result);
        }
    }

    @Test
    void address_emptyContent_returnsPromptMessage() throws IOException {
        // Prepare test data
        String mockJsonResponse = "{\"result\": \"Please provide a specific question\"}";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
            mockJsonResponse.getBytes(StandardCharsets.UTF_8)
        );
        when(mockEntity.getContent()).thenReturn(inputStream);

        try (MockedStatic<HttpRequestUtils> mockedStatic = Mockito.mockStatic(HttpRequestUtils.class)) {
            mockedStatic.when(() -> HttpRequestUtils.request(any(), any()))
                    .thenReturn(mockResponse);

            // Execute test
            String result = homework.web.util.AIHelperUtils.address("");

            // Verify result
            assertEquals("Please provide a specific question", result);
        }
    }
} 