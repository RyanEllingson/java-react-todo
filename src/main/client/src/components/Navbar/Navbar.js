import React, {useContext} from "react";
import {useHistory, NavLink} from "react-router-dom";
import {AuthContext} from "../../auth/auth";

const Navbar = function() {
    const {user, logoutUser} = useContext(AuthContext);
    const history = useHistory();

    const handleLogout = function(event) {
        event.preventDefault();
        logoutUser(history);
    }

    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-light shadow mb-5">
            <div className="container-fluid">
                <NavLink className="navbar-brand" to="/">Java React Todo</NavLink>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav ms-auto">
                        {!user && <li className="nav-item">
                            <NavLink exact className="nav-link" activeClassName="active" to="/register">Register</NavLink>
                        </li>}
                        {!user && <li className="nav-item">
                            <NavLink exact className="nav-link" activeClassName="active" to="/login">Login</NavLink>
                        </li>}
                        {user && <li className="nav-item">
                            <NavLink exact className="nav-link" activeClassName="active" to="/">Home</NavLink>    
                        </li>}
                        {user && <li className="nav-item">
                            <NavLink exact className="nav-link" activeClassName="active" to="/update/info">Update Info</NavLink>    
                        </li>}
                        {user && <li className="nav-item">
                            <NavLink exact className="nav-link" activeClassName="active" to="/update/password">Change Password</NavLink>    
                        </li>}
                        {user && <li className="nav-item">
                            <a className="nav-link" onClick={handleLogout} href="/login">Logout</a>
                        </li>}
                    </ul>
                </div>
            </div>
        </nav>
    )
}

export default Navbar;