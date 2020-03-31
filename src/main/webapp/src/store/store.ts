import testReducer from "../reducers/testReducer";
import { applyMiddleware, compose, createStore } from "redux";
import thunkMiddleware from "redux-thunk";

import { loadingBarMiddleware } from "react-redux-loading-bar";
import DevTools from "./devTools";

const defaultMiddlewares = [thunkMiddleware, loadingBarMiddleware()];
const composedMiddlewares = middlewares =>
  process.env.NODE_ENV === "development"
    ? compose(
        applyMiddleware(...defaultMiddlewares, ...middlewares),
        DevTools.instrument()
      )
    : compose(
        applyMiddleware(...defaultMiddlewares, ...middlewares),
        DevTools.instrument()
      );

const initStore = (initialsState?, middlewares = []) =>
  createStore(testReducer, initialsState, composedMiddlewares(middlewares));

export const store = initStore();
