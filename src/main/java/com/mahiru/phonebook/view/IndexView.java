package com.mahiru.phonebook.view;

import com.mahiru.phonebook.common.Result;
import com.mahiru.phonebook.controller.UserController;
import com.mahiru.phonebook.model.po.User;
import lombok.AllArgsConstructor;

/**
 * @author mahiru
 * @version v1.0.0
 * @className IndexView
 * @description index视图层
 * @date 2024/12/11 20:09
 **/
@AllArgsConstructor
public class IndexView extends BaseView {
    /**
     * @param
     * @return void
     * @author mahiru
     * @date 2024/12/11 21:50
     * @methodName showLoginView
     * @description 显示登录界面
     */
    public void showLoginView() {
        UserController userController = new UserController();

        System.out.print("\n\t=>请输入用户名：");
        String username = scanner.next();
        System.out.print("\t=>请输入密码：");
        String password = scanner.next();

        User userDTO = User.builder()
                .username(username)
                .password(password)
                .build();

        Result result = userController.login(userDTO);

        if (result.getCode() == 200) {
            System.out.println("\t=>登录成功...");

            new ContactView().showAllContactsView();
        } else {
            System.out.println("\t=>登录失败 -> " + result.getMessage());
        }
    }

    /**
     * @param
     * @return void
     * @author mahiru
     * @date 2024/12/11 21:51
     * @methodName showRegisterView
     * @description 显示注册界面
     */
    public void showRegisterView() {
        UserController userController = new UserController();


        System.out.print("\n\t=>请输入用户名：");
        String username = scanner.next();
        System.out.print("\t=>请输入密码：");
        String password = scanner.next();
        System.out.print("\t=>请确认密码：");
        String confirmPassword = scanner.next();

        if (!password.equals(confirmPassword)) {
            System.out.println("\t=>注册失败 -> 两次输入密码不一致");
            return;
        }

        User userDTO = User.builder()
                .username(username)
                .password(password)
                .build();

        Result result = userController.register(userDTO);

        if (result.getCode() == 200) {
            System.out.println("\t=>注册成功...");
        } else {
            System.out.println("\t=>注册失败 -> " + result.getMessage());
        }
    }

    /**
     * @param
     * @return void
     * @author mahiru
     * @date 2024/12/11 21:57
     * @methodName exitApplication
     * @description 退出应用
     */
    public void exitApplication() {
        System.out.print("\n\t=>是否退出应用（Y/N）：");

        String choice = scanner.next();
        if (choice.equalsIgnoreCase("Y")) {
            showLoading();
            System.out.println("\t=>退出成功...");
            System.exit(0);
        }

        if (!"N".equalsIgnoreCase(choice) && !"Y".equalsIgnoreCase(choice)) {
            showInvalidInputMessage(choice);
        }
    }

    /**
     * @author mahiru
     * @date 2024/12/12 10:30
     * @methodName handleChoice
     * @description 处理主页用户选择
     * @param choice 用户选择
     * @return void
     */
    public void handleMainMenuChoice(String choice) {
        switch (choice) {
            case "1" -> showLoginView();
            case "2" -> showRegisterView();
            case "3" -> exitApplication();
            default -> showInvalidInputMessage(choice);
        }
    }

    /**
     * @param
     * @return void
     * @author mahiru
     * @date 2024/12/11 20:31
     * @methodName showIndexMenu
     * @description 展示index界面
     */
    public void showIndexMenu() {
        while (true) {
            System.out.println("\n===== 欢迎使用个人电话簿应用 =====");
            System.out.println("1. 登录");
            System.out.println("2. 注册");
            System.out.println("3. 退出");
            System.out.print("请输入选项：");

            String choice = scanner.next();

            handleMainMenuChoice(choice);
        }
    }

    /**
     * @param
     * @return void
     * @author mahiru
     * @date 2024/12/11 20:30
     * @methodName run
     * @description 启动方法
     */
    public void run() {
        showLoading();
        showIndexMenu();
    }
}
