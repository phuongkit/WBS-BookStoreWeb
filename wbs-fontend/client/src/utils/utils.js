import React from 'react';

import { REGEXP } from './variableDefault';

export const getNameBook = (name, reprintYear) => {
    return reprintYear ? name + ` (Tái bản ${reprintYear})` : name;
};

export const checkRegex = (str) => {
    if (REGEXP.PHONE.test(str)) {
        return 0;
    }
    if (REGEXP.EMAIL.test(str)) {
        return 1;
    }
    if (REGEXP.PASSWORD.test(str)) {
        return 2;
    }
    return -1;
};

export const getPrice = (number) => {
    number = number / 1000;
    if (number < 1000) {
        return parseFloat(number).toFixed(3);
    } else {
        let post = number % 1000;
        number = Number.parseInt(number / 1000);
        return (
            number +
            '.' +
            (post < 10
                ? '00' + parseFloat(post).toFixed(3)
                : post < 100
                ? '0' + parseFloat(post).toFixed(3)
                : parseFloat(post).toFixed(3))
        );
    }
};

export const pseudo = (pseudoSelector, style) => {
    const className = `pseudo-${Math.floor(Math.random() * 1000000)}`;

    // simple encoding of style dictionary to CSS
    // for simplicity, assumes that keys are already in slug case and units have
    // been added, unlike React.CSSProperties
    const styleCSS =
        '{' +
        Object.entries(style)
            .map(([name, value]) => `${name}: ${value};`)
            .join('') +
        '}';

    return {
        className,
        injectStyle: () => <style>{`.${className}${pseudoSelector} ${styleCSS}`}</style>,
    };
};

export const getName = (name) => {
    if (name.length > 12) {
        name = name.slice(0, 12) + '...';
    }
    return name;
};

export const toSlug = (str) => {
    str = str.replace(/^\s+|\s+$/g, ''); // trim
    str = str.toLowerCase();

    // remove accents, swap ñ for n, etc
    var from = 'ãàáäâẽèéëêìíïîõòóöôùúüûñç·/_,:;';
    var to = 'aaaaaeeeeeiiiiooooouuuunc------';
    for (var i = 0, l = from.length; i < l; i++) {
        str = str.replace(new RegExp(from.charAt(i), 'g'), to.charAt(i));
    }

    str = str
        .replace(/[^a-z0-9 -]/g, '') // remove invalid chars
        .replace(/\s+/g, '-') // collapse whitespace and replace by -
        .replace(/-+/g, '-'); // collapse dashes

    return str;
};

export const toLocationSlug = (str) => {
    if (str) {
        const strReplaces = [
            'tỉnh',
            'thành phố',
            'quần đảo',
            'huyện đảo',
            'đảo',
            'huyện',
            'thị trấn',
            'quận',
            'thị xã',
            'xã',
            'phường',
        ];
        str = str.toLowerCase();
        for (let strReplace in strReplaces) {
            str = str.replace(strReplace, '');
        }
        return str;
    }
    return '';
};

export const toAddressSlug = (address) => {
    return {
        ...address,
        ward: toLocationSlug(address?.ward),
        district: toLocationSlug(address?.district),
        city: toLocationSlug(address?.city),
    };
};

export const toFullAddress = (address) => {
    let addressString = 'Việt Nam';
    if (address) {
        if (address?.city) {
            addressString = address?.city + ', ' + addressString;
            if (address?.district) {
                addressString = address?.district + ', ' + addressString;
                if (address?.ward) {
                    addressString = address?.ward + ', ' + addressString;
                    if (address?.homeAdd) {
                        addressString = address?.homeAdd + ', ' + addressString;
                    }
                }
            }
        }
    }
    return addressString;
};
