# Midterm_JavaTechnology
# Các nguyên tắc, mô hình phát triển phần mềm và thực tiễn áp dụng:

## Nguyên tắc phát triển phần mềm:
  Nguyên tắc thiết kế phần mềm KISS làm cho phần mềm trở nên đơn giản hơn bằng việc chia nhỏ các nhiệm vụ, điều kiện thành những thành phần nhỏ hơn.
  Nguyên tắc đảo ngược phụ thuộc (dependency injection).
  Nguyên tắc khuyến khích sử dụng mã nguồn được tái sử dụng để giảm sự lặp lại và duy trì sự nhất quán trong mã nguồn.
## Mẫu phát triển phần mềm:
  Mô hình Spring MVC: Sử dụng các phương thức HTTP như GET, POST, PUT và DELETE để quản lý các tài nguyên và tương tác giữa máy khách và máy chủ.
## Các phương pháp phát triển phần mềm:
Phương pháp phát triển phần mềm theo mô hình tiếp cận lặp (iterative model) phát triển các tính năng ở mỗi tính năng thực hiện đánh giá, phân tích, thiết kế, hiện thực và test

# Cấu trúc mã:
  ## Backend:
    Công nghệ: Spring Boot (mô hình Spring MVC)
    Dự án gồm các thư mục:
      Config: Chứa tệp tin dùng để cấu hình cho phép gửi đi các yêu cầu DELETE, PUT thông qua form với method POST
      
      Controller: Chứa các tệp tin chịu trách nhiệm xử lý các yêu cầu từ client và gọi đến các lớp Service để trả về kết quả
      
      Model: Chứa các tệp tin đại diện cho các đối tượng 
      
      Repository: Chứa các inteface repository kế thừa từ JpaRepository để thực hiện truy xuất database
      
      Service: Chứa các inteface của của các Repository để thực hiện các thao tác trên cơ sở dữ liệu và đồng thời xử lý nghiệp vụ cho các dữ liệu được truyền từ người dùng thông qua lớp Controller
      
      Security: Chứa các cấu hình liên quan đến bảo mật sử dụng Spring Security để thực hiện xác thực người dùng và phân quyền người dùng

# Các bước chạy dự án trên máy local:
B1: Tạo database có tên SpringWeb trong hệ cơ sở dữ liệu MySQL
B2: Vào tệp application.properties chỉnh sửa các thành phần sau
  spring.datasource.url: thay đổi port của MySQL tương ứng trên máy đang sử dụng (mặc định: 3306)
  spring.datasource.username: thay đổi thành username tương ứng trên máy đang sử dụng
  spring.datasource.password: thay đổi thành password tương ứng trên máy đang sử dụng
B3: Nhấn chạy chương trình trên ứng dụng IntelliJ (các thư viện đã được cài đặt và quản lý bằng maven)
B4: Chương trình chạy thành công

I- Product
  1) Get Products By Keyword
     <img width="870" alt="Screenshot 2024-11-29 at 9 02 16 PM" src="https://github.com/user-attachments/assets/759f0e59-c578-43aa-aef7-a2280bc7b3e5">
     <img width="847" alt="Screenshot 2024-11-29 at 9 04 36 PM" src="https://github.com/user-attachments/assets/9137ddab-419f-4a48-afc9-36c32748070d">
     
  2) Get Products By Category and Price
     <img width="838" alt="Screenshot 2024-11-29 at 9 07 45 PM" src="https://github.com/user-attachments/assets/9d9009dc-ecbb-4f27-a072-999711f45f84">
     <img width="854" alt="Screenshot 2024-11-29 at 9 11 47 PM" src="https://github.com/user-attachments/assets/692c6fc4-1e7d-4f6c-88a5-f91f003d4180">

  3) Get Products By Brands, Category and Price
     <img width="875" alt="Screenshot 2024-11-29 at 9 14 00 PM" src="https://github.com/user-attachments/assets/2dd2b5a9-1709-4feb-8544-a66afb3c0f7b">
     
  4) Get Products By Color, Category and Price
     <img width="843" alt="Screenshot 2024-11-29 at 9 16 19 PM" src="https://github.com/user-attachments/assets/a13eeb57-b4a2-4209-ad54-0fb814c3e358">

  5) Get Products By Category, Price, Brands, Color
     <img width="844" alt="Screenshot 2024-11-29 at 9 17 44 PM" src="https://github.com/user-attachments/assets/6718f199-4c69-48a1-820e-65daf5406a2e">
     <img width="840" alt="Screenshot 2024-11-29 at 9 20 39 PM" src="https://github.com/user-attachments/assets/09ffb63a-fbed-4b46-81a7-6b56f75a201e">
     
  6) Get Product By Id
      ** Required authentication **
      <img width="852" alt="Screenshot 2024-11-29 at 9 24 49 PM" src="https://github.com/user-attachments/assets/623629bc-c861-4424-9b58-e7d1a6a494f9">

  7) Insert Product
      ** Required authentication **
      <img width="850" alt="Screenshot 2024-11-29 at 9 29 34 PM" src="https://github.com/user-attachments/assets/0bb4ee3a-36a9-47b5-9377-533c1b9347f4">

  8) Delete Product
      ** Required authentication **
      <img width="847" alt="Screenshot 2024-11-29 at 9 30 07 PM" src="https://github.com/user-attachments/assets/0ce14ec3-820b-4e4d-9af1-4e31e5c31388">

  9) Update Product
      ** Required authentication **
      <img width="849" alt="Screenshot 2024-11-29 at 9 30 36 PM" src="https://github.com/user-attachments/assets/31ed4da6-1169-4801-af49-b06510186ce7">

II- Order
  1) Get Orders
     ** Required authentication **
     <img width="841" alt="Screenshot 2024-11-29 at 9 43 28 PM" src="https://github.com/user-attachments/assets/bb24bc58-5e2a-4fbb-9857-cb6015da28ad">

  2) Insert order
     ** Error Message While Inputting Name With Wrong Format **
     <img width="843" alt="Screenshot 2024-11-29 at 9 34 13 PM" src="https://github.com/user-attachments/assets/e5a8b988-b7f0-4505-b903-cddd35877833">
     ** Error Message When Name Is Empty **
     <img width="842" alt="Screenshot 2024-11-29 at 9 36 50 PM" src="https://github.com/user-attachments/assets/f97d005d-7dae-4119-a769-7c04b95a4677">
     ** Error Message When Phone Number Is Empty **
     <img width="843" alt="Screenshot 2024-11-29 at 9 38 05 PM" src="https://github.com/user-attachments/assets/d33985f6-d0c9-41df-8576-4b89ca067db0">
     ** Error Message While Inputting Phone Number With Wrong Format **
     <img width="837" alt="Screenshot 2024-11-29 at 9 38 50 PM" src="https://github.com/user-attachments/assets/a997b011-1fd3-40f7-8847-5e1ddf4a61a0">
     ** Error Message When Address Is Empty **
     <img width="840" alt="Screenshot 2024-11-29 at 9 39 45 PM" src="https://github.com/user-attachments/assets/489fa185-50f0-4b7b-b30e-97c9466c3aa6">
     ** Insert order successfully **
     <img width="834" alt="Screenshot 2024-11-29 at 9 41 53 PM" src="https://github.com/user-attachments/assets/e499a7c6-e7b0-4c81-ab96-740e6c7f24be">

  3) Delete order
     ** Required authentication **
     <img width="860" alt="Screenshot 2024-11-29 at 9 44 38 PM" src="https://github.com/user-attachments/assets/0bb2aa22-0e10-48f4-bd7e-dc81a0d62ee9">

  4) Update order status
     ** Required authentication **
     <img width="854" alt="Screenshot 2024-11-29 at 9 45 18 PM" src="https://github.com/user-attachments/assets/e9f2a80a-7974-4e09-8efb-6b7ba59747e1">

III- Cart
  1) Insert Item Cart
     ** Store Product In Cart By Id **
     <img width="858" alt="Screenshot 2024-11-29 at 9 48 46 PM" src="https://github.com/user-attachments/assets/b3cec314-d00e-4186-a401-705ac1160814">

  2) Get Cart
     ** Load Product's Information From Cookie
     <img width="839" alt="Screenshot 2024-11-29 at 9 49 58 PM" src="https://github.com/user-attachments/assets/f4501435-712f-4a79-8657-de9c7c51fa6a">

IV- User
  1) Insert user
     ** Show register page **
     <img width="852" alt="Screenshot 2024-11-29 at 9 57 36 PM" src="https://github.com/user-attachments/assets/90574a79-4eb5-4f70-a631-5eadb1c1e7f1">
     ** Register Successfully Then Redirect To Login Page **
     <img width="847" alt="Screenshot 2024-11-29 at 10 00 51 PM" src="https://github.com/user-attachments/assets/941204c2-0922-48ff-9676-43f2511664e1">


  


