import React from "react";
import { Route, Router, Switch } from "react-router-dom";
import { RouterService } from "../services";
import { Home } from "../containers/Home";
import { SignIn } from "../containers/SignIn/SignIn";
import { SignUp } from "../containers/SignUp/SignUp";

const RouterContainer = () => (
  <Router history={RouterService.history}>
    <Switch>
      <Route exact path="/sign-in" component={SignIn} />
      <Route exact path="/sign-up" component={SignUp} />
      <Route exact path="/" component={Home} />
    </Switch>
  </Router>
);

export default RouterContainer;
