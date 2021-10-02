const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
    app.use(
        '/api/*',
        createProxyMiddleware({
            target: 'http://localhost:8080',
            changeOrigin: true,
            secure: false,
            pathRewrite: {
                '^/api': ''
            },
        })
    );
    app.use(
        '/websocket/*',
        createProxyMiddleware({
            target: 'http://localhost:8080/ws',
            ws: true,
            changeOrigin: true,
            secure: false,
            pathRewrite: {
                '^/websocket': ''
            },
        })
    );
};