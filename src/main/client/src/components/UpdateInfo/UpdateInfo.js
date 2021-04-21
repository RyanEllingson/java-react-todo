import React, {useState, useEffect, useContext} from "react";
import {Redirect, useHistory} from "react-router-dom";
import {AuthContext} from "../../auth/auth";
import Form from "../Form";

const UpdateInfo = function() {
    const {user, errors, updateUserInfo, resetErrors} = useContext(AuthContext);
    const [inputs, setInputs] = useState(user ? {firstName: user.firstName, lastName: user.lastName, email: user.sub} : null);
    const [isLoading, setIsLoading] = useState(false);
    const history = useHistory();

    useEffect(() => {
        resetErrors();
    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const handleInputChange = function(event) {
        setInputs(curState => {
            const newInputs = {};
            Object.assign(newInputs, curState);
            newInputs[event.target.name] = event.target.value;
            return newInputs;
        });
    };

    const handleSubmit = function(event) {
        event.preventDefault();
        const {userId} = user;
        updateUserInfo({...inputs, userId}, history, setIsLoading);
    };

    const fields = [
        {
            fieldName: "firstName",
            fieldText: "First name",
            fieldType: "text"
        },
        {
            fieldName: "lastName",
            fieldText: "Last name",
            fieldType: "text"
        },
        {
            fieldName: "email",
            fieldText: "Email address",
            fieldType: "email"
        }
    ];

    return (
        user
        ? <div className="UpdateInfo">
            <div className="container">
                <div className="row">
                    <div className="col-12">
                        <Form
                            title="Update your information"
                            fields={fields}
                            inputs={inputs}
                            inputChangeHandler={handleInputChange}
                            errors={errors}
                            submitHandler={handleSubmit}
                            isLoading={isLoading}
                        />
                    </div>
                </div>
            </div>
        </div>
        : <Redirect to="/login" />
    );
};

export default UpdateInfo;