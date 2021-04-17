import React, {useState, useEffect, useContext} from "react";
import {Link, Redirect, useHistory} from "react-router-dom";
import {AuthContext} from "../../auth/auth";
import Form from "../Form";

const Login = function() {
    const [inputs, setInputs] = useState({email: "", password: ""});
    const {user, errors, loginUser, resetErrors} = useContext(AuthContext);
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
        loginUser(inputs, history);
    };

    const fields = [
        {
            fieldName: "email",
            fieldText: "Email address",
            fieldType: "email"
        },
        {
            fieldName: "password",
            fieldText: "Password",
            fieldType: "password"
        }
    ];

    return (
        user
        ? <Redirect to="/" />
        : <div className="Login">
            <div className="container">
                <div className="row">
                    <div className="col-12">
                        <Form
                            title="Sign in to your account"
                            fields={fields}
                            inputs={inputs}
                            inputChangeHandler={handleInputChange}
                            errors={errors}
                            submitHandler={handleSubmit}
                        >
                            <p className="card-text">Don't have an account? <Link to="/register">Sign up</Link></p>
                        </Form>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Login;