package cn.codeyang.course.service;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;

public class A {
    public static String convertLessonNumber(String lesson) {
        // 获取"第X节"中的数字部分
        String numberStr = lesson.replaceAll("[^\\d]", "");

        // 将数字部分转换为整数
        int number = Integer.parseInt(numberStr);

        // 返回转换后的结果
        return String.valueOf(number);
    }

    public static void main(String[] args) {
        // 测试
        String lesson1 = "第一节";
        String lesson2 = "第二节";

        System.out.println(convertLessonNumber(lesson1)); // 输出 "1"
        System.out.println(convertLessonNumber(lesson2)); // 输出 "2"
    }

    private static void getData(Sheet sheet) {
        // 迭代每一行，从第三行开始（索引为2）
        for (int rowIndex = 2; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            // 如果行为空，则跳过
            if (row == null) continue;
            // 获取第二列的数据：第几节
            Cell timeCell = row.getCell(1);
            String time = timeCell != null ? timeCell.getStringCellValue() : "";
            System.out.println(time);
            // 迭代每一列，从第二列开始（索引为1）
            for (int colIndex = 2; colIndex < row.getLastCellNum(); colIndex++) {
                Cell cell = row.getCell(colIndex);
                if (cell != null) {
                    // 获取第二行的星期
                    Row headerRow = sheet.getRow(1);
                    Cell headerCell = headerRow.getCell(colIndex);
                    String week = headerCell != null ? headerCell.getStringCellValue() : "";

                    // 获取课程信息并输出
                    String course = cell.getStringCellValue();
                    System.out.println("时间: " + time + ", : " + course);
                }
            }
        }
    }


}
