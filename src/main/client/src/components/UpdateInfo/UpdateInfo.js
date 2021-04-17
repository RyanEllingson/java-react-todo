import React, {useState, useEffect, useContext} from "react";
import {Redirect, useHistory} from "react-router-dom";
import {AuthContext} from "../../auth/auth";

const UpdateInfo = function() {
    const {user, errors, updateUserInfo, resetErrors} = useContext(AuthContext);
    const [inputs, setInputs] = useState(user ? {firstName: user.firstName, lastName: user.lastName, email: user.sub} : null);
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
        updateUserInfo({...inputs, userId}, history);
    };

    return (
        user
        ? <div className="UpdateInfo">
            <div className="container">
                <div className="row">
                    <div className="col-12">
                        <div className="card shadow">
                            <div className="card-body">
                                <h5 className="card-title text-center mb-3">Update your information</h5>
                                <form onSubmit={handleSubmit}>
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
                                        <label htmlFor="emailInput" className="form-label">Email</label>
                                        {errors.email && <div id="emailError" className="form-text text-danger">{errors.email}</div>}
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

export default UpdateInfo;