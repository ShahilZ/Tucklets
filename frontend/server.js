const https = require('https');

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
    "*": { 
      target: 'https://localhost:8443',
      // Secure = false since we are currently using a self-signed certificate and want to avoid DEPTH_ZERO_SELF_SIGNED_CERT issue.
      secure: false }
  }
}).listen(port, 'localhost', function (err, result) {
  if (err) {
    console.log(err);
  }

  console.log(`Server running at http://${hostname}:${port}/`);
});