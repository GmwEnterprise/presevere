module.exports = {
  devServer: {
    port: 4396,
    proxy: {
      '/': {
        target: 'http://127.0.0.1:4399/',
        ws: true,
        changeOrigin: true,
        pathRewrite: {
          '^/': '/'
        }
      }
    }
  }
}