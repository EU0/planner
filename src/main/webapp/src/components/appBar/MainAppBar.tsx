import React from "react";
import { AppBar, Button, Link, Toolbar } from "@material-ui/core";
import { useHeaderStyles } from "./headerStyles";

const MainAppBar = () => {
  const classes = useHeaderStyles();

  return (
    <div>
      <AppBar position="fixed" className={classes.appBar}>
        <Toolbar className={classes.toolbar}>
          <div className={classes.left} />
          <Link
            variant="h6"
            underline="none"
            color="inherit"
            className={classes.title}
            href="/"
          >
            {"onepirate"}
          </Link>
          <div className={classes.right}>
            <Button className={classes.rightLink}>{"Sign In"}</Button>
            <Button className={classes.rightLink}>{"Sign Up"}</Button>
          </div>
        </Toolbar>
      </AppBar>
      <div className={classes.placeholder} />
    </div>
  );
};

export { MainAppBar };
