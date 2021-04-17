import React, {useState, useEffect, useContext} from "react";
import {Redirect, useHistory} from "react-router-dom";
import {AuthContext} from "../../auth/auth";

const UpdatePassword = function() {
    const {user, errors, updatePassword, resetErrors} = useContext(AuthContext);
    const [inputs, setInputs] = useState({password: "", confirmPassword: ""});
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
        updatePassword({...inputs, userId}, history)
    };

    return (
        user
        ? <div className="UpdatePassword">
            <div className="container">
                <div className="row">
                    <div className="col-12">
                        <div className="card shadow">
                            <div className="card-body">
                                <h5 className="card-title text-center mb-3">Change your password</h5>
                                <form onSubmit={handleSubmit}>
                                    <div className="form-floating mb-3">
                                        <input type="password" className={`form-control ${errors.password && "is-invalid"}`} id="passwordInput" aria-describedby="password" placeholder="password" name="password" value={inputs.password} onChange={handleInputChange} />
                                        <label htmlFor="passwordInput" className="form-label">New password</label>
                                        {errors.password && <div id="passwordError" className="form-text text-danger">{errors.password}</div>}
                                    </div>
                                    <div className="form-floating mb-3">
                                        <input type="password" className={`form-control ${errors.confirmPassword && "is-invalid"}`} id="confirmPasswordInput" aria-describedby="confirm password" placeholder="confirm password" name="confirmPassword" value={inputs.confirmPassword} onChange={handleInputChange} />
                                        <label htmlFor="confirmPasswordInput" className="form-label">Confirm password</label>
                                        {errors.confirmPassword && <div id="confirmPasswordError" className="form-text text-danger">{errors.confirmPassword}</div>}
                                    </div>
                                    <button type="submit" className="btn btn-primary">Submit</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        : <Redirect to="/login" />
    );
};

export default UpdatePassword;