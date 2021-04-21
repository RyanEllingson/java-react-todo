import React, {useState, useEffect, useContext} from "react";
import {Redirect, useHistory} from "react-router-dom";
import {AuthContext} from "../../auth/auth";
import Form from "../Form";

const UpdatePassword = function() {
    const {user, errors, updatePassword, resetErrors} = useContext(AuthContext);
    const [inputs, setInputs] = useState({password: "", confirmPassword: ""});
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
        updatePassword({...inputs, userId}, history, setIsLoading)
    };

    const fields = [
        {
            fieldName: "password",
            fieldText: "Password",
            fieldType: "password"
        },
        {
            fieldName: "confirmPassword",
            fieldText: "Confirm password",
            fieldType: "password"
        }
    ];

    return (
        user
        ? <div className="UpdatePassword">
            <div className="container">
                <div className="row">
                    <div className="col-12">
                        <Form
                            title="Change your password"
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

export default UpdatePassword;