export const EGender = {
    FEMALE: {
        name: 'Chị',
        index: 0,
    },
    MALE: {
        name: 'Anh',
        index: 1,
    },
    UNKNOWN: {
        name: 'Không xác định',
        index: 2,
    },
    getNameFromIndex: (index) =>
        EGender[Object.keys(EGender)[index > EGender.UNKNOWN.index ? EGender.UNKNOWN.index : index]]?.name ||
        EGender.UNKNOWN.name,
    getIndexFromName: (name) =>
        Object.values(EGender).find((item) => item.name === name)?.index || EGender.UNKNOWN.index,
};

export const ESortOption = {
    NO_OPTION: {
        name: 'Không có tùy chọn',
        index: 0,
    },
    POPULAR: {
        name: 'Phổ biến',
        index: 1,
    },
    LATEST: {
        name: 'Mới nhất',
        index: 2,
    },
    TOP_SALES: {
        name: 'Bán chạy',
        index: 3,
    },
};

export const EHomeOption = {
    NEW: {
        name: 'SÁCH MỚI TUYỂN CHỌN',
        slug: 'sach-moi-tuyen-chon',
        index: 0,
    },
    SALE: {
        name: 'COMBO SÁCH HOT - GIẢM ĐẾN 25%',
        slug: 'combo-sach-hot-giam-gia',
        index: 1,
    },
    POPULAR: {
        name: 'SÁCH PHỔ BIẾN',
        slug: 'sach-pho-bien',
        index: 2,
    },
    SIMILAR: {
        name: 'SẢN PHẨM TƯƠNG TỰ',
        slug: 'san-pham-tuong-tu',
        index: 3,
    },
    HISTORY: {
        name: 'SẢN PHẨM BẠN ĐÃ XEM',
        slug: 'san-pham-ban-da-xem',
        index: 4,
    },
};

export const EBookLayout = {
    PAPERBACK: {
        name: 'Bìa mềm',
        index: 0,
    },
    HARDBACK: {
        name: 'Bìa cứng',
        index: 1,
    },
    BOX_SET: {
        name: 'Bộ hộp',
        index: 2,
    },
    BOOK_WITH_CD: {
        name: 'Sách Kèm Đĩa',
        index: 3,
    },
    getNameFromIndex: (index) =>
        EBookLayout[Object.keys(EBookLayout)[index > EBookLayout.PAPERBACK.index ? EBookLayout.PAPERBACK.index : index]]
            ?.name || EBookLayout.PAPERBACK.name,
};

export const EPayment = {
    CASH: {
        name: 'Thanh toán tiền mặt',
        index: 0,
    },
    MOMO: {
        name: 'Ví momo',
        index: 1,
    },
    VNPAY: {
        name: 'VNPay',
        index: 2,
    },
    getNameFromIndex: (index) =>
        EPayment[Object.keys(EPayment)[index > EPayment.CASH.index ? EPayment.CASH.index : index]]?.name ||
        EPayment.CASH.name,
};

export const EShippingMethod = {
    GHN_EXPRESS: {
        name: 'Giao hàng nhanh',
        index: 0,
    },
    GIAOHANGTIETKIEM: {
        name: 'Giao hàng tiết kiệm',
        index: 1,
    },
    getNameFromIndex: (index) =>
        EShippingMethod[
            Object.keys(EShippingMethod)[
                index > EShippingMethod.GHN_EXPRESS.index ? EShippingMethod.GHN_EXPRESS.index : index
            ]
        ]?.name || EShippingMethod.GHN_EXPRESS.name,
};

export const EProductStatus = {
    PRODUCT_UN_TRADING: {
        name: 'Chưa kinh doanh',
        index: 0,
    },
    PRODUCT_TRADING: {
        name: 'Đang kinh doanh',
        index: 1,
    },
    PRODUCT_TRADED: {
        name: 'Ngưng kinh doanh',
        index: 2,
    },
    getNameFromIndex: (index) =>
        EProductStatus[
            Object.keys(EProductStatus)[
                index > EProductStatus.PRODUCT_TRADING.index ? EProductStatus.PRODUCT_TRADING.index : index
            ]
        ]?.name || EProductStatus.PRODUCT_TRADING.name,
};

export const EOrderStatus = {
    ORDER_PENDING: {
        name: 'Đang chờ xác nhận',
        index: 0,
    },
    ORDER_AWAITING_PAYMENT: {
        name: 'Đang chờ thanh toán',
        index: 1,
    },
    ORDER_COMPLETED: {
        name: 'Đã hoàn thành',
        index: 2,
    },
    ORDER_CANCELLED: {
        name: 'Đã hủy',
        index: 3,
    },
    ORDER_AWAITING_FULFILLMENT: {
        name: '',
        index: 4,
    },
    ORDER_AWAITING_SHIPMENT: {
        name: '',
        index: 5,
    },
    ORDER_AWAITING_PICKUP: {
        name: '',
        index: 6,
    },
    ORDER_PARTIALLY_SHIPPED: {
        name: '',
        index: 7,
    },
    ORDER_SHIPPED: {
        name: '',
        index: 8,
    },
    ORDER_DECLINED: {
        name: '',
        index: 9,
    },
    ORDER_REFUNDED: {
        name: '',
        index: 10,
    },
    ORDER_DISPUTED: {
        name: '',
        index: 11,
    },
    ORDER_MANUAL_VERIFICATION_REQUIRED: {
        name: '',
        index: 12,
    },
    ORDER_PARTIALLY_REFUNDED: {
        name: '',
        index: 13,
    },
    ORDER_ready_to_pick: {
        name: 'Mới tạo đơn hàng',
        en: 'ready_to_pick',
        index: 14,
    },
    ORDER_picking: {
        name: 'Nhân nameên đang lấy hàng',
        en: 'picking',
        index: 15,
    },
    ORDER_cancel: {
        name: 'Hủy đơn hàng',
        en: 'cancel',
        index: 16,
    },
    ORDER_money_collect_picking: {
        name: 'Đang thu tiền người gửi',
        en: 'money_collect_picking',
        index: 17,
    },
    ORDER_picked: {
        name: 'Nhân nameên đã lấy hàng',
        en: 'picked',
        index: 18,
    },
    ORDER_storing: {
        name: 'Hàng đang nằm ở kho',
        en: 'storing',
        index: 19,
    },
    ORDER_transporting: {
        name: 'Đang luân chuyển hàng',
        en: 'transporting',
        index: 20,
    },
    ORDER_sorting: {
        name: 'Đang phân loại hàng hóa',
        en: 'sorting',
        index: 21,
    },
    ORDER_delivering: {
        name: 'Nhân nameên đang giao cho người nhận',
        en: 'delivering',
        index: 22,
    },
    ORDER_money_collect_delivering: {
        name: 'Nhân nameên đang thu tiền người nhận',
        en: 'money_collect_delivering',
        index: 23,
    },
    ORDER_delivered: {
        name: 'Nhân nameên đã giao hàng thành công',
        en: 'delivered',
        index: 24,
    },
    ORDER_delivery_fail: {
        name: 'Nhân nameên giao hàng thất bại',
        en: 'delivery_fail',
        index: 25,
    },
    ORDER_waiting_to_return: {
        name: 'Đang đợi trả hàng về cho người gửi',
        en: 'waiting_to_return',
        index: 26,
    },
    ORDER_return: {
        name: 'Trả hàng',
        en: 'return',
        index: 27,
    },
    ORDER_return_transporting: {
        name: 'Đang luân chuyển hàng trả',
        en: 'return_transporting',
        index: 28,
    },
    ORDER_return_sorting: {
        name: 'Đang phân loại hàng trả',
        en: 'return_sorting',
        index: 29,
    },
    ORDER_returning: {
        name: 'Nhân nameên đang đi trả hàng',
        en: 'returning',
        index: 30,
    },
    ORDER_return_fail: {
        name: 'Nhân nameên trả hàng thất bại',
        en: 'return_fail',
        index: 31,
    },
    ORDER_returned: {
        name: 'Nhân nameên trả hàng thành công',
        en: 'returned',
        index: 32,
    },
    ORDER_exception: {
        name: 'Đơn hàng ngoại lệ không nằm trong quy trình',
        en: 'exception',
        index: 33,
    },
    ORDER_damage: {
        name: 'Hàng bị hư hỏng',
        en: 'damage',
        index: 34,
    },
    ORDER_LOST: {
        name: 'Hàng bị mất',
        en: 'lost',
        index: 35,
    },
    getNameFromIndex: (index) =>
        EOrderStatus[
            Object.keys(EOrderStatus)[index > EOrderStatus.ORDER_LOST.index ? EOrderStatus.ORDER_PENDING.index : index]
        ]?.name || EOrderStatus.ORDER_PENDING.name,
};

export const EOrderStatusGHN = {
    ORDER_ready_to_pick: {
        vi: 'Mới tạo đơn hàng',
        en: 'ready_to_pick',
        index: 14,
    },
    ORDER_picking: {
        vi: 'Nhân viên đang lấy hàng',
        en: 'picking',
        index: 15,
    },
    ORDER_cancel: {
        vi: 'Hủy đơn hàng',
        en: 'cancel',
        index: 16,
    },
    ORDER_money_collect_picking: {
        vi: 'Đang thu tiền người gửi',
        en: 'money_collect_picking',
        index: 17,
    },
    ORDER_picked: {
        vi: 'Nhân viên đã lấy hàng',
        en: 'picked',
        index: 18,
    },
    ORDER_storing: {
        vi: 'Hàng đang nằm ở kho',
        en: 'storing',
        index: 19,
    },
    ORDER_transporting: {
        vi: 'Đang luân chuyển hàng',
        en: 'transporting',
        index: 20,
    },
    ORDER_sorting: {
        vi: 'Đang phân loại hàng hóa',
        en: 'sorting',
        index: 21,
    },
    ORDER_delivering: {
        vi: 'Nhân viên đang giao cho người nhận',
        en: 'delivering',
        index: 22,
    },
    ORDER_money_collect_delivering: {
        vi: 'Nhân viên đang thu tiền người nhận',
        en: 'money_collect_delivering',
        index: 23,
    },
    ORDER_delivered: {
        vi: 'Nhân viên đã giao hàng thành công',
        en: 'delivered',
        index: 24,
    },
    ORDER_delivery_fail: {
        vi: 'Nhân viên giao hàng thất bại',
        en: 'delivery_fail',
        index: 25,
    },
    ORDER_waiting_to_return: {
        vi: 'Đang đợi trả hàng về cho người gửi',
        en: 'waiting_to_return',
        index: 26,
    },
    ORDER_return: {
        vi: 'Trả hàng',
        en: 'return',
        index: 27,
    },
    ORDER_return_transporting: {
        vi: 'Đang luân chuyển hàng trả',
        en: 'return_transporting',
        index: 28,
    },
    ORDER_return_sorting: {
        vi: 'Đang phân loại hàng trả',
        en: 'return_sorting',
        index: 29,
    },
    ORDER_returning: {
        vi: 'Nhân viên đang đi trả hàng',
        en: 'returning',
        index: 30,
    },
    ORDER_return_fail: {
        vi: 'Nhân viên trả hàng thất bại',
        en: 'return_fail',
        index: 31,
    },
    ORDER_returned: {
        vi: 'Nhân viên trả hàng thành công',
        en: 'returned',
        index: 32,
    },
    ORDER_exception: {
        vi: 'Đơn hàng ngoại lệ không nằm trong quy trình',
        en: 'exception',
        index: 33,
    },
    ORDER_damage: {
        vi: 'Hàng bị hư hỏng',
        en: 'damage',
        index: 34,
    },
    ORDER_lost: {
        vi: 'Hàng bị mất',
        en: 'lost',
        index: 35,
    },
    getNameFromIndex: (index) =>
        EOrderStatus[
            Object.keys(EOrderStatus)[index > EOrderStatus.ORDER_LOST.index ? EOrderStatus.ORDER_PENDING.index : index]
        ]?.name || EOrderStatus.ORDER_PENDING.name,
};

export const REGEXP = {
    PHONE: new RegExp('^(0|84?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$'),
    EMAIL: new RegExp("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"),
    PASSWORD: new RegExp('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$'),
};

export const PAGE = {
    CENTER: 3,
    LEFT: 1,
    RIGHT: 1,
};

export const GHN_CONFIG = {
    token: '841e800e-6e2a-11ed-a83f-5a63c54f968d',
    shopId: '120835',
    thuduc: 3695,
    truongtho: 90746,
    weight: 250,
    length: 20,
    width: 15,
    height: 0.5,
    totalWeight: 30000,
    totalLength: 150,
    totalWidth: 150,
    totalHeight: 150,
    toName: 'Thái Phương',
    maxInsuranceValue: 5000000,
    provinceId: 202, // HCM
    districtId: 3695, // Thu Duc
    wardCode: '90476', // Truong Tho
};

export const MESSAGE = {
    PRODUCT_NO_INFO: 'Sản phẩm chưa có thông tin',
    LOGIN_USER_NAME_NOT_MATCH: 'Số điện thoại hoặc email không đúng',
    NAME_NOT_AVAILABLE: 'Tên không khả dung',
    ERROR_ACTION: 'Có lỗi xảy ra! Vui lòng thử lại sau',
};

export const API_BASE_URL = process.env.BACKEND_URL;

export const OAUTH2_REDIRECT_URI = process.env.FRONTEND_URL + ':' + process.env.PORT;

export const GOOGLE_AUTH_URL = API_BASE_URL + '/oauth2/authorize/google?redirect_uri=' + OAUTH2_REDIRECT_URI;
export const FACEBOOK_AUTH_URL = API_BASE_URL + '/oauth2/authorize/facebook?redirect_uri=' + OAUTH2_REDIRECT_URI;
export const GITHUB_AUTH_URL = API_BASE_URL + '/oauth2/authorize/github?redirect_uri=' + OAUTH2_REDIRECT_URI;
