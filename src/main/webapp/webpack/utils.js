const path = require("path");

const _root = path.resolve(".");
const getRootDir = arg => {
  return path.join.apply(path, [_root].concat(Array.of(arg)));
};

module.exports = {
  getRootDir
};
