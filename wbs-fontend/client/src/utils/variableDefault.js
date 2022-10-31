// const PRODUCT_NO_INFO = "Sản phẩm chưa có thông tin";

const PRODUCT_NO_INFO = "Sản phẩm chưa có thông tin";

const EGender = {
    FEMALE: {
        name: 'Chị',
        index: 0
    },
    MALE: {
        name: 'Anh',
        index: 1
    },
    UNKNOWN:{
        name: 'Người dùng' || 'Không xác định',
        index: 2
    },
    getNameFromIndex: (index) => EGender[(Object.keys(EGender)[index > EGender.UNKNOWN.index ? EGender.UNKNOWN.index : index])]?.name || EGender.UNKNOWN.name,
    getIndexFromName: (name) => (Object.values(EGender)).find(item => item.name === name)?.index || EGender.UNKNOWN.index,
}

export const ESortOption = {
    NO_OPTION: {
        name: 'Không có tùy chọn',
        index: 0
    },
    POPULAR: {
        name: 'Phổ biến',
        index: 1
    },
    LATEST: {
        name: 'Mới nhất',
        index: 2
    },
    TOP_SALES: {
        name: 'Bán chạy',
        index: 3
    }
}

export const EHomeOption = {
    NEW: {
        name: 'SÁCH MỚI TUYỂN CHỌN',
        slug: 'sach-moi-tuyen-chon',
        index: 0
    },
    SALE: {
        name: 'COMBO SÁCH HOT - GIẢM ĐẾN 25%',
        slug: 'combo-sach-hot-giam-gia',
        index: 1
    },
    POPULAR: {
        name: 'SÁCH PHỔ BIẾN',
        slug: 'sach-pho-bien',
        index: 2
    },
    SIMILAR: {
        name: 'SẢN PHẨM TƯƠNG TỰ',
        slug: 'san-pham-tuong-tu',
        index: 3
    },
    HISTORY: {
        name: 'SẢN PHẨM BẠN ĐÃ XEM',
        slug: 'san-pham-ban-da-xem',
        index: 4
    }
}

const EPayment = {
    CASH: 0,
    MOMO: 1
}

const EShippingMethod = {
    GHN_EXPRESS: 0,
    GIAOHANGTIETKIEM: 1
};


export const PAGE_COMMENT = {
    CENTER: 3,
    LEFT: 1,
    RIGHT: 1
}

export const MESSAGE = {
    PRODUCT_NO_INFO,
};

export const ENUM = {
    EGender,
    EPayment,
    EShippingMethod
}