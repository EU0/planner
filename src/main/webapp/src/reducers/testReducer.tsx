const initialState = {
  testCase: false
};

const testReducer = (state = initialState, action) => {
  if (action.type === "CHANGE") {
    return { ...state, testCase: action.payload };
  } else {
    return state;
  }
};

export default testReducer;
