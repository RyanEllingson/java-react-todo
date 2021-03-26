import React, {useState, useEffect} from "react";
import axios from "axios";
import jwt_decode from "jwt-decode";

import setAuthToken from "./setAuthToken";

export const AuthContext = React.createContext("auth");

export const logoutUser = function(setUser) {
    return function(history) {
        localStorage.removeItem("jwtToken");
        setAuthToken(null);
        setUser(null);
        history.push("/login");
    }
}

export const loginUser = function(setUser, setErrors) {
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

export const useAuth = function() {
    const [user, setUser] = useState(null);
    const [errors, setErrors] = useState({});
    useEffect(() => {
        if (localStorage.getItem("jwtToken")) {
            const token = localStorage.getItem("jwtToken");
            const decoded = jwt_decode(token);
            setUser(decoded);
            const currentTime = Date.now() / 1000;
            if (decoded.exp < currentTime) {
                logoutUser(setUser);
            }
        }
    }, []);
    return {
        user,
        errors,
        loginUser: loginUser(setUser, setErrors),
        logoutUser: logoutUser(setUser)
    };
}

export const Auth = function({children}) {
    const {user, errors, loginUser, logoutUser} = useAuth();

    return (
        <AuthContext.Provider
            value={{user, errors, loginUser, logoutUser}}>
            {children}
        </AuthContext.Provider>
    );
}