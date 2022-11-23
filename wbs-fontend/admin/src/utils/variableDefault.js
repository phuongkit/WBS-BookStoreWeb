// const PRODUCT_NO_INFO = "Sản phẩm chưa có thông tin";

const EGender = {
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

const EPayment = {
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

const EShippingMethod = {
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

const EProductStatus = {
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
    getNameFromIndex: (index) =>
        EOrderStatus[
            Object.keys(EOrderStatus)[
                index > EOrderStatus.ORDER_PENDING.index ? EOrderStatus.ORDER_PENDING.index : index
            ]
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
    token: '1a54568c-4713-11ed-8a70-52fa25d1292f',
    shopId: '3330973',
    thuduc: 3695,
    truongtho: 90746,
    weight: 250,
    length: 20,
    width: 15,
    height: 0.5,
    totalWeight: 999,
    totalLength: 100,
    totalWidth: 100,
    totalHeight: 100,
    toName: 'Thái Phương',
    maxInsuranceValue: 4900000,
};

export const MESSAGE = {
    PRODUCT_NO_INFO: 'Sản phẩm chưa có thông tin',
    LOGIN_USER_NAME_NOT_MATCH: 'Số điện thoại hoặc email không đúng',
    NAME_NOT_AVAILABLE: 'Tên không khả dung',
    ERROR_ACTION: 'Có lỗi xảy ra! Vui lòng thử lại sau',
};

export const ENUM = {
    EGender,
    EOrderStatus,
    EPayment,
    EProductStatus,
    EShippingMethod,
};
