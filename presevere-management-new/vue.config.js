module.exports = {
  devServer: {
    proxy: {
      '/': {
        target: 'http://127.0.0.1:4399/',
        ws: true,
        changeOrigin: true,
        pathRewrite: {
          '^/app': '/'
        }
      }
    }
  }
}