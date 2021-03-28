import React, {useState, useEffect, useContext} from "react";
import {Redirect} from "react-router-dom";
import axios from "axios";
import {AuthContext} from "../../auth/auth";

const Home = function() {
    // const [todos, setTodos] = useState([]);
    const {user} = useContext(AuthContext);

    const renderTodos = function() {
        console.log("sending todo get request");
        axios.get("/api/todos");
    }

    useEffect(() => {
        if (user) {
            renderTodos();
        }
        // eslint-disable-next-line
    }, []);

    return (
        user
        ? <h1>{`Welcome ${user.firstName} ${user.lastName}!`}</h1>
        : <Redirect to="/login" />
    );
};

export default Home;