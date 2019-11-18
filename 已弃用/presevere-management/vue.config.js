const webpack = require('webpack');

module.exports = {
  configureWebpack: {
    plugins: [
      new webpack.ProvidePlugin({
        $: 'jquery',
        jQuery: 'jquery',
        'window.jQuery': 'jquery',
        Popper: ['popper.js', 'default']
      })
    ]
  },
  devServer: {
    proxy: {
      '/app': {
        target: 'http://127.0.0.1:4200/',
        ws: true,
        changeOrigin: true,
        pathRewrite: {
          '^/app': '/'
        }
      }
    }
  }
};