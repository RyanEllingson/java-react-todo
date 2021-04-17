import React, {useState, useEffect, useContext} from "react";
import {Link, Redirect, useHistory} from "react-router-dom";
import {AuthContext} from "../../auth/auth";
import Form from "../Form";

const Register = function() {
    const [inputs, setInputs] = useState({firstName: "", lastName: "", email: "", password: "", confirmPassword: ""});
    const {user, errors, registerUser, resetErrors} = useContext(AuthContext);
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
        registerUser(inputs, history);
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
        },
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
        ? <Redirect to="/" />
        : <div className="Register">
            <div className="container">
                <div className="row">
                    <div className="col-12">
                        <Form
                            title="Register an account"
                            fields={fields}
                            inputs={inputs}
                            inputChangeHandler={handleInputChange}
                            errors={errors}
                            submitHandler={handleSubmit}
                        >
                            <p className="card-text">Already have an account? <Link to="/login">Sign in</Link></p>
                        </Form>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Register;