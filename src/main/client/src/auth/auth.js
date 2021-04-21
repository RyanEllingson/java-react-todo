import React, {useState, useEffect} from "react";
import axios from "axios";
import jwt_decode from "jwt-decode";

import setAuthToken from "./setAuthToken";

export const AuthContext = React.createContext("auth");

const logoutUser = function(setUser) {
    return function(history) {
        localStorage.removeItem("jwtToken");
        setAuthToken(null);
        setUser(null);
        history.push("/login");
    }
}

const loginUser = function(setUser, setErrors) {
    return (function(userData, history, setIsLoading) {
        setIsLoading(true);
        axios.post("/api/login", userData)
        .then(function(response) {
            setIsLoading(false);
            setErrors({});
            const token = response.data.payload;
            localStorage.setItem("jwtToken", token);
            setAuthToken(token);
            const decoded = jwt_decode(token);
            setUser(decoded);
            history.push("/");
        })
        .catch(function(error) {
            setIsLoading(false);
            setErrors(error.response.data.messages);
        });
    });
};

const initiatePasswordReset = function(setErrors) {
    return function(userData, setViewResetCodeInput, setInitiatePasswordResetIsLoading) {
        setInitiatePasswordResetIsLoading(true);
        axios.post("/api/reset", userData)
        .then(function() {
            setErrors({});
            setInitiatePasswordResetIsLoading(false);
            setViewResetCodeInput(true);
        })
        .catch(function(error) {
            setErrors(error.response.data.messages);
            setInitiatePasswordResetIsLoading(false);
        });
    };
};

const loginUserViaEmail = function(setUser, setErrors) {
    return function(userData, history, setLoginViaEmailIsLoading) {
        if (userData.password !== userData.confirmPassword) {
            setErrors({confirmPassword: "Confirm password must match password"});
        } else {
            setLoginViaEmailIsLoading(true);
            axios.post("/api/email_login", userData)
            .then(function(response) {
                setErrors({});
                const token = response.data.payload;
                localStorage.setItem("jwtToken", token);
                setAuthToken(token);
                const decoded = jwt_decode(token);
                setUser(decoded);
                history.push("/");
                setLoginViaEmailIsLoading(false);
            })
            .catch(function(error) {
                setErrors(error.response.data.messages);
                setLoginViaEmailIsLoading(false);
            });
        }
    };
};

const registerUser = function(setUser, setErrors) {
    return (function(userData, history, setIsLoading) {
        if (userData.password !== userData.confirmPassword) {
            setErrors({confirmPassword: "Confirm password must match password"});
        } else {
            setIsLoading(true);
            axios.post("/api/register", userData)
            .then(function(response) {
                setIsLoading(false);
                setErrors({});
                const token = response.data.payload;
                localStorage.setItem("jwtToken", token);
                setAuthToken(token);
                const decoded = jwt_decode(token);
                setUser(decoded);
                history.push("/");
            })
            .catch(function(error) {
                setIsLoading(false);
                setErrors(error.response.data.messages);
            });
        }
    });
};

const updateUserInfo = function(setUser, setErrors) {
    return function(userData, history, setIsLoading) {
        setIsLoading(true);
        axios.put("/api/users/info", userData)
        .then(function(response) {
            setIsLoading(false);
            setErrors({});
            const token = response.data.payload;
            localStorage.setItem("jwtToken", token);
            setAuthToken(token);
            const decoded = jwt_decode(token);
            setUser(decoded);
            history.push("/");
        })
        .catch(function(error) {
            setIsLoading(false);
            if (error.response.status === 401) {
                logoutUser(setUser)(history);
            } else if (error.response.data.messages) {
                setErrors(error.response.data.messages);
            }
        });
    };
};

const updatePassword = function(setUser, setErrors) {
    return function(userData, history, setIsLoading) {
        if (userData.password !== userData.confirmPassword) {
            setErrors({confirmPassword: "Confirm password must match password"});
        } else {
            setIsLoading(true);
            axios.put("/api/users/password", userData)
            .then(function(response) {
                setIsLoading(false);
                setErrors({});
                const token = response.data.payload;
                localStorage.setItem("jwtToken", token);
                setAuthToken(token);
                const decoded = jwt_decode(token);
                setUser(decoded);
                history.push("/");
            })
            .catch(function(error) {
                setIsLoading(false);
                if (error.response.status === 401) {
                    logoutUser(setUser)(history);
                } else if (error.response.data.messages) {
                    setErrors(error.response.data.messages);
                }
            });
        }
    }
}

const resetErrors = function(setErrors) {
    return function() {
        setErrors({});
    };
}

const useAuth = function() {
    const [user, setUser] = useState(null);
    const [errors, setErrors] = useState({});
    useEffect(() => {
        if (localStorage.getItem("jwtToken")) {
            const token = localStorage.getItem("jwtToken");
            setAuthToken(token);
            const decoded = jwt_decode(token);
            setUser(decoded);
        }
    }, []);
    return {
        user,
        errors,
        registerUser: registerUser(setUser, setErrors),
        loginUser: loginUser(setUser, setErrors),
        initiatePasswordReset: initiatePasswordReset(setErrors),
        loginUserViaEmail: loginUserViaEmail(setUser, setErrors),
        logoutUser: logoutUser(setUser),
        updateUserInfo: updateUserInfo(setUser, setErrors),
        updatePassword: updatePassword(setUser, setErrors),
        resetErrors: resetErrors(setErrors)
    };
}

export const Auth = function({children}) {
    const {user, errors, loginUser, initiatePasswordReset, loginUserViaEmail, logoutUser, registerUser, updateUserInfo, updatePassword, resetErrors} = useAuth();

    return (
        <AuthContext.Provider
            value={{user, errors, loginUser, initiatePasswordReset, loginUserViaEmail, logoutUser, registerUser, updateUserInfo, updatePassword, resetErrors}}>
            {children}
        </AuthContext.Provider>
    );
}