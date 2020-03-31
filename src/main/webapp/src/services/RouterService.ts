import { createBrowserHistory } from "history";

class RouterService {
  private readonly _history;

  constructor() {
    this._history = createBrowserHistory();
  }

  public static createRouterService() {
    return new RouterService();
  }

  get history() {
    return this._history;
  }

  get signUp() {
    return this._history.push("/sign-up");
  }

  get signIn() {
    return this._history.push("/sign-in");
  }
}

export { RouterService };
