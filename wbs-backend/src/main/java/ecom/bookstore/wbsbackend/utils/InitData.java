package ecom.bookstore.wbsbackend.utils;

import ecom.bookstore.wbsbackend.entities.Category;
import ecom.bookstore.wbsbackend.repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author minh phuong
 * @created 12/10/2022 - 5:01 PM
 */
@Component
public class InitData {
  private CategoryRepo categoryRepo;
  @Autowired public void CategoryRepo(CategoryRepo categoryRepo) {
    this.categoryRepo = categoryRepo;
  }
  public void initData() {
    Category[] categories = new Category[500];
    int maxIndexCategory = createCategory(categories);
  }

  public int createCategory(Category[] categories) {
    int i = 0;
    categories[i] = new Category("Sách Trong Nước", null);
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Văn Học", this.categoryRepo.findByName("Sách Trong Nước").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Tiểu Thuyết", this.categoryRepo.findByName("Sách Trong Nước").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Truyện Ngắn - Tản Văn", this.categoryRepo.findByName("Sách Trong Nước").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Light Novel", this.categoryRepo.findByName("Sách Trong Nước").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Ngôn Tình", this.categoryRepo.findByName("Sách Trong Nước").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Kinh Tế", this.categoryRepo.findByName("Sách Trong Nước").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Nhân Vật - Bài Học Kinh Doanh", this.categoryRepo.findByName("Kinh Tế").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Quản Trị - Lãnh Đạo", this.categoryRepo.findByName("Kinh Tế").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Marketing - Bán Hàng", this.categoryRepo.findByName("Kinh Tế").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Phân Tích Kinh Tế", this.categoryRepo.findByName("Kinh Tế").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Tâm Lý - Kỹ Năng Sống", this.categoryRepo.findByName("Sách Trong Nước").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Kỹ Năng Sống", this.categoryRepo.findByName("Tâm Lý - Kỹ Năng Sống").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Rèn Luyện Nhân Cách", this.categoryRepo.findByName("Tâm Lý - Kỹ Năng Sống").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Tâm Lý", this.categoryRepo.findByName("Tâm Lý - Kỹ Năng Sống").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Sách Cho Tuổi Mới Lớn", this.categoryRepo.findByName("Tâm Lý - Kỹ Năng Sống").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Nuôi Dạy Con", this.categoryRepo.findByName("Sách Trong Nước").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Cẩm Nang Làm Cha Mẹ", this.categoryRepo.findByName("Nuôi Dạy Con").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Phương Pháp Giáo Dục Trẻ Các Nước", this.categoryRepo.findByName("Nuôi Dạy Con").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Phát Triển Trí Tuệ Cho Trẻ", this.categoryRepo.findByName("Nuôi Dạy Con").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Phát Triển Kỹ Năng Cho Trẻ", this.categoryRepo.findByName("Nuôi Dạy Con").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Sách Thiếu Nhi", this.categoryRepo.findByName("Sách Trong Nước").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Manga - Comic", this.categoryRepo.findByName("Sách Thiếu Nhi").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Kiến Thức Bách Khoa", this.categoryRepo.findByName("Sách Thiếu Nhi").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Sách Tranh Kỹ Năng Sống Cho Trẻ", this.categoryRepo.findByName("Sách Thiếu Nhi").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Vừa Học - Vừa Học Vừa Chơi Với Trẻ", this.categoryRepo.findByName("Sách Thiếu Nhi").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Tiểu sử - hồi ký", this.categoryRepo.findByName("Sách Trong Nước").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Câu Chuyện Cuộc Đời", this.categoryRepo.findByName("Tiểu sử - hồi ký").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Chính Trị", this.categoryRepo.findByName("Tiểu sử - hồi ký").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Kinh Tế", this.categoryRepo.findByName("Tiểu sử - hồi ký").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Nghệ Thuật - Giải Trí", this.categoryRepo.findByName("Tiểu sử - hồi ký").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Giáo Khoa - Tham Khảo", this.categoryRepo.findByName("Sách Trong Nước").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Sách Giáo Khoa", this.categoryRepo.findByName("Giáo Khoa - Tham Khảo").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Sách Tham Khảo", this.categoryRepo.findByName("Giáo Khoa - Tham Khảo").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Luyện Thi THPT Quốc Gia", this.categoryRepo.findByName("Giáo Khoa - Tham Khảo").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Mẫu Giáo", this.categoryRepo.findByName("Giáo Khoa - Tham Khảo").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Sách Học Ngoại Ngữ", this.categoryRepo.findByName("Sách Trong Nước").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Tiếng Anh", this.categoryRepo.findByName("Sách Học Ngoại Ngữ").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Tiếng Nhật", this.categoryRepo.findByName("Sách Học Ngoại Ngữ").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Tiếng Hoa", this.categoryRepo.findByName("Sách Học Ngoại Ngữ").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category("Tiếng Hàn", this.categoryRepo.findByName("Sách Học Ngoại Ngữ").orElse(null));
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category();
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category();
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category();
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category();
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category();
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category();
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category();
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category();
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category();
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category();
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category();
    this.categoryRepo.save(categories[i]);

    i++;categories[i] = new Category();
    this.categoryRepo.save(categories[i]);


    return i;
  }
}
