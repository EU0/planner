import { Container } from "@material-ui/core";
import { homeStyles } from "./homeStyle";
import * as React from "react";

export const ProductMainLayout = () => {
  const classes = homeStyles();

  return (
    <section className={classes.root}>
      <Container className={classes.container}>
        <div className={classes.backdrop} />
        <div className={classes.background} />
      </Container>
    </section>
  );
};
