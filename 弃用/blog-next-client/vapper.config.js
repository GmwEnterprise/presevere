const fs = require('fs')

module.exports = {
  host: '127.0.0.1',
  template: fs.readFileSync('./public/ssr.html', 'utf-8'),
  clientEntry: './src/main.client.js',
  serverEntry: './src/main.server.js'
}