package ecom.bookstore.wbsbackend.utils;

import ecom.bookstore.wbsbackend.entities.*;
import ecom.bookstore.wbsbackend.models.enums.*;
import ecom.bookstore.wbsbackend.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author minh phuong
 * @created 12/10/2022 - 5:01 PM
 */
@Component
public class InitData {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  private AuthorRepo authorRepo;

  @Autowired
  public void AuthorRepo(AuthorRepo authorRepo) {
    this.authorRepo = authorRepo;
  }

  private BookRepo bookRepo;

  @Autowired
  public void BookRepo(BookRepo bookRepo) {
    this.bookRepo = bookRepo;
  }

  private CategoryRepo categoryRepo;

  @Autowired
  public void CategoryRepo(CategoryRepo categoryRepo) {
    this.categoryRepo = categoryRepo;
  }

  private GenreRepo genreRepo;

  @Autowired
  public void GenreRepo(GenreRepo genreRepo) {
    this.genreRepo = genreRepo;
  }

  private ImageRepo imageRepo;

  @Autowired
  public void ImageRepo(ImageRepo imageRepo) {
    this.imageRepo = imageRepo;
  }

  private LanguageRepo languageRepo;

  @Autowired
  public void LanguageRepo(LanguageRepo languageRepo) {
    this.languageRepo = languageRepo;
  }

  private PublisherRepo publisherRepo;

  @Autowired
  public void PublisherRepo(PublisherRepo publisherRepo) {
    this.publisherRepo = publisherRepo;
  }

  private TranslatorRepo translatorRepo;

  @Autowired
  public void TranslatorRepo(TranslatorRepo translatorRepo) {
    this.translatorRepo = translatorRepo;
  }
  private SeriesRepo seriesRepo;
  @Autowired public void SeriesRepo(SeriesRepo seriesRepo) {
    this.seriesRepo = seriesRepo;
  }
  private SupplierRepo supplierRepo;

  @Autowired
  public void SupplierRepo(SupplierRepo supplierRepo) {
    this.supplierRepo = supplierRepo;
  }

  public void init() {

    this.LOGGER.info("Start init data to database");

    LanguageVariable lv = new LanguageVariable();
    createLanguage(lv);

    CategoryVariable cv = new CategoryVariable();
    createCategory(cv);

    GenreVariable gv = new GenreVariable();
    createGenre(gv);

    SupplierVariable sv = new SupplierVariable();
    createSupplier(sv);

    PublisherVariable pv = new PublisherVariable();
    createPublisher(pv);

    AuthorVariable av = new AuthorVariable();
    createAuthor(av);

    TranslatorVariable tv = new TranslatorVariable();
    createTranslator(tv);
    
    SeriesVariable srv = new SeriesVariable();
    createSeries(srv,av, pv);

    Book[] books = new Book[500];
    int maxIndexBook = createBook(books, lv, cv, gv, sv, pv, av, tv, srv);

    this.LOGGER.info("Init data to database is done!");
  }

  private int createBook(
      Book[] books,
      LanguageVariable lv,
      CategoryVariable cv,
      GenreVariable gv,
      SupplierVariable sv,
      PublisherVariable pv,
      AuthorVariable av,
      TranslatorVariable tv, SeriesVariable srv) {
    int i = 0;
    books[i] =
        new Book(
            "Nhà Giả Kim",
            new BigDecimal("79000"),
            cv.categoryTieuThuyet,
            null,
            null,
            sv.supplierNhaNam,
            new HashSet<>(Collections.singletonList(av.authorPauloCoelho)),
            new HashSet<>(Collections.singletonList(tv.translatorLeChuCau)),
            null,
            lv.languageVN,
            pv.publisherHoiNhaVan,
            1988,
            2020,
            220,
            20.5,
            13d,
            null,
            227,
            EBookLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("image_195509_1_36793.jpg"),
            "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(new String[] {"8935235226272_1.jpg", "8935235226272_2.jpg"}))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Cây Cam Ngọt Của Tôi",
            new BigDecimal("108000"),
            cv.categoryTieuThuyet,
            null,
            null,
            sv.supplierNhaNam,
            new HashSet<>(Collections.singletonList(av.authorJoseMauro)),
            new HashSet<>(Arrays.asList(tv.translatorNguyenBichLan, tv.translatorToYenLy)),
            null,
            lv.languageVN,
            pv.publisherHoiNhaVan,
            2020,
            null,
            280,
            20d,
            14.5d,
            null,
            244,
            EBookLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("image_217480.jpg"),
            "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2020_12_17_16_50_30_1-390x510.jpg",
                      "2020_12_17_16_50_30_2-390x510.jpg",
                      "2020_12_17_16_50_30_3-390x510.jpg",
                      "2020_12_17_16_50_30_4-390x510.jpg",
                      "2020_12_17_16_50_30_5-390x510.jpg",
                      "2020_12_17_16_50_30_6-390x510.jpg",
                      "2020_12_17_16_50_30_7-390x510.jpg",
                      "2020_12_17_16_50_30_8-390x510.jpg",
                      "2020_12_17_16_50_30_9-390x510.jpg",
                      "2020_12_17_16_50_30_10-390x510.jpg",
                      "2020_12_17_16_50_30_11-390x510.jpg",
                      "2020_12_17_16_50_30_12-390x510.jpg"
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Từ Điển Tiếng “Em”",
            new BigDecimal("69000"),
            cv.categoryTruyenNganTanVan,
            null,
            null,
            sv.supplierSkyBooks,
            new HashSet<>(Collections.singletonList(av.authorKhotudien)),
            new HashSet<>(),
            null,
            lv.languageVN,
            pv.publisherPhuNuVietNam,
            null,
            2021,
            300,
            12d,
            10d,
            null,
            280,
            EBookLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("bia_tudientiengem-_1_.jpg"),
            "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "bia_tudientiengem_1_.png",
                      "122794058_3479567975430769_1331743639861310999_o_1.jpg",
                      "123026494_3479568002097433_2539352269014278525_o_1.jpg",
                      "122702904_3479568285430738_3407610756011378446_o_1.jpg",
                      "125495151_3288476084615326_7140752655633785953_o_1.jpg"
                    }))));
    books[i] = this.bookRepo.save(books[i]);

        i++;
        books[i] =
            new Book(
                "Chiến Thắng Con Quỷ Trong Bạn",
                new BigDecimal("115000"),
                cv.categoryKyNangSong,
                null,
                null,
                sv.supplierThaiHa,
                new HashSet<>(
                    Collections.singletonList(av.authorNapoleonHill)),
                new HashSet<>(),
                srv.seriesComboSachKyNangTuyetChieuLamGiau,
                lv.languageVN,
                pv.publisherLaoDong,
                2011,
                2021,
                300,
                24d,
                16d,
                null,
                293,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail("chien-thang-con-quy-trong-ban_10.2021_bia-01.jpg"),
                "");
        books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "chien_thang_con_quy_trong_ban_10.2021_bia_01.jpg",
                        "chien_thang_con_quy_trong_ban_10.2021_bia_4.jpg",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "13 Nguyên Tắc Nghĩ Giàu Làm Giàu - Think And Grow Rich",
            new BigDecimal("110000"),
            cv.categoryKyNangSong,
            null,
            null,
            sv.supplierThaiHa,
            new HashSet<>(
                Collections.singletonList(av.authorNapoleonHill)),
                new HashSet<>(),
                srv.seriesComboSachKyNangTuyetChieuLamGiau,
                lv.languageVN,
                pv.publisherLaoDong,
                1937,
                2020,
                550,
                24d,
                15.5d,
                null,
                399,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail("image_195509_1_39473.jpg"),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "2021_06_22_16_46_12_1-390x510.jpg",
                        "2021_06_22_16_46_12_2-390x510.jpg",
                        "2021_06_22_16_46_12_3-390x510.jpg",
                        "2021_06_22_16_46_12_4-390x510.jpg",
                        "2021_06_22_16_46_12_5-390x510.jpg"
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Lời Thú Tội Mới Của Một Sát Thủ Kinh Tế",
            new BigDecimal("190000"),
            cv.categoryNhanVatBaiHoc,
            null,
            null,
            sv.supplierTanViet,
            new HashSet<>(
                Collections.singletonList(av.authorJohnPerkins)),
                new HashSet<>(Arrays.asList(tv.translatorThuyHang, tv.translatorAnhThu)),
                srv.seriesComboLoiThuToiVaNgheThuatLanhDao,
                lv.languageVN,
                pv.publisherThongTan,
                2017,
                null,
                500,
                17d,
                24d,
                null,
                496,
                EBookLayout.HARDBACK,
                null,
                null,
                Image.createBookThumbnail("loi_thu_toi_moi_cua_mot_sat_thu_kinh_te_1_2019_02_22_14_51_50.jpg"),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "loi_thu_toi_moi_cua_mot_sat_thu_kinh_te_2_2019_02_22_14_51_50.jpg",
                        "loi_thu_toi_moi_cua_mot_sat_thu_kinh_te_3_2019_02_22_14_51_50.jpg",
                        "loi_thu_toi_moi_cua_mot_sat_thu_kinh_te_4_2019_02_22_14_51_50.jpg",
                        "loi_thu_toi_moi_cua_mot_sat_thu_kinh_te_5_2019_02_22_14_51_50.jpg",
                        "loi_thu_toi_moi_cua_mot_sat_thu_kinh_te_6_2019_02_22_14_51_50.jpg",
                        "loi_thu_toi_moi_cua_mot_sat_thu_kinh_te_7_2019_02_22_14_51_50.jpg",
                        "loi_thu_toi_moi_cua_mot_sat_thu_kinh_te_8_2019_02_22_14_51_50.jpg",
                        "loi_thu_toi_moi_cua_mot_sat_thu_kinh_te_9_2019_02_22_14_51_50.jpg",
                        "loi_thu_toi_moi_cua_mot_sat_thu_kinh_te_10_2019_02_22_14_51_50.jpg",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Nghệ Thuật Lãnh Đạo - Chân Dung Những CEO, Nhà Sáng Lập Và Những Người Thay Đổi Cuộc Chơi Vĩ Đại Nhất Thế Giới",
            new BigDecimal("105000"),
            cv.categoryNhanVatBaiHoc,
            null,
            null,
            sv.supplierTanViet,
            new HashSet<>(
                Collections.singletonList(av.authorDavidMRubenstei)),
                new HashSet<>(),
                null,
                lv.languageVN,
                pv.publisherDanTri,
                2021,
                null,
                500,
                24d,
                16d,
                null,
                480,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail("nghethuanhdao.jpg"),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "nghethuatlanhdao.jpg",
                        "nghe-thuat-lanh-dao-01.jpg",
                        "z2489592573766_cdeb8b40cf0b129581493f28c40e22ef.jpg",
                        "z2489592586233_22751e1e3f964b82763770c99de86bde.jpg",
                        "z2489592635433_30da3d9ea8e3af38ed505a7dffe192b8.jpg",
                        "z2489592575618_414925546e6caaf2f0cc9bf5841aaf11.jpg",
                        "2021_05_14_16_26_15_1-390x510.jpg",
                        "2021_05_14_16_26_15_2-390x510.jpg",
                        "2021_05_14_16_26_15_3-390x510.jpg",
                        "2021_05_14_16_26_15_4-390x510.jpg",
                        "2021_05_14_16_26_15_5-390x510.jpg",
                        "2021_05_14_16_26_15_6-390x510.jpg",
                        "2021_05_14_16_26_15_7-390x510.jpg",
                        "2021_05_14_16_26_15_8-390x510.jpg",
                        "2021_05_14_16_26_15_9-390x510.jpg",
                        "2021_05_14_16_26_15_10-390x510.jpg",
                        "2021_05_14_16_26_15_11-390x510.jpg",
                        "2021_05_14_16_26_15_12-390x510.jpg"
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Phù Thủy Sàn Chứng Khoán Thế Hệ Mới",
            new BigDecimal("259000"),
            cv.categoryChungKhoangBatDongSan,
            null,
            null,
            sv.supplierAlphaBooks,
            new HashSet<>(
                Collections.singletonList(av.authorJackDSchwager)),
                new HashSet<>(),
                srv.seriesComboChienLucDauTuChinhKhoangVaPhuThuySanChinhKhoang,
                lv.languageVN,
                pv.publisherLaoDong,
                2022,
                null,
                560,
                24d,
                16d,
                2d,
                528,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail("ph_-th_y-s_n-ch_ng-kho_n-th_-h_-m_i-24.03.2022.jpg"),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "ph_th_y_s_n_ch_ng_kho_n_th_h_m_i_24.03.2022.jpg",
                        "2022_09_17_11_03_26_1-390x510.gif",
                        "2022_09_17_11_03_26_2-390x510.gif",
                        "2022_09_17_11_03_26_3-390x510.gif",
                        "2022_09_17_11_03_26_4-390x510.gif",
                        "2022_09_17_11_03_26_5-390x510.gif",
                        "2022_09_17_11_03_26_6-390x510.gif",
                        "2022_09_17_11_03_26_7-390x510.gif",
                        "2022_09_17_11_03_26_8-390x510.gif",
                        "2022_09_17_11_03_26_9-390x510.gif",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Chiến Lược Đầu Tư Chứng Khoán",
            new BigDecimal("169000"),
            cv.categoryChungKhoangBatDongSan,
            null,
            null,
            sv.supplierAlphaBooks,
            new HashSet<>(
                Arrays.asList(av.authorDavidBrown, av.authorKassandraBentley)),
                new HashSet<>(),
                srv.seriesComboChienLucDauTuChinhKhoangVaPhuThuySanChinhKhoang,
                lv.languageVN,
                pv.publisherLaoDong,
                2021,
                2021,
                300,
                20.5d,
                13d,
                null,
                368,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail("image_232600.jpg"),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "2021_07_03_21_20_01_1-390x510.gif",
                        "2021_07_03_21_20_01_2-390x510.gif",
                        "2021_07_03_21_20_01_3-390x510.gif",
                        "2021_07_03_21_20_01_4-390x510.gif",
                        "2021_07_03_21_20_01_5-390x510.gif",
                        "2021_07_03_21_20_01_6-390x510.gif",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Phong Thái Của Bậc Thầy Thuyết Phục",
            new BigDecimal("99000"),
            cv.categoryKyNangSong,
            null,
            null,
            sv.supplierAlphaBooks,
            new HashSet<>(
                Collections.singletonList(av.authorDaveLakhani)),
                new HashSet<>(),
                srv.seriesComboSachBacThayThuyetPhuc,
                lv.languageVN,
                pv.publisherLaoDongXaHoi,
                2020,
                2020,
                250,
                20.5d,
                13d,
                null,
                244,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail("phong-thai-cua-bac-thay-thuyet-phuc-01.jpg"),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "phong_thai_cua_bac_thay_thuyet_phuc-01.jpg",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Những Đòn Tâm Lý Trong Thuyết Phục",
            new BigDecimal("169000"),
            cv.categoryKyNangSong,
            null,
            null,
            sv.supplierAlphaBooks,
            new HashSet<>(
                Collections.singletonList(av.authorRobertBCialdini)),
                new HashSet<>(),
                srv.seriesComboSachBacThayThuyetPhuc,
                lv.languageVN,
                pv.publisherLaoDong,
                2020,
                2020,
                400,
                23d,
                15d,
                null,
                360,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail("image_244718_1_5349.jpg"),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "V2021_06_23_08_03_10_1-390x510.jpg",
                        "V2021_06_23_08_03_10_2-390x510.jpg",
                        "V2021_06_23_08_03_10_3-390x510.jpg",
                        "V2021_06_23_08_03_10_4-390x510.jpg",
                        "V2021_06_23_08_03_10_5-390x510.jpg",
                        "V2021_06_23_08_03_10_6-390x510.jpg",
                        "V2021_06_23_08_03_10_7-390x510.jpg",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Lớp Học Mật Ngữ - Tập 1",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(
                Collections.singletonList(av.authorBROgroup)),
                new HashSet<>(),
                srv.seriesLopHocMatNgu,
                lv.languageVN,
                pv.publisherBaoSinhVienVietNamHoaHocTro,
                2019,
                2020,
                128,
                22d,
                15d,
                0.5d,
                0,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail("z2023256882537_9ed40a5bf624cf04f14a8eed6f5090ae_1.jpg"),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "z2023256882537_9ed40a5bf624cf04f14a8eed6f5090ae.jpg",
                        "z2023256899175_c0dca866625680e4d8a8a5908417ba43.jpg",
                    }))));

    i++;
    books[i] =
        new Book(
            "Lớp Học Mật Ngữ - Tập 2",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(
                Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EBookLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("lhmn1.jpg"),
            "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "2021_06_21_08_54_09_1-390x510.jpg",
                        "2021_06_21_08_54_09_2-390x510.jpg",
                        "2021_06_21_08_54_09_3-390x510.jpg",
                        "2021_06_21_08_54_09_4-390x510.jpg",
                        "2021_06_21_08_54_09_5-390x510.jpg",
                        "2021_06_21_08_54_09_6-390x510.jpg",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Lớp Học Mật Ngữ - Tập 3",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(
                Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EBookLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("lhmn3.jpg"),
            "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "2021_06_21_08_54_50_1-390x510.jpg",
                        "2021_06_21_08_54_50_2-390x510.jpg",
                        "2021_06_21_08_54_50_3-390x510.jpg",
                        "2021_06_21_08_54_50_4-390x510.jpg",
                        "2021_06_21_08_54_50_5-390x510.jpg",
                        "2021_06_21_08_54_50_6-390x510.jpg",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Lớp Học Mật Ngữ - Tập 4",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(
                Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EBookLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("lhmn4.jpg"),
            "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "2021_06_21_08_55_41_1-390x510.jpg",
                        "2021_06_21_08_55_41_2-390x510.jpg",
                        "2021_06_21_08_55_41_3-390x510.jpg",
                        "2021_06_21_08_55_41_4-390x510.jpg",
                        "2021_06_21_08_55_41_5-390x510.jpg",
                        "2021_06_21_08_55_41_6-390x510.jpg",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Lớp Học Mật Ngữ - Tập 5",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(
                Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EBookLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("lhmn5.jpg"),
            "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "2021_06_21_08_56_31_1-390x510.jpg",
                        "2021_06_21_08_56_31_2-390x510.jpg",
                        "2021_06_21_08_56_31_3-390x510.jpg",
                        "2021_06_21_08_56_31_4-390x510.jpg",
                        "2021_06_21_08_56_31_5-390x510.jpg",
                        "2021_06_21_08_56_31_6-390x510.jpg",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Lớp Học Mật Ngữ - Tập 6",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(
                Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EBookLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("lhmn6.jpg"),
            "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Lớp Học Mật Ngữ - Tập 7",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(
                Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EBookLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("lhmn7.jpg"),
            "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "2021_06_21_08_57_12_1-390x510.jpg",
                        "2021_06_21_08_57_12_2-390x510.jpg",
                        "2021_06_21_08_57_12_3-390x510.jpg",
                        "2021_06_21_08_57_12_4-390x510.jpg",
                        "2021_06_21_08_57_12_5-390x510.jpg",
                        "2021_06_21_08_57_12_6-390x510.jpg",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Lớp Học Mật Ngữ - Tập 8",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(
                Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EBookLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("lhmn8.jpg"),
            "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "2021_06_21_08_57_55_1-390x510.jpg",
                        "2021_06_21_08_57_55_2-390x510.jpg",
                        "2021_06_21_08_57_55_3-390x510.jpg",
                        "2021_06_21_08_57_55_4-390x510.jpg",
                        "2021_06_21_08_57_55_5-390x510.jpg",
                        "2021_06_21_08_57_55_6-390x510.jpg",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Lớp Học Mật Ngữ - Tập 9",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(
                Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EBookLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("lhmn9.jpg"),
            "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "2021_06_21_08_58_32_1-390x510.jpg",
                        "2021_06_21_08_58_32_2-390x510.jpg",
                        "2021_06_21_08_58_32_3-390x510.jpg",
                        "2021_06_21_08_58_32_4-390x510.jpg",
                        "2021_06_21_08_58_32_5-390x510.jpg",
                        "2021_06_21_08_58_32_6-390x510.jpg",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Lớp Học Mật Ngữ - Tập 10",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(
                Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EBookLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("lhmn10.jpg"),
            "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "2021_06_21_08_59_39_1-390x510.jpg",
                        "2021_06_21_08_59_39_2-390x510.jpg",
                        "2021_06_21_08_59_39_3-390x510.jpg",
                        "2021_06_21_08_59_39_4-390x510.jpg",
                        "2021_06_21_08_59_39_5-390x510.jpg",
                        "2021_06_21_08_59_39_6-390x510.jpg",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Lớp Học Mật Ngữ - Tập 11",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(
                Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EBookLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("lhmn11.jpg"),
            "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "2021_06_21_09_00_59_1-390x510.jpg",
                        "2021_06_21_09_00_59_2-390x510.jpg",
                        "2021_06_21_09_00_59_3-390x510.jpg",
                        "2021_06_21_09_00_59_4-390x510.jpg",
                        "2021_06_21_09_00_59_5-390x510.jpg",
                        "2021_06_21_09_00_59_6-390x510.jpg",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Lớp Học Mật Ngữ - Tập 12",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(
                Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            150,
            22d,
            15d,
            0.5d,
            0,
            EBookLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("lhmn12_1.jpg"),
            "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "2021_06_21_08_51_41_1-390x510.jpg",
                        "2021_06_21_08_51_41_2-390x510.jpg",
                        "2021_06_21_08_51_41_3-390x510.jpg",
                        "2021_06_21_08_51_41_4-390x510.jpg",
                        "2021_06_21_08_51_41_5-390x510.jpg",
                        "2021_06_21_08_51_41_6-390x510.jpg",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Lớp Học Mật Ngữ - Tập 13",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(
                Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            150,
            22d,
            15d,
            0.5d,
            0,
            EBookLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("lhmn13.jpg"),
            "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "lhmn13_1.jpg",
                        "2020_04_24_15_41_08_1-390x510.jpg",
                        "2020_04_24_15_41_08_2-390x510.jpg",
                        "2020_04_24_15_41_08_3-390x510.jpg",
                        "2020_04_24_15_41_08_4-390x510.jpg",
                        "2020_04_24_15_41_08_5-390x510.jpg",
                        "2020_04_24_15_41_08_6-390x510.jpg",
                        "2020_04_24_15_41_08_7-390x510.jpg",
                        "2020_04_24_15_41_08_8-390x510.jpg",
                        "2020_04_24_15_41_08_9-390x510.jpg",
                        "2020_04_24_15_41_08_10-390x510.jpg",
                        "2020_04_24_15_41_08_11-390x510.jpg",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Lớp Học Mật Ngữ - Tập 14",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(
                Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EBookLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("z2117335097693_89800b4f2dc73ddfdf8ade8747580206.jpg"),
            "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "z2117335097693_89800b4f2dc73ddfdf8ade8747580206_1.jpg",
                        "lop_hoc_mat_ngu___tap_14_tai_ban_2020___tang_kem_huy_hieu_mau_ngau_nhien_2_2021_11_18_15_37_21.png",
                        "lop_hoc_mat_ngu___tap_14_tai_ban_2020___tang_kem_huy_hieu_mau_ngau_nhien_3_2021_11_18_15_37_21.jpg",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Lớp Học Mật Ngữ - Tập 15",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(
                Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            135,
            22d,
            15d,
            0.5d,
            0,
            EBookLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("8938507002840.jpg"),
            "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "",
                        "",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Lớp Học Mật Ngữ - Tập 16",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(
                Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            136,
            22d,
            15d,
            0.5d,
            0,
            EBookLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("8938507002857_1.jpg"),
            "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "",
                        "",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Lớp Học Mật Ngữ - Tập 17",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(
                Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            136,
            22d,
            15d,
            0.5d,
            0,
            EBookLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("8938507002864_1.jpg"),
            "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "",
                        "",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Lớp Học Mật Ngữ - Tập 18",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(
                Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            150,
            22d,
            15d,
            0.5d,
            0,
            EBookLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("05b0dc68643430dd9e28edb7a04c4d62_2_1.jpg"),
            "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "z2548854923559_05b0dc68643430dd9e28edb7a04c4d62_2_1.jpg",
                        "z2548855058041_2f48d83ab73967924ad07cd139cccab3.jpg",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Lớp Học Mật Ngữ - Tập 19",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(
                Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EBookLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("l_p-h_c-m_t-ng_-t_p-19.jpg"),
            "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "l_p-h_c-m_t-ng_-t_p-19-qu_.jpg",
                        "l_p-h_c-m_t-ng_-19.jpg",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Lớp Học Mật Ngữ - Tập 20",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(
                Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EBookLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("cover_lhmn20.jpg"),
            "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "1_1.jpeg",
                        "lhmn20_fbcover_hht.jpg",
                        "insta_lhmn20_hht_1.jpg",
                        "untitled-2hhtchd_1.jpg",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "Lớp Học Mật Ngữ - Tập 21",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(
                Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            130,
            22d,
            15d,
            0.5d,
            0,
            EBookLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("untitled-2hhtchd.jpg"),
            "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "untitled-2hhtchd_1.jpg",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "",
            new BigDecimal("000"),
            cv.category,
            null,
            null,
            sv.supplier,
            new HashSet<>(
                Collections.singletonList(av.author)),
                new HashSet<>(),
                null,
                lv.languageVN,
                pv.publisher,
                20,
                null,
                0,
                d,
                d,
                null,
                0,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail(""),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "",
                        "",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "",
            new BigDecimal("000"),
            cv.category,
            null,
            null,
            sv.supplier,
            new HashSet<>(
                Collections.singletonList(av.author)),
                new HashSet<>(),
                null,
                lv.languageVN,
                pv.publisher,
                20,
                null,
                0,
                d,
                d,
                null,
                0,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail(""),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "",
                        "",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "",
            new BigDecimal("000"),
            cv.category,
            null,
            null,
            sv.supplier,
            new HashSet<>(
                Collections.singletonList(av.author)),
                new HashSet<>(),
                null,
                lv.languageVN,
                pv.publisher,
                20,
                null,
                0,
                d,
                d,
                null,
                0,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail(""),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "",
                        "",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "",
            new BigDecimal("000"),
            cv.category,
            null,
            null,
            sv.supplier,
            new HashSet<>(
                Collections.singletonList(av.author)),
                new HashSet<>(),
                null,
                lv.languageVN,
                pv.publisher,
                20,
                null,
                0,
                d,
                d,
                null,
                0,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail(""),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "",
                        "",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "",
            new BigDecimal("000"),
            cv.category,
            null,
            null,
            sv.supplier,
            new HashSet<>(
                Collections.singletonList(av.author)),
                new HashSet<>(),
                null,
                lv.languageVN,
                pv.publisher,
                20,
                null,
                0,
                d,
                d,
                null,
                0,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail(""),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "",
                        "",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "",
            new BigDecimal("000"),
            cv.category,
            null,
            null,
            sv.supplier,
            new HashSet<>(
                Collections.singletonList(av.author)),
                new HashSet<>(),
                null,
                lv.languageVN,
                pv.publisher,
                20,
                null,
                0,
                d,
                d,
                null,
                0,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail(""),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "",
                        "",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "",
            new BigDecimal("000"),
            cv.category,
            null,
            null,
            sv.supplier,
            new HashSet<>(
                Collections.singletonList(av.author)),
                new HashSet<>(),
                null,
                lv.languageVN,
                pv.publisher,
                20,
                null,
                0,
                d,
                d,
                null,
                0,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail(""),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "",
                        "",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "",
            new BigDecimal("000"),
            cv.category,
            null,
            null,
            sv.supplier,
            new HashSet<>(
                Collections.singletonList(av.author)),
                new HashSet<>(),
                null,
                lv.languageVN,
                pv.publisher,
                20,
                null,
                0,
                d,
                d,
                null,
                0,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail(""),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "",
                        "",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "",
            new BigDecimal("000"),
            cv.category,
            null,
            null,
            sv.supplier,
            new HashSet<>(
                Collections.singletonList(av.author)),
                new HashSet<>(),
                null,
                lv.languageVN,
                pv.publisher,
                20,
                null,
                0,
                d,
                d,
                null,
                0,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail(""),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "",
                        "",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "",
            new BigDecimal("000"),
            cv.category,
            null,
            null,
            sv.supplier,
            new HashSet<>(
                Collections.singletonList(av.author)),
                new HashSet<>(),
                null,
                lv.languageVN,
                pv.publisher,
                20,
                null,
                0,
                d,
                d,
                null,
                0,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail(""),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "",
                        "",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "",
            new BigDecimal("000"),
            cv.category,
            null,
            null,
            sv.supplier,
            new HashSet<>(
                Collections.singletonList(av.author)),
                new HashSet<>(),
                null,
                lv.languageVN,
                pv.publisher,
                20,
                null,
                0,
                d,
                d,
                null,
                0,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail(""),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "",
                        "",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "",
            new BigDecimal("000"),
            cv.category,
            null,
            null,
            sv.supplier,
            new HashSet<>(
                Collections.singletonList(av.author)),
                new HashSet<>(),
                null,
                lv.languageVN,
                pv.publisher,
                20,
                null,
                0,
                d,
                d,
                null,
                0,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail(""),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "",
                        "",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "",
            new BigDecimal("000"),
            cv.category,
            null,
            null,
            sv.supplier,
            new HashSet<>(
                Collections.singletonList(av.author)),
                new HashSet<>(),
                null,
                lv.languageVN,
                pv.publisher,
                20,
                null,
                0,
                d,
                d,
                null,
                0,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail(""),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "",
                        "",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "",
            new BigDecimal("000"),
            cv.category,
            null,
            null,
            sv.supplier,
            new HashSet<>(
                Collections.singletonList(av.author)),
                new HashSet<>(),
                null,
                lv.languageVN,
                pv.publisher,
                20,
                null,
                0,
                d,
                d,
                null,
                0,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail(""),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "",
                        "",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "",
            new BigDecimal("000"),
            cv.category,
            null,
            null,
            sv.supplier,
            new HashSet<>(
                Collections.singletonList(av.author)),
                new HashSet<>(),
                null,
                lv.languageVN,
                pv.publisher,
                20,
                null,
                0,
                d,
                d,
                null,
                0,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail(""),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "",
                        "",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "",
            new BigDecimal("000"),
            cv.category,
            null,
            null,
            sv.supplier,
            new HashSet<>(
                Collections.singletonList(av.author)),
                new HashSet<>(),
                null,
                lv.languageVN,
                pv.publisher,
                20,
                null,
                0,
                d,
                d,
                null,
                0,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail(""),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "",
                        "",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "",
            new BigDecimal("000"),
            cv.category,
            null,
            null,
            sv.supplier,
            new HashSet<>(
                Collections.singletonList(av.author)),
                new HashSet<>(),
                null,
                lv.languageVN,
                pv.publisher,
                20,
                null,
                0,
                d,
                d,
                null,
                0,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail(""),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "",
                        "",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "",
            new BigDecimal("000"),
            cv.category,
            null,
            null,
            sv.supplier,
            new HashSet<>(
                Collections.singletonList(av.author)),
                new HashSet<>(),
                null,
                lv.languageVN,
                pv.publisher,
                20,
                null,
                0,
                d,
                d,
                null,
                0,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail(""),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "",
                        "",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "",
            new BigDecimal("000"),
            cv.category,
            null,
            null,
            sv.supplier,
            new HashSet<>(
                Collections.singletonList(av.author)),
                new HashSet<>(),
                null,
                lv.languageVN,
                pv.publisher,
                20,
                null,
                0,
                d,
                d,
                null,
                0,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail(""),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "",
                        "",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "",
            new BigDecimal("000"),
            cv.category,
            null,
            null,
            sv.supplier,
            new HashSet<>(
                Collections.singletonList(av.author)),
                new HashSet<>(),
                null,
                lv.languageVN,
                pv.publisher,
                20,
                null,
                0,
                d,
                d,
                null,
                0,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail(""),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "",
                        "",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    i++;
    books[i] =
        new Book(
            "",
            new BigDecimal("000"),
            cv.category,
            null,
            null,
            sv.supplier,
            new HashSet<>(
                Collections.singletonList(av.author)),
                new HashSet<>(),
                null,
                lv.languageVN,
                pv.publisher,
                20,
                null,
                0,
                d,
                d,
                null,
                0,
                EBookLayout.PAPERBACK,
                null,
                null,
                Image.createBookThumbnail(""),
                "");
    books[i] = this.bookRepo.save(books[i]);
    books[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                        "",
                        "",
                    }))));
    books[i] = this.bookRepo.save(books[i]);

    return i;
  }

  static class SupplierVariable {
    public SupplierVariable() {}

    public Supplier supplierMinhLong;
    public Supplier supplierDongA;
    public Supplier supplierNhaNam;
    public Supplier supplierSkyBooks;
    public Supplier supplierNXBTre;
    public Supplier supplierFIRSTNEWS;
        public Supplier supplierThaiHa;
        public Supplier supplierTanViet;
        public Supplier supplierAlphaBooks;
        public Supplier supplierBaoTienPhong;
    //    public Supplier supplier;
    //    public Supplier supplier;
    //    public Supplier supplier;
    //    public Supplier supplier;
    //    public Supplier supplier;
    //    public Supplier supplier;
    //    public Supplier supplier;
    //    public Supplier supplier;
    //    public Supplier supplier;
    //    public Supplier supplier;
  }

  public void createSupplier(SupplierVariable sv) {
    sv.supplierMinhLong = new Supplier("Minh Long");
    sv.supplierMinhLong = this.supplierRepo.save(sv.supplierMinhLong);

    sv.supplierDongA = new Supplier("Đông A");
    sv.supplierDongA = this.supplierRepo.save(sv.supplierDongA);

    sv.supplierNhaNam = new Supplier("Nhã Nam");
    sv.supplierNhaNam = this.supplierRepo.save(sv.supplierNhaNam);

    sv.supplierSkyBooks = new Supplier("Skybooks");
    sv.supplierSkyBooks = this.supplierRepo.save(sv.supplierSkyBooks);

    sv.supplierNXBTre = new Supplier("NXB Trẻ");
    sv.supplierNXBTre = this.supplierRepo.save(sv.supplierNXBTre);

    sv.supplierFIRSTNEWS = new Supplier("FIRST NEWS");
    sv.supplierFIRSTNEWS = this.supplierRepo.save(sv.supplierFIRSTNEWS);

    sv.supplierThaiHa = new Supplier("Thái Hà");
    sv.supplierThaiHa = this.supplierRepo.save(sv.supplierThaiHa);

    sv.supplierTanViet = new Supplier("Tân Việt");
    sv.supplierTanViet = this.supplierRepo.save(sv.supplierTanViet);

    sv.supplierAlphaBooks = new Supplier("Alpha Books");
    sv.supplierAlphaBooks = this.supplierRepo.save(sv.supplierAlphaBooks);

    sv.supplierBaoTienPhong = new Supplier("Báo Tiền Phong");
    sv.supplierBaoTienPhong = this.supplierRepo.save(sv.supplierBaoTienPhong);
//
//    sv.supplier = new Supplier("");
//    sv.supplier = this.supplierRepo.save(sv.supplier);
//
//    sv.supplier = new Supplier("");
//    sv.supplier = this.supplierRepo.save(sv.supplier);
//
//    sv.supplier = new Supplier("");
//    sv.supplier = this.supplierRepo.save(sv.supplier);
//
//    sv.supplier = new Supplier("");
//    sv.supplier = this.supplierRepo.save(sv.supplier);
//
//    sv.supplier = new Supplier("");
//    sv.supplier = this.supplierRepo.save(sv.supplier);
//
//    sv.supplier = new Supplier("");
//    sv.supplier = this.supplierRepo.save(sv.supplier);
//
//    sv.supplier = new Supplier("");
//    sv.supplier = this.supplierRepo.save(sv.supplier);
//
//    sv.supplier = new Supplier("");
//    sv.supplier = this.supplierRepo.save(sv.supplier);
//
//    sv.supplier = new Supplier("");
//    sv.supplier = this.supplierRepo.save(sv.supplier);
//
//    sv.supplier = new Supplier("");
//    sv.supplier = this.supplierRepo.save(sv.supplier);
//
//    sv.supplier = new Supplier("");
//    sv.supplier = this.supplierRepo.save(sv.supplier);

  }

  static class PublisherVariable {
    public PublisherVariable() {}

    public Publisher publisherPhuNu;
    public Publisher publisherDanTri;
    public Publisher publisherHoiNhaVan;
    public Publisher publisherPhuNuVietNam;
    public Publisher publisherTre;
    public Publisher publisherTongHopTPHCM;
        public Publisher publisherLaoDong;
        public Publisher publisherThongTan;
        public Publisher publisherLaoDongXaHoi;
        public Publisher publisherBaoSinhVienVietNamHoaHocTro;
    //    public Publisher publisher;
    //    public Publisher publisher;
    //    public Publisher publisher;
    //    public Publisher publisher;
    //    public Publisher publisher;
    //    public Publisher publisher;
    //    public Publisher publisher;
  }

  public void createPublisher(PublisherVariable pv) {
    pv.publisherPhuNu = new Publisher("NXB Phụ Nữ");
    pv.publisherPhuNu = this.publisherRepo.save(pv.publisherPhuNu);

    pv.publisherDanTri = new Publisher("NXB Dân Trí");
    pv.publisherDanTri = this.publisherRepo.save(pv.publisherDanTri);

    pv.publisherHoiNhaVan = new Publisher("NXB Hội Nhà Văn");
    pv.publisherHoiNhaVan = this.publisherRepo.save(pv.publisherHoiNhaVan);

    pv.publisherPhuNuVietNam = new Publisher("NXB Phụ Nữ Việt Nam");
    pv.publisherPhuNuVietNam = this.publisherRepo.save(pv.publisherPhuNuVietNam);

    pv.publisherTre = new Publisher("NXB Trẻ");
    pv.publisherTre = this.publisherRepo.save(pv.publisherTre);

    pv.publisherTongHopTPHCM = new Publisher("NXB Tổng Hợp TPHCM");
    pv.publisherTongHopTPHCM = this.publisherRepo.save(pv.publisherTongHopTPHCM);

    pv.publisherLaoDong = new Publisher("NXB Lao Động");
    pv.publisherLaoDong = this.publisherRepo.save(pv.publisherLaoDong);

    pv.publisherThongTan = new Publisher("NXB Thông Tấn");
    pv.publisherThongTan = this.publisherRepo.save(pv.publisherThongTan);

    pv.publisherLaoDongXaHoi = new Publisher("NXB Lao Động - Xã Hội");
    pv.publisherLaoDongXaHoi = this.publisherRepo.save(pv.publisherLaoDongXaHoi);

    pv.publisherBaoSinhVienVietNamHoaHocTro = new Publisher("Báo Sinh Viên VN - Hoa Học Trò");
    pv.publisherBaoSinhVienVietNamHoaHocTro = this.publisherRepo.save(pv.publisherBaoSinhVienVietNamHoaHocTro);
//
//    pv.publisher = new Publisher("");
//    pv.publisher = this.publisherRepo.save(pv.publisher);
//
//    pv.publisher = new Publisher("");
//    pv.publisher = this.publisherRepo.save(pv.publisher);
//
//    pv.publisher = new Publisher("");
//    pv.publisher = this.publisherRepo.save(pv.publisher);
//
//    pv.publisher = new Publisher("");
//    pv.publisher = this.publisherRepo.save(pv.publisher);
//
//    pv.publisher = new Publisher("");
//    pv.publisher = this.publisherRepo.save(pv.publisher);
//
//    pv.publisher = new Publisher("");
//    pv.publisher = this.publisherRepo.save(pv.publisher);
//
//    pv.publisher = new Publisher("");
//    pv.publisher = this.publisherRepo.save(pv.publisher);
//
//    pv.publisher = new Publisher("");
//    pv.publisher = this.publisherRepo.save(pv.publisher);
//
//    pv.publisher = new Publisher("");
//    pv.publisher = this.publisherRepo.save(pv.publisher);
//
//    pv.publisher = new Publisher("");
//    pv.publisher = this.publisherRepo.save(pv.publisher);
  }

  static class AuthorVariable {
    public AuthorVariable() {}

    public Author authorHoaDuong;
    public Author authorMarioPuzo;
    public Author authorPauloCoelho;
    public Author authorKhotudien;
    public Author authorJoseMauro;
    public Author authorHaeMin;
    public Author authorNguyenNhatAnh;
    public Author authorHarukiMurakami;
    public Author authorNguyenPhong;
        public Author authorNapoleonHill;
        public Author authorJohnPerkins;
        public Author authorDavidMRubenstei;
        public Author authorDavidBrown;
        public Author authorKassandraBentley;
        public Author authorRobertBCialdini;
        public Author authorDaveLakhani;
        public Author authorJackDSchwager;
        public Author authorBROgroup;
    //    public Author author;
    //    public Author author;
    //    public Author author;
    //    public Author author;
    //    public Author author;
    //    public Author author;
    //    public Author author;
    //    public Author author;
    //    public Author author;
    //    public Author author;
    //    public Author author;
    //    public Author author;
    //    public Author author;
    //    public Author author;
  }

  public void createAuthor(AuthorVariable av) {
    av.authorHoaDuong = new Author("Hoa Dương");
    av.authorHoaDuong = this.authorRepo.save(av.authorHoaDuong);

    av.authorMarioPuzo = new Author("Mario Puzo");
    av.authorMarioPuzo = this.authorRepo.save(av.authorMarioPuzo);

    av.authorPauloCoelho = new Author("Paulo Coelho");
    av.authorPauloCoelho = this.authorRepo.save(av.authorPauloCoelho);

    av.authorKhotudien = new Author("Khotudien");
    av.authorKhotudien = this.authorRepo.save(av.authorKhotudien);

    av.authorJoseMauro = new Author("José Mauro de Vasconcelos");
    av.authorJoseMauro = this.authorRepo.save(av.authorJoseMauro);

    av.authorHaeMin = new Author("Hae Min");
    av.authorHaeMin = this.authorRepo.save(av.authorHaeMin);

    av.authorNguyenNhatAnh = new Author("Nguyễn Nhật Ánh");
    av.authorNguyenNhatAnh = this.authorRepo.save(av.authorNguyenNhatAnh);

    av.authorHarukiMurakami = new Author("Haruki Murakami");
    av.authorHarukiMurakami = this.authorRepo.save(av.authorHarukiMurakami);

    av.authorNguyenPhong = new Author("Nguyên Phong");
    av.authorNguyenPhong = this.authorRepo.save(av.authorNguyenPhong);

    av.authorNapoleonHill = new Author("Napoleon Hill");
    av.authorNapoleonHill = this.authorRepo.save(av.authorNapoleonHill);

    av.authorJohnPerkins = new Author("John Perkins");
    av.authorJohnPerkins = this.authorRepo.save(av.authorJohnPerkins);

    av.authorDavidMRubenstei = new Author("David M Rubenstei");
    av.authorDavidMRubenstei = this.authorRepo.save(av.authorDavidMRubenstei);

    av.authorDavidBrown = new Author("David Brown");
    av.authorDavidBrown = this.authorRepo.save(av.authorDavidBrown);

    av.authorKassandraBentley = new Author("Kassandra Bentley");
    av.authorKassandraBentley = this.authorRepo.save(av.authorKassandraBentley);

    av.authorRobertBCialdini = new Author("Robert B Cialdini");
    av.authorRobertBCialdini = this.authorRepo.save(av.authorRobertBCialdini);

    av.authorDaveLakhani = new Author("Dave Lakhani");
    av.authorDaveLakhani = this.authorRepo.save(av.authorDaveLakhani);

    av.authorJackDSchwager = new Author("Jack D Schwager");
    av.authorJackDSchwager = this.authorRepo.save(av.authorJackDSchwager);

    av.authorBROgroup = new Author("B R O group");
    av.authorBROgroup = this.authorRepo.save(av.authorBROgroup);
//
//    av.author = new Author("");
//    av.author = this.authorRepo.save(av.author);
//
//    av.author = new Author("");
//    av.author = this.authorRepo.save(av.author);
//
//    av.author = new Author("");
//    av.author = this.authorRepo.save(av.author);
//
//    av.author = new Author("");
//    av.author = this.authorRepo.save(av.author);
//
//    av.author = new Author("");
//    av.author = this.authorRepo.save(av.author);
//
//    av.author = new Author("");
//    av.author = this.authorRepo.save(av.author);
//
//    av.author = new Author("");
//    av.author = this.authorRepo.save(av.author);
//
//    av.author = new Author("");
//    av.author = this.authorRepo.save(av.author);
//
//    av.author = new Author("");
//    av.author = this.authorRepo.save(av.author);
//
//    av.author = new Author("");
//    av.author = this.authorRepo.save(av.author);
//
//    av.author = new Author("");
//    av.author = this.authorRepo.save(av.author);
//
//    av.author = new Author("");
//    av.author = this.authorRepo.save(av.author);
  }

  static class TranslatorVariable {
    private TranslatorVariable() {}

    public Translator translatorLeChuCau;
    public Translator translatorNguyenBichLan;
    public Translator translatorToYenLy;
    public Translator translatorThuyHang;
    public Translator translatorAnhThu;
//    public Translator translator;
//    public Translator translator;
//    public Translator translator;
//    public Translator translator;
//    public Translator translator;
//    public Translator translator;
//    public Translator translator;
//    public Translator translator;
//    public Translator translator;
//    public Translator translator;
//    public Translator translator;
//    public Translator translator;
//    public Translator translator;
//    public Translator translator;
//    public Translator translator;
  }

  public void createTranslator(TranslatorVariable tv) {
    tv.translatorLeChuCau = new Translator("Lê Chu Cầu", EGender.MALE);
    tv.translatorLeChuCau = this.translatorRepo.save(tv.translatorLeChuCau);

    tv.translatorNguyenBichLan = new Translator("Nguyễn Bích Lan", EGender.FEMALE);
    tv.translatorNguyenBichLan = this.translatorRepo.save(tv.translatorNguyenBichLan);

    tv.translatorToYenLy = new Translator("Tô Yến Ly", EGender.FEMALE);
    tv.translatorToYenLy = this.translatorRepo.save(tv.translatorToYenLy);

    tv.translatorThuyHang = new Translator("Thúy Hằng", EGender.FEMALE);
    tv.translatorThuyHang = this.translatorRepo.save(tv.translatorThuyHang);

    tv.translatorAnhThu = new Translator("Anh Thư", EGender.FEMALE);
    tv.translatorAnhThu = this.translatorRepo.save(tv.translatorAnhThu);
//
//    tv.translator = new Translator("");
//    tv.translator = this.translatorRepo.save(tv.translator);
//
//    tv.translator = new Translator("");
//    tv.translator = this.translatorRepo.save(tv.translator);
//
//    tv.translator = new Translator("");
//    tv.translator = this.translatorRepo.save(tv.translator);
//
//    tv.translator = new Translator("");
//    tv.translator = this.translatorRepo.save(tv.translator);
//
//    tv.translator = new Translator("");
//    tv.translator = this.translatorRepo.save(tv.translator);
//
//    tv.translator = new Translator("");
//    tv.translator = this.translatorRepo.save(tv.translator);
//
//    tv.translator = new Translator("");
//    tv.translator = this.translatorRepo.save(tv.translator);
//
//    tv.translator = new Translator("");
//    tv.translator = this.translatorRepo.save(tv.translator);
//
//    tv.translator = new Translator("");
//    tv.translator = this.translatorRepo.save(tv.translator);
//
//    tv.translator = new Translator("");
//    tv.translator = this.translatorRepo.save(tv.translator);
//
//    tv.translator = new Translator("");
//    tv.translator = this.translatorRepo.save(tv.translator);
//
//    tv.translator = new Translator("");
//    tv.translator = this.translatorRepo.save(tv.translator);
//
//    tv.translator = new Translator("");
//    tv.translator = this.translatorRepo.save(tv.translator);
//
//    tv.translator = new Translator("");
//    tv.translator = this.translatorRepo.save(tv.translator);
  }

  static class SeriesVariable {
    private SeriesVariable() {

    }

    public Series seriesComboSachKyNangTuyetChieuLamGiau;
    public Series seriesComboLoiThuToiVaNgheThuatLanhDao;
        public Series seriesComboChienLucDauTuChinhKhoangVaPhuThuySanChinhKhoang;
    public Series seriesComboSachBacThayThuyetPhuc;

    public Series seriesLopHocMatNgu;
//    public Series series;
//    public Series series;
//    public Series series;
//    public Series series;
//    public Series series;
  }

  public void createSeries(SeriesVariable srv, AuthorVariable av, SupplierVariable sv,  PublisherVariable pv) {
    srv.seriesComboSachKyNangTuyetChieuLamGiau = Series.createCombo("Combo Sách Kỹ Năng, Tuyệt Chiêu Làm Giàu: Chiến Thắng Con Quỷ Trong Bạn + Think And Grow Rich - 13 Nguyên Tắc Nghĩ Giàu Làm Giàu (Bộ 2 Cuốn)", new HashSet<>(
        Collections.singletonList(av.authorNapoleonHill)), sv.supplierThaiHa, pv.publisherLaoDong);
    srv.seriesComboSachKyNangTuyetChieuLamGiau = this.seriesRepo.save(srv.seriesComboSachKyNangTuyetChieuLamGiau);

    srv.seriesComboLoiThuToiVaNgheThuatLanhDao = Series.createCombo("Combo Sách Lời Thú Tội Mới Của Một Sát Thủ Kinh Tế + Nghệ Thuật Lãnh Đạo (Bộ 2 Cuốn)", new HashSet<>(
        Arrays.asList(av.authorJohnPerkins, av.authorDavidMRubenstei)), sv.supplierTanViet, null);
    srv.seriesComboLoiThuToiVaNgheThuatLanhDao = this.seriesRepo.save(srv.seriesComboLoiThuToiVaNgheThuatLanhDao);

    srv.seriesComboChienLucDauTuChinhKhoangVaPhuThuySanChinhKhoang = Series.createCombo("Combo Sách Chiến Lược Đầu Tư Chứng Khoán + Phù Thủy Sàn Chứng Khoán Thế Hệ Mới (Bộ 2 Cuốn)", new HashSet<>(
        Arrays.asList(av.authorDavidBrown, av.authorKassandraBentley)), sv.supplierAlphaBooks, pv.publisherLaoDong);
    srv.seriesComboChienLucDauTuChinhKhoangVaPhuThuySanChinhKhoang = this.seriesRepo.save(srv.seriesComboChienLucDauTuChinhKhoangVaPhuThuySanChinhKhoang);

    srv.seriesComboSachBacThayThuyetPhuc = Series.createCombo("Combo Sách Bậc Thầy Thuyết Phục: Những Đòn Tâm Lý Trong Thuyết Phục + Phong Thái Của Bậc Thầy Thuyết Phục (Bộ 2 Cuốn)", new HashSet<>(
        Arrays.asList(av.authorRobertBCialdini, av.authorDaveLakhani, av.authorJackDSchwager)), sv.supplierAlphaBooks, null);
    srv.seriesComboSachBacThayThuyetPhuc = this.seriesRepo.save(srv.seriesComboSachBacThayThuyetPhuc);
//
//    srv.seriesCombo = Series.createCombo("", new HashSet<>(
//        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
//    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
//
//    srv.seriesCombo = Series.createCombo("", new HashSet<>(
//        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
//    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
//
//    srv.seriesCombo = Series.createCombo("", new HashSet<>(
//        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
//    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
//
//    srv.seriesCombo = Series.createCombo("", new HashSet<>(
//        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
//    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
//
//    srv.seriesCombo = Series.createCombo("", new HashSet<>(
//        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
//    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
//
//    srv.seriesCombo = Series.createCombo("", new HashSet<>(
//        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
//    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
//
//    srv.seriesCombo = Series.createCombo("", new HashSet<>(
//        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
//    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
//
//    srv.seriesCombo = Series.createCombo("", new HashSet<>(
//        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
//    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
//
//    srv.seriesCombo = Series.createCombo("", new HashSet<>(
//        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
//    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
//
//    srv.seriesCombo = Series.createCombo("", new HashSet<>(
//        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
//    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
//
//    srv.seriesCombo = Series.createCombo("", new HashSet<>(
//        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
//    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
//
//    srv.seriesCombo = Series.createCombo("", new HashSet<>(
//        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
//    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
//
//    srv.seriesCombo = Series.createCombo("", new HashSet<>(
//        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
//    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
//
//    srv.seriesCombo = Series.createCombo("", new HashSet<>(
//        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
//    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
//
//    srv.seriesCombo = Series.createCombo("", new HashSet<>(
//        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
//    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
//
    srv.seriesLopHocMatNgu = Series.createSeries("Lớp Học Mật Ngữ", new HashSet<>(
        Collections.singletonList(av.authorBROgroup)), pv.publisherBaoSinhVienVietNamHoaHocTro);
    srv.seriesLopHocMatNgu = this.seriesRepo.save(srv.seriesLopHocMatNgu);
//
//    srv.series = Series.createSeries("", new HashSet<>(
//        Collections.singletonList(av.author)), pv.publisher);
//    srv.series = this.seriesRepo.save(srv.series);
//
//    srv.series = Series.createSeries("", new HashSet<>(
//        Collections.singletonList(av.author)), pv.publisher);
//    srv.series = this.seriesRepo.save(srv.series);
//
//    srv.series = Series.createSeries("", new HashSet<>(
//        Collections.singletonList(av.author)), pv.publisher);
//    srv.series = this.seriesRepo.save(srv.series);
//
//    srv.series = Series.createSeries("", new HashSet<>(
//        Collections.singletonList(av.author)), pv.publisher);
//    srv.series = this.seriesRepo.save(srv.series);
//
//    srv.series = Series.createSeries("", new HashSet<>(
//        Collections.singletonList(av.author)), pv.publisher);
//    srv.series = this.seriesRepo.save(srv.series);
//
//    srv.series = Series.createSeries("", new HashSet<>(
//        Collections.singletonList(av.author)), pv.publisher);
//    srv.series = this.seriesRepo.save(srv.series);
//
//    srv.series = Series.createSeries("", new HashSet<>(
//        Collections.singletonList(av.author)), pv.publisher);
//    srv.series = this.seriesRepo.save(srv.series);
//
//    srv.series = Series.createSeries("", new HashSet<>(
//        Collections.singletonList(av.author)), pv.publisher);
//    srv.series = this.seriesRepo.save(srv.series);
//
//    srv.series = Series.createSeries("", new HashSet<>(
//        Collections.singletonList(av.author)), pv.publisher);
//    srv.series = this.seriesRepo.save(srv.series);
//
//    srv.series = Series.createSeries("", new HashSet<>(
//        Collections.singletonList(av.author)), pv.publisher);
//    srv.series = this.seriesRepo.save(srv.series);
//
//    srv.series = Series.createSeries("", new HashSet<>(
//        Collections.singletonList(av.author)), pv.publisher);
//    srv.series = this.seriesRepo.save(srv.series);
//
//    srv.series = Series.createSeries("", new HashSet<>(
//        Collections.singletonList(av.author)), pv.publisher);
//    srv.series = this.seriesRepo.save(srv.series);
    
  }

  static class GenreVariable {
    public GenreVariable() {}
  }

  public void createGenre(GenreVariable gv) {}

  static class CategoryVariable {
    public CategoryVariable() {}

    //
    public Category categorySachVanHoc;
    public Category categoryTieuThuyet;
    public Category categoryTruyenNganTanVan;
    public Category categoryLightNovel;
    public Category categoryNgonTinh;

    //
    public Category categorySachThieuNhi;
    public Category categoryManga;
    public Category categoryKienThucBachKhoa;
    public Category categorySachTranhKyNangSong;
    public Category categoryVuaHocVuaChoi;
    public Category categoryTruyenThieuNhi;

    //
    public Category categorySachKinhTe;
    public Category categoryNhanVatBaiHoc;
    public Category categoryQuanTriLanhDao;
    public Category categoryMarketing;
    public Category categoryPhanTichKinhTe;
    public Category categoryChungKhoangBatDongSan;

    //
    public Category categorySachTieuSuHoiKy;
    public Category categoryCauTruyenCuocDoi;
    public Category categoryChinhTri;
    public Category categoryKinhTe;
    public Category categoryNgheThuatGiaiTri;

    //
    public Category categorySachTamLyKyNangSong;
    public Category categoryKyNangSong;
    public Category categoryRenLuyenNhanCach;
    public Category categoryTamLy;

    //
    public Category categorySachChoTuoiMoiLon;
    public Category categorySachGiaoKhoaThamKhao;
    public Category categorySachGiaoKhoa;
    public Category categorySachThamKhao;
    public Category categoryLuyenThiTHPT;
    public Category categoryMauGiao;

    //
    public Category categorySachNuoiDayCon;
    public Category categoryCamNangLamChaMe;
    public Category categoryPhuongPhapGiaoDucTre;
    public Category categoryPhatTrienTriTueChoTre;
    public Category categoryPhatTrienKyNangChoTre;

    //
    public Category categorySachHocNgoaiNgu;
    public Category categoryTiengAnh;
    public Category categoryTiengNhat;
    public Category categoryTiengHoa;
    public Category categoryTiengHan;
  }

  public void createCategory(CategoryVariable cv) {
    cv.categorySachVanHoc = new Category("Sách Văn Học", null);
    cv.categorySachVanHoc = this.categoryRepo.save(cv.categorySachVanHoc);

    cv.categoryTieuThuyet = new Category("Tiểu Thuyết", cv.categorySachVanHoc);
    cv.categoryTieuThuyet = this.categoryRepo.save(cv.categoryTieuThuyet);

    cv.categoryTruyenNganTanVan = new Category("Truyện Ngắn - Tản Văn", cv.categorySachVanHoc);
    cv.categoryTruyenNganTanVan = this.categoryRepo.save(cv.categoryTruyenNganTanVan);

    cv.categoryLightNovel = new Category("Light Novel", cv.categorySachVanHoc);
    cv.categoryLightNovel = this.categoryRepo.save(cv.categoryLightNovel);

    cv.categoryNgonTinh = new Category("Ngôn Tình", cv.categorySachVanHoc);
    cv.categoryNgonTinh = this.categoryRepo.save(cv.categoryNgonTinh);

    //

    cv.categorySachKinhTe = new Category("Sách Kinh Tế", null);
    cv.categorySachKinhTe = this.categoryRepo.save(cv.categorySachKinhTe);

    cv.categoryNhanVatBaiHoc = new Category("Nhân Vật - Bài Học Kinh Doanh", cv.categorySachKinhTe);
    cv.categoryNhanVatBaiHoc = this.categoryRepo.save(cv.categoryNhanVatBaiHoc);

    cv.categoryQuanTriLanhDao = new Category("Quản Trị - Lãnh Đạo", cv.categorySachKinhTe);
    cv.categoryQuanTriLanhDao = this.categoryRepo.save(cv.categoryQuanTriLanhDao);

    cv.categoryMarketing = new Category("Marketing - Bán Hàng", cv.categorySachKinhTe);
    cv.categoryMarketing = this.categoryRepo.save(cv.categoryMarketing);

    cv.categoryPhanTichKinhTe = new Category("Phân Tích Kinh Tế", cv.categorySachKinhTe);
    cv.categoryPhanTichKinhTe = this.categoryRepo.save(cv.categoryPhanTichKinhTe);

    cv.categoryChungKhoangBatDongSan = new Category("Chứng Khoán - Bất Động Sản", cv.categorySachKinhTe);
    cv.categoryChungKhoangBatDongSan = this.categoryRepo.save(cv.categoryChungKhoangBatDongSan);

    //

    cv.categorySachTamLyKyNangSong = new Category("Sách Tâm Lý - Kỹ Năng Sống", null);
    cv.categorySachTamLyKyNangSong = this.categoryRepo.save(cv.categorySachTamLyKyNangSong);

    cv.categoryKyNangSong = new Category("Kỹ Năng Sống", cv.categorySachTamLyKyNangSong);
    cv.categoryKyNangSong = this.categoryRepo.save(cv.categoryKyNangSong);

    cv.categoryRenLuyenNhanCach =
        new Category("Rèn Luyện Nhân Cách", cv.categorySachTamLyKyNangSong);
    cv.categoryRenLuyenNhanCach = this.categoryRepo.save(cv.categoryRenLuyenNhanCach);

    cv.categoryTamLy = new Category("Tâm Lý", cv.categorySachTamLyKyNangSong);
    cv.categoryTamLy = this.categoryRepo.save(cv.categoryTamLy);

    cv.categorySachChoTuoiMoiLon =
        new Category("Sách Cho Tuổi Mới Lớn", cv.categorySachTamLyKyNangSong);
    cv.categorySachChoTuoiMoiLon = this.categoryRepo.save(cv.categorySachChoTuoiMoiLon);

    //

    cv.categorySachNuoiDayCon = new Category("Sách Nuôi Dạy Con", null);
    cv.categorySachNuoiDayCon = this.categoryRepo.save(cv.categorySachNuoiDayCon);

    cv.categoryCamNangLamChaMe = new Category("Cẩm Nang Làm Cha Mẹ", cv.categorySachNuoiDayCon);
    cv.categoryCamNangLamChaMe = this.categoryRepo.save(cv.categoryCamNangLamChaMe);

    cv.categoryPhuongPhapGiaoDucTre =
        new Category("Phương Pháp Giáo Dục Trẻ Các Nước", cv.categorySachNuoiDayCon);
    cv.categoryPhuongPhapGiaoDucTre = this.categoryRepo.save(cv.categoryPhuongPhapGiaoDucTre);

    cv.categoryPhatTrienTriTueChoTre =
        new Category("Phát Triển Trí Tuệ Cho Trẻ", cv.categorySachNuoiDayCon);
    cv.categoryPhatTrienTriTueChoTre = this.categoryRepo.save(cv.categoryPhatTrienTriTueChoTre);

    cv.categoryPhatTrienKyNangChoTre =
        new Category("Phát Triển Kỹ Năng Cho Trẻ", cv.categorySachNuoiDayCon);
    cv.categoryPhatTrienKyNangChoTre = this.categoryRepo.save(cv.categoryPhatTrienKyNangChoTre);

    //

    cv.categorySachThieuNhi = new Category("Sách Thiếu Nhi", null);
    cv.categorySachThieuNhi = this.categoryRepo.save(cv.categorySachThieuNhi);

    cv.categoryManga = new Category("Manga - Comic", cv.categorySachThieuNhi);
    cv.categoryManga = this.categoryRepo.save(cv.categoryManga);

    cv.categoryKienThucBachKhoa = new Category("Kiến Thức Bách Khoa", cv.categorySachThieuNhi);
    cv.categoryKienThucBachKhoa = this.categoryRepo.save(cv.categoryKienThucBachKhoa);

    cv.categorySachTranhKyNangSong =
        new Category("Sách Tranh Kỹ Năng Sống Cho Trẻ", cv.categorySachThieuNhi);
    cv.categorySachTranhKyNangSong = this.categoryRepo.save(cv.categorySachTranhKyNangSong);

    cv.categoryVuaHocVuaChoi =
        new Category("Vừa Học - Vừa Học Vừa Chơi Với Trẻ", cv.categorySachThieuNhi);
    cv.categoryVuaHocVuaChoi = this.categoryRepo.save(cv.categoryVuaHocVuaChoi);

    cv.categoryTruyenThieuNhi = new Category("Truyện Thiếu Nhi", cv.categorySachThieuNhi);
    cv.categoryTruyenThieuNhi = this.categoryRepo.save(cv.categoryTruyenThieuNhi);

    //

    cv.categorySachTieuSuHoiKy = new Category("Sách Tiểu sử - hồi ký", null);
    cv.categorySachTieuSuHoiKy = this.categoryRepo.save(cv.categorySachTieuSuHoiKy);

    cv.categoryCauTruyenCuocDoi = new Category("Câu Chuyện Cuộc Đời", cv.categorySachTieuSuHoiKy);
    cv.categoryCauTruyenCuocDoi = this.categoryRepo.save(cv.categoryCauTruyenCuocDoi);

    cv.categoryChinhTri = new Category("Chính Trị", cv.categorySachTieuSuHoiKy);
    cv.categoryChinhTri = this.categoryRepo.save(cv.categoryChinhTri);

    cv.categoryKinhTe = new Category("Kinh Tế", cv.categorySachTieuSuHoiKy);
    cv.categoryKinhTe = this.categoryRepo.save(cv.categoryKinhTe);

    cv.categoryNgheThuatGiaiTri = new Category("Nghệ Thuật - Giải Trí", cv.categorySachTieuSuHoiKy);
    cv.categoryNgheThuatGiaiTri = this.categoryRepo.save(cv.categoryNgheThuatGiaiTri);

    //

    cv.categorySachGiaoKhoaThamKhao = new Category("Sách Giáo Khoa - Tham Khảo", null);
    cv.categorySachGiaoKhoaThamKhao = this.categoryRepo.save(cv.categorySachGiaoKhoaThamKhao);

    cv.categorySachGiaoKhoa = new Category("Sách Giáo Khoa", cv.categorySachGiaoKhoaThamKhao);
    cv.categorySachGiaoKhoa = this.categoryRepo.save(cv.categorySachGiaoKhoa);

    cv.categorySachThamKhao = new Category("Sách Tham Khảo", cv.categorySachGiaoKhoaThamKhao);
    cv.categorySachThamKhao = this.categoryRepo.save(cv.categorySachThamKhao);

    cv.categoryLuyenThiTHPT =
        new Category("Luyện Thi THPT Quốc Gia", cv.categorySachGiaoKhoaThamKhao);
    cv.categoryLuyenThiTHPT = this.categoryRepo.save(cv.categoryLuyenThiTHPT);

    cv.categoryMauGiao = new Category("Mẫu Giáo", cv.categorySachGiaoKhoaThamKhao);
    cv.categoryMauGiao = this.categoryRepo.save(cv.categoryMauGiao);

    //

    cv.categorySachHocNgoaiNgu = new Category("Sách Học Ngoại Ngữ", null);
    cv.categorySachHocNgoaiNgu = this.categoryRepo.save(cv.categorySachHocNgoaiNgu);

    cv.categoryTiengAnh = new Category("Tiếng Anh", cv.categorySachHocNgoaiNgu);
    cv.categoryTiengAnh = this.categoryRepo.save(cv.categoryTiengAnh);

    cv.categoryTiengNhat = new Category("Tiếng Nhật", cv.categorySachHocNgoaiNgu);
    cv.categoryTiengNhat = this.categoryRepo.save(cv.categoryTiengNhat);

    cv.categoryTiengHoa = new Category("Tiếng Hoa", cv.categorySachHocNgoaiNgu);
    cv.categoryTiengHoa = this.categoryRepo.save(cv.categoryTiengHoa);

    cv.categoryTiengHan = new Category("Tiếng Hàn", cv.categorySachHocNgoaiNgu);
    cv.categoryTiengHan = this.categoryRepo.save(cv.categoryTiengHan);
  }

  static class LanguageVariable {
    public LanguageVariable() {}

    public Language languageVN;
    public Language languageEN;
  }

  private void createLanguage(LanguageVariable lv) {
    lv.languageVN = new Language(ELanguage.VN, "vn");
    lv.languageVN = this.languageRepo.save(lv.languageVN);

    lv.languageEN = new Language(ELanguage.EN, "en");
    lv.languageEN = this.languageRepo.save(lv.languageEN);
  }
}
