import React, {useContext} from "react";
import {Redirect} from "react-router-dom";
import {AuthContext} from "../../auth/auth";

const Home = function() {
    const {user} = useContext(AuthContext);
    return (
        user
        ? <h1>{`Welcome ${user.firstName} ${user.lastName}!`}</h1>
        : <Redirect to="/login" />
    );
};

export default Home;