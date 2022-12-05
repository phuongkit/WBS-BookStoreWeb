import './new.scss';
import React from "react";
import { useState } from "react";
import { EGender } from "../../utils";
import { useDispatch } from "react-redux";
import { createAuthorApi } from '../../redux/author/authorsApi';
import { useNavigate }  from 'react-router-dom';

function NewAuthor({title}) {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [fullName, setFullname] = useState('');
    const [gender, setGender] = useState(EGender.UNKNOWN.index);
    const handleInsert = (e) => {
        e.preventDefault();
        let data = {
            fullName: fullName,
            gender: Number.parseInt(gender),
        }
        console.log(data);
        createAuthorApi(dispatch, data);
        navigate('/authors');
    }
    return (
        <div className="new">
            <div className="newContainer">
                <div className="top">
                    <h1>{title}</h1>
                </div>
                <div className="bottom">
                    <div className="right">
                        <form onSubmit={handleInsert}>

                            <div className="formInput">
                                <label>Tên đầy đủ</label>
                                <input value={fullName} type="text" onChange={(e) => setFullname(e.target.value)} required/>
                            </div>
                            <div className="formInput">
                                <label>Giới tính</label>
                                <input value={gender} type="text" onChange={(e) => setGender(e.target.value)} required/>
                            </div>
                            <div className="formInput">
                            </div>

                            <button type="submit">Send</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default NewAuthor;