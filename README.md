# itsme-core

### itsme-api

```xml
<dependency>
    <groupId>me.topits</groupId>
    <artifactId>itsme-api</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### itsme-tools

```xml
<dependency>
    <groupId>me.topits</groupId>
    <artifactId>itsme-tools</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

###  mybatis-generator

```xml
<dependency>
    <groupId>me.topits</groupId>
    <artifactId>mybatis-generator</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

```java
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author Wang Ziyue
 * @date 2020/5/19 9:34
 */
public class MybatisGeneratorTester {

    public static void tableGenerator(List<String> tableList, String moduleName) {
        String databaseUrl = "jdbc:mysql://127.0.0.1:3306/xxx?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8";
        String databaseUser = "root";
        String databasePassword = "";
        String author = "";
        String projectPath = "";
        String packageName = "";

        for (String tableName : tableList) {
            MybatisGeneratorConfig mybatisGeneratorConfig = new MybatisGeneratorConfig()
                    .setDatabaseUrl(databaseUrl)
                    .setDatabaseUser(databaseUser)
                    .setDatabasePassword(databasePassword)
                    .setAuthor(author)
                    .setProjectPath(projectPath)
                    .setPackageName(packageName)
                    .setTableName(tableName)
                    .setModuleName(moduleName);
            mybatisGeneratorConfig.execute();
        }
    }

    public static void main(String[] args) {
        List<String> tableList = Lists.newArrayList();
        tableList.add("");
        tableGenerator(tableList, "base");
    }
}

```