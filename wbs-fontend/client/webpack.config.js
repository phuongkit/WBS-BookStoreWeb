const path = require("path");
const dotenv = require('dotenv');
const HtmlWebpackPlugin = require("html-webpack-plugin");
const webpack = require("webpack");

dotenv.config();

module.exports = {
  entry: "./src/index.jsx", // Dẫn tới file index.js ta đã tạo
  output: {
    path: path.join(__dirname, "/build"), // Thư mục chứa file được build ra
    filename: "bundle.js" // Tên file được build ra
  },
  module: {
    rules: [
      {
        test: /\.js|\.jsx$/, // Sẽ sử dụng babel-loader cho những file .js
        exclude: /node_modules/, // Loại trừ thư mục node_modules
        use: ["babel-loader"]
      },
      {
        test: /\.css$|\.scss$/, // Sử dụng style-loader, css-loader cho file .css
        use: ["style-loader", "css-loader", "sass-loader", "postcss-loader"]
      }
    ]
  },
  resolve: {
    extensions: ['', '.js', '.jsx', '.css', '.sass', '.scss'],
    alias: {
      '~' : path.resolve(__dirname, './src'),
      '@Components': path.resolve(__dirname, './src/components'),
      process: "process/browser",
      },
  },
  mode: 'production',
  // Chứa các plugins sẽ cài đặt trong tương lai
  plugins: [
	new HtmlWebpackPlugin({
      template: "./public/index.html"
    }),
    new webpack.DefinePlugin({
      'process.env': JSON.stringify(process.env)
   }),
  ],
  devServer: {
    host: 'localhost',
    port: process.env.PORT || 3000
  },
};