package org.example.doanki2.email;

import org.example.doanki2.entity.Order_Details;
import org.example.doanki2.entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private AdminEmail adminEmailProperties;

    @Autowired
    private AppUrlApiEmail appProperties; // Tạo lớp để lưu URL của ứng dụng

    @Async
    public void sendOrderConfirmationEmail(Orders order) {
        String subject = "Đặt hàng thành công";
        String orderDetailsUrl = appProperties.getBaseUrl() + order.getOrder_id(); // URL để xem chi tiết đơn hàng

        // Chuyển đổi chi tiết đơn hàng thành chuỗi
        StringBuilder orderDetailsBuilder = new StringBuilder();
        List<Order_Details> orderDetails = order.getOrderDetails();

        if (orderDetails != null && !orderDetails.isEmpty()) {
            for (Order_Details detail : orderDetails) {
                if (detail.getProducts() != null) {
                    orderDetailsBuilder.append(String.format(
                            "Sản phẩm: %s, Số lượng: %d, Giá: %s\n",
                            detail.getProducts().getProduct_name(),
                            detail.getQuantity(),
                            detail.getProducts().getPrice()
                    ));
                } else {
                    orderDetailsBuilder.append(String.format(
                            "Sản phẩm không xác định, Số lượng: %d\n",
                            detail.getQuantity()
                    ));
                }
            }
        } else {
            orderDetailsBuilder.append("Không có chi tiết đơn hàng.");
        }

        String text = String.format(
                "Chào %s %s,\n\nCảm ơn bạn đã đặt hàng. Đơn hàng của bạn (ID: %d) đã được xác nhận. " +
                        "Chi tiết đơn hàng:\n" +
                        "Tổng số tiền: %s\n" +
                        "Địa chỉ: %s\n" +
                        "Số điện thoại: %s\n" +
                        "Email: %s\n\n" +
                        "Bạn có thể xem chi tiết đơn hàng tại: %s\n" + // Thêm liên kết để xem chi tiết đơn hàng
                        "Trân trọng,\nĐội ngũ của chúng tôi",
                order.getFirst_name(),
                order.getLast_name(),
                order.getOrder_id(),
                order.getTotal_amount().toString(),
                order.getAddress(),
                order.getPhone_number(),
                order.getEmail(),
                orderDetailsUrl // Thêm liên kết ở đây
        );

        // Gửi email cho người dùng
        sendEmail(order.getEmail(), subject, text);

        // Tạo nội dung email cho admin
        String adminSubject = "Thông báo đơn hàng mới";
        String adminText = String.format(
                "Một đơn hàng mới đã được đặt!\n\n" +
                        "Chi tiết đơn hàng:\n" +
                        "ID Đơn hàng: %d\n" +
                        "Người đặt hàng: %s %s\n" +
                        "Tổng số tiền: %s\n" +
                        "Địa chỉ: %s\n" +
                        "Số điện thoại: %s\n" +
                        "Email: %s\n\n" +
                        "Xem chi tiết đơn hàng tại: %s\n" + // Thêm liên kết để xem chi tiết đơn hàng
                        "Trân trọng,\nĐội ngũ của chúng tôi",
                order.getOrder_id(),
                order.getFirst_name(),
                order.getLast_name(),
                order.getTotal_amount().toString(),
                order.getAddress(),
                order.getPhone_number(),
                order.getEmail(),
                orderDetailsUrl // Thêm liên kết ở đây
        );

        // Gửi email cho admin
        sendEmail(adminEmailProperties.getAdminEmail(), adminSubject, adminText);
    }

    @Async
    public void sendOrderStatusUpdateEmail(Orders order) {
        String subject = "Cập nhật trạng thái đơn hàng";
        String orderDetailsUrl = appProperties.getBaseUrl() + order.getOrder_id();

        // Tạo nội dung email thông báo trạng thái mới
        String text = String.format(
                "Chào %s %s,\n\nTrạng thái đơn hàng của bạn (ID: %d) đã được cập nhật thành: %s.\n" +
                        "Chi tiết đơn hàng:\n" +
                        "Tổng số tiền: %s\n" +
                        "Địa chỉ: %s\n" +
                        "Số điện thoại: %s\n" +
                        "Email: %s\n\n" +
                        "Xem chi tiết đơn hàng tại: %s\n" +
                        "Trân trọng,\nĐội ngũ của chúng tôi",
                order.getFirst_name(),
                order.getLast_name(),
                order.getOrder_id(),
                order.getStatus(),
                order.getTotal_amount().toString(),
                order.getAddress(),
                order.getPhone_number(),
                order.getEmail(),
                orderDetailsUrl
        );

        // Gửi email cho người dùng
        sendEmail(order.getEmail(), subject, text);

        // Tạo nội dung email cho admin
        String adminSubject = "Cập nhật trạng thái đơn hàng";
        String adminText = String.format(
                "Trạng thái đơn hàng của khách hàng đã được cập nhật!\n\n" +
                        "Chi tiết đơn hàng:\n" +
                        "ID Đơn hàng: %d\n" +
                        "Người đặt hàng: %s %s\n" +
                        "Trạng thái mới: %s\n" +
                        "Tổng số tiền: %s\n" +
                        "Địa chỉ: %s\n" +
                        "Số điện thoại: %s\n" +
                        "Email: %s\n\n" +
                        "Xem chi tiết đơn hàng tại: %s\n" +
                        "Trân trọng,\nĐội ngũ của chúng tôi",
                order.getOrder_id(),
                order.getFirst_name(),
                order.getLast_name(),
                order.getStatus(),
                order.getTotal_amount().toString(),
                order.getAddress(),
                order.getPhone_number(),
                order.getEmail(),
                orderDetailsUrl
        );

        // Gửi email cho admin
        sendEmail(adminEmailProperties.getAdminEmail(), adminSubject, adminText);
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
