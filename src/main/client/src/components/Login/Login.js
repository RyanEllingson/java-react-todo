import React, {useState, useContext} from "react";
import {Redirect, useHistory} from "react-router-dom";
import {AuthContext} from "../../auth/auth";

const Login = function() {
    const [inputs, setInputs] = useState({email: "", password: ""});
    const {user, errors, loginUser} = useContext(AuthContext);
    const history = useHistory();

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
                            <div className="card">
                                <div className="card-body">
                                    <form onSubmit={handleSubmit}>
                                        <div className="form-floating mb-3">
                                            <input type="email" className={`form-control ${errors.email && "is-invalid"}`} id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="name@example.com" name="email" value={inputs.email} onChange={handleInputChange} />
                                            <label htmlFor="exampleInputEmail1" className="form-label">Email address</label>
                                            {errors.email && <div id="emailHelp" className="form-text text-danger">{errors.email}</div>}
                                        </div>
                                        <div className="form-floating mb-3">
                                            <input type="password" className={`form-control ${errors.password && "is-invalid"}`} id="exampleInputPassword1" placeholder="password" name="password" value={inputs.password} onChange={handleInputChange} />
                                            <label htmlFor="exampleInputPassword1" className="form-label">Password</label>
                                            {errors.password && <div id="emailHelp" className="form-text text-danger">{errors.password}</div>}
                                        </div>
                                        <button type="submit" className="btn btn-primary">Submit</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        
    );
}

export default Login;