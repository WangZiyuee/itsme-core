package me.topits;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author Wang Ziyue
 * @since 2020/5/19 9:34
 */
public class CodeGeneratorTester {


    public static void tableGenerator(List<String> tableList, String moduleName) {
        String databaseUrl = "jdbc:mysql://127.0.0.1:3311/mall_dev?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8";
        String databaseUser = "root";
        String databasePassword = "123123123";
        String author = "Wang Ziyue";
        String projectPath = "D:\\work-core\\itsme-core\\mybatis-generator";
        String packageName = "me.topits";

        for (String tableName : tableList) {
            CodeGenerator codeGenerator = new CodeGenerator()
                    .setDatabaseUrl(databaseUrl)
                    .setDatabaseUser(databaseUser)
                    .setDatabasePassword(databasePassword)
                    .setAuthor(author)
                    .setProjectPath(projectPath)
                    .setPackageName(packageName)
                    .setTableName(tableName)
                    .setModuleName(moduleName);
            codeGenerator.execute();
        }
    }

    public static void main(String[] args) {
        List<String> tableList = Lists.newArrayList();
        tableList.add("aa_menu");
        tableGenerator(tableList, "base");
    }
}
