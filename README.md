# Summer Drinks App

App quản lý đồ uống mùa hè với Firebase (Authentication + Realtime Database).

---

## Cài đặt Firebase

### Bước 1: Tạo project Firebase
1. Truy cập https://console.firebase.google.com/
2. Nhấn **"Add project"** (Thêm dự án)
3. Đặt tên project (VD: `summer-drinks`) → nhấn **Continue**
4. Tắt Google Analytics (không cần) → nhấn **Create project**

### Bước 2: Thêm app Android vào Firebase
1. Trong Firebase Console, nhấn icon **Android** để thêm app
2. Điền package name: `com.example.myfirstapp`
3. Nhấn **Register app**
4. Tải file **`google-services.json`**
5. Copy file `google-services.json` vào thư mục gốc của project (cùng cấp với file `build.gradle.kts`)

```
adr/
├── google-services.json   <-- đặt file ở đây
├── build.gradle.kts
├── src/
│   └── ...
```

### Bước 3: Bật Authentication
1. Trong Firebase Console → menu bên trái → chọn **Authentication**
2. Nhấn **Get started**
3. Tab **Sign-in method** → bật **Email/Password** → nhấn **Save**

### Bước 4: Bật Realtime Database
1. Trong Firebase Console → menu bên trái → chọn **Realtime Database**
2. Nhấn **Create Database**
3. Chọn location (mặc định được) → nhấn **Next**
4. Chọn **Start in test mode** → nhấn **Enable**

> **Lưu ý:** Test mode cho phép đọc/ghi tự do trong 30 ngày. Khi deploy thật cần cập nhật security rules.

### Bước 5: Build và chạy
1. Mở project bằng Android Studio
2. Đợi Gradle sync xong
3. Chạy app trên máy ảo hoặc điện thoại thật

---

## Cách hoạt động của app

### Màn hình Đăng ký (RegisterActivity)
- Nhập: Họ tên, Email, Số điện thoại, Mật khẩu, Xác nhận mật khẩu
- Nhấn **Đăng ký** → tạo tài khoản trên Firebase Auth
- Mật khẩu tối thiểu 6 ký tự
- Đăng ký thành công → tự động vào trang chính

### Màn hình Đăng nhập (LoginActivity)
- Nhập Email + Mật khẩu
- Nhấn **Đăng nhập** → xác thực qua Firebase Auth
- Nếu đã đăng nhập trước đó → tự động vào trang chính (không cần đăng nhập lại)

### Màn hình chính (MainActivity) - Quản lý đồ uống
- Hiển thị danh sách đồ uống từ Firebase Realtime Database (cập nhật realtime)
- **Tìm kiếm**: gõ tên hoặc loại đồ uống để lọc
- **Đăng xuất**: nhấn nút "Đăng xuất" góc trên phải

### CRUD đồ uống

| Chức năng | Cách dùng |
|-----------|-----------|
| **Thêm (Create)** | Nhấn **"+ Thêm đồ uống mới"** → điền form (tên, mô tả, giá, emoji, loại) → nhấn **Lưu** |
| **Xem (Read)** | Danh sách đồ uống tự động hiển thị từ Firebase, cập nhật realtime |
| **Sửa (Update)** | Nhấn nút **"Sửa"** trên item → chỉnh sửa thông tin → nhấn **Cập nhật** |
| **Xóa (Delete)** | Nhấn nút **"Xóa"** trên item → xác nhận → đồ uống bị xóa khỏi database |

### Cấu trúc dữ liệu trên Firebase

```
drinks/
├── -ABC123/
│   ├── id: "-ABC123"
│   ├── name: "Trà Đào Cam Sả"
│   ├── description: "Hương vị trà thơm ngát"
│   ├── price: "45.000đ"
│   ├── emoji: "🍑"
│   └── category: "Trà"
├── -DEF456/
│   ├── id: "-DEF456"
│   ├── name: "Mojito Chanh"
│   ├── ...
```

---

## Cấu trúc project

```
src/main/java/com/example/myfirstapp/
├── LoginActivity.java        # Màn hình đăng nhập (Firebase Auth)
├── RegisterActivity.java     # Màn hình đăng ký (Firebase Auth)
├── MainActivity.java         # Màn hình chính - danh sách đồ uống + CRUD
├── AddEditDrinkActivity.java # Form thêm/sửa đồ uống
├── Drink.java                # Model đồ uống
└── DrinkAdapter.java         # Adapter cho RecyclerView

src/main/res/layout/
├── activity_login.xml
├── activity_register.xml
├── activity_main.xml
├── activity_add_edit_drink.xml
└── item_drink.xml            # Layout 1 item đồ uống trong danh sách
```

---

## Tech Stack
- **Language:** Java
- **Min SDK:** 24 (Android 7.0)
- **Firebase Auth:** Đăng nhập / Đăng ký bằng Email & Password
- **Firebase Realtime Database:** Lưu trữ và đồng bộ dữ liệu đồ uống realtime
