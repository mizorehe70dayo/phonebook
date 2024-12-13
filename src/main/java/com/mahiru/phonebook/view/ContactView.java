package com.mahiru.phonebook.view;

import com.mahiru.phonebook.common.Result;
import com.mahiru.phonebook.config.UserContext;
import com.mahiru.phonebook.controller.ContactController;
import com.mahiru.phonebook.controller.ContactTypeController;
import com.mahiru.phonebook.controller.PhoneTypeController;
import com.mahiru.phonebook.controller.UserController;
import com.mahiru.phonebook.model.dto.ContactDTO;
import com.mahiru.phonebook.model.po.Contact;
import com.mahiru.phonebook.model.po.ContactType;
import com.mahiru.phonebook.model.po.PhoneType;
import com.mahiru.phonebook.model.po.User;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.spi.ToolProvider.findFirst;

/**
 * @author mahiru
 * @version v1.0.0
 * @className ContactView
 * @description 联系人视图层
 * @date 2024/12/12 14:37
 **/
public class ContactView extends BaseView {
    static String USERNAME;

    /**
     * @param
     * @return void
     * @author mahiru
     * @date 2024/12/12 17:17
     * @methodName showAllContactsView
     * @description 显示所有联系人视图
     */
    public void showAllContactsView() {
        UserController userController = new UserController();
        USERNAME = userController.getUserName(UserContext.getUserId());

        while (true) {
            System.out.println("\n欢迎您：" + USERNAME);
            System.out.println("========= 联系人列表 =========");

            ContactController contactController = new ContactController();

            List<Contact> contactList = contactController.getAllContacts();
            if (contactList.isEmpty()) {
                System.out.println("\t=>联系人列表为空");
            } else {
                System.out.println("\tID\t|\t姓名\t\t|\t电话\t");
                for (Contact contact : contactList) {
                    System.out.println("\t" + contact.getId() + "\t|\t" + contact.getName() + "\t|\t" + contact.getPhone() + "\t");
                }
            }

            System.out.println("1. 添加联系人");
            System.out.println("2. 修改联系人");
            System.out.println("3. 删除联系人");
            System.out.println("4. 查询联系人");
            System.out.println("5. 导出联系人到文件");
            System.out.print("请输入选项：");

            String choice = scanner.next();
            handleContactMenuChoice(choice);
        }
    }

    /**
     * @param choice
     * @return void
     * @author mahiru
     * @date 2024/12/12 17:24
     * @methodName handleContactMenuChoice
     * @description 处理联系人菜单选项
     */
    public void handleContactMenuChoice(String choice) {
        switch (choice) {
            case "1" -> showAddContactView();
            case "2" -> showUpdateContactView();
            case "3" -> showDeleteContactView();
            case "4" -> showSearchContactView();
            case "5" -> exportContactsToExcel();
            default -> showInvalidInputMessage(choice);
        }
    }

    /**
     * @author mahiru
     * @date 2024/12/13 20:07
     * @methodName exportContactsToFile
     * @description 导出联系人到文件
     * @param
     * @return void
     */
    public void exportContactsToExcel() {
        // 定义文件路径
        String filePath = "D:\\workbench4java\\java-study\\project-study\\phonebook\\ExportFolder\\contacts_export.xlsx";

        // 确保文件夹存在
        File dir = new File("D:\\workbench4java\\java-study\\project-study\\phonebook\\ExportFolder");
        if (!dir.exists()) {
            dir.mkdirs();  // 创建文件夹
        }

        // 删除原有文件（如果存在）
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();  // 删除旧文件
        }

        // 假设这里是从数据库或其他数据源获取联系人列表
        ContactController contactController = new ContactController();
        List<ContactDTO> contacts = contactController.getAllContactsList().getData();

        try (XSSFWorkbook workbook = new XSSFWorkbook(); FileOutputStream fileOut = new FileOutputStream(filePath)) {
            // 创建一个新的表格页
            XSSFSheet sheet = workbook.createSheet("Contacts");

            // 创建表头行
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("姓名");
            headerRow.createCell(2).setCellValue("电话");
            headerRow.createCell(3).setCellValue("邮箱");
            headerRow.createCell(4).setCellValue("地址");
            headerRow.createCell(5).setCellValue("生日");
            headerRow.createCell(6).setCellValue("描述");
            headerRow.createCell(7).setCellValue("联系人类别");
            headerRow.createCell(8).setCellValue("电话类别");
            headerRow.createCell(9).setCellValue("创建时间");

            // 格式化日期时间
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // 填充联系人数据
            int rowNum = 1;
            for (ContactDTO contact : contacts) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(contact.getId());
                row.createCell(1).setCellValue(contact.getName());
                row.createCell(2).setCellValue(contact.getPhone() != null ? contact.getPhone() : "无");
                row.createCell(3).setCellValue(contact.getEmail() != null ? contact.getEmail() : "无");
                row.createCell(4).setCellValue(contact.getAddress() != null ? contact.getAddress() : "无");
                row.createCell(5).setCellValue(contact.getBirthday() != null ? contact.getBirthday().format(formatter) : "无");
                row.createCell(6).setCellValue(contact.getDescription() != null ? contact.getDescription() : "无");
                row.createCell(7).setCellValue(contact.getContactType() != null ? contact.getContactType().getName() : "无");
                row.createCell(8).setCellValue(contact.getPhoneType() != null ? contact.getPhoneType().getName() : "无");
                row.createCell(9).setCellValue(contact.getCreatedTime() != null ? contact.getCreatedTime().format(formatter) : "无");
            }

            // 设置样式（例如列宽和字体）
            for (int i = 0; i < 10; i++) {
                sheet.autoSizeColumn(i); // 自动调整列宽
            }

            // 将数据写入文件
            workbook.write(fileOut);
            System.out.println("联系人信息已导出到 Excel 文件: " + filePath);
        } catch (FileNotFoundException e) {
            System.out.println("文件未找到: " + filePath);
        } catch (IOException e) {
            System.out.println("导出联系人信息时发生错误: " + e.getMessage());
        }
    }

    /**
     * @author mahiru
     * @date 2024/12/13 20:07
     * @methodName showSearchContactView
     * @description 显示查询联系人视图
     * @param
     * @return void
     */
    public void showSearchContactView() {
        System.out.print("\n\t=>请输入关键字：");
        String keyword = scanner.next();

        ContactController contactController = new ContactController();
        Result rs = contactController.getAllContactsByNameKeyWord(keyword);

        List<Contact> contactList = (List<Contact>) rs.getData();

        if (contactList.isEmpty()) {
            System.out.println("\t=>联系人列表为空");
        } else {
            System.out.println("\tID\t|\t姓名");
            for (Contact contact : contactList) {
                System.out.println("\t" + contact.getId() + "\t|\t" + contact.getName());
            }

            System.out.print("\t=>请输入联系人ID：");
            Long id = scanner.nextLong();

            showContactInfoView(id);
        }
    }

    /**
     * @param
     * @return void
     * @author mahiru
     * @date 2024/12/12 17:25
     * @methodName showAddContactView
     * @description 显示添加联系人视图
     */
    public void showAddContactView() {
        System.out.print("\n\t=>请输入联系人姓名：");
        String name = scanner.next();
        System.out.print("\t=>请输入联系人电话：");
        String phone = scanner.next();

        ContactController contactController = new ContactController();

        Contact contactDTO = Contact.builder()
                .name(name)
                .phone(phone)
                .build();

        Result result = contactController.addContact(contactDTO);

        if (result.getCode() == 200) {
            System.out.println("\t=>联系人添加成功");
        } else {
            System.out.println("\t=>联系人添加失败：" + result.getMessage());
        }
    }

    /**
     * @param
     * @return void
     * @author mahiru
     * @date 2024/12/13 10:22
     * @methodName showDeleteContactView
     * @description 显示删除联系人视图
     */
    public void showDeleteContactView() {
        System.out.print("\n\t=>请输入联系人ID：");
        Long id = scanner.nextLong();

        ContactController contactController = new ContactController();
        List<Contact> contactList = contactController.getAllContacts();

        Contact targetContact = (Contact) contactList.stream()
                .filter(contact -> contact.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (targetContact == null) {
            System.out.println("\t=> 联系人ID不存在");
            return;
        }

        System.out.println("\t=>确认删除联系人：" + targetContact.getName() + "(" + targetContact.getPhone() + ")？(y/n)：");
        String confirm = scanner.next();
        if ("n".equals(confirm)) {
            return;
        } else if (!"y".equals(confirm)) {
            showInvalidInputMessage(confirm);
            return;
        }

        Result result = contactController.deleteContact(id);
        if (result.getCode() == 200) {
            System.out.println("\t=> 联系人删除成功");
        } else {
            System.out.println("\t=> 联系人删除失败：" + result.getMessage());
        }
    }

    /**
     * @param
     * @return void
     * @author mahiru
     * @date 2024/12/13 11:09
     * @methodName showUpdateContactView
     * @description 显示更新联系人视图
     */
    public void showUpdateContactView() {
        System.out.print("\n\t=>请输入联系人ID：");
        Long id = scanner.nextLong();

        // 获取联系人详细信息
        ContactDTO contact = showContactInfoView(id);
        if (contact == null) {
            return;
        }

        // 显示可修改的字段及序号
        Map<Integer, String> fields = Map.of(
                1, "姓名",
                2, "电话",
                3, "邮箱",
                4, "生日",
                5, "地址",
                6, "备注",
                7, "联系人类别",
                8, "电话类别"
        );

        // 用户输入修改的字段编号（例如：1,3,4）
        System.out.print("\n\t请输入你想修改的字段序号：");
        String input = scanner.next();
        String[] fieldNumbers = input.split("");

        // 进行修改
        for (String fieldNumber : fieldNumbers) {
            int index = Integer.parseInt(fieldNumber.trim());
            if (fields.containsKey(index)) {
                modifyField(contact, index);
            } else {
                System.out.println("\t无效的序号：" + index);
            }
        }

        // 展示修改后的联系人信息（直接从内存中获取，不再重新查询数据库）
        System.out.println("\n\t修改后的信息：");
        displayContactInfo(contact);

        // 用户确认修改
        System.out.print("\n\t确认修改吗？(Y/N)：");
        String confirmation = scanner.nextLine();
        if (!"Y".equalsIgnoreCase(confirmation)) {
            showInvalidInputMessage(confirmation);
            return;
        }

        ContactController contactController = new ContactController();
        Result rs = contactController.updateContact(contact);

        if (rs.getCode() == 200) {
            System.out.println("\t=> 联系人修改成功");
        } else {
            System.out.println("\t=> 联系人修改失败：" + rs.getMessage());
        }
    }

    /**
     * @param contact
     * @param fieldIndex
     * @return void
     * @author mahiru
     * @date 2024/12/13 16:54
     * @methodName modifyField
     * @description 修改字段
     */
    private void modifyField(ContactDTO contact, int fieldIndex) {
        switch (fieldIndex) {
            case 1:
                System.out.print("\n\t请输入新的姓名：");
                String name = scanner.nextLine();
                contact.setName(name);
                break;
            case 2:
                System.out.print("\n\t请输入新的电话：");
                String phone = scanner.nextLine();
                contact.setPhone(phone);
                break;
            case 3:
                System.out.print("\n\t请输入新的邮箱：");
                String email = scanner.nextLine();
                contact.setEmail(email);
                break;
            case 4:
                System.out.print("\n\t请输入新的生日（YYYY-MM-DD）：");
                String birthday = scanner.nextLine();
                contact.setBirthday(LocalDateTime.parse(birthday + "T00:00:00"));
                break;
            case 5:
                System.out.print("\n\t请输入新的地址：");
                String address = scanner.nextLine();
                contact.setAddress(address);
                break;
            case 6:
                System.out.print("\n\t请输入新的备注：");
                String description = scanner.nextLine();
                contact.setDescription(description);
                break;
            case 7:
                modifyContactType(contact);
                break;
            case 8:
                modifyPhoneType(contact);
                break;
            default:
                System.out.println("\t无效的字段选择！");
        }
    }

    /**
     * @param contact
     * @return void
     * @author mahiru
     * @date 2024/12/13 18:07
     * @methodName modifyContactType
     * @description 修改联系人类别
     */
    private void modifyContactType(ContactDTO contact) {
        ContactTypeController contactTypeController = new ContactTypeController();
        List<ContactType> contactTypes = contactTypeController.fetchAllContactTypes();
        System.out.println("\n\t可选联系人类别：");
        for (ContactType contactType : contactTypes) {
            System.out.println("\t" + contactType.getId() + " (" + contactType.getName() + ")");
        }

        System.out.print("\n\t请输入联系人类别的ID：");
        int contactTypeId = scanner.nextInt();
        scanner.nextLine();  // consume the newline

        Result rs = contactTypeController.findContactTypeById(contactTypeId);

        if (rs.getCode() != 200) {
            System.out.println("\t无效的联系人类别ID：" + contactTypeId);
            return;
        }
        System.out.println("contactType = " + rs.getData().toString());
        contact.setContactType((ContactType) rs.getData());
    }

    /**
     * @param contact
     * @return void
     * @author mahiru
     * @date 2024/12/13 18:07
     * @methodName modifyPhoneType
     * @description 修改电话类别
     */
    private void modifyPhoneType(ContactDTO contact) {
        PhoneTypeController phoneTypeController = new PhoneTypeController();
        List<PhoneType> phoneTypes = phoneTypeController.fetchAllPhoneTypes();
        System.out.println("\n\t可选电话类别：");
        for (PhoneType phoneType : phoneTypes) {
            System.out.println("\t" + phoneType.getId() + " (" + phoneType.getName() + ")");
        }

        System.out.print("\n\t请输入电话类别的ID：");
        int phoneTypeId = scanner.nextInt();
        scanner.nextLine();  // consume the newline

        Result rs = phoneTypeController.findPhoneTypeById(phoneTypeId);

        if (rs.getCode() != 200) {
            System.out.println("\t无效的电话类别ID：" + phoneTypeId);
            return;
        }
        contact.setPhoneType((PhoneType) rs.getData());
    }


    /**
     * @param contact
     * @return void
     * @author mahiru
     * @date 2024/12/13 16:49
     * @methodName displayContactInfo
     * @description 显示修改后联系人信息
     */
    public void displayContactInfo(ContactDTO contact) {
        System.out.println("\n\tID：" + contact.getId());
        System.out.println("\t姓名：" + contact.getName());
        System.out.println("\t电话：" + contact.getPhone());
        System.out.println("\t邮件：" + contact.getEmail());
        System.out.println("\t生日：" + (contact.getBirthday() != null ? contact.getBirthday() : "无"));
        System.out.println("\t地址：" + contact.getAddress());
        System.out.println("\t备注：" + (contact.getDescription() != null ? contact.getDescription() : "无"));
        System.out.println("\t联系人类别：" + (contact.getContactType() != null ? contact.getContactType().getName() : "未分类"));
        System.out.println("\t电话类别：" + (contact.getPhoneType() != null ? contact.getPhoneType().getName() : "未分类"));
    }

    /**
     * @param
     * @return ContactDTO 联系人信息
     * @author mahiru
     * @date 2024/12/13 11:15
     * @methodName showContactInfoView
     * @description 显示联系人信息视图
     */
    public ContactDTO showContactInfoView(Long id) {
        ContactController contactController = new ContactController();
        ContactDTO contactDTO = contactController.getContactById(id);

        if (contactDTO == null) {
            System.out.println("\t=>联系人不存在");
            return null;
        }

        // 显示当前联系人信息
        System.out.println("\n\t当前联系人信息：");
        System.out.println("\tID：" + contactDTO.getId());
        System.out.println("\t1.姓名：" + contactDTO.getName());
        System.out.println("\t2.电话：" + contactDTO.getPhone());
        System.out.println("\t3.邮件：" + contactDTO.getEmail());
        System.out.println("\t4.生日：" + (contactDTO.getBirthday() != null ? contactDTO.getBirthday() : "无"));
        System.out.println("\t5.地址：" + contactDTO.getAddress());
        System.out.println("\t6.备注：" + (contactDTO.getDescription() != null ? contactDTO.getDescription() : "无"));

        // 处理类别字段为空的情况
        System.out.println("\t7.联系人类别：" + (contactDTO.getContactType() != null ? contactDTO.getContactType().getName() : "未分类"));
        System.out.println("\t8.电话类别：" + (contactDTO.getPhoneType() != null ? contactDTO.getPhoneType().getName() : "未分类"));

        System.out.println("\t创建时间：" + contactDTO.getCreatedTime());

        return contactDTO;
    }

}
