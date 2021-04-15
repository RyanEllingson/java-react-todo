import React, {useState, useEffect, useContext} from "react";
import {Link, Redirect, useHistory} from "react-router-dom";
import {AuthContext} from "../../auth/auth";

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

    return (
        user
        ? <Redirect to="/" />
        : <div className="Register">
            <div className="container">
                <div className="row">
                    <div className="col-12">
                        <div className="card shadow">
                            <div className="card-body">
                                <h5 className="card-title text-center mb-3">Register an account</h5>
                                <form className="mb-3" onSubmit={handleSubmit}>
                                    <div className="form-floating mb-3">
                                        <input type="text" className={`form-control ${errors.firstName && "is-invalid"}`} id="firstNameInput" aria-describedby="first name" placeholder="first name" name="firstName" value={inputs.firstName} onChange={handleInputChange} />
                                        <label htmlFor="firstNameInput" className="form-label">First name</label>
                                        {errors.firstName && <div id="firstNameError" className="form-text text-danger">{errors.firstName}</div>}
                                    </div>
                                    <div className="form-floating mb-3">
                                        <input type="text" className={`form-control ${errors.lastName && "is-invalid"}`} id="lastNameInput" aria-describedby="last name" placeholder="last name" name="lastName" value={inputs.lastName} onChange={handleInputChange} />
                                        <label htmlFor="lastNameInput" className="form-label">Last name</label>
                                        {errors.lastName && <div id="lastNameError" className="form-text text-danger">{errors.lastName}</div>}
                                    </div>
                                    <div className="form-floating mb-3">
                                        <input type="email" className={`form-control ${errors.email && "is-invalid"}`} id="emailInput" aria-describedby="email" placeholder="name@example.com" name="email" value={inputs.email} onChange={handleInputChange} />
                                        <label htmlFor="emailInput" className="form-label">Email address</label>
                                        {errors.email && <div id="emailError" className="form-text text-danger">{errors.email}</div>}
                                    </div>
                                    <div className="form-floating mb-3">
                                        <input type="password" className={`form-control ${errors.password && "is-invalid"}`} id="passwordInput" placeholder="password" aria-describedby="password" name="password" value={inputs.password} onChange={handleInputChange} />
                                        <label htmlFor="passwordInput" className="form-label">Password</label>
                                        {errors.password && <div id="passwordError" className="form-text text-danger">{errors.password}</div>}
                                    </div>
                                    <div className="form-floating mb-3">
                                        <input type="password" className={`form-control ${errors.confirmPassword && "is-invalid"}`} id="confirmPasswordInput" placeholder="confirm password" aria-describedby="confirm password" name="confirmPassword" value={inputs.confirmPassword} onChange={handleInputChange} />
                                        <label htmlFor="confirmPasswordInput" className="form-label">Confirm password</label>
                                        {errors.confirmPassword && <div id="confirmPasswordError" className="form-text text-danger">{errors.confirmPassword}</div>}
                                    </div>
                                    <button type="submit" className="btn btn-primary">Submit</button>
                                </form>
                                <p className="card-text">Already have an account? <Link to="/login">Sign in</Link></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Register;