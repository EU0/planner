import React from "react";
import { Provider } from "react-redux";
import { store } from "../store/store";
import DevTools from "../store/devTools";
import RouterContainer from "../routing/RouterContainer";
import { ThemeProvider } from "@material-ui/core/styles";
import theme from "./theme";

const Application = () => {
  return (
    <>
      <ThemeProvider theme={theme}>
        <Provider store={store}>
          {process.env.NODE_ENV === "development" && <DevTools />}
          <RouterContainer />
        </Provider>
      </ThemeProvider>
    </>
  );
};

export default Application;
