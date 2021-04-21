import React from "react";
import {BrowserRouter as Router, Switch, Route} from "react-router-dom";
import {Auth} from "./auth/auth";
import Navbar from "./components/Navbar";
import Home from "./components/Home";
import Register from "./components/Register";
import Login from "./components/Login";
import UpdateInfo from "./components/UpdateInfo";
import UpdatePassword from "./components/UpdatePassword";
import PasswordReset from "./components/PasswordReset";

function App() {
  return (
    <Auth>
      <Router>
        <Navbar />
        <Switch>
          <Route exact path="/" component={Home} />
          <Route exact path="/register" component={Register} />
          <Route exact path="/login" component={Login} />
          <Route exact path="/reset" component={PasswordReset} />
          <Route exact path="/update/info" component={UpdateInfo} />
          <Route exact path="/update/password" component={UpdatePassword} />
        </Switch>
      </Router>
    </Auth>
    
  );
}

export default App;
