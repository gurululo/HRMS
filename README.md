# 企业人事管理系统 (HRMS)

一套基于传统 Java Web 单体架构开发的企业人事管理系统，采用 Spring MVC + JSP + Bootstrap 构建，使用本地文件进行业务数据持久化存储。

---

## 一、技术架构与技术选型

- **核心语言**：Java (JDK 1.8 规范)
- **后端框架**：Spring Framework 4.3.25.RELEASE、Spring MVC
- **持久化方案**：本地 JSON 文件存储，使用 Jackson 2.8 进行序列化与反序列化
- **前端页面**：JSP、JSTL 1.2
- **前端UI与脚本**：Bootstrap 3.3.7、jQuery 1.12.4
- **安全与加密**：SHA-256 + 随机 Salt 哈希存储
- **日志组件**：SLF4J + Log4j 1.2
- **构建工具**：Apache Maven
- **部署容器**：Apache Tomcat 8.5.x（支持标准 WAR 包部署及嵌入式插件运行）

---

## 二、业务功能模块

系统包含五个基础人事管理模块：

1. **管理员登录与安全退出**
   - 单系统管理员账号机制。
   - 采用加盐哈希（SHA-256）存储密码，保障凭证存储安全。
   - 基于 Spring MVC HandlerInterceptor 拦截器进行会话鉴权。

2. **员工管理**
   - 核心字段：员工编号（工号）、姓名、所属部门、担任岗位。
   - 业务功能：员工信息录入、编辑修改、删除校验、多条件组合检索与列表展示。
   - 业务约束：员工工号全局唯一；删除员工前校验名下是否存在有效劳动合同。

3. **部门管理**
   - 核心字段：部门名称、上级部门。
   - 业务功能：部门新增、信息修改、删除控制、表格列表展示以及树形层级架构展示。
   - 业务约束：删除部门前校验是否存在下属子部门或关联在职员工；禁止将上级部门设置为自身或其子孙节点。

4. **岗位管理**
   - 核心字段：岗位名称。
   - 业务功能：岗位新增、岗位名称编辑、删除校验、岗位名称关键字检索与列表展示。
   - 业务约束：岗位名称不可重复；删除岗位前校验是否存在任职员工。

5. **合同管理**
   - 核心字段：关联员工、合同开始日期、合同结束日期。
   - 业务功能：劳动合同登记、合同信息修改、合同解约/删除、关键字检索。
   - 业务约束：必须关联系统中已存在的有效员工；合同开始日期不得晚于合同结束日期。

---

## 三、项目目录结构

```text
hrms-system/
├── pom.xml                                   # Maven 项目依赖及构建配置文件
├── README.md                                 # 项目说明及部署文档
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── hrms/
│   │   │           ├── common/               # 基础公共类（统一返回结果 ResultMsg 等）
│   │   │           ├── controller/           # Spring MVC 控制器层
│   │   │           ├── dao/                  # 数据访问接口及本地文件实现类
│   │   │           │   └── impl/
│   │   │           ├── entity/               # 业务实体类（员工、部门、岗位、合同、管理员）
│   │   │           ├── interceptor/          # 登录会话拦截器
│   │   │           ├── service/              # 业务服务接口及实现类
│   │   │           │   └── impl/
│   │   │           └── util/                 # 工具类（数据文件管理、密码加密等）
│   │   ├── resources/
│   │   │   ├── applicationContext.xml        # Spring 核心容器配置文件
│   │   │   ├── spring-mvc.xml                # Spring MVC 视图解析与拦截器配置
│   │   │   ├── hrms-config.properties        # 系统运行环境属性配置（数据文件存储路径等）
│   │   │   ├── log4j.properties              # 系统日志输出配置
│   │   │   └── data/                         # 初始数据模板文件（自动初始化至存储目录）
│   │   │       ├── admin.json
│   │   │       ├── departments.json
│   │   │       ├── positions.json
│   │   │       ├── employees.json
│   │   │       └── contracts.json
│   │   └── webapp/
│   │       ├── index.jsp                     # 欢迎引导页面
│   │       ├── static/                       # 静态资源文件（离线本地化）
│   │       │   ├── css/                      # Bootstrap 3 与自定义样式
│   │       │   ├── js/                       # jQuery 1.12.4 与 Bootstrap JS
│   │       │   └── fonts/                    # Glyphicons 字体文件
│   │       └── WEB-INF/
│   │           ├── web.xml                   # Web 应用程序部署描述符
│   │           └── views/                    # JSP 页面视图层
│   │               ├── common/               # 公共页头、导航侧边栏、页脚
│   │               ├── login.jsp             # 管理员登录页面
│   │               ├── index.jsp             # 系统首页看板
│   │               ├── employee/             # 员工列表与表单
│   │               ├── department/           # 部门列表、表单与树形视图
│   │               ├── position/             # 岗位列表与表单
│   │               └── contract/             # 合同列表与表单
```

---

## 四、数据持久化与存储说明

1. 系统不依赖外部关系型或非关系型数据库，所有业务数据持久化保存在本地 JSON 格式文件中。
2. 存储路径通过 `hrms-config.properties` 中的 `data.storage.path` 进行配置，默认设置为 `./data`（相对于运行时工作目录）。
3. 系统首次启动时，若检测到目标存储目录下缺少相关文件，将自动从类路径资源中载入初始内置数据。
4. 所有数据更新采用同步写入保护，服务停止与重启后数据完整保留。

---

## 五、环境要求与部署运行说明

### 1. 环境准备
- **操作系统**：Windows / Linux / macOS
- **JDK**：Java SE 8 (1.8) 或更高版本
- **Maven**：Apache Maven 3.3.9 及以上
- **Tomcat**：Apache Tomcat 8.5.x

### 2. 编译与打包
在项目根目录下执行以下 Maven 命令进行编译并构建 WAR 包：
```bash
mvn clean package
```
执行完毕后，将在 `target/` 目录下生成 `hrms.war`。

### 3. 部署与运行

#### 方式一：部署至外部 Tomcat 8.5
1. 将 `target/hrms.war` 复制到 Tomcat 的 `webapps/` 目录下；
2. 启动 Tomcat 服务：
   - Windows：运行 `bin/startup.bat`
   - Linux：运行 `bin/startup.sh`
3. 访问系统：打开浏览器，访问 `http://localhost:8080/hrms`。

#### 方式二：使用 Maven Tomcat 插件快速启动
在开发或测试环境下，可直接在项目根目录运行：
```bash
mvn tomcat7:run
```
启动成功后，访问 `http://localhost:8080/hrms` 即可进入系统。

---

## 六、初始管理员凭据

- **登录地址**：`http://localhost:8080/hrms/login`
- **用户名**：`admin`
- **默认密码**：`admin123`
