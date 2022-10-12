import { getRate, postRate } from './ratesSlice';
import { rateService } from '~/services';

export const getRates = async (dispatch, id) => {
    let res = await rateService.getRateByProductId(id);
    dispatch(getRate(res));
};
export const postRates = async (dispatch, data) => {
    let res = await rateService.postRate(data);
    dispatch(postRate(res.data));
};
