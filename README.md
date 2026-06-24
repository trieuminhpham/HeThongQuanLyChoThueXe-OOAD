# Hệ thống quản lý cho thuê xe tự lái

## 1. Giới thiệu

Đây là dự án bài tập lớn môn **Phân tích và thiết kế hướng đối tượng (OOAD)** với đề tài **Hệ thống quản lý cho thuê xe tự lái**.

Hệ thống được xây dựng nhằm hỗ trợ cửa hàng cho thuê xe quản lý các nghiệp vụ chính như: quản lý xe, quản lý danh mục xe, quản lý khách hàng, quản lý nhân viên, quản lý tài khoản, lập và xử lý hợp đồng thuê xe, quản lý thanh toán, áp dụng khuyến mãi và thống kê hoạt động kinh doanh.

Dự án được thiết kế theo hướng đối tượng, sử dụng mô hình nhiều lớp gồm giao diện, controller, service, DAO và model. Cơ sở dữ liệu sử dụng **SQL Server**.

---

## 2. Mục tiêu dự án

- Tin học hóa quy trình quản lý cho thuê xe tự lái.
- Hạn chế sai sót khi quản lý thủ công bằng sổ sách hoặc file Excel.
- Quản lý tập trung thông tin xe, khách hàng, nhân viên, hợp đồng và thanh toán.
- Theo dõi trạng thái xe và trạng thái hợp đồng trong từng giai đoạn thuê xe.
- Hỗ trợ phân quyền người dùng theo vai trò.
- Cung cấp dữ liệu phục vụ báo cáo, thống kê doanh thu và hoạt động kinh doanh.

---

## 3. Công nghệ sử dụng

| Thành phần | Công nghệ |
|---|---|
| Ngôn ngữ lập trình | Java |
| Giao diện | Java Swing |
| Cơ sở dữ liệu | Microsoft SQL Server |
| Kết nối CSDL | JDBC |
| IDE khuyến nghị | NetBeans / IntelliJ IDEA / Eclipse |
| Mô hình thiết kế | MVC, DAO, Service |

---

## 4. Cấu trúc mã nguồn

```text
src/
├── app/          Điểm khởi chạy ứng dụng
├── config/       Kết nối database và phiên đăng nhập
├── model/        Các đối tượng dữ liệu
├── dao/          Truy vấn SQL bằng JDBC/PreparedStatement
├── service/      Kiểm tra dữ liệu, phân quyền và nghiệp vụ
├── util/         Theme, định dạng, validation, trạng thái và phân quyền
└── view/
    ├── auth/       Đăng nhập nhân viên
    ├── customer/   Tra cứu và đặt xe dành cho khách hàng
    ├── management/ Các panel quản trị
    └── dialog/     Các hộp thoại nhập liệu/nghiệp vụ
```

Luồng gọi tiêu chuẩn:

```text
View → Service → DAO → SQL Server
```

- View chỉ nhận dữ liệu từ giao diện và hiển thị kết quả.
- Service chịu trách nhiệm validation, quyền và trạng thái nghiệp vụ.
- DAO thực hiện truy vấn SQL và ánh xạ `ResultSet` sang model.
- `Session` lưu nhân viên đang đăng nhập.
- `PermissionUtil` là nơi định nghĩa tập trung quyền theo vai trò.
- `UITheme` định nghĩa màu sắc, font, nút, bảng và card dùng chung.

---

## 5. Các vai trò người dùng

Hệ thống có 3 nhóm người dùng chính:

### 5.1. Chủ cửa hàng

Chủ cửa hàng có quyền quản lý toàn bộ hệ thống, bao gồm:

- Quản lý nhân viên.
- Quản lý tài khoản nhân viên.
- Quản lý danh mục xe.
- Quản lý xe.
- Theo dõi hợp đồng, thanh toán và thống kê.
- Khóa hoặc mở khóa tài khoản nhân viên.
- Cập nhật trạng thái làm việc của nhân viên.

### 5.2. Nhân viên kinh doanh

Nhân viên kinh doanh phụ trách các nghiệp vụ liên quan đến khách hàng và hợp đồng:

- Quản lý thông tin khách hàng.
- Tra cứu xe khả dụng.
- Tạo yêu cầu thuê xe.
- Duyệt yêu cầu đặt trước xe.
- Lập hợp đồng thuê xe.
- Ghi nhận tiền cọc.
- Bàn giao xe.
- Nhận trả xe.
- Quyết toán hợp đồng.
- Áp dụng chương trình khuyến mãi.

### 5.3. Nhân viên kỹ thuật

Nhân viên kỹ thuật phụ trách tình trạng kỹ thuật của xe:

- Xem danh sách xe.
- Cập nhật trạng thái xe.
- Chuyển xe sang trạng thái bảo trì hoặc bảo dưỡng.
- Xác nhận xe sẵn sàng sau khi kiểm tra kỹ thuật.

---

## 6. Chức năng chính

### 6.1. Quản lý xác thực

- Đăng nhập.
- Đăng xuất.
- Kiểm tra trạng thái tài khoản.
- Kiểm tra quyền truy cập theo vai trò.
- Khóa / mở khóa tài khoản.

### 6.2. Quản lý danh mục xe

- Thêm danh mục xe.
- Sửa thông tin danh mục xe.
- Xóa danh mục xe nếu không còn xe liên quan.
- Tra cứu danh mục xe.
- Quản lý các thông tin: tên loại xe, mô tả, nhiên liệu, số chỗ, phân khúc.

### 6.3. Quản lý xe

- Thêm xe mới.
- Sửa thông tin xe.
- Tra cứu xe.
- Cập nhật trạng thái xe.
- Theo dõi số km hiện tại.
- Theo dõi đơn giá thuê theo ngày.
- Quản lý biển số, số khung, số máy, màu xe, năm sản xuất.

Các trạng thái xe gồm:

- `SanSang`: xe sẵn sàng cho thuê.
- `GiuCho`: xe đang được giữ chỗ sau khi yêu cầu thuê được duyệt.
- `DaDatCoc`: xe đã được khách hàng đặt cọc.
- `DangThue`: xe đang được khách hàng thuê.
- `ChoQuyetToan`: xe đã được trả, chờ quyết toán.
- `BaoTri`: xe đang bảo trì.
- `BaoDuong`: xe đang bảo dưỡng.
- `DaThanhLy`: xe đã thanh lý, không còn kinh doanh.

### 6.4. Quản lý khách hàng

- Thêm khách hàng.
- Cập nhật thông tin khách hàng.
- Tìm kiếm khách hàng.
- Kiểm tra số CCCD, số bằng lái và ngày hết hạn bằng lái.
- Xem lịch sử thuê xe.
- Quản lý điểm tích lũy và loại khách hàng.

### 6.5. Quản lý nhân viên

- Thêm nhân viên mới.
- Cập nhật thông tin nhân viên.
- Tìm kiếm nhân viên.
- Xem danh sách nhân viên.
- Cập nhật vai trò nhân viên.
- Cập nhật trạng thái làm việc.

Các vai trò nhân viên gồm:

- `ChuCuaHang`
- `NhanVienKinhDoanh`
- `NhanVienKyThuat`

Các trạng thái làm việc gồm:

- `DangLam`
- `DaNghi`

### 6.6. Quản lý tài khoản

- Tạo tài khoản cho nhân viên.
- Cập nhật thông tin tài khoản.
- Khóa tài khoản.
- Mở khóa tài khoản.
- Kiểm tra trạng thái tài khoản khi đăng nhập.

Các trạng thái tài khoản gồm:

- `HoatDong`
- `BiKhoa`

### 6.7. Quản lý hợp đồng

Hệ thống hỗ trợ quy trình thuê xe theo các trạng thái nghiệp vụ rõ ràng:

1. Khách hàng / nhân viên tạo yêu cầu thuê xe.
2. Hợp đồng ở trạng thái `ChoDuyet`.
3. Nhân viên kinh doanh duyệt yêu cầu.
4. Hợp đồng chuyển sang `GiuCho`, xe chuyển sang `GiuCho`.
5. Khách hàng thanh toán tiền cọc.
6. Hợp đồng chuyển sang `DaDatCoc`, xe chuyển sang `DaDatCoc`.
7. Nhân viên bàn giao xe.
8. Hợp đồng chuyển sang `DangThue`, xe chuyển sang `DangThue`.
9. Khách hàng trả xe.
10. Hợp đồng chuyển sang `ChoQuyetToan`, xe chuyển sang `ChoQuyetToan`.
11. Nhân viên tính phí phát sinh và quyết toán.
12. Hợp đồng chuyển sang `DaQuyetToan`, xe trở về `SanSang` hoặc chuyển sang `BaoTri` / `BaoDuong` nếu cần kiểm tra kỹ thuật.

Các trạng thái hợp đồng gồm:

- `ChoDuyet`
- `GiuCho`
- `DaDatCoc`
- `DangThue`
- `ChoQuyetToan`
- `DaQuyetToan`
- `DaHuy`

### 6.8. Quản lý thanh toán

Hệ thống lưu các giao dịch tài chính phát sinh trong quá trình thuê xe.

Các hình thức thanh toán gồm:

- `TienMat`
- `ATM`
- `ChuyenKhoan`

Các trạng thái giao dịch gồm:

- `ThanhCong`
- `ThatBai`
- `ChoXuLy`

Các loại thanh toán gồm:

- `TienCoc`: tiền cọc.
- `ThuThem`: thu thêm khi phát sinh chi phí.
- `HoanCoc`: hoàn cọc cho khách hàng.
- `QuyetToan`: thanh toán khi kết thúc hợp đồng.

Các loại giao dịch gồm:

- `Thu`
- `Chi`

### 6.9. Quản lý khuyến mãi

- Thêm chương trình khuyến mãi.
- Cập nhật chương trình khuyến mãi.
- Kiểm tra điều kiện áp dụng.
- Áp dụng khuyến mãi vào hợp đồng.
- Theo dõi trạng thái khuyến mãi.

---

## 7. Cấu trúc cơ sở dữ liệu

Cơ sở dữ liệu sử dụng tên:

```sql
HeThongQuanLyChoThueXe
```

Gồm 8 bảng chính:

| Bảng | Chức năng |
|---|---|
| `DanhMucXe` | Lưu thông tin phân loại xe |
| `Xe` | Lưu thông tin chi tiết từng xe |
| `NhanVien` | Lưu thông tin nhân viên |
| `TaiKhoan` | Lưu thông tin đăng nhập của nhân viên |
| `KhachHang` | Lưu thông tin khách hàng |
| `KhuyenMai` | Lưu thông tin chương trình khuyến mãi |
| `HopDong` | Lưu thông tin hợp đồng thuê xe |
| `ThanhToan` | Lưu thông tin giao dịch thanh toán |

### 7.1. Quan hệ giữa các bảng

- Một `DanhMucXe` có nhiều `Xe`.
- Một `NhanVien` có một `TaiKhoan`.
- Một `KhachHang` có nhiều `HopDong`.
- Một `Xe` có thể xuất hiện trong nhiều `HopDong` theo thời gian.
- Một `NhanVien` có thể xử lý nhiều `HopDong`.
- Một `KhuyenMai` có thể được áp dụng cho nhiều `HopDong`.
- Một `HopDong` có thể phát sinh nhiều `ThanhToan`.

---

## 8. Các lớp thực thể và controller

### 8.1. Lớp thực thể

Hệ thống có 8 lớp thực thể tương ứng với 8 bảng trong cơ sở dữ liệu:

- `DanhMucXe`
- `Xe`
- `KhachHang`
- `NhanVien`
- `TaiKhoan`
- `HopDong`
- `ThanhToan`
- `KhuyenMai`

### 8.2. Lớp controller

Hệ thống có 9 lớp controller:

- `AuthController`
- `DanhMucXeController`
- `XeController`
- `KhachHangController`
- `NhanVienController`
- `TaiKhoanController`
- `HopDongController`
- `ThanhToanController`
- `KhuyenMaiController`

Trong đó, `AuthController` không tương ứng trực tiếp với một bảng dữ liệu riêng, mà xử lý nghiệp vụ đăng nhập, đăng xuất và kiểm tra quyền người dùng.

---

## 9. Tài khoản mẫu

Sau khi chạy file SQL mẫu, hệ thống có các tài khoản mặc định sau:

| Vai trò | Tên đăng nhập | Mật khẩu |
|---|---|---|
| Chủ cửa hàng | `owner` | `123456` |
| Nhân viên kinh doanh | `sale01` | `123456` |
| Nhân viên kỹ thuật | `tech01` | `123456` |

Mật khẩu được lưu trong cơ sở dữ liệu dưới dạng mã băm SHA-256.

---

## 10. Cài đặt cơ sở dữ liệu

### Bước 1: Mở SQL Server Management Studio

Đăng nhập vào SQL Server bằng tài khoản có quyền tạo database.

### Bước 2: Chạy file SQL

Mở file:

```text
HeThongQuanLyChoThueXe.sql
```

Sau đó chạy toàn bộ script để tạo database, tạo bảng, tạo ràng buộc và thêm dữ liệu mẫu.

### Bước 3: Kiểm tra database

Sau khi chạy script, kiểm tra database:

```sql
USE HeThongQuanLyChoThueXe;
SELECT * FROM DanhMucXe;
SELECT * FROM Xe;
SELECT * FROM NhanVien;
SELECT * FROM TaiKhoan;
SELECT * FROM KhachHang;
SELECT * FROM HopDong;
SELECT * FROM ThanhToan;
```

---

## 11. Cấu hình kết nối cơ sở dữ liệu

Trong project Java, cần cấu hình kết nối đến SQL Server trong lớp cấu hình kết nối cơ sở dữ liệu, ví dụ:

```java
private static final String URL =
    "jdbc:sqlserver://localhost:1433;databaseName=HeThongQuanLyChoThueXe;encrypt=true;trustServerCertificate=true";

private static final String USER = "sa";
private static final String PASSWORD = "your_password";
```

Cần thay `USER` và `PASSWORD` theo tài khoản SQL Server trên máy đang chạy.

---

## 12. Cách chạy chương trình

### Cách 1: Chạy bằng NetBeans

1. Mở NetBeans.
2. Chọn **File > Open Project**.
3. Chọn thư mục project.
4. Kiểm tra cấu hình JDK.
5. Kiểm tra thư viện JDBC SQL Server.
6. Chạy file `Main.java`.

### Cách 2: Chạy bằng IDE khác

1. Mở project bằng IntelliJ IDEA hoặc Eclipse.
2. Đảm bảo project sử dụng đúng JDK.
3. Thêm SQL Server JDBC Driver nếu chưa có.
4. Cấu hình kết nối database.
5. Chạy lớp `Main`.

---

## 13. Quy trình nghiệp vụ chính

### 13.1. Quy trình đặt trước xe

1. Khách hàng chọn xe muốn thuê.
2. Hệ thống kiểm tra xe có trạng thái `SanSang`.
3. Khách hàng nhập thông tin thuê xe.
4. Hệ thống tạo hợp đồng với trạng thái `ChoDuyet`.
5. Nhân viên kinh doanh duyệt yêu cầu.
6. Nếu duyệt thành công, hợp đồng chuyển sang `GiuCho`, xe chuyển sang `GiuCho`.
7. Nếu từ chối, hợp đồng chuyển sang `DaHuy`.

### 13.2. Quy trình đặt cọc

1. Nhân viên chọn hợp đồng đã được duyệt.
2. Khách hàng thanh toán tiền cọc.
3. Hệ thống tạo bản ghi trong bảng `ThanhToan`.
4. Hợp đồng chuyển sang `DaDatCoc`.
5. Xe chuyển sang `DaDatCoc`.

### 13.3. Quy trình bàn giao xe

1. Nhân viên chọn hợp đồng đã đặt cọc.
2. Nhân viên kiểm tra thông tin khách hàng và xe.
3. Hệ thống ghi nhận số km khi nhận.
4. Hợp đồng chuyển sang `DangThue`.
5. Xe chuyển sang `DangThue`.

### 13.4. Quy trình trả xe và quyết toán

1. Khách hàng trả xe.
2. Nhân viên ghi nhận ngày trả thực tế và số km khi trả.
3. Hệ thống tính phí phát sinh nếu có.
4. Hệ thống tạo giao dịch thanh toán quyết toán.
5. Hợp đồng chuyển sang `DaQuyetToan`.
6. Xe chuyển về `SanSang`, hoặc chuyển sang `BaoTri` / `BaoDuong` nếu cần kiểm tra.

---

## 14. Hướng phát triển

Trong tương lai, hệ thống có thể được phát triển thêm các chức năng:

- Đặt xe trực tuyến qua website hoặc ứng dụng di động.
- Tích hợp thanh toán điện tử.
- Gửi email hoặc tin nhắn xác nhận đặt xe.
- Quản lý lịch sử bảo trì và bảo dưỡng chi tiết.
- Quản lý nhiều chi nhánh.
- Thống kê doanh thu theo ngày, tháng, quý, năm.
- Thống kê hiệu suất khai thác từng xe.
- Tự động cảnh báo bằng lái hết hạn hoặc xe cần bảo dưỡng.
- Phân quyền chi tiết hơn theo từng chức năng.

---

## 15. Thành viên nhóm

- Nguyễn Hoàng Anh - 20233994
- Phạm Triệu Minh - 20234026
- Phạm Hồng Duy Minh - 20234025
- Nguyễn Việt Trường - 20234042

---

## 16. Giảng viên hướng dẫn

**TS. Nguyễn Thị Kim Thoa**

---

## 17. Ghi chú

Dự án được xây dựng phục vụ mục đích học tập trong môn Phân tích và thiết kế hướng đối tượng. Một số chức năng có thể được mô phỏng ở mức cơ bản để phù hợp với phạm vi bài tập lớn.
