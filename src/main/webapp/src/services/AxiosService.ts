import axios from "axios";

class AxiosService {
  public static BASE_URL = "http://localhost:8081";
  private _axios;

  constructor() {
    this._axios = axios.create();
    this._axios.interceptors.request.use(this.onRequestSuccess);
    this._axios.interceptors.response.use(
      this.onResponseSuccess,
      this.onResponseError
    );
  }

  public static createInstance() {
    return new AxiosService();
  }

  private _setToken = authenticateResponse => {
    if (authenticateResponse.data) {
      const { token } = authenticateResponse.data;
      localStorage.setItem("Authorization", token);
    }
  };

  private onRequestSuccess = config => {
    const token = localStorage.getItem("Authorization");
    if (token) {
      config.headers.Authorization = token;
    }
    return config;
  };

  private onResponseSuccess = response => {
    return Promise.reject(response);
  };

  private onResponseError = error => error;

  public getAuthenticate = (
    login: string,
    password: string,
    rememberMe: boolean
  ) => {
    this._axios
      .post("api/authenticate", {
        login,
        password,
        rememberMe
      })
      .then(token => this._setToken(token))
      .catch(token => console.log(token));
  };

  public getRegister = (
    login: string,
    firstName: string,
    lastName: string,
    email: string,
    password: string
  ) => {
    this._axios.post("api/register", {
      login,
      firstName,
      lastName,
      email,
      password
    });
  };
}

export { AxiosService };
