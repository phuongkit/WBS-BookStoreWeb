const path = require("path");
require('dotenv').config()
const HtmlWebpackPlugin = require("html-webpack-plugin");

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
        use: ["style-loader", "css-loader", "sass-loader"]
      }
    ]
  },
  resolve: {
    extensions: ['', '.js', '.jsx', '.css', '.sass', '.scss'],
    alias: {
      '~' : path.resolve(__dirname, './src'),
      '@Components': path.resolve(__dirname, './src/components'),
      },
  },
  mode: 'production',
  // Chứa các plugins sẽ cài đặt trong tương lai
  plugins: [
	new HtmlWebpackPlugin({
      template: "./public/index.html"
    })
  ],
  devServer: {
    host: 'localhost',
    port: process.env.PORT || 3000
  },
};