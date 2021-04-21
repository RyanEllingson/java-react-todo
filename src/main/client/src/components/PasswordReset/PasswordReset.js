import React, {useState, useContext} from "react";
import {AuthContext} from "../../auth/auth";
import {Redirect, useHistory} from "react-router-dom";
import Form from "../Form";

const PasswordReset = function() {
    const [inputs, setInputs] = useState({email: "", resetCode: ""});
    const [viewResetCodeInput, setViewResetCodeInput] = useState(false);
    const {user, errors, initiatePasswordReset, loginUserViaEmail} = useContext(AuthContext);
    const history = useHistory();

    const handleInputChange = function(event) {
        setInputs(curState => {
            const newInputs = {};
            Object.assign(newInputs, curState);
            newInputs[event.target.name] = event.target.value;
            return newInputs;
        });
    };

    const handleInitiatePasswordReset = function(event) {
        event.preventDefault();
        initiatePasswordReset(inputs, setViewResetCodeInput);
    };

    const handleLoginUserViaEmail = function(event) {
        event.preventDefault();
        loginUserViaEmail(inputs, history);
    }

    const emailInputFields = [
        {
            fieldName: "email",
            fieldText: "Email address",
            fieldType: "email"
        }
    ];

    const resetCodeInputFields = [
        {
            fieldName: "resetCode",
            fieldText: "Reset code",
            fieldType: "text"
        }
    ];

    return (
        user
        ? <Redirect to="/" />
        : <div className="PasswordReset">
            <div className="container">
                <div className="row">
                    <div className="col-12">
                        {viewResetCodeInput
                        ? <Form
                            title="Enter reset code"
                            fields={resetCodeInputFields}
                            inputs={inputs}
                            inputChangeHandler={handleInputChange}
                            errors={errors}
                            submitHandler={handleLoginUserViaEmail}
                        />
                        : <Form
                            title="Email a reset code"
                            fields={emailInputFields}
                            inputs={inputs}
                            inputChangeHandler={handleInputChange}
                            errors={errors}
                            submitHandler={handleInitiatePasswordReset}
                        />}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default PasswordReset;