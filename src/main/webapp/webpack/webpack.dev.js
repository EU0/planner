const webpack = require("webpack");
const utils = require("./utils");
const webpackMerge = require("webpack-merge");
const BrowserSyncPlugin = require("browser-sync-webpack-plugin");
const FriendlyErrorsWebpackPlugin = require("friendly-errors-webpack-plugin");
const SimpleProgressWebpackPlugin = require("simple-progress-webpack-plugin");
const WebpackNotifierPlugin = require("webpack-notifier");
const path = require("path");

const commonConfig = require("./webpack.common.js");

const ENV = "development";

module.exports = options =>
  webpackMerge(commonConfig({ env: ENV }), {
    devtool: "cheap-module-source-map", // https://reactjs.org/docs/cross-origin-errors.html
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
      new BrowserSyncPlugin(
        {
          host: "localhost",
          port: 9000,
          proxy: {
            target: `http://localhost:8989`,
            ws: true,
            proxyOptions: {
              changeOrigin: false //pass the Host header to the backend unchanged  https://github.com/Browsersync/browser-sync/issues/430
            }
          },
          socket: {
            clients: {
              heartbeatTimeout: 60000
            }
          }
        },
        {
          reload: false
        }
      ),
      new webpack.NamedModulesPlugin(),
      new webpack.HotModuleReplacementPlugin(),
      new webpack.WatchIgnorePlugin([utils.getRootDir("src/test")]),
      new WebpackNotifierPlugin({
        title: "App"
      })
    ].filter(Boolean)
  });
