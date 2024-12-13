package com.mahiru.phonebook.view;

import java.util.Scanner;

/**
 * @author mahiru
 * @version v1.0.0
 * @className BaseView
 * @description 视图层公共方法
 * @date 2024/12/11 20:28
 **/
public class BaseView {
    public Scanner scanner = new Scanner(System.in);

    /**
     * @param
     * @return void
     * @author mahiru
     * @date 2024/12/11 20:43
     * @methodName showInvalidInputMessage
     * @description 输入无效提示界面
     */
    public void showInvalidInputMessage(String in) {
        System.out.println("\t=>无效的选项( " + in + " )!");
    }

    /**
     * @param
     * @return void
     * @author mahiru
     * @date 2024/12/11 20:27
     * @methodName run
     * @description 加载进度条
     */
    public void showLoading() {
        int progress = 0;
        int totalBlocks = 20;

        while (progress <= totalBlocks) {
            System.out.print("\r[");
            for (int i = 0; i < totalBlocks; i++) {
                if (i < progress) {
                    System.out.print("■");
                } else {
                    System.out.print("□");
                }
            }
            System.out.print("] " + (progress * 100 / totalBlocks) + "%");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progress++;
        }

        System.out.println();
    }
}
