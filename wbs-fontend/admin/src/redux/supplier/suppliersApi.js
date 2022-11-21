import { getAllSuppliers, getOneSupplier } from './suppliersSlice';
import { supplierService } from '../../services';

export const getAllSuppliersApi = async (dispatch) => {
    let res = await supplierService.getAll();
    dispatch(getAllSuppliers(res.data));
};

export const createsupplierApi = async (dispatch, id) => {
    let res = await supplierService.getById(id);
    dispatch(getOneSupplier(res.data));
};

export const updatesupplierApi = async (dispatch, id, data) => {
    let res = await supplierService.update(id, data);
    dispatch(getOneSupplier(res.data));
};

export const deletesupplierByIdApi = async (dispatch, id) => {
    let res = await supplierService.deleteById(id);
    dispatch(getOneSupplier(res.data));
};