# 🌐💻XÂY DỰNG WEBSITE KINH DOANH THIẾT BỊ ĐIỆN TỬ GIA DỤNG🛒📱

Dự án được xây dựng bởi 👩‍💻 [Ngọc Hân](https://github.com/HanNguyenLA) và 👨‍💻 [Phúc Hảo](https://github.com/haophan361)
---

## :rocket: Mô tả dự án
Dự án này nhằm xây dựng một website bán hàng trực tuyến cho các sản phẩm thiết bị điện tử gia dụng, bao gồm các chức năng như:
- **Quản lý tài khoản người dùng🧑‍💻 :** Người dùng có thể tạo tài khoản, đăng nhập và thay đổi thông tin cá nhân.
- **Quản lý sản phẩm📦 :** Cung cấp giao diện để thêm, sửa, và xóa sản phẩm, cùng với các chức năng quản lý kho.
- **Quản lý đơn hàng🛒:** Nhân viên có thể theo dõi, xác lnhận và xử lí các đơn hàng của khách hàng.
- **Tìm kiếm và lọc sản phẩm🔍:** Người dùng có thể tìm kiếm và lọc sản phẩm theo các tiêu chí khác nhau như tên sản phẩm loại sản phẩm, thương hiệu,...
- **Đánh giá sản phẩm⭐:** Người dùng có thể đánh giá các sản phẩm mà họ đã mua.
- **Khuyến mãi và giảm giá🎁:** Quản trị viên có thể thiết lập mã giảm giá và khuyến mãi cho sản phẩm.
- **:star2:Dashboard:star2::** Cung cấp các báo cáo chi tiết về số lượng mặt hàng, tổng doanh thu, sản phẩm trong giỏ hàng, người dùng đăng ký, biểu đồ bán chạy, doanh thu theo loại sản phẩm, doanh thu theo tháng
## 🌍 Các công nghệ sử dụng
- **Frontend :art:**: HTML, CSS, JavaScript, BootStrap 3, BootStrap 4, Bootsrap 5, Thymeleaf 
- **Backend :coffee:**: Java (Spring Boot) , Spring Security,  Spring Data JPA
- **Database :floppy_disk:**: MySQL
- **Cloud Storage :cloud:**: Firebase Storage
- **Version Control :octopus: :**: Git, GitHub

## :wrench: Cài đặt 
### 1. Cài đặt môi trường phát triển💻
Trước khi bắt đầu cài đặt dự án, bạn cần đảm bảo rằng máy tính của bạn đã cài đặt các công cụ sau:

- Java JDK 21: Cài đặt JDK từ [Amazon Corretto](https://docs.aws.amazon.com/corretto/latest/corretto-21-ug/downloads-list.html) .
- Maven: Cài đặt Maven từ [Maven official website](https://maven.apache.org/download.cgi).
- MySQL: Cài đặt [MySQL](https://dev.mysql.com/downloads/workbench/) và tạo một cơ sở dữ liệu để lưu trữ thông tin của ứng dụng.
- IDE: Sử dụng một IDE như [IntelliJ IDEA](https://www.jetbrains.com/idea/) hoặc [Eclipse](https://www.eclipse.org/) để phát triển ứng dụng.
### 2. Clone dự án và cài đặt dependencies :package:
Vui lòng clone dự án từ github về máy của bạn:
```bash
git clone https://github.com/haophan361/project-CNTT
```
Cài đặt các phụ thuộc bằng maven:
```bash
mvn clean install
```
### 3. Cấu hình cơ sở dữ liệu💾
Tạo cơ sở dữ liệu trong MySQL (ví dụ: electronic_store).
Cập nhật thông tin kết nối cơ sở dữ liệu trong file application.properties:
``` properties
spring.application.name=H_Ecommerce
spring.datasource.url=jdbc:mysql://localhost:3306/H_Ecommerce
spring.datasource.username=root
spring.datasource.password=20112004
```
### 4. Chạy ứng dụng🚀
Để chạy ứng dụng 
```bash 
mvn spring-boot:run
```
### 5.Truy cập trang web :globe_with_meridians:
Vui lòng mở trình duyệt và truy cập link:
[http://localhost:8080](http://localhost:8080)



## :file_folder: Cấu trúc folder 

**project-CNTT**                   
│
├── :file_folder: **src**                          
│   ├── :file_folder: **main**                     
│   │   ├── :file_folder: **java**                
│   │   │   └── :file_folder: **com**             
│   │   │       └── 📂: **h_ecommerce_store**      
│   │   │           ├── 🌐: **Controller**
│   │   │           ├── 📦: **DTO**  
│   │   │           ├── 🗂️: **Entity**         
│   │   │           ├── 🛠️: **Repository**     
│   │   │           ├── 🔐: **SecurityConfig**      
│   │   │           ├── 🔧: **Service**    
│   │   │           ├── ⚙️: **Util**  
│   │   │           └── 📋: **HEcommerceApplication**       
│   │   └── :open_file_folder: **resources**        
│   │       ├── 📂: **static** 
│   │            ├── 📁: **css**
│   │            ├── 📁: **fonts**  
│   │            ├── 📁: **img**         
│   │            ├── 📁: **js** 
│   │       ├── 📂: **template** 
│   │           ├── 🧑‍💻: **admin**
│   │           ├── 📦: **fragment**  
│   │           ├── 🧑‍💼: **staff**         
│   │           └── 🌐: **web** 
│   │       ├── 🔐: **key_firebase** 
│   │       └── :loudspeaker: **application.properties**                      
│   └── 📂: **test**                                      
│       └── 📂: **java**      
│           └── 📂: **com** 
│               └── 📂: **h_ecommerce_store** 
│                   └── ⚙️: **HEcommerceApplicationTests.java** 
│
├── :page_facing_up: **pom.xml**                    
└── :memo: **README.md**  

## :camera: Ảnh minh họa giao diện
![Giao diện sản phẩm](https://firebasestorage.googleapis.com/v0/b/h-ecommerce-a8e7c.appspot.com/o/Screenshot%202024-12-02%20221541.png?alt=media&token=54683579-cc83-457c-9440-00fa3ac7239f)
---
## :memo: Đánh giá và phản hồi
Chúng tôi rất mong nhận được phản hồi từ bạn về các tính năng của website.


## :clap: Cảm ơn 
Cảm ơn bạn đã theo dõi dự án của chúng tôi. Hy vọng bạn sẽ có những trải nghiệm tuyệt vời khi sử dụng sản phẩm.

## :heart:  Đội ngũ phát triển

Dự án này được thực hiện với sự đóng góp và nỗ lực của các thành viên sau:

-  **Ngọc Hân** : Quản lý sản phẩm, tìm kiếm và lọc sản phẩm.
- **Phúc Hảo** : Quản lý tài khoản người dùng, quản lý đơn hàng, phát triển Dashboard.

---



