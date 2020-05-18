const http = require('http');

const hostname = '127.0.0.1';
const port = 3000;

const webpack = require('webpack');
const WebpackDevServer = require('webpack-dev-server');
const config = require('./webpack.dev.config');

const server = new WebpackDevServer(webpack(config), {
  publicPath: '/frontend/dist/',
  contentBase: 'frontend/dist/',
  hot: true,
  historyApiFallback: true,
  proxy: {
    "*": "http://localhost:8083"
  }
}).listen(3000, 'localhost', function (err, result) {
  if (err) {
    console.log(err);
  }

  console.log(`Server running at http://${hostname}:${port}/`);
});