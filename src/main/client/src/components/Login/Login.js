import React, {useState, useEffect, useContext} from "react";
import {Link, Redirect, useHistory} from "react-router-dom";
import {AuthContext} from "../../auth/auth";

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

    return (
        user
        ? <Redirect to="/" />
        : <div className="Login">
                <div className="container">
                    <div className="row">
                        <div className="col-12">
                            <div className="card shadow">
                                <div className="card-body">
                                    <h5 className="card-title text-center mb-3">Sign in to your account</h5>
                                    <form className="mb-3" onSubmit={handleSubmit}>
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
                                        <button type="submit" className="btn btn-primary">Submit</button>
                                    </form>
                                    <p className="card-text">Don't have an account? <Link to="/register">Sign up</Link></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        
    );
}

export default Login;