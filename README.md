# LongPlayUpdater v1

- Android 12–14.
- Cài app trên máy trước.
- Quét QR có nội dung `longplay://update`.
- App mở và kiểm tra Google Play services ngay.
- Nếu Google Play services cần cập nhật/sửa, mở luồng xử lý chính thức của Google.
- Nếu Play services đang hoạt động, mở trang Google Play services trong CH Play.

Giới hạn: app thường không thể ép silent-update Google Play services; Google/Play Store quyết định việc cài bản cập nhật.
