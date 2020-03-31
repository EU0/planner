const webpack = require("webpack");
const utils = require("./utils");
const webpackMerge = require("webpack-merge");
const SimpleProgressWebpackPlugin = require("simple-progress-webpack-plugin");
const WebpackNotifierPlugin = require("webpack-notifier");

const commonConfig = require("./webpack.common.js");

const ENV = "development";

module.exports = options =>
  webpackMerge(commonConfig({ env: ENV }), {
    devtool: "cheap-module-source-map",
    mode: ENV,
    module: {
      rules: [
        {
          test: /\.(sa|sc|c)ss$/,
          use: [
            "style-loader",
            "css-loader",
            "postcss-loader",
            {
              loader: "sass-loader"
            }
          ]
        }
      ]
    },
    devServer: {
      port: 8989,
      hot: true,
      contentBase: utils.getRootDir("dist"),
      proxy: [
        {
          context: ["/api"],
          target: `http://localhost:8081`,
          secure: false
        }
      ],
      watchOptions: {
        ignored: /node_modules/
      },
      historyApiFallback: true
    },
    plugins: [
      new SimpleProgressWebpackPlugin({
        format: "debug"
      }),
      new webpack.WatchIgnorePlugin([utils.getRootDir("src/test")]),
      new WebpackNotifierPlugin({
        title: "App"
      })
    ].filter(Boolean)
  });
