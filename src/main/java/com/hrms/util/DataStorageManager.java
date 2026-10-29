package com.hrms.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 本地JSON文件持久化存储管理器
 */
@Component
public class DataStorageManager implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(DataStorageManager.class);

    @Value("${data.storage.path:./data}")
    private String dataStoragePath;

    private File dataDir;
    private ObjectMapper objectMapper;

    @Override
    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        init();
    }

    public void init() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        resolveDataDirectory();
        initializeDefaultDataFiles();
    }

    /**
     * 解析并创建数据存储目录
     */
    private void resolveDataDirectory() {
        File dir = new File(dataStoragePath);
        if (!dir.isAbsolute()) {
            dir = new File(System.getProperty("user.dir"), dataStoragePath);
        }
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            logger.info("创建数据存储目录: {}, 结果: {}", dir.getAbsolutePath(), created);
        }
        this.dataDir = dir;
        logger.info("数据文件持久化目录就绪: {}", dataDir.getAbsolutePath());
    }

    /**
     * 初始化默认数据文件（如果不存在则从类路径模板复制）
     */
    private void initializeDefaultDataFiles() {
        String[] files = new String[]{
                "admin.json",
                "departments.json",
                "positions.json",
                "employees.json",
                "contracts.json"
        };

        for (String filename : files) {
            File targetFile = new File(dataDir, filename);
            if (!targetFile.exists()) {
                try {
                    ClassPathResource resource = new ClassPathResource("data/" + filename);
                    if (resource.exists()) {
                        InputStream in = resource.getInputStream();
                        FileOutputStream out = new FileOutputStream(targetFile);
                        IOUtils.copy(in, out);
                        IOUtils.closeQuietly(in);
                        IOUtils.closeQuietly(out);
                        logger.info("初始化数据文件成功: {}", targetFile.getName());
                    } else {
                        // 创建空数组或默认对象
                        if ("admin.json".equals(filename)) {
                            targetFile.createNewFile();
                        } else {
                            objectMapper.writeValue(targetFile, new ArrayList<Object>());
                        }
                    }
                } catch (Exception e) {
                    logger.error("初始化数据文件失败: " + filename, e);
                }
            }
        }
    }

    /**
     * 获取指定文件名的数据文件对象
     */
    public File getFile(String filename) {
        return new File(dataDir, filename);
    }

    /**
     * 读取列表数据
     */
    public synchronized <T> List<T> loadList(String filename, Class<T> elementType) {
        File file = getFile(filename);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<T>();
        }
        try (InputStream in = new FileInputStream(file)) {
            return objectMapper.readValue(in, objectMapper.getTypeFactory().constructCollectionType(List.class, elementType));
        } catch (Exception e) {
            logger.error("读取数据文件失败: " + filename, e);
            return new ArrayList<T>();
        }
    }

    /**
     * 保存列表数据
     */
    public synchronized <T> void saveList(String filename, List<T> list) {
        File file = getFile(filename);
        try (FileOutputStream out = new FileOutputStream(file)) {
            objectMapper.writeValue(out, list);
        } catch (Exception e) {
            logger.error("写入数据文件失败: " + filename, e);
            throw new RuntimeException("持久化写入失败: " + filename, e);
        }
    }

    /**
     * 读取单个对象
     */
    public synchronized <T> T loadObject(String filename, Class<T> clazz) {
        File file = getFile(filename);
        if (!file.exists() || file.length() == 0) {
            return null;
        }
        try (InputStream in = new FileInputStream(file)) {
            return objectMapper.readValue(in, clazz);
        } catch (Exception e) {
            logger.error("读取单对象数据文件失败: " + filename, e);
            return null;
        }
    }

    /**
     * 保存单个对象
     */
    public synchronized <T> void saveObject(String filename, T object) {
        File file = getFile(filename);
        try (FileOutputStream out = new FileOutputStream(file)) {
            objectMapper.writeValue(out, object);
        } catch (Exception e) {
            logger.error("写入单对象数据文件失败: " + filename, e);
            throw new RuntimeException("持久化写入失败: " + filename, e);
        }
    }

    /**
     * 生成下一个自增ID
     */
    public synchronized long generateNextId(String filename) {
        List<Map<String, Object>> list = loadList(filename, (Class<Map<String, Object>>) (Class<?>) Map.class);
        long maxId = 0L;
        for (Map<String, Object> map : list) {
            Object idObj = map.get("id");
            if (idObj instanceof Number) {
                long currentId = ((Number) idObj).longValue();
                if (currentId > maxId) {
                    maxId = currentId;
                }
            }
        }
        return maxId + 1;
    }
}
