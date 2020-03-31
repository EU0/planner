const utils = require("./utils");

const path = require("path");
const webpack = require("webpack");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const ForkTsCheckerWebpackPlugin = require("fork-ts-checker-webpack-plugin");
path.resolve(__dirname, "..");

const getTsLoaderRule = env => {
  const rules = [
    {
      loader: "thread-loader",
      options: {
        workers: require("os").cpus().length - 1
      }
    },
    {
      loader: "ts-loader",
      options: {
        transpileOnly: true,
        happyPackMode: true
      }
    }
  ];
  if (env === "development") {
    rules.unshift({
      loader: "react-hot-loader/webpack"
    });
  }
  return rules;
};

module.exports = options => ({
  entry: {
    main: utils.getRootDir("src/index.tsx")
  },
  output: {
    path: utils.getRootDir("dist"),
    filename: "[name].[hash].bundle.js",
    chunkFilename: "[name].[hash].chunk.js"
  },
  resolve: {
    extensions: [".js", ".jsx", ".png", "jpg", ".sass", ".tsx", ".ts"],
    modules: ["node_modules"]
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        enforce: "pre",
        loader: "source-map-loader"
      },
      {
        test: /\.tsx?$/,
        use: getTsLoaderRule(options.env),
        include: [utils.getRootDir("src")],
        exclude: [utils.getRootDir("node_modules")]
      },
      {
        test: /\.(jpe?g|png|gif|svg|woff2?|ttf|eot)$/i,
        loader: "file-loader",
        options: {
          digest: "hex",
          hash: "sha512",
          name: "content/[hash].[ext]"
        }
      },
      {
        test: /\.([jt])sx?$/,
        enforce: "pre",
        loader: "eslint-loader",
        exclude: [utils.getRootDir("node_modules")]
      }
    ]
  },
  optimization: {
    splitChunks: {
      cacheGroups: {
        commons: {
          test: /[\\/]node_modules[\\/]/,
          name: "vendors",
          chunks: "all"
        }
      }
    }
  },
  plugins: [
    new webpack.DefinePlugin({
      "process.env": {
        NODE_ENV: `'${options.env}'`
      }
    }),
    new ForkTsCheckerWebpackPlugin({ eslint: true }),
    new HtmlWebpackPlugin({
      template: "src/assets/html/index.html",
      chunksSortMode: "dependency",
      appMountId: "main",
      title: "Core",
      inject: "body"
    })
  ]
});
