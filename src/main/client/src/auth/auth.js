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
    return (function(userData, history) {
        axios.post("/api/login", userData)
        .then(function(response) {
            setErrors({})
            const token = response.data.payload;
            localStorage.setItem("jwtToken", token);
            setAuthToken(token);
            const decoded = jwt_decode(token);
            setUser(decoded);
            history.push("/");
        })
        .catch(function(error) {
            setErrors(error.response.data.messages);
        });
    });
};

const registerUser = function(setUser, setErrors) {
    return (function(userData, history) {
        if (userData.password !== userData.confirmPassword) {
            setErrors({confirmPassword: "Confirm password must match password"});
        } else {
            axios.post("/api/register", userData)
            .then(function(response) {
                setErrors({});
                const token = response.data.payload;
                localStorage.setItem("jwtToken", token);
                setAuthToken(token);
                const decoded = jwt_decode(token);
                setUser(decoded);
                history.push("/");
            })
            .catch(function(error) {
                setErrors(error.response.data.messages);
            });
        }
    });
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
            const decoded = jwt_decode(token);
            setUser(decoded);
        }
    }, []);
    return {
        user,
        errors,
        registerUser: registerUser(setUser, setErrors),
        loginUser: loginUser(setUser, setErrors),
        logoutUser: logoutUser(setUser),
        resetErrors: resetErrors(setErrors)
    };
}

export const Auth = function({children}) {
    const {user, errors, loginUser, logoutUser, registerUser, resetErrors} = useAuth();

    return (
        <AuthContext.Provider
            value={{user, errors, loginUser, logoutUser, registerUser, resetErrors}}>
            {children}
        </AuthContext.Provider>
    );
}