import React from "react";
import {BrowserRouter as Router, Switch, Route} from "react-router-dom";
import {Auth} from "./auth/auth";
import Navbar from "./components/Navbar";
import Home from "./components/Home";
import Register from "./components/Register";
import Login from "./components/Login";


function App() {
  return (
    <Auth>
      <Router>
        <Navbar />
        <Switch>
          <Route exact path="/" component={Home} />
          <Route exact path="/register" component={Register} />
          <Route exact path="/login" component={Login} />
        </Switch>
      </Router>
    </Auth>
    
  );
}

export default App;
