import './new.scss';
import DriveFolderUploadOutlinedIcon from '@mui/icons-material/DriveFolderUploadOutlined';
import React, { useEffect, useState, useRef } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';
// import FileBase64 from 'react-file-base64';
// import { CardMedia } from '@material-ui/core';
import { getAllCategoriesApi } from '../../redux/category/categoriesApi';
import { createProduct, getProductByIdApi, updateProduct } from '../../redux/product/productsApi';
import { getAllProductApi } from '../../redux/product/productsApi';
const New = ({ title, action, isUpdate }) => {
    const { productId } = useParams();
    console.log('isUpdate', isUpdate);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    // const user = useSelector((state) => state.auth.login?.currentUser);
    const product = useSelector((state) => state.products.oneProduct.data);
    let getuser = JSON.parse(localStorage.getItem('customerInfo'));
    let getlocationed = JSON.parse(localStorage.getItem('locationed')) || '';

    useEffect(() => {
        getAllCategoriesApi(dispatch);
        getAllProductApi(dispatch);
    }, []);
    const categoriesList = useSelector((state) => state.categories?.allCategory?.data) || [];
    const authorList = useSelector((state) => state.authors.allAuthor.data) || [];
    const genreList = useSelector((state) => state.genres.allGenre.data) || [];
    const publisherList = useSelector((state) => state.publishers.allPublisher.data) || [];
    const supplierList = useSelector((state) => state.suppliers.allSupplier.data) || [];
    const translatorList = useSelector((state) => state.translators.allTranslator.data) || [];
    const productList = useSelector((state) => state.products?.pageProducts?.data) || [];
    const [file, setFile] = useState();
    const [files, setFiles] = useState();
    const [name, setName] = useState();
    const [price, setPrice] = useState('0');
    const [category_id, setCategory] = useState('1');
    const [author_id, setAuthor] = useState('1');
    const [genre_id, setGenres] = useState(null);
    const [publisher_id, setPublisher] = useState('1');
    const [supplier_id, setSupplier] = useState('1');
    const [translator_id, setTranslator] = useState(null);
    const [amount, setAmount] = useState('0');
    const [description, setDescription] = useState();
    const [status, setStatus] = useState('');
    const [productState, setProductState] = useState();
    useEffect(() => {
        if (isUpdate) {
            getProductByIdApi(dispatch, productId);
        }
    }, [productId]);
    // const getLengthProduct = async () => {
    //   try {
    //     const res = await axios.get("/product/getLength");
    //     setProductState(res.data);
    //   } catch (err) {
    //     return err;
    //   }
    // };

    // //Load trang
    // useEffect(() => {
    //   if (user?.accessToken) {
    //     get1Product(user?.accessToken, dispatch, productid);
    //     getLengthProduct();
    //   }

    //   // eslint-disable-next-line react-hooks/exhaustive-deps
    // }, []);

    async function getFileFromUrl(url, urls, defaultType = 'image/png') {
        let name = url.split('/').pop();
        const response = await fetch(url);
        const data = await response.blob();
        let file = new File([data], name, {
            type: data.type || defaultType,
        });
        let container = new DataTransfer();
        container.items.add(file);
        document.getElementById('file').files = container.files;
        setFile(container.files);
        if (urls && urls.length > 0) {
            let containers = new DataTransfer();
            for (let i = 0; i < urls.length; i++) {
                let responses = await fetch(urls[i]);
                let datas = await responses.blob();
                let files = new File([data], url.split('/').pop(), {
                    type: datas.type || defaultType,
                });
                containers.items.add(files);
            }
            document.getElementById('files').files = containers.files;
            setFiles(containers.files);
        }
    }

    useEffect(() => {
        if (isUpdate && product && product.id === Number.parseInt(productId)) {
            getFileFromUrl(product.img, product.gallery);
            setName(product.name);
            setPrice(product.originPrice);
            setCategory(categoriesList.find((item) => item.title === product.categoryName)?.id);
            setAmount(product.availableQuantity);
            setDescription(product.description);
            setStatus(product.status);
        }
    }, [isUpdate, product]);

    const handleInsert = (e) => {
        e.preventDefault();

        const newProduct = {
            name: name,
            description: description,
            standCost: 0,
            listPrice: Number(price),
            quantity: Number(amount),
            shopId: Number(getuser.shopId),
            status: 'PRODUCT_UN_TRADING',
            categoryId: Number(category_id),
            descriptions: {
                additionalProp1: 'string',
                additionalProp2: 'string',
                additionalProp3: 'string',
            },
            location: getlocationed[0] || '',
        };
        console.log(newProduct);
        let formData = new FormData();
        formData.append('data', new Blob([JSON.stringify({ ...newProduct })], { type: 'application/json' }));
        if (file?.length > 0) {
            formData.append('image', file[0]);
        }
        if (files?.length > 0) {
            if (files?.length > 0) {
                for (let i = 0; i < files.length; i++) {
                    formData.append('images', files[i]);
                }
            }
        }
        isUpdate
            ? updateProduct(product.id, formData, dispatch, navigate, product)
            : createProduct(formData, dispatch, navigate, productList);
    };
    return (
        <div className="new">
            <div className="newContainer">
                <div className="top">
                    <h1>{title}</h1>
                </div>
                <div className="bottom">
                    <div className="left">
                        <img src={file ? URL.createObjectURL(file[0]) : ``} alt="" />
                        {/* <CardMedia
              image={file || ''}
              title='Title'
              
            /> */}
                    </div>
                    <div className="right">
                        <form onSubmit={handleInsert}>
                            <div className="formInput">
                                <label htmlFor="file">
                                    Image: <DriveFolderUploadOutlinedIcon className="icon" />
                                </label>
                                {/* <FileBase64
                  accept='image/*'
                  multiple={true}
                  type='file'
                  
                  onDone={({ base64 }) => setFile(base64)}
                /> */}
                                <input
                                    type="file"
                                    id="file"
                                    // multiple={true}
                                    // files={isUpdate && product && getFileFromUrl(product.img)}
                                    onChange={(e) => setFile(e.target.files)}
                                />
                            </div>

                            <div className="formInput">
                                <label htmlFor="files">
                                    Image: <DriveFolderUploadOutlinedIcon className="icon" />
                                </label>
                                {/* <FileBase64
                  accept='image/*'
                  multiple={true}
                  type='file'
                  
                  onDone={({ base64 }) => setFile(base64)}
                /> */}
                                <input
                                    type="file"
                                    id="files"
                                    multiple={true}
                                    // files={isUpdate && product && getFileFromUrl(product.img)}
                                    onChange={(e) => setFiles(e.target.files)}
                                />
                            </div>

                            <div className="formInput">
                                <label>Name</label>
                                <input value={name} type="text" onChange={(e) => setName(e.target.value)} />
                            </div>
                            <div className="formInput">
                                <label>Price</label>
                                <input value={price} type="text" onChange={(e) => setPrice(e.target.value)} />
                            </div>

                            <div className="formInput">
                                <label>Amount</label>
                                <input value={amount} type="text" onChange={(e) => setAmount(e.target.value)} />
                            </div>

                            <div className="formInput">
                                <label>Description</label>
                                <input
                                    value={description}
                                    type="text"
                                    onChange={(e) => setDescription(e.target.value)}
                                />
                            </div>

                            <div className="formInput">
                                <label>Category</label>

                                <select
                                    className="table-group-action-input form-control"
                                    onChange={(e) => {
                                        setCategory(e.target.value);
                                    }}
                                >
                                    {categoriesList &&
                                        categoriesList?.map((item, index) => (
                                            <option
                                                key={index}
                                                value={item.id}
                                                selected={isUpdate && product && item.name === product.categoryName}
                                                onSelect={() => setCategory(item.id)}
                                            >
                                                {item.name}
                                            </option>
                                        ))}
                                </select>
                            </div>
                            <div className="formInput">
                                <label>Author</label>
                                <select
                                    className="table-group-action-input form-control"
                                    onChange={(e) => {
                                        setAuthor(e.target.value);
                                    }}
                                >
                                    {authorList &&
                                        authorList?.map((item, index) => (
                                            <option
                                                key={index}
                                                value={item.id}
                                                selected={isUpdate && product && product.authors.contain(item.fullName)}
                                                onSelect={() => setAuthor(item.id)}
                                            >
                                                {item.fullName}
                                            </option>
                                        ))}
                                </select>
                            </div>
                            <div className="formInput">
                                <label>Publisher</label>

                                <select
                                    className="table-group-action-input form-control"
                                    onChange={(e) => {
                                        setPublisher(e.target.value);
                                    }}
                                >
                                    {publisherList &&
                                        publisherList?.map((item, index) => (
                                            <option
                                                key={index}
                                                value={item.id}
                                                selected={isUpdate && product && item.name === product.categoryName}
                                                onSelect={() => setPublisher(item.id)}
                                            >
                                                {item.name}
                                            </option>
                                        ))}
                                </select>
                            </div>
                            <div className="formInput">
                                <label>Supplier</label>

                                <select
                                    className="table-group-action-input form-control"
                                    onChange={(e) => {
                                        setSupplier(e.target.value);
                                    }}
                                >
                                    {supplierList &&
                                        supplierList?.map((item, index) => (
                                            <option
                                                key={index}
                                                value={item.id}
                                                selected={isUpdate && product && item.name === product.categoryName}
                                                onSelect={() => setSupplier(item.id)}
                                            >
                                                {item.name}
                                            </option>
                                        ))}
                                </select>
                            </div>
                            <div className="formInput">
                                <label>Translator</label>
                                <select
                                    className="table-group-action-input form-control"
                                    onChange={(e) => {
                                        setTranslator(e.target.value);
                                    }}
                                >
                                    {translatorList &&
                                        translatorList?.map((item, index) => (
                                            <option
                                                key={index}
                                                value={item.id}
                                                selected={isUpdate && product && product.translators.contain(item.fullName)}
                                                onSelect={() => setTranslator(item.id)}
                                            >
                                                {item.fullName}
                                            </option>
                                        ))}
                                </select>
                            </div>
                            {/* 
                            <select
                                className="table-group-action-input form-control"
                                onChange={(e) => setBrand(e.target.value)}
                            >
                                {brandList?.map((item, index) => (
                                    <option
                                        key={index}
                                        value={item.id}
                                        selected={isUpdate && product && item.name === product.brand}
                                        onSelect={() => setBrand(item.id)}
                                    >
                                        {item.name}
                                    </option>
                                ))}
                            </select> */}

                            <button type="submit">Send</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default New;
